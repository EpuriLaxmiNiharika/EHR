package com.example.niharika.hospitalmanagementsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.HttpGet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;


public class Patient_after_loginActivity extends Activity {

    String URL = "http://ec2-52-66-43-37.ap-south-1.compute.amazonaws.com:8080/EHR-PatientHistoryService/api/patientHistory/Zf7NwmWcchWg4ztNFnWlaKrPFOlPFrtr";
    String[] json_response = {"userId", "password"};
    ListView listView;
    int[] ids = {R.id.patient_img_reason, R.id.patient_reason, R.id.patient_subreason};
    String keys[] = {"image", "reason", "subreason"};
    int images[] = {R.drawable.book_appointment, R.drawable.report, R.drawable.patient_medical_history, R.drawable.charts, R.drawable.alarm, R.drawable.feedback};
    String reason[] = {"Book an appointment", "View Appointments    ", "Medical history form ", "Patient Reports          ", "Set Remainders         ", "Feedback Form          "};
    String subreason[] = {"Find doctors and book appointment in convinient slot", "View past current and upcoming Appointments", "Details of recent Medical history form", "Patient Progress Reports", "Get alerts so that you never miss a dose", ""};
    int num_fields = images.length;
    ArrayList arrayList;
    String patient_id;
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_listview);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = settings.edit();

        listView = (ListView) findViewById(R.id.patient_lv);
        Intent i = getIntent();
        patient_id = i.getStringExtra("id");
      //  Toast.makeText(getApplicationContext(), patient_id, Toast.LENGTH_SHORT).show();
        arrayList = new ArrayList();

        for (int i1 = 0; i1 < num_fields; i1++) {
            HashMap hm = new HashMap();
            hm.put(keys[0], images[i1]);
            hm.put(keys[1], reason[i1]);
            hm.put(keys[2], subreason[i1]);
            arrayList.add(hm);
        }

        SimpleAdapter sa = new SimpleAdapter(this, arrayList, R.layout.patient_listview_item, keys, ids);

        listView.setAdapter(sa);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                HashMap hashMap = (HashMap) arrayList.get(position);
                ImageView imageView = (ImageView) view.findViewById(R.id.arrow);
                if (id == 0) { // Book Appointement

                    // callDoctorsAPI();

                 //   Intent i1 = new Intent(Patient_after_loginActivity.this, DoctorPatientSearch_Activity.class);
                    Intent i1 = new Intent(Patient_after_loginActivity.this, DisplaySpecialization.class);
                    i1.putExtra("id", patient_id);
                    startActivity(i1);
                }
                // http://ec2-52-66-43-37.ap-south-1.compute.amazonaws.com:8080/EHR-PatientHistoryService/api/patientHistory/Zf7NwmWcchWg4ztNFnWlaKrPFOlPFrtr
                if (id == 1) { //View Patient History

                    Intent i1 = new Intent(Patient_after_loginActivity.this, ViewAllAppointments.class);
                    i1.putExtra("id", patient_id);
                    startActivity(i1);
                }

                if (id == 2) { // Medical history form
                    GetRecentMed_History asyncTask = null;
                    try {
                        asyncTask = new GetRecentMed_History();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if(Build.VERSION.SDK_INT>=11){
                        BlockingQueue<Runnable> runnables = new LinkedBlockingDeque<>(100);
                        Executor executor= new ThreadPoolExecutor(60,100,10, TimeUnit.SECONDS,runnables);
                        asyncTask.executeOnExecutor(executor);

                    }
                    else{
                        asyncTask.execute();
                    }

                    Log.e("LATER","LATER");

                }

                if (id == 3) {

                    Intent i = new Intent(getApplicationContext(), DisplayProgressReports.class);
                    i.putExtra("id", patient_id);
                    startActivity(i);

                    //   Object[] params = {"niharika", "condition", "symptom1", false, "medicine", false, "alergies1,2,3", "2018-04-13",230,80,74};
                    //   AsyncTask_PastHistory asyncT =  new AsyncTask_PastHistory();
                    //  asyncT.execute(params);
                }

                if (id == 4) {
                    Intent i = new Intent(Patient_after_loginActivity.this, AlarmMe.class);
                    i.putExtra("id", patient_id);
                    startActivity(i);
                }

                if (id == 5) {
                    Intent i = new Intent(Patient_after_loginActivity.this, FeedBackFormActivity.class);
                    i.putExtra("id", patient_id);
                    startActivity(i);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        int back_pressed = settings.getInt("back_pressed", 0);
        editor.putInt("back_pressed", back_pressed + 1);
        editor.commit();
        System.out.println("back: " + (back_pressed + 1));

        //  Intent i = new Intent(Intent.ACTION_MAIN);
        if (back_pressed <= 2) {
            Intent i = new Intent(this, Patient_after_loginActivity.class);
            i.putExtra("id", patient_id);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
        } else {

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    public void download_file() {

        String keyName = "first_test2";
        String bucket_name = "patient-history";
        try {
            BasicAWSCredentials credentials = new BasicAWSCredentials("AKIAIEJSVFYSVM6S6WMA", "PhN9yIniM/dkacTU6dHHn/PxZea7GiDwgJ2i1Ak+");

            System.setProperty(SDKGlobalConfiguration.ENFORCE_S3_SIGV4_SYSTEM_PROPERTY, "true");

            AmazonS3Client s3Client = new AmazonS3Client(credentials);

            s3Client.setEndpoint("s3-ap-south-1.amazonaws.com");

            TransferUtility transferUtility = new TransferUtility(s3Client, this);

            S3Object object = s3Client.getObject(new GetObjectRequest("patient-history", keyName));
            //  Toast.makeText(this,"aksalksaa: "+object,Toast.LENGTH_SHORT).show();

            String file_name = object.getKey();
            File f = Environment.getExternalStorageDirectory();
            String path = f.getAbsolutePath();
            //adding file name to Path
            path = path + "/" + file_name;

            File new_file = new File(path);

            if (object != null) {
                System.out.println("Object not null");
                System.out.println("Object not null");
                System.out.println("Object not null");
                System.out.println("Object not null");
                System.out.println("Object not null");

                System.out.println("Object content");
                System.out.println(object.getObjectContent());

                System.out.println("Object meta data");
                System.out.println(object.getObjectMetadata());

         //       Toast.makeText(this, "object content: " + object.getObjectContent(), Toast.LENGTH_SHORT).show();


            } else {
                System.out.println("Object  null");
                System.out.println("Object  null");
                System.out.println("Object  null");
                System.out.println("Object  null");
                System.out.println("Object  null");
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e);
            System.out.println(e);
            System.out.println(e);
        }


    }


    public void logoutUser(View v) {
        Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public class GetRecentMed_History extends AsyncTask<String, String, Void> {
String url = "";

        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        final String ur_1 = "http://13.127.188.57:8080/PatientHealthHistoryAPI/webapi/patient_history/patient/" + patient_id;

        public GetRecentMed_History() throws MalformedURLException {


        }

        private ProgressDialog progressDialog = new ProgressDialog(Patient_after_loginActivity.this);
        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    Patient_after_loginActivity.GetRecentMed_History.this.cancel(true);
                }
            });
        }

        //{"doc_id":"1","name":"Dr. John","hospital_id":["123"],"hospital_name":["Mayuri hospital"],"designation":"MD","specialization_id":"1","specialization_name":"ENT","gender":null,"year_join":2010}
        @Override
        protected Void doInBackground(String... strings) {
            try {
                // Set up HTTP post

                HttpClient httpClient = new DefaultHttpClient();

                HttpGet httpGet = new HttpGet(ur_1);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();

                // Read content & Log
                inputStream = httpEntity.getContent();
            } catch (UnsupportedEncodingException e1) {
                Log.e("ENCODING EXCEPTION", e1.toString());
                e1.printStackTrace();
            } catch (ClientProtocolException e2) {
                Log.e("ClientProtocolException", e2.toString());
                e2.printStackTrace();
            } catch (IllegalStateException e3) {
                Log.e("IllegalStateException", e3.toString());
                e3.printStackTrace();
            } catch (IOException e4) {
                Log.e("IOException", e4.toString());
                e4.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");

                    Log.e("HEYOUTPUT: ", result);
                }

                inputStream.close();
                result = sBuilder.toString();

                JSONObject jsonObject = new JSONObject(result);
                 url = jsonObject.getString("prescription_file_url");

            } catch (Exception e) {
                Log.e("ERROR....", "Error converting result " + e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            int i;
            try {
                Log.e("OUT:", result);
                JSONArray jArray = new JSONArray(result);
                Log.e("lEN", jArray.length() + "");
                //*JSONArray jArray =new JSONArray();

                //  jArray = new JSONArray(result);

                if (jArray == null) {
                    Log.e("I am null", "Null");
                    this.progressDialog.dismiss();
                    return;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent i1 = new Intent(getApplicationContext(), DownloadPdf.class);
            i1.putExtra("id", patient_id);
            i1.putExtra("url",url);
            startActivity(i1);
        }
    }
}

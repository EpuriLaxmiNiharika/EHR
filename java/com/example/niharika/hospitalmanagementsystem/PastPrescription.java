package com.example.niharika.hospitalmanagementsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.loopj.android.http.HttpGet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class PastPrescription extends Activity {

    WebView webView;
    String url1 = "";
    String doc_id = "";

    String patient_id = "";
    ArrayList<String> dateof_appointment = new ArrayList<String>();
    ArrayList<String> doctors_id = new ArrayList<String>();
    ArrayList<String> doctors_name = new ArrayList<String>();
    ArrayList<String> doc_hospital_id = new ArrayList<String>();
    ArrayList<String> doc_hospital_name = new ArrayList<String>();
    ArrayList<String> patient_id_al = new ArrayList<String>();
    ArrayList<String> reasonof_appointment = new ArrayList<String>();
    ArrayList<Integer> slot_chosen_al= new ArrayList<Integer>();
    ArrayList<String> prescription_url = new ArrayList<>();
    String pre_url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();

        patient_id = i.getStringExtra("id");
        url1 = i.getStringExtra("url");
        doc_id = i.getStringExtra("doctor_id");

      //  Toast.makeText(this,"DOCTOR_ID:"+doc_id,Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_past_prescription);
        webView = (WebView) findViewById(R.id.web_view_past_pdf);

        String file_name = url1;
        //date_of_visit + patient_id + doctor_id;

  /*      String url = "https://s3.ap-south-1.amazonaws.com/patient-history/" + file_name;

        //  String doc="<iframe src='http://docs.google.com/viewer?url=+https://s3.ap-south-1.amazonaws.com/patient-history/06688454-f726-4eba-8cb6-46e823410585+' width='100%' height='100%' style='border: none;'></iframe>";

        url = "http://docs.google.com/gview?embedded=true&url=" + url;
        String doc = "<iframe src=" + url + " width='100%' height='100%' style='border: none;'></iframe>";
        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        //webView.loadUrl(url);
        webView.loadData(doc, "text/html", "UTF-8");*/


        try {

            getPast_DoctorAppointmentsAsyncTask asyncTask = new getPast_DoctorAppointmentsAsyncTask();
            if(Build.VERSION.SDK_INT>=11){
                BlockingQueue<Runnable> runnables = new LinkedBlockingDeque<>(100);
                Executor executor= new ThreadPoolExecutor(60,100,10, TimeUnit.SECONDS,runnables);
                asyncTask.executeOnExecutor(executor);

            }
            else{
                asyncTask.execute();
            }

            Log.e("LATER","LATER");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, DisplayPastDayAppointments.class);
        i.putExtra("id", patient_id);
        startActivity(i);
    }


    public class getPast_DoctorAppointmentsAsyncTask extends AsyncTask<String, String, Void> {


   /*     Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD");
        String cur_date = dateFormat.format(calendar);*/

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-DD");
        String today_date = mdformat.format(calendar.getTime());
        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        String ur_1= "http://13.127.188.57:8080/"+"rest_api/webapi/appointments/patient_past_appointments?patient_id="+patient_id+"&current_date="+today_date +"&doc_id="+doc_id;


///patient_pastday_appointments?patient_id=1234&past_date=2017-12-31
        //  String[] doctors_name;

        public getPast_DoctorAppointmentsAsyncTask() {
            // Log.e("ENCODING EXCEPTION", cur_date);

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            if(month<9){
                if(day<10)
                {
                    today_date = year + "-0"+(month+1) + "-0"+day;
                }
                else{
                    today_date = year + "-0"+(month+1) +"-"+day;
                }
            }
            else{
                if(day<10){
                    today_date = year +"-"+(month+1) + "-0"+day;
                }
                else{
                    today_date = year +"-" +(month+1) +"-"+day+"";

                }
            }
            Log.e("Date",today_date);
            ur_1= "http://13.127.188.57:8080/"+"rest_api/webapi/appointments/patient_past_appointments?patient_id="+patient_id+"&current_date="+today_date +"&doc_id="+doc_id;
        }

        // HttpConnection connection;
        // HttpClient httpClient;

        private ProgressDialog progressDialog = new ProgressDialog(PastPrescription.this);
        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    getPast_DoctorAppointmentsAsyncTask.this.cancel(true);
                }
            });
        }

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

                Log.e("In new page","In new page");
                Log.e("In new page","In new page");
                Log.e("In new page","In new page");
                Log.e("In new page","In new page");
                Log.e("In new page","In new page");

/*
                {
                    "date_appoint": "2016-10-10",
                        "doctor_id": "1",
                        "doctor_name": "Niharika",
                        "hospital_id": "123",
                        "hospital_name": "Mayuri hospital",
                        "patient_id": "1",
                        "reason_appointment": "ear pain",
                        "slot_chosen": 4
                }
*/

                for (i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    String date_appointment = jObject.getString("date_appoint");

                    String doctor_id = jObject.getString("doctor_id");

                    String doctor_name = jObject.getString("doctor_name");

                    String hospital_id = jObject.getString("hospital_id");

                    String hospital_name = jObject.getString("hospital_name");

                    String patient_id = jObject.getString("patient_id");

                    String reason_appointment = jObject.getString("reason_appointment");

                    String prescription = jObject.getString("prescription");

                    int slot_chosen = jObject.getInt("slot_chosen");


                    dateof_appointment.add(date_appointment);

                    Log.e("Date appointment", date_appointment);

                    doctors_id.add(doctor_id);
                    Log.e("Doctor ID", doctor_id);


                    doctors_name.add(doctor_name);
                    Log.e("Doc Name", doctor_name);


                    doc_hospital_id.add(hospital_id);
                    Log.e("Hospital_ID ", hospital_id);

                    doc_hospital_name.add(hospital_name);

                    Log.e("Hospital_Name ", hospital_name);

                    patient_id_al.add(patient_id);

                    Log.e("Patient id ", patient_id);

                    reasonof_appointment.add(reason_appointment);

                    Log.e("Reason of appointment", reason_appointment);

                    prescription_url.add(prescription);
                    pre_url = prescription;
                    Log.e("Prescription URL", prescription);

                    slot_chosen_al.add(slot_chosen);

                    Log.e("Slot chosen", slot_chosen + "");

                } // End Loop*/

                this.progressDialog.dismiss();


                String file_name = url1;
                //date_of_visit + patient_id + doctor_id;

                String url1 = "https://s3.ap-south-1.amazonaws.com/patient-history/" + pre_url;
             //   Toast.makeText(getApplicationContext(),url1,Toast.LENGTH_SHORT).show();

                //  String doc="<iframe src='http://docs.google.com/viewer?url=+https://s3.ap-south-1.amazonaws.com/patient-history/06688454-f726-4eba-8cb6-46e823410585+' width='100%' height='100%' style='border: none;'></iframe>";

                url1 = "http://docs.google.com/gview?embedded=true&url=" + url1;
                String doc = "<iframe src=" + url1 + " width='100%' height='100%' style='border: none;'></iframe>";
                webView.getSettings().setJavaScriptEnabled(true);
                WebSettings webSettings = webView.getSettings();
                webSettings.setAllowFileAccess(true);
                webSettings.setAllowFileAccessFromFileURLs(true);
                webSettings.setAllowUniversalAccessFromFileURLs(true);
                //webView.loadUrl(url);
                webView.loadData(doc, "text/html", "UTF-8");


            } catch (Exception e) {
                Log.e("JSONException", "Error: " + e.toString());
            } // catch (JSONException e)
        }
    }

}

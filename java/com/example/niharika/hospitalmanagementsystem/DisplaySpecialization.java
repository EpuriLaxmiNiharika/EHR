package com.example.niharika.hospitalmanagementsystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
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
import java.net.URL;
import java.util.ArrayList;
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

public class DisplaySpecialization extends AppCompatActivity {

    ArrayList<String> spec_id = new ArrayList<String>();
    ArrayList<Integer> spec_name= new ArrayList<Integer>();
    ListView lv;

    ArrayList<HashMap<String,Object>>specializations = new ArrayList<HashMap<String,Object>>();

    ArrayList<String> specialization_ids = new ArrayList<String>();
    ArrayList<String> specialization_names = new ArrayList<String>();

    int[] keys= {R.id.list_item_specialization};

    String[] ids={"name"};
    String patient_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_specialization);
        lv = (ListView)findViewById(R.id.show_specialization);

        Intent i = getIntent();
        patient_id = i.getStringExtra("id");


        try {
            GetSpecializationAsyncTask asyncTask =  new GetSpecializationAsyncTask();

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

    public class GetSpecializationAsyncTask extends AsyncTask<String, String, Void> {


        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        final String ur_1 = "http://13.127.188.57:8080/SpecializationAPI/webapi/specializations";

        java.net.URL uri = new URL(ur_1);

        public GetSpecializationAsyncTask() throws MalformedURLException {


        }

        // HttpConnection connection;
        // HttpClient httpClient;

        private ProgressDialog progressDialog = new ProgressDialog(DisplaySpecialization.this);
        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    GetSpecializationAsyncTask.this.cancel(true);
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
            }
            catch (Exception e){
                e.printStackTrace();
            }

            try {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");

                    Log.e("HEYOUTPUT: ",result);
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
                Log.e("OUT:",result);
                JSONArray jArray = new JSONArray(result);
                Log.e("lEN",jArray.length()+"");
                //*JSONArray jArray =new JSONArray();

                //  jArray = new JSONArray(result);

                if(jArray==null) {
                    Log.e("I am null","Null");
                    this.progressDialog.dismiss();
                    return;
                }




                for(i=0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);



                    String specialization_id = jObject.getString("specialization_id");

                    String specialization_name = jObject.getString("specialization_name");


                    specialization_ids.add(specialization_id);

                    Log.e("Specialization ID",specialization_id);

                    specialization_names.add(specialization_name);

                    Log.e("Specialization Name",specialization_name);



                }



                this.progressDialog.dismiss();

                for(int i1 = 0; i1<jArray.length();i1++){

                    HashMap hm = new HashMap();
                    hm.put(ids[0],specialization_names.get(i1));
                    specializations.add(hm);
                }

                // Adding items to listview

                SimpleAdapter sa = new SimpleAdapter(
                        DisplaySpecialization.this,specializations, R.layout.listitem_specialization, ids,keys);
                lv.setAdapter(sa);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap hm = specializations.get(position);
                        String spec_id = specialization_ids.get(position);
                        Intent i1 = new Intent(getApplicationContext(),SearchDoctor.class);
                   //     Toast.makeText(getApplicationContext(),patient_id,Toast.LENGTH_SHORT).show();
                        i1.putExtra("id",patient_id);
                        i1.putExtra("spec_id",spec_id);
                        //http://13.127.188.57:8080/doctordetails/webapi/doctors/specialization_id/
                        startActivity(i1);
                    }
                });

            } catch (Exception e) {
                Log.e("JSONException", "Error: " + e.toString());
            } // catch (JSONException e)
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this,Patient_after_loginActivity.class);
        i.putExtra("id",patient_id);
        startActivity(i);
    }
}

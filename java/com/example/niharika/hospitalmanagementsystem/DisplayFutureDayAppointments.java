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
import java.text.DateFormat;
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

public class DisplayFutureDayAppointments extends AppCompatActivity {

    String patient_id = "";
    ListView lv;

    ArrayList<String> dateof_appointment = new ArrayList<String>();
    ArrayList<String> doctors_id = new ArrayList<String>();
    ArrayList<String> doctors_name = new ArrayList<String>();
    ArrayList<String> doc_hospital_id = new ArrayList<String>();
    ArrayList<String> doc_hospital_name = new ArrayList<String>();
    ArrayList<String> patient_id_al = new ArrayList<String>();
    ArrayList<String> reasonof_appointment = new ArrayList<String>();
    ArrayList<Integer> slot_chosen_al= new ArrayList<Integer>();
    ArrayList<String> prescription_url = new ArrayList<>();

    ArrayList<HashMap<String,Object>>appointemnt_list_items = new ArrayList<HashMap<String,Object>>();


    int[] keys= {R.id.lv_Future_date_booked,R.id.lv_Future_hosp_name,
            R.id.lv_Future_doc_name,R.id.lv_Future_reason_visit};

    String[] ids={"date","hosp_name","doc_name","reason"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_future_day_appointments);
        patient_id = getIntent().getStringExtra("id");
        lv = (ListView)findViewById(R.id.lv_future_day_appoint);
        try {

            getFutureAppointmentsAsyncTask asyncTask = new getFutureAppointmentsAsyncTask();
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

    public class getFutureAppointmentsAsyncTask extends AsyncTask<String, String, Void> {


   /*     Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD");
        String cur_date = dateFormat.format(calendar);*/

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-DD");
        String today_date = mdformat.format(calendar.getTime());
        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        String ur_1= "http://13.127.188.57:8080/"+"rest_api/webapi/appointments/patient_future_appointments?patient_id="+patient_id+"&current_date="+today_date;
        ;
///patient_Futureday_appointments?patient_id=1234&Future_date=2017-12-31
        //  String[] doctors_name;

        public getFutureAppointmentsAsyncTask() throws MalformedURLException {
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
            ur_1 = "http://13.127.188.57:8080/"+"rest_api/webapi/appointments/patient_future_appointments?patient_id="+patient_id+"&current_date="+today_date;
        }

        // HttpConnection connection;
        // HttpClient httpClient;

        private ProgressDialog progressDialog = new ProgressDialog(DisplayFutureDayAppointments.this);
        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    getFutureAppointmentsAsyncTask.this.cancel(true);
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

                    Log.e("Prescription URL", prescription);

                    slot_chosen_al.add(slot_chosen);

                    Log.e("Slot chosen", slot_chosen + "");

                } // End Loop*/

                this.progressDialog.dismiss();


                for (int i1 = 0; i1 < jArray.length(); i1++) {
                    HashMap hm = new HashMap();

                    //////////
                    int slot = slot_chosen_al.get(i1);
                    String slot_str = "";
                    if(slot==1){
                        slot_str = "  10:00-11:00AM";
                    }
                    else if(slot==2){
                        slot_str = "  11:00-12:00PM";
                    }
                    else if(slot==3){
                        slot_str = "  1:30-3:30PM";
                    }
                    else if(slot==4){
                        slot_str = "  3:30-5:00PM";
                    }
                    else if(slot==5){
                        slot_str = "  5:30-7PM";
                    }
                    else if(slot==6){
                        slot_str = "  7:00-8:30PM";
                    }
                    else{
                        slot_str = "  8:30-10PM";
                    }
                    ///////
                    hm.put(ids[0], dateof_appointment.get(i1)+"\n"+""+slot_str);
                    hm.put(ids[1], doc_hospital_name.get(i1));
                    hm.put(ids[2], doctors_name.get(i1));
                    hm.put(ids[3], reasonof_appointment.get(i1));

                    appointemnt_list_items.add(hm);
                }

                // Adding items to listview

                SimpleAdapter sa = new SimpleAdapter(
                        DisplayFutureDayAppointments.this, appointemnt_list_items, R.layout.list_item_view_future_day_appoint, ids, keys);
                lv.setAdapter(sa);

            } catch (Exception e) {
                Log.e("JSONException", "Error: " + e.toString());
            } // catch (JSONException e)
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), ViewAllAppointments.class);
        i.putExtra("id", patient_id);
        startActivity(i);
    }

}

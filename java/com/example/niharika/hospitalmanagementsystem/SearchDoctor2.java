
package com.example.niharika.hospitalmanagementsystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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


public class SearchDoctor2 extends AppCompatActivity {

    // List view
    private ListView lv;
    final String URL = "http://52.66.43.37:8080/doctordetails/webapi/doctors";

    // Listview Adapter
   /* String doctors_id[];
    String doctors_name[];
    String designation[];
    String address[];
    String location[];
    String degrees[];
    String years_exp[];
    String reviews[]; */

   ArrayList<String> doctors_id = new ArrayList<String>();
    ArrayList<String> doctors_name = new ArrayList<String>();
    ArrayList<String> doc_specialization = new ArrayList<String>();
    ArrayList<String> doc_hospital = new ArrayList<String>();
    ArrayList<String> doctors_location = new ArrayList<String>();
    ArrayList<String> doctors_degrees = new ArrayList<String>();
    ArrayList<Integer> doctors_years_exp= new ArrayList<Integer>();
    ArrayList<Double> doctors_reviews = new ArrayList<Double>();

    // Search EditText
    EditText inputSearch;
    String patient_id;

    private static final String TAG_USER = "user";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";

//    JSONArray user = null;

    // ArrayList for Listview
    ArrayList<HashMap<String,Object>>doctors_al;
    ArrayList<HashMap<String,Object>>search_res;
   /* int[] keys = {R.id.lv_doc_id,R.id.lv_docname,R.id.lv_doc_specialization,
            R.id.lv_doc_hospital, R.id.lv_doc_hosp_location,R.id.lv_doc_degrees,
            R.id.lv_doc_exp,R.id.lv_doc_rating};
            */
    //String[] ids={"id","name","designation","address","location","degrees","years_exp","reviews"};

    int[] keys= {R.id.lv_docname,R.id.lv_doc_specialization,
            R.id.lv_doc_hospital,R.id.lv_doc_degrees,
            R.id.lv_doc_exp};

    String[] ids={"name","designation","address","location","degrees","years_exp","reviews"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_doctors);
        lv = (ListView) findViewById(R.id.doctors_lv);
        doctors_al = new ArrayList<HashMap<String, Object>>();
        search_res = new ArrayList<HashMap<String, Object>>();
        Intent i = getIntent();
        patient_id = i.getStringExtra("id");
        // Listview Data

        final String URL = "http://52.66.43.37:8080/doctordetails/webapi/doctors";

        GetDoctorsAsyncTask asyncTask = null;
        try {
            asyncTask = new GetDoctorsAsyncTask();
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

    public class GetDoctorsAsyncTask extends AsyncTask<String, String, Void>{


        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        final String ur_1 = "http://52.66.43.37:8080/doctordetails/webapi/doctors";
        java.net.URL uri = new URL(ur_1);
      //  String[] doctors_name;

        public GetDoctorsAsyncTask() throws MalformedURLException {

        }
        // HttpConnection connection;
        // HttpClient httpClient;

        private ProgressDialog progressDialog = new ProgressDialog(SearchDoctor2.this);
        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    GetDoctorsAsyncTask.this.cancel(true);
                }
            });
        }


        @Override
        protected Void doInBackground(String... strings) {
            try {
                // Set up HTTP post

                // HttpClient is more then less deprecated. Need to change to URLConnection
                HttpClient httpClient = new DefaultHttpClient();

                HttpGet httpGet = new HttpGet(ur_1);
              //  HttpPost httpPost = new HttpPost(ur_1);
                //httpPost.setEntity(new UrlEncodedFormEntity(param));
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
            // Convert response to string using String Builder
            try {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                 //   result = line + result;
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

             //   designation = new String[jArray.length()];

                for(i=0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    String id = jObject.getString("doc_id");

                    String name = jObject.getString("name");

                    JSONArray hospital_id  = jObject.getJSONArray("hospital_id");

                    JSONArray hospital_name = jObject.getJSONArray("hospital_name");

                    String designation  = jObject.getString("designation");

                    String specialization = jObject.getString("specialization");

                    Integer years_exp = jObject.getInt("year_join");


                    doctors_id.add(id);
                    Log.e("Doc id",id);

                    doctors_name.add(name);
                    Log.e("Doc Name",name);

                    String hosp_name = hospital_name.getString(0);

                    doc_hospital.add(hosp_name);
                    Log.e("Hospital_Name ",hosp_name);

                    doctors_degrees.add(designation);

                    Log.e("degrees ",designation);

                    doc_specialization.add(specialization);

                    Log.e("Specialization ",specialization);


                    doctors_location.add(hosp_name);


                    Log.e("Doctors_location ",hosp_name);

                    doctors_years_exp.add(years_exp);

                    Log.e("Years Exp ",years_exp+"");

                    /*
                   {R.id.lv_docname,R.id.lv_doc_specialization,
            R.id.lv_doc_hospital, R.id.lv_doc_hosp_location,R.id.lv_doc_degrees,
            R.id.lv_doc_exp,R.id.lv_doc_rating}

                    * */

                } // End Loop*/
               this.progressDialog.dismiss();

                inputSearch = (EditText) findViewById(R.id.search_docs);
                for(int i1 = 0; i1<jArray.length();i1++){
                    HashMap hm = new HashMap();
                    hm.put(ids[0],doctors_name.get(i1));
                    hm.put(ids[1],doc_specialization.get(i1));
                    hm.put(ids[2],doc_hospital.get(i1));
                    hm.put(ids[3],doctors_location.get(i1));
                    hm.put(ids[4],doctors_degrees.get(i1));
                    hm.put(ids[5],doctors_years_exp.get(i1));
                    doctors_al.add(hm);
                    search_res.add(hm);
                }

                // Adding items to listview
                SimpleAdapter sa = new SimpleAdapter(
                        SearchDoctor2.this,doctors_al, R.layout.viewdoctor_listitem, ids,keys);
                lv.setAdapter(sa);

                inputSearch.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                        int textlength = inputSearch.getText().length();
                        String searchString= inputSearch.getText().toString();
                        search_res.clear();
                        String attr = null;

                        for (int i = 0; i < doctors_al.size(); i++)
                        {

                            attr = doctors_al.get(i).get(ids[0]).toString().trim();

                            Log.i("attr", attr+"");

                            if (textlength  <= attr.length())
                            {

                                if (searchString.equalsIgnoreCase(attr.substring(0,textlength)))
                                {
                                    search_res.add(doctors_al.get(i));
                                }
                            }
                        }

                        SimpleAdapter simple = new SimpleAdapter(SearchDoctor2.this, search_res, R.layout.viewdoctor_listitem,
                                ids,keys);

                        lv.setAdapter(simple);
                    }


                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                  int arg3) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub

                    }
                });

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap hm = search_res.get(position);
                        String doctor_name = hm.get(ids[0]).toString().trim();
                        Intent i = new Intent(getApplicationContext(),BookAppointmentActivity.class);
                        i.putExtra("id",patient_id);
                        i.putExtra("doctor",doctor_name);
                        i.putExtra("designation",hm.get(ids[1]).toString().trim());
                        startActivity(i);
                    }
                });

            } catch (Exception e) {
                Log.e("JSONException", "Error: " + e.toString());
            } // catch (JSONException e)
        }
    }

}
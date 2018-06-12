
package com.example.niharika.hospitalmanagementsystem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.loopj.android.http.HttpGet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;


public class SearchDoctor extends AppCompatActivity {

    // List view
    private ListView lv;
    final String URL = "http://13.127.188.57:8080/doctordetails/webapi/doctors";
    final String URL_ratings = "http://52.66.43.37:8080/FeedBackAPI/webapi/feedback/doctor";

    //{"doc_id":"1","name":"Dr. John","hospital_id":["123"],
// "hospital_name":["Mayuri hospital"],"designation":"MD",
// "specialization_id":"1","specialization_name":"ENT","gender":null,"year_join":2010}

    ArrayList<String> doctors_id = new ArrayList<String>();
    ArrayList<String> doctors_name = new ArrayList<String>();
    ArrayList<String> doc_hospital_id = new ArrayList<String>();
    ArrayList<String> doc_hospital_name = new ArrayList<String>();
    ArrayList<String> doc_designation = new ArrayList<String>();
    ArrayList<String> doc_specialization_id = new ArrayList<String>();
    ArrayList<String> doc_specialization_name = new ArrayList<String>();
    ArrayList<Integer> doctors_years_exp= new ArrayList<Integer>();

    ArrayList<String> search_doc_ids = new ArrayList<String>();
    ArrayList<String> full_doc_ids = new ArrayList<String>();

    // Search EditText
    EditText inputSearch;
    String patient_id;
String specialization_id = "";
    private static final String TAG_USER = "user";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";

    ArrayList<HashMap<String,Object>>doctors_al;
    ArrayList<HashMap<String,Object>>search_res;


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

        specialization_id = i.getStringExtra("spec_id");

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

     //   final String ur_1 = "http://13.127.188.57:8080/doctordetails/webapi/doctors";

        final String ur_1 =   "http://13.127.188.57:8080/doctordetails/webapi/doctors/specialization_id/"+specialization_id;

        java.net.URL uri = new URL(ur_1);
      //  String[] doctors_name;

        public GetDoctorsAsyncTask() throws MalformedURLException {


        }

        // HttpConnection connection;
        // HttpClient httpClient;

        private ProgressDialog progressDialog = new ProgressDialog(SearchDoctor.this);
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



//{"doc_id":"1","name":"Dr. John","hospital_id":["123"],
// "hospital_name":["Mayuri hospital"],"designation":"MD",
// "specialization_id":"1","specialization_name":"ENT","gender":null,"year_join":2010}
                for(i=0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    String id = jObject.getString("doc_id");

                    String name = jObject.getString("name");

                    JSONArray hosp_id = jObject.getJSONArray("hospital_id");

                    JSONArray hospital_name = jObject.getJSONArray("hospital_name");

                    String designation  = jObject.getString("designation");

                    String specialization_id = jObject.getString("specialization_id");

                    String specialization_name = jObject.getString("specialization_name");

                    Integer years_exp = jObject.getInt("year_join");

                      /*

                 ArrayList<String> doctors_id = new ArrayList<String>();
    ArrayList<String> doctors_name = new ArrayList<String>();
    ArrayList<String> doc_hospital_id = new ArrayList<String>();
    ArrayList<String> doc_hospital_name = new ArrayList<String>();
    ArrayList<String> doc_designation = new ArrayList<String>();
    ArrayList<String> doc_specialization_id = new ArrayList<String>();
    ArrayList<String> doc_specialization_name = new ArrayList<String>();
    ArrayList<Integer> doctors_years_exp= new ArrayList<Integer>();

                * */

                    doctors_id.add(id);
                    Log.e("Doc id",id);

                    doctors_name.add(name);
                    Log.e("Doc Name",name);

                    String hosp_name = hospital_name.getString(0);

                    doc_hospital_id.add((String)hosp_id.get(0));
                    Log.e("Hospital_ID ",(String)hosp_id.get(0));

                    doc_hospital_name.add((String)hospital_name.get(0));

                    Log.e("Hospital_Name ",(String)hospital_name.get(0));

                    doc_designation.add(designation);

                    Log.e("designation ",designation);

                    doc_specialization_id.add(specialization_id);

                    Log.e("Specialization ID",specialization_id);

                    doc_specialization_name.add(specialization_name);

                    Log.e("Specialization Name",specialization_name);

                    doctors_years_exp.add(years_exp);

                    Log.e("Years Exp ",years_exp+"");


                } // End Loop*/



               this.progressDialog.dismiss();

                inputSearch = (EditText) findViewById(R.id.search_docs);
                for(int i1 = 0; i1<jArray.length();i1++){
                    HashMap hm = new HashMap();
                    hm.put(ids[0],doctors_name.get(i1));
                    hm.put(ids[1],doc_specialization_name.get(i1));
                    hm.put(ids[2],doc_hospital_name.get(i1));
                    hm.put(ids[3],doc_designation.get(i1));
                    hm.put(ids[4],doctors_years_exp.get(i1));
                    doctors_al.add(hm);
                    search_res.add(hm);
                    full_doc_ids.add(doctors_id.get(i1));
                    search_doc_ids.add(doctors_id.get(i1));
                }

                // Adding items to listview

                SimpleAdapter sa = new SimpleAdapter(
                        SearchDoctor.this,doctors_al, R.layout.viewdoctor_listitem, ids,keys);
                lv.setAdapter(sa);

                inputSearch.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                        int textlength = inputSearch.getText().length();
                        String searchString= inputSearch.getText().toString();
                        search_res.clear();
                        search_doc_ids.clear();
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
                                    search_doc_ids.add(full_doc_ids.get(i));
                                }
                            }
                        }

                        SimpleAdapter simple = new SimpleAdapter(SearchDoctor.this, search_res, R.layout.viewdoctor_listitem,
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
                        String doctor_id =full_doc_ids.get(position);
                        Intent i1 = new Intent(getApplicationContext(),BookAppointmentActivity.class);
                 //       Toast.makeText(getApplicationContext(),patient_id,Toast.LENGTH_SHORT).show();
                        i1.putExtra("id",patient_id);
                        i1.putExtra("doctor",doctor_name);
                        i1.putExtra("doctor_id",doctor_id);
                        i1.putExtra("designation",hm.get(ids[1]).toString().trim());
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
        Intent i = new Intent(this,DisplaySpecialization.class);
        i.putExtra("id",patient_id);
        startActivity(i);

    }
}
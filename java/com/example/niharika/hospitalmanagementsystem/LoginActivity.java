package com.example.niharika.hospitalmanagementsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.HttpGet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

public class LoginActivity extends Activity {

    EditText id;
    EditText pass;
    SharedPreferences settings;
    String[] json_response = {"userId","encryptedPassword"};
String id_pat ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id = (EditText)findViewById(R.id.login_id);
        pass = (EditText)findViewById(R.id.login_pass);
        id_pat = id.getText().toString();
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("back_pressed",0);
        //  editor.putBoolean("is_registered",false);
        editor.commit();

    }

    public void registerUser(View v){
        Intent i = new Intent(this,RegisterActivity_1.class);
        startActivity(i);
    }

    public void loginUser(View v){
   //     SharedPreferences.Editor editor = settings.edit();
     //   editor.putBoolean("is_logged",true);
      //  editor.commit();
        id_pat = id.getText().toString();
        final String login_id = id.getText().toString();
       final String login_pass = pass.getText().toString();
        Log.e("Patient_id",id_pat);
        if(login_id.isEmpty()){
            id.requestFocus();
            id.setError("Empty");
        }
        else{
            if(login_pass.isEmpty()){
                pass.requestFocus();
                pass.setError("Empty");
            }
            else {
                Log.e("-------:",id_pat);
                System.out.println("patient_id:--- "+id_pat);
              /*  Intent i = new Intent(LoginActivity.this,Patient_after_loginActivity.class);
                i.putExtra("patient_id",id_pat);
                startActivity(i);*/
                /*Log.e("ID",login_id);
                Intent i = new Intent(LoginActivity.this,Patient_after_loginActivity.class);
                i.putExtra("patient_id",login_id);
                startActivity(i);*/

                try {
                   GetLoginDetails asyncTask =  new GetLoginDetails();

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


            /*    RequestQueue requestQueue = Volley.newRequestQueue(this);
                JSONObject json = new JSONObject();
                JsonObjectRequest jsonObjectRequest;
                URL =  "http://13.127.188.57:8080/EHR-WebServices/api/users/"+login_id;
                jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(LoginActivity.this,response.toString()+"---resp",Toast.LENGTH_SHORT).show();
                            String id = (String)response.get("userId");
                            String passwd = (String)response.get("encryptedPassword");
                            Log.e("PASSWPORD",passwd);
                            Log.e("PASSWPORD",passwd);
                            Log.e("PASSWPORD",passwd);

                            if(passwd.equals(login_pass)){
                                Intent i = new Intent(LoginActivity.this,Patient_after_loginActivity.class);
                                i.putExtra("patient_id",login_id);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(LoginActivity.this,"Wrong password",Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Log.e("response",error.toString());
                        Toast.makeText(LoginActivity.this,error.toString()+"---err",Toast.LENGTH_SHORT).show();
                        System.out.print("Sorry Wrong UserId or password");
                    }
                }
                );
                requestQueue.add(jsonObjectRequest);
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
            }
        }
    }
    @Override
    public void onBackPressed(){
          Intent i = new Intent(Intent.ACTION_MAIN);
      //  Intent i = new Intent(this,Patient_after_loginActivity.class);
        //i.putExtra("id",patient_id);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }
    public class GetLoginDetails extends AsyncTask<String, String, Void> {

        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        final String ur_1 = "http://13.127.188.57:8080/EHR-WebServices/api/users/"+id_pat;

        private ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {

            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    GetLoginDetails.this.cancel(true);
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
                this.progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Invalid Username or password",Toast.LENGTH_SHORT).show();
                return null;
            } catch (ClientProtocolException e2) {
                Log.e("ClientProtocolException", e2.toString());
                e2.printStackTrace();
                this.progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Invalid Username or password",Toast.LENGTH_SHORT).show();
                return null;
            } catch (IllegalStateException e3) {
                Log.e("IllegalStateException", e3.toString());
                e3.printStackTrace();
                this.progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Invalid Username or password",Toast.LENGTH_SHORT).show();
                return null;
            } catch (IOException e4) {
                Log.e("IOException", e4.toString());
                e4.printStackTrace();
                this.progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Invalid Username or password",Toast.LENGTH_SHORT).show();
                return null;
            } catch (Exception e) {
                this.progressDialog.dismiss();
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Invalid Username or password",Toast.LENGTH_SHORT).show();
                return null;
            }

            try {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                    this.progressDialog.dismiss();
                    Log.e("HEYOUTPUT: ", result);
                }

                inputStream.close();
                result = sBuilder.toString();

            } catch (Exception e) {
                this.progressDialog.dismiss();
                Log.e("ERROR....", "Error converting result " + e.toString());
                Toast.makeText(getApplicationContext(),"Invalid Username or password",Toast.LENGTH_SHORT).show();
                return null;
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            int i;
            try {
                if(result==null){
                    Toast.makeText(getApplicationContext(),"Invalid Username or password",Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("PATIENTID",id_pat);
                Log.e("OUT:", result);
                JSONObject jArray = new JSONObject(result);
               // Log.e("lEN", jArray.length() + "");
                //*JSONArray jArray =new JSONArray();

                //  jArray = new JSONArray(result);

                if (jArray == null) {
                    Toast.makeText(getApplicationContext(),"Invalid Username or password",Toast.LENGTH_SHORT).show();
                    this.progressDialog.dismiss();
                    return;
                }

                String password = "";

            //    for (i = 0; i < jArray.length(); i++) {

                     password = jArray.getString("encryptedPassword");
                    Log.e("Password", password+"");
              //  }

                if(password.equals(pass.getText().toString())){
                    Intent i1 = new Intent(LoginActivity.this,Patient_after_loginActivity.class);
                    i1.putExtra("id",id_pat);
                    startActivity(i1);
                }
                else{
                    this.progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Wrong password!!",Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

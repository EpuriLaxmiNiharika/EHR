package com.example.niharika.hospitalmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpConnection;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by niharika on 25/5/18.
 */

public class GetDoctorsAsyncTask extends AsyncTask<String, String, Void>{

    Context c;
    InputStream inputStream = null;
    String result = "";
    ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

    final String ur_1 = "http://52.66.43.37:8080/doctordetails/webapi/doctors";
    URL uri = new URL(ur_1);
    String[] doctors_name;
   // HttpConnection connection;
  // HttpClient httpClient;
    public GetDoctorsAsyncTask(Context c) throws IOException {
       this.c = c;
     //   new JSONPARSER();
      // httpClient = new DefaultHttpClient();
      // connection = (HttpConnection)uri.openConnection();

   }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            // Set up HTTP post

            // HttpClient is more then less deprecated. Need to change to URLConnection
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(ur_1);
            //httpPost.setEntity(new UrlEncodedFormEntity(param));
            HttpResponse httpResponse = httpClient.execute(httpPost);
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
        // Convert response to string using String Builder
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            result = sBuilder.toString();

            Log.e("HEYOUTPUT: ",result);

        } catch (Exception e) {
            Log.e("ERROR....", "Error converting result " + e.toString());
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
     /*   try {
            connection = (HttpConnection)uri.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
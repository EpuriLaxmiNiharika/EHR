package com.example.niharika.hospitalmanagementsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DownloadPdf extends Activity {

    WebView webView;String patient_id = "";
    String url1 = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();

        patient_id = i.getStringExtra("id");
        url1 = i.getStringExtra("url");

        setContentView(R.layout.activity_download_pdf);
        webView = (WebView)findViewById(R.id.web_view_pdf);

        String file_name = url1;
        //date_of_visit + patient_id + doctor_id;

        String url = "https://s3.ap-south-1.amazonaws.com/patient-history/"+file_name;

        System.out.println(url);
        Log.e("URL",url);
        Log.e("URL",url);
      //  String doc="<iframe src='http://docs.google.com/viewer?url=+https://s3.ap-south-1.amazonaws.com/patient-history/06688454-f726-4eba-8cb6-46e823410585+' width='100%' height='100%' style='border: none;'></iframe>";

        url =  "http://docs.google.com/gview?embedded=true&url="+url;
        String doc = "<iframe src=" + url +" width='100%' height='100%' style='border: none;'></iframe>";
        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        //webView.loadUrl(url);
        webView.loadData(doc,"text/html", "UTF-8");
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,Patient_after_loginActivity.class);
        i.putExtra("id",patient_id);
        startActivity(i);
    }
}

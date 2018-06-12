package com.example.niharika.hospitalmanagementsystem;

/**
 * Created by niharika on 11/4/18.
 */

import android.os.Environment;

import java.io.*;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
//import com.pdfcrowd.*;
import java.io.*;
import java.util.List;
import java.util.UUID;

public class Upload_Download_S3 {

    /*public Upload_Download_S3() {
        String keyName = "first_test2";
          String bucket_name     = "patient-history";
       try {
           BasicAWSCredentials credentials = new BasicAWSCredentials("AKIAIEJSVFYSVM6S6WMA", "PhN9yIniM/dkacTU6dHHn/PxZea7GiDwgJ2i1Ak+");

           System.setProperty(SDKGlobalConfiguration.ENFORCE_S3_SIGV4_SYSTEM_PROPERTY, "true");

           AmazonS3Client s3Client = new AmazonS3Client(credentials);

           s3Client.setEndpoint("s3-ap-south-1.amazonaws.com");


           S3Object object = s3Client.getObject(new GetObjectRequest("patient-history", keyName));


           download_file();
           String file_name = "aws_s3_file";
           File f = Environment.getExternalStorageDirectory();
           String path = f.getAbsolutePath();
           //adding file name to Path
           path = path + "/" +file_name;


           if (object != null) {
               System.out.println("Object not null");
               System.out.println("Object not null");
               System.out.println("Object not null");
               System.out.println("Object not null");
               System.out.println("Object not null");

           } else {
               System.out.println("Object  null");
               System.out.println("Object  null");
               System.out.println("Object  null");
               System.out.println("Object  null");
               System.out.println("Object  null");
           }
       }
       catch (Exception e){
           System.out.println(e);
           System.out.println(e);
           System.out.println(e);
           System.out.println(e);
       }
*/
      /*  InputStream reader = new BufferedInputStream(object.getObjectContent());
        File f = new File("new_file");

        try {
            OutputStream writer = new BufferedOutputStream(new FileOutputStream(f));
        }
        catch (FileNotFoundException e){
            System.out.println("Exception Name");
            System.out.println("Exception Name");
        }
        */
  //  }

}


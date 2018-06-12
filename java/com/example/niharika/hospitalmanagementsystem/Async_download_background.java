package com.example.niharika.hospitalmanagementsystem;

import android.os.AsyncTask;
import android.widget.Toast;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

/**
 * Created by niharika on 11/4/18.
 */

public class Async_download_background  extends AsyncTask {


    @Override
    protected Object doInBackground(Object[] objects) {

        String keyName = "first_test2";
        String bucket_name = "patient-history";
        System.out.print("hey");
        try {
            BasicAWSCredentials credentials = new BasicAWSCredentials("AKIAIEJSVFYSVM6S6WMA", "PhN9yIniM/dkacTU6dHHn/PxZea7GiDwgJ2i1Ak+");

            System.setProperty(SDKGlobalConfiguration.ENFORCE_S3_SIGV4_SYSTEM_PROPERTY, "true");

            AmazonS3Client s3Client = new AmazonS3Client(credentials);

            s3Client.setEndpoint("s3-ap-south-1.amazonaws.com");


            S3Object object = s3Client.getObject(new GetObjectRequest(bucket_name, keyName));
            //  Toast.makeText(this,"aksalksaa: "+object,Toast.LENGTH_SHORT).show();

        /*    String file_name = object.getKey();
            File f = Environment.getExternalStorageDirectory();
            String path = f.getAbsolutePath();
            //adding file name to Path
            path = path + "/" +file_name;

            File new_file = new File(path);
*/
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


                //     Toast.makeText(this,"object content: "+object.getObjectContent(),Toast.LENGTH_SHORT).show();


               /*

                BufferedReader reader = new BufferedReader(new InputStreamReader(object.getObjectContent()));

                FileWriter writer = new FileWriter(filepath);

//                Writer writer = new OutputStreamWriter(new FileOutputStream(new_file));

                System.out.print("hey I AM DONE READING");
                while (true){
                    System.out.print("hey I am there");
                    String line = reader.readLine();
                    if(line==null){
                        break;
                    }
                    writer.write(line+"\n");
                }*/
                // writer.flush();
                // writer.close();
                // reader.close();
                return object;
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
        return null;
    }

}

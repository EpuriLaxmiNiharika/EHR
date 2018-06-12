package com.example.niharika.hospitalmanagementsystem;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;


public class AsyncT_BookAppointment extends AsyncTask {

    public AsyncT_BookAppointment(AsyncBookAppointmentResponse response){
        this.response = response;
    }
    AsyncBookAppointmentResponse response = null;
    String output = "";
    @Override
    protected String doInBackground(Object... params) {
        String result = "";
        try {
            Log.e("JSON","HEY");

            Log.e("JSON","HEY");
            Log.e("JSON","HEY");
            Log.e("JSON","HEY");


            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost("http://13.127.188.57:8080/rest_api/webapi/appointments/appointment");
            // URL url = new URL("http://52.66.43.37:8080/EHR-WebServices/api/users");
            //   HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //  conn.setRequestMethod("POST");
            //  conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            // conn.setRequestProperty("Accept", "application/json");
            // conn.setDoOutput(true);
            // conn.setDoInput(true);
//            String[] params= {name,email,mobile,password,genderselected,address,date_of_birth,roles.get(blood_group_selected)};
//                    Object[] params = {"niharika", "condition", "symptom1", false, "medicine", false, "alergies1,2,3", "28_07_97",230,80,74};

            JSONObject jsonParam = new JSONObject();
           /*

            jsonParam.accumulate("patientId","laxmiNiharikaepuri");
            jsonParam.accumulate("conditions", "condi");
            jsonParam.accumulate("symptoms",  "symp1");
            jsonParam.accumulate("isTobbaco", true);
            jsonParam.accumulate("prevMedication", "med1");
            jsonParam.accumulate("isAlohocolic", true);
            jsonParam.accumulate("allergies", "aler1");
            jsonParam.accumulate("pdate", "1997-7-28");


            "date_appoint": "16_5_2018",
                    "doctor_id": "1",
                    "patient_id": "123",
                    "reason_appointment": "ear pain",
                    "slot_chosen": 4*/

          //  Log.e("payiet", (String) params[2]);
          //  System.out.print("PATIENT ID"+(String)params[2]);

            jsonParam.accumulate("date_appoint",(String)params[0]);
            jsonParam.accumulate("doctor_id", (String)params[1]);
            jsonParam.accumulate("patient_id",  (String) params[2]);
            jsonParam.accumulate("reason_appointment", (String)params[3]);
            jsonParam.accumulate("slot_chosen", Integer.parseInt((String)params[4]));


            Log.e("DATE", (String) params[0]);
            Log.e("doc id", (String) params[1]);
            Log.e("pat id", (String) params[2]);
            Log.e("reason", (String) params[3]);
            Log.e("slot", (String) params[4]);

            String json = jsonParam.toString();

            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
            InputStream inputStream;
            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);
                Log.e("Result",result);
                Log.e("Result1:",result);
                Log.e("Result2:",result);
                Log.e("Result3:",result);
                output = result;
               /* System.out.println(result+"Ass");
                System.out.println(result);
                System.out.println(result);
                System.out.println(result);*/

               if(result.equals("0")){



               }
               else if(result.equals("1")){


               }
               else{


               }
            }
            else
            {
                result = "Did not work!";
                System.out.println("Didnt work");
                System.out.println("Didnt work");System.out.println("Didnt work");System.out.println("Didnt work");
            }


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        try {
            response.processFinish(output);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }
}

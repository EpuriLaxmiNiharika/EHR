package com.example.niharika.hospitalmanagementsystem;

import android.os.AsyncTask;
import android.util.Log;

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

/**
 * Created by niharika on 11/4/18.
 */

public class AsyncTask_PastHistory extends AsyncTask {
    @Override
    protected String doInBackground(Object... params) {
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost("http://ec2-52-66-43-37.ap-south-1.compute.amazonaws.com:8080/EHR-PatientHistoryService/api/patientHistory");
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

            */


            jsonParam.accumulate("patientId",(String)params[0]+"5");
            jsonParam.accumulate("conditions", (String)params[1]);
            jsonParam.accumulate("symptoms",  (String)params[2]);
            jsonParam.accumulate("isTobbaco", (Boolean)params[3]);
            jsonParam.accumulate("prevMedication", (String)params[4]);
            jsonParam.accumulate("isAlohocolic", (Boolean)params[5]);
            jsonParam.accumulate("allergies", (String)params[6]);
            jsonParam.accumulate("pdate", (String)params[7]);

            jsonParam.accumulate("sugarLevel",(Integer)(params[8]));
            jsonParam.accumulate("weight",(Integer)params[9]);
            jsonParam.accumulate("bloodPressure",(Integer)params[10]);

            Log.i("JSON", jsonParam.toString());
         /*   DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();

            System.out.print("STATUS"+ String.valueOf(conn.getResponseCode()));
            System.out.print("STATUS"+ String.valueOf(conn.getResponseCode()));
            System.out.print("STATUS"+ String.valueOf(conn.getResponseCode()));
            System.out.print("STATUS"+ String.valueOf(conn.getResponseCode()));

            System.out.print("MSG"+ String.valueOf(conn.getResponseMessage().toString()));
            System.out.print("MSG"+ String.valueOf(conn.getResponseMessage().toString()));
            System.out.print("MSG"+ String.valueOf(conn.getResponseMessage().toString()));
            System.out.print("MSG"+ String.valueOf(conn.getResponseMessage().toString()));

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG", conn.getResponseMessage());

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        */
            // 4. convert JSONObject to JSON to String
            String json = jsonParam.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
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
                System.out.println(result);
                System.out.println(result); System.out.println(result); System.out.println(result);
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

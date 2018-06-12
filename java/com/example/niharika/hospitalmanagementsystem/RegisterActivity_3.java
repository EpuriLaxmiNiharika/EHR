package com.example.niharika.hospitalmanagementsystem;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONObject;


public class RegisterActivity_3 extends Activity {

    EditText location;
    String name,phone,gender,email;
    String address = "";
    String dob = "";
    EditText password,confirm_pass;
    String pass_str,confirmpass_str ="";
    String blood_group="";
   // SharedPreferences settings;
   // SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_3);

        location = (EditText)findViewById(R.id.reg_address);
        password = (EditText)findViewById(R.id.reg_pass);
        confirm_pass = (EditText)findViewById(R.id.reg_confirm_pass);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        phone = i.getStringExtra("mobile");
        gender = i.getStringExtra("gender");
        email = i.getStringExtra("email");
        dob = i.getStringExtra("dob");
        blood_group = i.getStringExtra("blood_group");

    //    settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
      //  editor= settings.edit();
   //     boolean is_reg = editor.("is_registered");
     //   boolean is_reg = settings.getBoolean("is_registered",false);
      //  editor.commit();
    }


    public void getCurrentLocation(View v){

        Intent i = new Intent(RegisterActivity_3.this,MyLocationUsingLocationAPI.class);
     /*   i.putExtra("name",name);
        i.putExtra("mobile",phone);
        i.putExtra("gender",gender);
        i.putExtra("email",email);
        i.putExtra("dob",dob);
        i.putExtra("address",address);
        i.putExtra("pass",pass_str);
        i.putExtra("confirm_pass",confirmpass_str);
        i.putExtra("blood_group",)*/
        startActivityForResult(i,1);
    }

    public void registerUser(View v){
       // if(settings.getBoolean("is_logged",false)){
        pass_str = password.getText().toString();
        confirmpass_str = confirm_pass.getText().toString();

        if(address.isEmpty()){
            location.requestFocus();
            location.setError("Empty");
        }
        else {
            if (pass_str.isEmpty()) {
                password.requestFocus();
                password.setError("Empty");
            }
            if (confirmpass_str.isEmpty()) {
                confirm_pass.requestFocus();
                confirm_pass.setError("Empty");
            }
            if (!pass_str.equals(confirmpass_str)) {
                confirm_pass.requestFocus();
                confirm_pass.setError("Passwords don't match");
            } else {
                if(pass_str.length()<=5){
                    password.setError("Enter minimum 6 characters");
                }

                else {
               //     Toast.makeText(getApplicationContext(), name + "--" + phone + "--" + gender + "--" + email + "--" + dob + "--" + address, Toast.LENGTH_SHORT).show();

                    Long phonenum = Long.parseLong(phone);
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    JSONObject json = new JSONObject();
                    JsonObjectRequest jsonObjectRequest;
                    String[] params = {name, email, phone, pass_str, gender, address, dob, blood_group};
                    AsyncT asyncT = new AsyncT();
                    asyncT.execute(params);
                    //   editor.putBoolean("is_registered",true);
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        }

        }




    /*    Intent i = new Intent(getApplicationContext(),RegistrerActivity_4.class);
        i.putExtra("name",name);
        i.putExtra("mobile",phone);
        i.putExtra("gender",gender);
        i.putExtra("email",email);
        i.putExtra("dob",dob);
        i.putExtra("address",address);
    */

   // }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null) return;
        // super.onActivityResult(requestCode, resultCode, data);
            if(resultCode==RESULT_OK) {
                address = data.getStringExtra("address");
            //    Toast.makeText(this, "address: " + address, Toast.LENGTH_SHORT).show();
                if (!address.equals("NAN")) {
                    location.setText(address);
                } else {

                }
            }
        }
}

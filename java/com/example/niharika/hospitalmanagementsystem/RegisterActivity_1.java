package com.example.niharika.hospitalmanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity_1 extends AppCompatActivity {

    EditText name;
    EditText mobile;
    String namePerson,mobilePerson = "";
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_1);
        name = (EditText)findViewById(R.id.reg_name);
        mobile = (EditText)findViewById(R.id.reg_mobile);
        email = (EditText) findViewById(R.id.reg_email);
        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("is_logged",true);
        editor.commit();

    }

    public void next_page(View v){

        namePerson = name.getText().toString();
        mobilePerson = mobile.getText().toString();
        if(namePerson.isEmpty()){
            name.requestFocus();
            name.setError("Empty");
        }
        else{
            if(namePerson.length()<=5){
                name.requestFocus();
                name.setError("Enter minimum 6 characters");
            }

            else if(mobilePerson.isEmpty()){
                mobile.requestFocus();
                mobile.setError("Empty");
            }
            else{
                if(!validatePhone(mobilePerson)){
                    mobile.requestFocus();
                    mobile.setError("Enter valid Mobile Number");
                }
               // Toast.makeText(getApplicationContext(), namePerson + "--" + mobilePerson, Toast.LENGTH_SHORT).show();
                else {
                    String emailid = email.getText().toString();
                    if (emailid.isEmpty()) {
                        email.requestFocus();
                        email.setError("Empty");
                    } else {
                        if (Patterns.EMAIL_ADDRESS.matcher(emailid).matches()) {
                            Intent i = new Intent(this, RegisterActivity_2.class);
                            i.putExtra("name", namePerson);
                            i.putExtra("mobile", mobilePerson);
                            i.putExtra("email", emailid);
                            startActivity(i);
                        } else {
                            email.requestFocus();
                            email.setError("Enter valid emailAddress");
                        }

                    }
                }
            }
        }

    }

    boolean validatePhone(String phone){
        boolean check;
        Pattern pattern;
        Matcher matcher;

        String email_str = "^([+][9][1]|[9][1]|[0]){0,1}([7-9]{1})([0-9]{9})";

        pattern = Pattern.compile(email_str);
        matcher = pattern.matcher(phone);
        check = matcher.matches();
        return check;
    }
}

package com.example.niharika.hospitalmanagementsystem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class RegisterActivity_2 extends Activity {

    RadioGroup gender;
    RadioButton female,male;
    String name,phone,emailid;
    String genderselected = "Male";
    TextView date_of_birth;
    RadioGroup blood_group;
    String dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_2);
        blood_group = (RadioGroup)findViewById(R.id.reg_blood_group);
        date_of_birth = (TextView)findViewById(R.id.reg_dob);
        gender = (RadioGroup)findViewById(R.id.reg_gender);
        female = (RadioButton)findViewById(R.id.radio_girl);
        male = (RadioButton)findViewById(R.id.radio_boy);
        Intent i = getIntent();
        name = i.getStringExtra("name");
        phone = i.getStringExtra("mobile");
        emailid = i.getStringExtra("email");
     //   Toast.makeText(getApplicationContext(),name + "---"+phone,Toast.LENGTH_SHORT).show();
    }

    public void changeBackground(View v){
        //int id = v.getId();
        RadioButton rb = (RadioButton)v;
        int id = rb.getId();

        if(id == R.id.radio_boy){
            female.setBackgroundColor(Color.TRANSPARENT);
            genderselected = "Male";
            male.setBackgroundColor(getResources().getColor(R.color.login_background));
        }
        else{
            genderselected = "Female";
            male.setBackgroundColor(Color.TRANSPARENT);
            female.setBackgroundColor(getResources().getColor(R.color.login_background));
        }
    }

    public void displayDPD(final View v){
        //    Toast.makeText(getApplicationContext(),"Text",Toast.LENGTH_SHORT).show();
        Calendar c = Calendar.getInstance();
        int c_year = c.get(Calendar.YEAR);
        int c_month = c.get(Calendar.MONTH);
        int c_date = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                if(dayOfMonth<10){
                    date_of_birth.setText("0"+dayOfMonth+"_"+(month+1)+"_"+year);
                }

                else {
                    date_of_birth.setText(dayOfMonth + "_" + (month + 1) + "_" + year);
                }
                dob = date_of_birth.getText().toString();
             //   Toast.makeText(getApplicationContext(),"chosen date: "+date_of_birth,Toast.LENGTH_SHORT).show();
            }
        },c_year,c_month,c_date);

        DatePicker dp = dpd.getDatePicker();
        dp.setMaxDate(System.currentTimeMillis());
        dpd.show();

    }

    public void next_btn(View v){
     //   Toast.makeText(getApplicationContext(),name + "---"+phone,Toast.LENGTH_SHORT).show();
            int id = blood_group.getCheckedRadioButtonId();
         //   Toast.makeText(this,"hey I am clicked",Toast.LENGTH_SHORT).show();
            String bg ="";

            if(id == R.id.reg_opos){
             //   Toast.makeText(this,"O+",Toast.LENGTH_SHORT).show();

            //    System.out.println("O+");
                bg = "O+";
            }
            else if(id == R.id.reg_oneg){
            //    Toast.makeText(this,"O-",Toast.LENGTH_SHORT).show();

            //    System.out.println("O-");
                bg = "O-";
            }
            else if(id == R.id.reg_bpos){
            //    Toast.makeText(this,"B+",Toast.LENGTH_SHORT).show();

            //    System.out.println("B+");
                bg = "B+";
            }
           else  if(id == R.id.reg_bneg){
             //   Toast.makeText(this,"B-",Toast.LENGTH_SHORT).show();

           //     System.out.println("B-");
                bg = "B-";
            }
            else if(id == R.id.reg_abpos){
           //     Toast.makeText(this,"AB+",Toast.LENGTH_SHORT).show();
            //    System.out.println("AB+");
                bg = "AB+";
            }
            else{
                int id1 = R.id.ab_neg;
                if(id1==id){
                //    Toast.makeText(this,"AB-",Toast.LENGTH_SHORT).show();
                //    System.out.println("AB-");
                    bg = "AB-";
                }
                else{
                    bg = "no blood group";
                }
            }


            dob = date_of_birth.getText().toString();

            if(dob.length()>10){
               Toast.makeText(this,"Enter Your date of birth",Toast.LENGTH_SHORT).show();
            }

           else{
                Intent i = new Intent(this,RegisterActivity_3.class);
                i.putExtra("name",name);
                i.putExtra("mobile",phone);
                i.putExtra("gender",genderselected);
                i.putExtra("email",emailid);
                i.putExtra("dob",dob);
                i.putExtra("blood_group",bg);
                startActivity(i);
            }

    }
}


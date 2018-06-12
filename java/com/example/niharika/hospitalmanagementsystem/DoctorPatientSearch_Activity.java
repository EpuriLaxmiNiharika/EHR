package com.example.niharika.hospitalmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DoctorPatientSearch_Activity extends AppCompatActivity {

    String patient_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_search);
        Intent i = getIntent();
        patient_id = i.getStringExtra("id");
      //  Toast.makeText(getApplicationContext(),patient_id,Toast.LENGTH_SHORT).show();
    }


    public void searchDoctors(View v){
        Intent i = new Intent(this,SearchDoctor.class);
        i.putExtra("id",patient_id);
        startActivity(i);
    }

    public void searchHospitals(View v){
        Intent i = new Intent(this,SearchHospitals.class);
        i.putExtra("id",patient_id);
        startActivity(i);
    }
}

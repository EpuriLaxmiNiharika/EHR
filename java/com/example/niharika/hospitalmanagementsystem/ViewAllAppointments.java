package com.example.niharika.hospitalmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ViewAllAppointments extends AppCompatActivity {

    String patient_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_appointments);
        Intent i = getIntent();
        patient_id = i.getStringExtra("id");
    }

    public void getCurrentAppointments(View v){

        Intent i = new Intent(this,DisplayCurrentDayAppointments.class);
        i.putExtra("id",patient_id);
        startActivity(i);

    }

    public void getPastAppointments(View v){
        Intent i = new Intent(this,DisplayPastDayAppointments.class);
        i.putExtra("id",patient_id);
        startActivity(i);
    }

    public void getFutureAppointments(View v){
        Intent i = new Intent(this,DisplayFutureDayAppointments.class);
        i.putExtra("id",patient_id);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Patient_after_loginActivity.class);
        i.putExtra("id", patient_id);
        startActivity(i);
    }
}

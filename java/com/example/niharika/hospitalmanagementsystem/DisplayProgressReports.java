package com.example.niharika.hospitalmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DisplayProgressReports extends AppCompatActivity {

    String patient_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_progress_reports);
        patient_id = getIntent().getStringExtra("id");
    }

    public void displaySugarLevels(View v){
        Intent i1 = new Intent(this, Display_patient_SugarLevels_Graph.class);
        i1.putExtra("id", patient_id);
        startActivity(i1);
    }

    public void displayBPlevels(View v){
        Intent i1 = new Intent(this, Display_patient_BPLevels_Graph.class);
        i1.putExtra("id", patient_id);
        startActivity(i1);
    }

    public void displayWeightlevels(View v){

        Intent i1 = new Intent(this, Display_patient_WeightLevels_Graph.class);
        i1.putExtra("id", patient_id);
        startActivity(i1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this,Patient_after_loginActivity.class);
        i.putExtra("id",patient_id);
        startActivity(i);
    }
}

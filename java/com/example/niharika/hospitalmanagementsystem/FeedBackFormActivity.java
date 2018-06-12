package com.example.niharika.hospitalmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class FeedBackFormActivity extends AppCompatActivity {

    RatingBar doc,hosp,staff,overall;
    EditText any_comments;
    float rating_doc,rating_hosp,rating_staff,rating_overall=0;
    String patient_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_form);
        hosp = (RatingBar)findViewById(R.id.env);
        doc = (RatingBar)findViewById(R.id.doc);
        staff = (RatingBar)findViewById(R.id.staff);
        overall = (RatingBar)findViewById(R.id.overall_exp);
        any_comments = (EditText)findViewById(R.id.any_comments);

        patient_id = getIntent().getStringExtra("id");

        rating_doc = doc.getRating();
        rating_hosp = hosp.getRating();
        rating_staff = staff.getRating();
        rating_overall = overall.getRating();

     /*   hosp.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating_hosp = rating;
            }
        });

        doc.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });

        staff.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });

        overall.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        }); */
    }

    public void SubmitFeedback(View v){
        rating_doc = doc.getRating();
        rating_hosp = hosp.getRating();
        rating_staff = staff.getRating();
        rating_overall = overall.getRating();

   //     Toast.makeText(this,rating_doc+"--"+rating_hosp+"--"+rating_staff+"--"+rating_overall,Toast.LENGTH_SHORT).show();

        Toast.makeText(this,"Feedback Submitted",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,Patient_after_loginActivity.class);
        i.putExtra("id",patient_id);
        startActivity(i);

    }

}

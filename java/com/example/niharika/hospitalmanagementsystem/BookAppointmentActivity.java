package com.example.niharika.hospitalmanagementsystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity implements AsyncBookAppointmentResponse{

    String slot_selected;
    LinearLayout layout_morning_slots, layout_afternoon_slots, layout_evening_slots;
    RadioGroup morning_slots, afternoon_slots, evening_slots;
    TextView book_appointment_patient;
    String appointment_date;
    String patientId, doctorName, doctorDesignation;
    TextView book_doctor_name, book_doctor_specialization;
    AlertDialog ald;
    EditText reason_visit;
    int slot_chosen = 1;
    String doc_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        layout_morning_slots = (LinearLayout) findViewById(R.id.layout_morning_slots);
        layout_afternoon_slots = (LinearLayout) findViewById(R.id.layout_afternoon_slots);
        layout_evening_slots = (LinearLayout) findViewById(R.id.layout_evening_slots);
        book_appointment_patient = (TextView) findViewById(R.id.book_appointment_patient);
        morning_slots = (RadioGroup) findViewById(R.id.slots_morning);
        afternoon_slots = (RadioGroup) findViewById(R.id.slots_afternoon);
        evening_slots = (RadioGroup) findViewById(R.id.slots_evening);
        book_doctor_name = (TextView) findViewById(R.id.book_doctor_name);
        book_doctor_specialization = (TextView) findViewById(R.id.book_doctor_specialization);
        reason_visit = (EditText)findViewById(R.id.reason_visit);

        slot_selected = "9-10AM";
        Intent i = getIntent();
        patientId = i.getStringExtra("id");
 //       Toast.makeText(getApplicationContext(),patientId,Toast.LENGTH_SHORT).show();

        doctorName = i.getStringExtra("doctor");
        doc_id = i.getStringExtra("doctor_id");
        doctorDesignation = i.getStringExtra("designation");
        book_doctor_name.setText(doctorName);
        book_doctor_specialization.setText(doctorDesignation);

    }


    public void displayMorningSlots(View v) {

        morning_slots.setVisibility(View.VISIBLE);
        evening_slots.setVisibility(View.GONE);
        afternoon_slots.setVisibility(View.GONE);
        layout_morning_slots.setVisibility(layout_morning_slots.VISIBLE);
        layout_afternoon_slots.setVisibility(layout_afternoon_slots.GONE);
        layout_evening_slots.setVisibility(layout_evening_slots.GONE);
    }

    public void displayAfternoonSlots(View v) {

        morning_slots.setVisibility(View.GONE);
        evening_slots.setVisibility(View.GONE);
        afternoon_slots.setVisibility(View.VISIBLE);

        layout_morning_slots.setVisibility(layout_morning_slots.GONE);
        layout_afternoon_slots.setVisibility(layout_afternoon_slots.VISIBLE);
        layout_evening_slots.setVisibility(layout_evening_slots.GONE);
    }

    public void displayEveningSlots(View v) {
        morning_slots.setVisibility(View.GONE);
        evening_slots.setVisibility(View.VISIBLE);
        afternoon_slots.setVisibility(View.GONE);
        layout_morning_slots.setVisibility(layout_morning_slots.GONE);
        layout_afternoon_slots.setVisibility(layout_afternoon_slots.GONE);
        layout_evening_slots.setVisibility(layout_evening_slots.VISIBLE);
    }


    public void bookAppointment1(View v) {
        RadioButton slot1 = (RadioButton) findViewById(R.id.morning_slot1);
        RadioButton slot2 = (RadioButton) findViewById(R.id.morning_slot2);

        RadioButton rb = (RadioButton) v;

        int id = rb.getId();
        if (id == R.id.morning_slot1) {
            slot1.setTextColor(ContextCompat.getColor(this, R.color.button_pressed_color));
            slot2.setTextColor(ContextCompat.getColor(this, R.color.doctor_subreason));
            slot_chosen = 1;
        }

        if (id == R.id.morning_slot2) {
            slot2.setTextColor(ContextCompat.getColor(this, R.color.button_pressed_color));
            slot1.setTextColor(ContextCompat.getColor(this, R.color.doctor_subreason));
            slot_chosen = 2;
        }

        slot_selected = rb.getText().toString().trim();
    }

    public void bookAppointment2(View v) {
        RadioButton slot1 = (RadioButton) findViewById(R.id.afternoon_slot1);
        RadioButton slot2 = (RadioButton) findViewById(R.id.afternoon_slot2);


        RadioButton rb = (RadioButton) v;

        int id = rb.getId();
        if (id == R.id.afternoon_slot1) {
            slot1.setTextColor(ContextCompat.getColor(this, R.color.button_pressed_color));
            slot2.setTextColor(ContextCompat.getColor(this, R.color.doctor_subreason));
            slot_chosen = 3;
        }

        if (id == R.id.afternoon_slot2) {
            slot2.setTextColor(ContextCompat.getColor(this, R.color.button_pressed_color));
            slot1.setTextColor(ContextCompat.getColor(this, R.color.doctor_subreason));
            slot_chosen = 4;
        }

        slot_selected = rb.getText().toString().trim();
    }

    public void bookAppointment3(View v) {
        RadioButton slot1 = (RadioButton) findViewById(R.id.evening_slot1);
        RadioButton slot2 = (RadioButton) findViewById(R.id.evening_slot2);
        RadioButton slot3 = (RadioButton) findViewById(R.id.evening_slot3);

        RadioButton rb = (RadioButton) v;

        int id = rb.getId();
        if (id == R.id.evening_slot1) {
            slot1.setTextColor(ContextCompat.getColor(this, R.color.button_pressed_color));
            slot2.setTextColor(ContextCompat.getColor(this, R.color.doctor_subreason));
            slot3.setTextColor(ContextCompat.getColor(this, R.color.doctor_subreason));
            slot_chosen = 5;
        }

        if (id == R.id.evening_slot2) {
            slot2.setTextColor(ContextCompat.getColor(this, R.color.button_pressed_color));
            slot1.setTextColor(ContextCompat.getColor(this, R.color.doctor_subreason));
            slot3.setTextColor(ContextCompat.getColor(this, R.color.doctor_subreason));
            slot_chosen = 6;

        }
        if (id == R.id.evening_slot3) {
            slot3.setTextColor(ContextCompat.getColor(this, R.color.button_pressed_color));
            slot2.setTextColor(ContextCompat.getColor(this, R.color.doctor_subreason));
            slot1.setTextColor(ContextCompat.getColor(this, R.color.doctor_subreason));
            slot2.setTextColor(ContextCompat.getColor(this, R.color.doctor_subreason));
            slot_chosen = 7;
        }

        slot_selected = rb.getText().toString().trim();
    }


    public void displayDPD(final View v) {
        //    Toast.makeText(getApplicationContext(),"Text",Toast.LENGTH_SHORT).show();
        Calendar c = Calendar.getInstance();
        int c_year = c.get(Calendar.YEAR);
        int c_month = c.get(Calendar.MONTH);
        int c_date = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(BookAppointmentActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (dayOfMonth < 10 ) {
                    if(month>9) {
                        book_appointment_patient.setText(year + "-"+(month+1) + "-0"+dayOfMonth);

                    }
                    else{

                        book_appointment_patient.setText(year + "-0"+(month+1)+"-0"+dayOfMonth);
                    }
                } else {
                    if(month>9) {
                        book_appointment_patient.setText(year + "-"+(month+1) + "-"+dayOfMonth);
                    }
                    else{
                        book_appointment_patient.setText(year + "-0"+(month+1)+"-"+dayOfMonth);
                    }
                }
                appointment_date = book_appointment_patient.getText().toString();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd_MM_yyyy");
                String today_date = mdformat.format(calendar.getTime());
                //  Toast.makeText(getApplicationContext(),"Today: "+today_date +" chosen: "+appointment_date,Toast.LENGTH_SHORT).show();
            }
        }, c_year, c_month, c_date);

        DatePicker dp = dpd.getDatePicker();
        dp.setMinDate(System.currentTimeMillis() + 24 * 60 * 60 * 1000l);
        Long time = 6 * 24 * 60 * 60 * 1000l;
        dp.setMaxDate(System.currentTimeMillis() + time);
        dpd.show();
    }

    public void displayDialog(View v) {
        // Toast.makeText(this,slot_selected,Toast.LENGTH_SHORT).show();
        LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View v1 = li.inflate(R.layout.appointment_confirmation_dialog, null, false);
        Button accept = (Button) v1.findViewById(R.id.appointment_confirm);
        Button decline = (Button) v1.findViewById(R.id.appointment_deny);

        AlertDialog.Builder ad = new AlertDialog.Builder(BookAppointmentActivity.this);
        ad.setView(v1);
        ald = ad.create();
        ald.show();


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Intent i = new Intent(BookAppointmentActivity.this,Patient_after_loginActivity.class);
                // i.putExtra("id",patientId);
                ald.dismiss();
            //    Toast.makeText(BookAppointmentActivity.this, "Booking Confirmed!!", Toast.LENGTH_SHORT).show();

              //  Toast.makeText(BookAppointmentActivity.this,slot_chosen+"---"+doc_id,Toast.LENGTH_SHORT).show();

                /* Intent i = new Intent(BookAppointmentActivity.this, Patient_after_loginActivity.class);
                i.putExtra("id", patientId);
                startActivity(i); */

             //   Toast.makeText(getApplicationContext(),patientId,Toast.LENGTH_SHORT).show();
                String[] params = {appointment_date,doc_id,patientId,reason_visit.getText().toString().trim(),slot_chosen+""};
                AsyncT_BookAppointment asyncT = new AsyncT_BookAppointment(new AsyncBookAppointmentResponse() {
                    @Override
                    public void processFinish(String output) throws JSONException {
                        JSONObject jsonObject = new JSONObject(output);

                        String resp =  jsonObject.getString("response");
                      //  Toast.makeText(getApplicationContext(),"heyeye: " +
                        //        ""+output,Toast.LENGTH_SHORT).show();

                        if(resp.equals("0")){
                            finish();
                            Toast.makeText(getApplicationContext(),"Hey!! Booking Confirmed",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(BookAppointmentActivity.this, Patient_after_loginActivity.class);
                            i.putExtra("id", patientId);
                            startActivity(i);

                        }
                        else if(resp.equals("1")){
                            Toast.makeText(getApplicationContext(),"Booking Failed... Slots Not available",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Cannot book Multiple appointments",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                asyncT.execute(params);

            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ald.dismiss();
                Toast.makeText(BookAppointmentActivity.this, "Booking Deny!!", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(BookAppointmentActivity.this, Patient_after_loginActivity.class);
                i.putExtra("id", patientId);
                startActivity(i);
            }
        });
    }

    @Override
    public void processFinish(String output) {

      //  Toast.makeText(this,"heyyy:--- "+output,Toast.LENGTH_SHORT).show();
    }
}
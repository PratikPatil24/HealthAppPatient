package com.pratik.healthapppatient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorAppointmentActivity extends AppCompatActivity {

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment);

        Intent i = getIntent();
        id = i.getStringExtra("ID");
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
    }
}

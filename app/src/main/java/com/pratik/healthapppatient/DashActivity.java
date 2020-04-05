package com.pratik.healthapppatient;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DashActivity extends AppCompatActivity {

    //Firebase Auth
    private FirebaseAuth mAuth;

    //Firebase Firestore
    private FirebaseFirestore db;

    TextView StatisticsTextView;
    MaterialButton ViewHistoryButton, CheckHealthButton, BookAppButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        mAuth = FirebaseAuth.getInstance();

        //Firebase Firestore
        db = FirebaseFirestore.getInstance();

        StatisticsTextView = findViewById(R.id.textViewStatistics);

        ViewHistoryButton = findViewById(R.id.btnViewHistory);
        CheckHealthButton = findViewById(R.id.btnCheckHealth);
        BookAppButton = findViewById(R.id.btnBookApp);

        ViewHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashActivity.this, ViewHistoryActivity.class);
                startActivity(i);
            }
        });

        CheckHealthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashActivity.this, CheckHealthActivity.class);
                startActivity(i);
            }
        });

        BookAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashActivity.this, BookAppointmentActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.userprofile) {
            startActivity(new Intent(getBaseContext(), ProfileActivitiy.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
}

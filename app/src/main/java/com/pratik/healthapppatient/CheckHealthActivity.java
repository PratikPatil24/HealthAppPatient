package com.pratik.healthapppatient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class CheckHealthActivity extends AppCompatActivity {

    TextInputEditText TempTextInput, BPSTextInput, BPDTextInput, HeartRateTextInput, SugarTextInput;
    ImageView TempImageView, BPImageView, HeartRateImageView, SugarImageView;
    MaterialButton CheckHealthButton, BookAppButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_health);

        TempTextInput = findViewById(R.id.textInputTemperature);
        BPSTextInput = findViewById(R.id.textInputBloodPressureS);
        BPDTextInput = findViewById(R.id.textInputBloodPressureD);
        HeartRateTextInput = findViewById(R.id.textInputHeartRate);
        SugarTextInput = findViewById(R.id.textInputSugar);

        TempImageView = findViewById(R.id.imageViewTemperature);
        BPImageView = findViewById(R.id.imageViewBloodPressure);
        HeartRateImageView = findViewById(R.id.imageViewHeartRate);
        SugarImageView = findViewById(R.id.imageViewSugar);

        CheckHealthButton = findViewById(R.id.btnCheckHealth);
        BookAppButton = findViewById(R.id.btnBookApp);

        CheckHealthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TempTextInput.getText().toString().equals(null) || BPSTextInput.getText().toString().equals(null) || BPDTextInput.getText().toString().equals(null) || HeartRateTextInput.getText().toString().equals(null) || SugarTextInput.getText().toString().equals(null)) {
                    Snackbar.make(v, "Enter Add Details!", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                Snackbar.make(v, "Checking Health...", Snackbar.LENGTH_SHORT).show();
                float temp, bps, bpd, heart, sugar;
                temp = Float.parseFloat(TempTextInput.getText().toString());
                bps = Float.parseFloat(BPSTextInput.getText().toString());
                bpd = Float.parseFloat(BPDTextInput.getText().toString());
                heart = Float.parseFloat(HeartRateTextInput.getText().toString());
                sugar = Float.parseFloat(SugarTextInput.getText().toString());

                //Checking Temperature
                if (temp < 36.5 || temp > 37.5) {
                    TempImageView.setImageDrawable(getDrawable(R.drawable.cross));
                } else {
                    TempImageView.setImageDrawable(getDrawable(R.drawable.correct));
                }

                //Checking BP
                if (bps < 90 || bps > 120 || bpd < 60 || bpd > 80) {
                    BPImageView.setImageDrawable(getDrawable(R.drawable.cross));
                } else {
                    BPImageView.setImageDrawable(getDrawable(R.drawable.correct));
                }

                //Checking Heart Rate
                if (heart < 60 || heart > 100) {
                    HeartRateImageView.setImageDrawable(getDrawable(R.drawable.cross));
                } else {
                    HeartRateImageView.setImageDrawable(getDrawable(R.drawable.correct));
                }

                //Checking Sugar Level
                if (sugar < 72 || sugar > 99) {
                    SugarImageView.setImageDrawable(getDrawable(R.drawable.cross));
                } else {
                    SugarImageView.setImageDrawable(getDrawable(R.drawable.correct));
                }

            }
        });

        BookAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CheckHealthActivity.this, BookAppointmentActivity.class);
                startActivity(i);
            }
        });
    }
}

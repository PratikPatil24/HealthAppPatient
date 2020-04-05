package com.pratik.healthapppatient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pratik.healthapppatient.models.Doctor;

import java.util.HashMap;
import java.util.Map;

public class DoctorAppointmentActivity extends AppCompatActivity {

    TextView NameTextView, SpecialityTextView, DegreeTextView, AgeTextView, GenderTextView;
    TextInputEditText DayTextInput, MonthTextInput, YearTextInput;
    TextView DateTextView;
    MaterialButton GetAppointmentButton;
    Doctor doctor;
    String day, month, year;
    //Firebase Auth
    private FirebaseAuth mAuth;
    //Firebase Firestore
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment);

        Intent i = getIntent();
        doctor = (Doctor) i.getSerializableExtra("Doctor");

        mAuth = FirebaseAuth.getInstance();

        //Firebase Firestore
        db = FirebaseFirestore.getInstance();

        NameTextView = findViewById(R.id.textViewDoctorName);
        SpecialityTextView = findViewById(R.id.textViewSpeciality);
        DegreeTextView = findViewById(R.id.textViewDegree);
        AgeTextView = findViewById(R.id.textViewAge);
        GenderTextView = findViewById(R.id.textViewGender);

        DayTextInput = findViewById(R.id.textInputDay);
        MonthTextInput = findViewById(R.id.textInputMonth);
        YearTextInput = findViewById(R.id.textInputYear);

        GetAppointmentButton = findViewById(R.id.btnBookAppointment);

        NameTextView.setText("Dr. " + doctor.getName());
        SpecialityTextView.setText(doctor.getSpeciality());
        DegreeTextView.setText(doctor.getDegree());
        AgeTextView.setText(String.valueOf(doctor.getAge()));
        GenderTextView.setText(doctor.getGender());

        GetAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                day = DayTextInput.getText().toString();
                month = MonthTextInput.getText().toString();
                year = YearTextInput.getText().toString();

                final Map<String, Object> appointment = new HashMap<>();
                appointment.put("pID", mAuth.getCurrentUser().getPhoneNumber());
                appointment.put("dID", doctor.getID());
                appointment.put("day", day);
                appointment.put("month", month);
                appointment.put("year", year);

                db.collection("doctors").document(doctor.getID()).collection(day + month + year).document()
                        .set(appointment)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("UserAdd", "DocumentSnapshot successfully written!");
                                Snackbar.make(v, "Appointment Added!", Snackbar.LENGTH_SHORT).show();
                                //Toast.makeText(DoctorAppointmentActivity.this, "Information Updated!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("UserAdd", "Error writing document", e);
                                Snackbar.make(v, "Appointment Not Added!", Snackbar.LENGTH_SHORT).show();
                                //Toast.makeText(DoctorAppointmentActivity.this, "Information Not Updated!", Toast.LENGTH_SHORT).show();
                            }
                        });

                db.collection("patients").document(mAuth.getCurrentUser().getPhoneNumber()).collection("appointments").document()
                        .set(appointment)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("UserAdd", "DocumentSnapshot successfully written!");
                                Snackbar.make(v, "Appointment Added!", Snackbar.LENGTH_SHORT).show();
                                //Toast.makeText(DoctorAppointmentActivity.this, "Information Updated!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("UserAdd", "Error writing document", e);
                                Snackbar.make(v, "Appointment Not Added!", Snackbar.LENGTH_SHORT).show();
                                //Toast.makeText(DoctorAppointmentActivity.this, "Information Not Updated!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}

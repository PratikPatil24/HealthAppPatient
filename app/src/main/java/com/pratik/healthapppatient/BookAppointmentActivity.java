package com.pratik.healthapppatient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pratik.healthapppatient.adapters.DoctorAdapter;
import com.pratik.healthapppatient.models.Doctor;
import com.pratik.healthapppatient.models.Patient;

import java.util.ArrayList;

public class BookAppointmentActivity extends AppCompatActivity {

    Patient patient;
    //Recycler
    DoctorAdapter madapter;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    ArrayList<Doctor> doctors = new ArrayList<>();

    //Firebase Auth
    private FirebaseAuth mAuth;

    //Firebase Firestore
    private FirebaseFirestore db;

    private CollectionReference AppointRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        mAuth = FirebaseAuth.getInstance();

        //Firebase Firestore
        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerDoctor);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        getPatientData();

        /*Doctors data fetching Logic
        1. fetch patient details
        2. When details received then (In Success) then fetch doctors of same area
        3. When all area doctors fetched then (In Success) fetch left doctors

        In this way order is maintained otherwise fuction with least documents populates recycler view first
         */

    }

    void getPatientData() {
        DocumentReference docRef = db.collection("patients").document(mAuth.getCurrentUser().getPhoneNumber());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Patient Data", "DocumentSnapshot data: " + document.getData());
                        patient = new Patient(document.getId(), document.getId(), document.get("name").toString(),
                                document.get("state").toString(), document.get("city").toString(), document.get("area").toString(), document.get("addressline").toString(),
                                document.get("gender").toString(), Float.parseFloat(document.get("weight").toString()), Float.parseFloat(document.get("height").toString()),
                                Integer.parseInt(document.get("age").toString()));

                        //Loading Doctors
                        getData();
                    } else {
                        Log.d("Patient Data", "No such document");
                    }
                } else {
                    Log.d("Patient Data", "get failed with ", task.getException());
                }
            }
        });
    }

    void getData() {

        Toast.makeText(this, "Loading Data...", Toast.LENGTH_SHORT).show();
        db.collection("doctors")
                .whereEqualTo("area", patient.getArea())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Doctor doctor = new Doctor(document.getId(), document.getId().substring(0, document.getId().length() - 1), document.get("name").toString(),
                                        document.get("speciality").toString(), document.get("degree").toString(),
                                        document.get("state").toString(), document.get("city").toString(), document.get("area").toString(), document.get("addressline").toString(),
                                        document.get("gender").toString(), Integer.parseInt(document.get("age").toString()));
                                doctors.add(doctor);
                                Log.d("Document Fetch", document.getId() + " => " + document.getData());
                            }
                            madapter = new DoctorAdapter(doctors);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(madapter);
                            madapter.notifyDataSetChanged();

                            //Loding Other Doctors
                            getLeftData();


                            //For Button Click
                            madapter.setOnItemClickListener(new DoctorAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Doctor doctor, int position) {
                                    //Toast.makeText(BookAppointmentActivity.this, doctor.getPhoneno(), Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(BookAppointmentActivity.this, DoctorAppointmentActivity.class);
                                    i.putExtra("Doctor", doctor);
                                    startActivity(i);
                                }
                            });

                        } else {
                            Log.d("Document Fetch", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    void getLeftData() {
        db.collection("doctors")
                .whereEqualTo("state", patient.getState())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (!document.get("area").toString().equals(patient.getArea())) {
                                    Doctor doctor = new Doctor(document.getId(), document.getId().substring(0, document.getId().length() - 1), document.get("name").toString(),
                                            document.get("speciality").toString(), document.get("degree").toString(),
                                            document.get("state").toString(), document.get("city").toString(), document.get("area").toString(), document.get("addressline").toString(),
                                            document.get("gender").toString(), Integer.parseInt(document.get("age").toString()));
                                    doctors.add(doctor);
                                    Log.d("Document Fetch", document.getId() + " => " + document.getData());
                                }
                            }
                            madapter = new DoctorAdapter(doctors);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(madapter);
                            madapter.notifyDataSetChanged();

                            //For Button Click
                            madapter.setOnItemClickListener(new DoctorAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Doctor doctor, int position) {
                                    //Toast.makeText(BookAppointmentActivity.this, doctor.getPhoneno(), Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(BookAppointmentActivity.this, DoctorAppointmentActivity.class);
                                    i.putExtra("Doctor", doctor);
                                    startActivity(i);
                                }
                            });

                        } else {
                            Log.d("Document Fetch", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}

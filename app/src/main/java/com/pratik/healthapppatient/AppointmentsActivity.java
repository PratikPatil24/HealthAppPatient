package com.pratik.healthapppatient;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pratik.healthapppatient.adapters.AppointmentAdapter;
import com.pratik.healthapppatient.models.Appointment;

import java.util.ArrayList;

public class AppointmentsActivity extends AppCompatActivity {

    //Recycler
    AppointmentAdapter madapter;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    ArrayList<Appointment> appointments = new ArrayList<>();
    //Firebase Auth
    private FirebaseAuth mAuth;
    //Firebase Firestore
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        mAuth = FirebaseAuth.getInstance();

        //Firebase Firestore
        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerAppointment);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        getData();
    }

    void getData() {

        Toast.makeText(this, "Loading Data...", Toast.LENGTH_SHORT).show();
        db.collection("patients").document(mAuth.getCurrentUser().getPhoneNumber())
                .collection("appointments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Appointment appointment = new Appointment(document.get("pID").toString(), document.get("dID").toString(),
                                        document.get("doctorName").toString(), document.get("speciality").toString(), document.get("degree").toString(),
                                        document.get("address").toString(), document.get("day").toString(), document.get("month").toString(), document.get("year").toString());

                                appointments.add(appointment);

                                Log.d("Document Fetch", document.getId() + " => " + document.getData());
                            }
                            madapter = new AppointmentAdapter(appointments);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(madapter);
                            madapter.notifyDataSetChanged();

                        } else {
                            Log.d("Document Fetch", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}

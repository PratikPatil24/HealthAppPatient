package com.pratik.healthapppatient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivitiy extends AppCompatActivity {

    TextView PhoneNumberTextView, GenderTextView;
    TextInputEditText NameTextInput, AgeTextInput, WeightTextInput, HeightTextInput;
    TextInputEditText StateTextInput, CityTextInput, AreaTextInput, AddressLineTextInput;
    MaterialButton SignOutButton, UpdateInfoButton;
    String phoneno, gender;
    //Firebase Auth
    private FirebaseAuth mAuth;
    //Firebase Firestore
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        //Firebase Firestore
        db = FirebaseFirestore.getInstance();

        PhoneNumberTextView = findViewById(R.id.textViewPhoneNumber);
        NameTextInput = findViewById(R.id.textInputEditTextName);
        AgeTextInput = findViewById(R.id.textInputEditTextAge);
        WeightTextInput = findViewById(R.id.textInputEditTextWeight);
        HeightTextInput = findViewById(R.id.textInputEditTextHeight);

        StateTextInput = findViewById(R.id.textInputEditTextState);
        CityTextInput = findViewById(R.id.textInputEditTextCity);
        AreaTextInput = findViewById(R.id.textInputEditTextArea);
        AddressLineTextInput = findViewById(R.id.textInputEditTextLine);

        GenderTextView = findViewById(R.id.textViewGender);

        UpdateInfoButton = findViewById(R.id.btnUpdateInfo);
        SignOutButton = findViewById(R.id.btnSignOut);


        DocumentReference docRef = db.collection("patients").document(mAuth.getCurrentUser().getPhoneNumber());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        PhoneNumberTextView.setText(mAuth.getCurrentUser().getPhoneNumber());
                        NameTextInput.setText(document.get("name").toString());
                        WeightTextInput.setText(document.get("weight").toString());
                        HeightTextInput.setText(document.get("height").toString());
                        AgeTextInput.setText(document.get("age").toString());
                        GenderTextView.setText(document.get("gender").toString());
                        StateTextInput.setText(document.get("state").toString());
                        CityTextInput.setText(document.get("city").toString());
                        AreaTextInput.setText(document.get("area").toString());
                        AddressLineTextInput.setText(document.get("addressline").toString());

                        gender = document.get("gender").toString();
                    } else {
                        Log.d("UserFetch", "No such document");
                        Toast.makeText(ProfileActivitiy.this, "User Not Found!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("UserFetch", "get failed with ", task.getException());
                    Toast.makeText(ProfileActivitiy.this, "Fetch Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        UpdateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Snackbar.make(v, "Updating data...", Snackbar.LENGTH_SHORT).show();

                phoneno = mAuth.getCurrentUser().getPhoneNumber();
//                int checked = GenderRadioGroup.getCheckedRadioButtonId();
//                if (checked == -1) {
//                    Toast.makeText(ProfileActivitiy.this, "Select User Type!", Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (checked == R.id.radioButtonMale)
//                    gender = "Male";
//                else if (checked == R.id.radioButtonFemale)
//                    gender = "Female";
//                else if (checked == R.id.radioButtonOthers)
//                    gender = "Others";

                final Map<String, Object> user = new HashMap<>();
                user.put("userType", "patient");
                user.put("name", NameTextInput.getText().toString());
                user.put("age", Integer.parseInt(AgeTextInput.getText().toString()));
                user.put("weight", Integer.parseInt(WeightTextInput.getText().toString()));
                user.put("height", Integer.parseInt(HeightTextInput.getText().toString()));
                user.put("gender", gender);
                user.put("state", StateTextInput.getText().toString().toLowerCase());
                user.put("city", CityTextInput.getText().toString().toLowerCase());
                user.put("area", AreaTextInput.getText().toString().toLowerCase());
                user.put("addressline", AddressLineTextInput.getText().toString().toLowerCase());

                db.collection("patients").document(phoneno)
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("UserAdd", "DocumentSnapshot successfully written!");
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("UserAdd", "Error writing document", e);
                                Snackbar.make(v, "Data not Updated!", Snackbar.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}

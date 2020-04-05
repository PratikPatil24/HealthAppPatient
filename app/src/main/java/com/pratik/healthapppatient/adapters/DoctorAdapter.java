package com.pratik.healthapppatient.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.pratik.healthapppatient.R;
import com.pratik.healthapppatient.models.Doctor;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    //For Button Click Interface
    private OnItemClickListener listener;

    private List<Doctor> doctors;

    public DoctorAdapter(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Doctor doctor = doctors.get(position);
        holder.NameTextView.setText(doctor.getName());
        holder.SpecialityTextView.setText(doctor.getSpeciality());
        holder.DegreeTextView.setText(doctor.getDegree());
        holder.AddressTextView.setText((doctor.getAddressline() + ", " + doctor.getArea() + ", " + doctor.getCity() + ", " + doctor.getState()));
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // For Button Click
    public interface OnItemClickListener {
        void onItemClick(Doctor doctor, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView NameTextView, SpecialityTextView, DegreeTextView, AddressTextView;
        MaterialButton BookAppButton;

        MyViewHolder(View view) {
            super(view);

            NameTextView = view.findViewById(R.id.textViewDoctorName);
            SpecialityTextView = view.findViewById(R.id.textViewSpeciality);
            DegreeTextView = view.findViewById(R.id.textViewDegree);
            AddressTextView = view.findViewById(R.id.textViewAddress);
            BookAppButton = view.findViewById(R.id.btnBookApp);

            BookAppButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(doctors.get(position), position);
                    }
                }
            });
        }
    }
}
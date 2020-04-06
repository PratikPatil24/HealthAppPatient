package com.pratik.healthapppatient.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pratik.healthapppatient.R;
import com.pratik.healthapppatient.models.Appointment;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {

    private List<Appointment> appointments;

    public AppointmentAdapter(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.NameTextView.setText("Dr. " + appointment.getDoctorName());
        holder.DateTextView.setText(appointment.getDay() + "/" + appointment.getMonth() + "/" + appointment.getYear());
        holder.SpecialityTextView.setText(appointment.getSpeciality());
        holder.DegreeTextView.setText(appointment.getDegree());
        holder.AddressTextView.setText(appointment.getAddress());
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView NameTextView, DateTextView, SpecialityTextView, DegreeTextView, AddressTextView;

        MyViewHolder(View view) {
            super(view);

            NameTextView = view.findViewById(R.id.textViewDoctorName);
            DateTextView = view.findViewById(R.id.textViewDate);
            SpecialityTextView = view.findViewById(R.id.textViewSpeciality);
            DegreeTextView = view.findViewById(R.id.textViewDegree);
            AddressTextView = view.findViewById(R.id.textViewAddress);
        }
    }
}
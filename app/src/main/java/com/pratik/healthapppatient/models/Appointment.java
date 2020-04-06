package com.pratik.healthapppatient.models;

public class Appointment {

    String pID, dID, doctorName, speciality, degree, address, day, month, year, otp;

    public Appointment() {
    }

    public Appointment(String pID, String dID, String doctorName, String speciality, String degree, String address, String day, String month, String year, String otp) {
        this.pID = pID;
        this.dID = dID;
        this.doctorName = doctorName;
        this.speciality = speciality;
        this.degree = degree;
        this.address = address;
        this.day = day;
        this.month = month;
        this.year = year;
        this.otp = otp;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getdID() {
        return dID;
    }

    public void setdID(String dID) {
        this.dID = dID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}

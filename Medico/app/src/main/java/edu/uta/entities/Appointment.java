package edu.uta.entities;

/**
 * Created by gaurav on 11/23/15.
 */
public class Appointment {

    private String date;
    private boolean appointment_status;
    private int doctor_id;
    private int patient_id;
    private int doctor_encounter_form;
    private int doctor_order_form;
    private int medical_report;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isAppointment_status() {
        return appointment_status;
    }

    public void setAppointment_status(boolean appointment_status) {
        this.appointment_status = appointment_status;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getDoctor_encounter_form() {
        return doctor_encounter_form;
    }

    public void setDoctor_encounter_form(int doctor_encounter_form) {
        this.doctor_encounter_form = doctor_encounter_form;
    }

    public int getDoctor_order_form() {
        return doctor_order_form;
    }

    public void setDoctor_order_form(int doctor_order_form) {
        this.doctor_order_form = doctor_order_form;
    }

    public int getMedical_report() {
        return medical_report;
    }

    public void setMedical_report(int medical_report) {
        this.medical_report = medical_report;
    }
}

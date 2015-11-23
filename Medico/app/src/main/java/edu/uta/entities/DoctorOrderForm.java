package edu.uta.entities;

/**
 * Created by gaurav on 11/23/15.
 */
public class DoctorOrderForm {

    private int appointment_id;
    private String symptoms;
    private String diagnosis;
    private String medicine_prescribed;

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getMedicine_prescribed() {
        return medicine_prescribed;
    }

    public void setMedicine_prescribed(String medicine_prescribed) {
        this.medicine_prescribed = medicine_prescribed;
    }
}

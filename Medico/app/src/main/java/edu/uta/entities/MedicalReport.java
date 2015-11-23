package edu.uta.entities;

import java.util.Date;

/**
 * Class for Medical Test
 * Created by achyut on 10/8/15.
 */
public class MedicalReport {

    private int appointment_id;
    private String bloodpressure;
    private String temperature;

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getBloodpressure() {
        return bloodpressure;
    }

    public void setBloodpressure(String bloodpressure) {
        this.bloodpressure = bloodpressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}

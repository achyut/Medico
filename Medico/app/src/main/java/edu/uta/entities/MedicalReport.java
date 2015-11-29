package edu.uta.entities;

import java.util.Date;

/**
 * Class for Medical Test
 * Created by achyut on 10/8/15.
 */
public class MedicalReport {

    private String appointment_id = "0";
    private String bloodpressure = "0";
    private String temperature = "0";
    private String heartbeat = "0";
    private String airflow = "0";
    private String bloodsugerlevel = "0";

    public String getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(String appointment_id) {
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

    public String getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(String heartbeat) {
        this.heartbeat = heartbeat;
    }

    public String getAirflow() {
        return airflow;
    }

    public void setAirflow(String airflow) {
        this.airflow = airflow;
    }

    public String getBloodsugerlevel() {
        return bloodsugerlevel;
    }

    public void setBloodsugerlevel(String bloodsugerlevel) {
        this.bloodsugerlevel = bloodsugerlevel;
    }
}

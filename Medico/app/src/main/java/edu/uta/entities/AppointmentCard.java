package edu.uta.entities;

import java.io.Serializable;

/**
 * Created by gaurav on 11/28/15.
 */
public class AppointmentCard{

    private String date;
    private String doctorname;
    private boolean status;
    private String patientname;
    private int appointmentid;

    public AppointmentCard(String date, String doctorname,String patientname, int appointmentid, boolean status) {
        this.date = date;
        this.doctorname = doctorname;
        this.patientname = patientname;
        this.appointmentid = appointmentid;
        this.status = status;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getAppointmentid() {
        return appointmentid;
    }

    public void setAppointmentid(int appointmentid) {
        this.appointmentid = appointmentid;
    }

    @Override
    public String toString() {
        return "AppointmentCard{" +
                "date='" + date + '\'' +
                ", doctorname='" + doctorname + '\'' +
                ", status=" + status +
                ", appointmentid=" + appointmentid +
                '}';
    }
}

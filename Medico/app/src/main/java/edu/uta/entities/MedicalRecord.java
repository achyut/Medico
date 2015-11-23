package edu.uta.entities;

import java.util.List;

/**
 * Medical Record class
 * Created by achyut on 10/8/15.
 */
public class MedicalRecord {

    private int patient_id;
    private int patient_information_id;

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getPatient_information_id() {
        return patient_information_id;
    }

    public void setPatient_information_id(int patient_information_id) {
        this.patient_information_id = patient_information_id;
    }
}

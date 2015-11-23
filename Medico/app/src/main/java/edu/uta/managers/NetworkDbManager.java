package edu.uta.managers;

import java.util.List;

import edu.uta.entities.Appointment;
import edu.uta.entities.MedicalRecord;
import edu.uta.entities.PatientEncounterForm;
import edu.uta.entities.PatientInformation;
import edu.uta.entities.User;

/**
 * Created by gaurav on 11/23/15.
 */
public class NetworkDbManager implements DBMgr
{

    @Override
    public User doLogin(String username, String password) {
        return null;
    }

    @Override
    public User registerUser(User user) {
        return null;
    }

    @Override
    public boolean forgotPassword(String email) {
        return false;
    }

    @Override
    public boolean updatePassword(String oldPassword, String newPassword) {
        return false;
    }

    @Override
    public List<Appointment> createAppointment(Appointment appointment, PatientEncounterForm encounterForm) {
        return null;
    }

    @Override
    public List<Appointment> viewPatientAppointments(int patientId) {
        return null;
    }

    @Override
    public PatientInformation createPatientInformation(PatientInformation patientInformation) {
        return null;
    }

    @Override
    public PatientInformation updatePatientInformation(PatientInformation patientInformation) {
        return null;
    }

    @Override
    public PatientInformation viewPatientInformation(int patientId) {
        return null;
    }

    @Override
    public MedicalRecord viewMedicalRecord(String userId) {
        return null;
    }
}

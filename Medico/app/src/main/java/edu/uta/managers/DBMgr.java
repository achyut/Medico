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
public interface DBMgr {
    public User doLogin(String username,String password);
    public User registerUser(User user);
    public boolean forgotPassword(String email);
    public boolean updatePassword(String oldPassword,String newPassword);
    public List<Appointment> createAppointment(Appointment appointment,PatientEncounterForm encounterForm );
    public List<Appointment> viewPatientAppointments(int patientId);
    public PatientInformation createPatientInformation(PatientInformation patientInformation);
    public PatientInformation updatePatientInformation(PatientInformation patientInformation);
    public PatientInformation viewPatientInformation(int patientId);

    public MedicalRecord viewMedicalRecord(String userId);

}

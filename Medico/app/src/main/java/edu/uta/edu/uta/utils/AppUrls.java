package edu.uta.edu.uta.utils;

/**
 * Created by gaurav on 11/23/15.
 */
public class AppUrls {
    final static String BASE_URL = "http://192.168.43.34:8000/";
   // final static String BASE_URL = "http://f40f13bc.ngrok.io/";


    final  static String LOGIN_URL = BASE_URL+"login";
    final static String LOGOUT_URL = BASE_URL+"logout";
    final static String REGISTER_URL = BASE_URL+"register";
    final static String FORGOTPASSWORD_URL = BASE_URL+"resetpassword";
    final static String UPDATEPASSWORD_URL = BASE_URL+"updatepassword";
    final static String CHECK_FOR_PATIENT_INFORMATION = BASE_URL+"checkforpatientinfo";
    final static String CREATE_PATIENT_INFORMATION = BASE_URL+"patientinformations";
    final static String UPDATE_PATIENT_INFORMATION = BASE_URL+"patientinformations";
    final static String GET_PATIENT_INFORMATION = BASE_URL+"patientinformations";

    final static String GET_USER_URL = BASE_URL+"users/";
    final static String GET_ALL_DOCTORS = BASE_URL+"getalldoctors";

    final static String CREATEA_NEW_APPOINTMENT = BASE_URL+"appointments";
    final static String DELETE_APPOINTMENT  = BASE_URL+"appointments";
    final static String GET_APPOINTMENTS_OF_USER = BASE_URL+"getuserappointment";
    final static String GET_APPOINTMENTS_OF_DOCTOR = BASE_URL+"getdoctorappointment";
    final static String GET_APPOINTMENT_DETAILS = BASE_URL+"appointments";

    final static String GET_DEVICE_VALUES = BASE_URL+"devicedata";
    final static String SAVE_REPORT = BASE_URL+"medicalreports";

    final static String CREATE_DOCTOR_ORDER = BASE_URL+"doctororders";
    final static String GET_DOCTOR_ORDER = BASE_URL+"doctororders";
    final static String SEARCH_URL = BASE_URL+"search";

    public static String getSearchUrl() {
        return SEARCH_URL;
    }

    public static String getGetDoctorOrder(String appointmentId) {
        return GET_DOCTOR_ORDER+"/"+appointmentId;
    }

    public static String getCreateDoctorOrder() {
        return CREATE_DOCTOR_ORDER;
    }

    public static String getSaveReport() {
        return SAVE_REPORT;
    }

    public static String getGetDeviceValues() {
        return GET_DEVICE_VALUES;
    }

    public static String getGetAppointmentDetails(int appointmentid) {
        return GET_APPOINTMENT_DETAILS+"/"+appointmentid;
    }

    public static String getGetAppointmentsOfDoctor(int userid) {
        return GET_APPOINTMENTS_OF_DOCTOR+"/"+userid;
    }

    public static String getGetAppointmentsOfUser(int userid) {
        return GET_APPOINTMENTS_OF_USER+"/"+userid;
    }

    public static String getDeleteAppointment(int appointmentid) {
        return DELETE_APPOINTMENT+"/"+appointmentid;
    }

    public static String getCreateaNewAppointment() {
        return CREATEA_NEW_APPOINTMENT;
    }

    public static String getGetAllDoctors() {
        return GET_ALL_DOCTORS;
    }

    public static String getGetPatientInformation(int userid) {
        return GET_PATIENT_INFORMATION+"/"+userid;
    }

    public static String getGetUserUrl(int userid) {
        return GET_USER_URL+userid;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getLoginUrl() {
        return LOGIN_URL;
    }

    public static String getLogoutUrl() {
        return LOGOUT_URL;
    }

    public static String getRegisterUrl() {
        return REGISTER_URL;
    }

    public static String getForgotpasswordUrl() {
        return FORGOTPASSWORD_URL;
    }

    public static String getUpdatepasswordUrl(int userid) {
        return UPDATEPASSWORD_URL+"/"+userid;
    }

    public static String getCheckForPatientInformation(int userid) {
        return CHECK_FOR_PATIENT_INFORMATION+"/"+userid;
    }

    public static String getCreatePatientInformation() {
        return CREATE_PATIENT_INFORMATION;
    }

    public static String getUpdatePatientInformation(int userid) {
        return UPDATE_PATIENT_INFORMATION+"/"+userid;
    }

    public static String getGetUserUrl() {
        return GET_USER_URL;
    }
}

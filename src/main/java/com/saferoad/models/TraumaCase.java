package com.saferoad.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// TraumaCase — Health Department (EMS / Hospital Emergency Triage) Model
public class TraumaCase {

    private static int caseCounter = 5001;

    private String caseId;
    private String patientName;
    private int patientAge;
    private String gender;
    private String linkedAccidentId;
    private String hospitalName;
    private String triageLevel;     // CRITICAL_TRIAGE, SEVERE_CARE, STABLE_MINOR
    private String goldenHourWindow; // e.g., "Active (22 mins remaining)"
    private String ambulanceUnit;
    private boolean corridorRequested;
    private String status;          // EN_ROUTE, ICU_ADMITTED, STABILIZED, DISCHARGED
    private String admissionTime;

    public TraumaCase(String patientName, int patientAge, String gender, String linkedAccidentId,
                      String hospitalName, String triageLevel, String ambulanceUnit, boolean corridorRequested) {
        this.caseId = "TRM-" + (caseCounter++);
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.gender = gender;
        this.linkedAccidentId = linkedAccidentId != null && !linkedAccidentId.isBlank() ? linkedAccidentId : "N/A (Direct Arrival)";
        this.hospitalName = hospitalName;
        this.triageLevel = triageLevel;
        this.goldenHourWindow = "Within Golden Hour (42m remaining)";
        this.ambulanceUnit = ambulanceUnit;
        this.corridorRequested = corridorRequested;
        this.status = "EN_ROUTE";
        this.admissionTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public TraumaCase(String caseId, String patientName, int patientAge, String gender, String linkedAccidentId,
                      String hospitalName, String triageLevel, String goldenHourWindow,
                      String ambulanceUnit, boolean corridorRequested, String status, String admissionTime) {
        this.caseId = caseId;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.gender = gender;
        this.linkedAccidentId = linkedAccidentId;
        this.hospitalName = hospitalName;
        this.triageLevel = triageLevel;
        this.goldenHourWindow = goldenHourWindow;
        this.ambulanceUnit = ambulanceUnit;
        this.corridorRequested = corridorRequested;
        this.status = status;
        this.admissionTime = admissionTime;
    }

    public String getCaseId()            { return caseId; }
    public String getPatientName()       { return patientName; }
    public int getPatientAge()           { return patientAge; }
    public String getGender()            { return gender; }
    public String getLinkedAccidentId()  { return linkedAccidentId; }
    public String getHospitalName()      { return hospitalName; }
    public String getTriageLevel()       { return triageLevel; }
    public String getGoldenHourWindow()  { return goldenHourWindow; }
    public String getAmbulanceUnit()     { return ambulanceUnit; }
    public boolean isCorridorRequested() { return corridorRequested; }
    public String getStatus()            { return status; }
    public String getAdmissionTime()     { return admissionTime; }

    public void setStatus(String status) { this.status = status; }
    public void setCorridorRequested(boolean val) { this.corridorRequested = val; }
}

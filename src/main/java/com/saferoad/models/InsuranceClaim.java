package com.saferoad.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// InsuranceClaim — Insurance Agency (MACT Claims, FIR Verification, Fraud Audit) Model
public class InsuranceClaim {

    private static int claimCounter = 7001;

    private String claimId;
    private String policyNumber;
    private String vehiclePlate;
    private String policyholderName;
    private String linkedAccidentId;
    private String insuranceCompany;
    private String claimType;        // THIRD_PARTY_LIABILITY, OWN_DAMAGE, COMPREHENSIVE_TOTAL_LOSS
    private double claimedAmount;
    private double approvedAmount;
    private String status;           // UNDER_AUDIT, APPROVED_SETTLED, REJECTED_FRAUD, DISPUTED
    private String filingDate;
    private String assessorNotes;

    public InsuranceClaim(String policyNumber, String vehiclePlate, String policyholderName,
                          String linkedAccidentId, String insuranceCompany, String claimType,
                          double claimedAmount, double approvedAmount, String status, String assessorNotes) {
        this.claimId = "CLM-" + (claimCounter++);
        this.policyNumber = policyNumber;
        this.vehiclePlate = vehiclePlate;
        this.policyholderName = policyholderName;
        this.linkedAccidentId = linkedAccidentId != null && !linkedAccidentId.isBlank() ? linkedAccidentId : "N/A";
        this.insuranceCompany = insuranceCompany;
        this.claimType = claimType;
        this.claimedAmount = claimedAmount;
        this.approvedAmount = approvedAmount;
        this.status = status != null ? status : "UNDER_AUDIT";
        this.filingDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.assessorNotes = assessorNotes;
    }

    public InsuranceClaim(String claimId, String policyNumber, String vehiclePlate, String policyholderName,
                          String linkedAccidentId, String insuranceCompany, String claimType,
                          double claimedAmount, double approvedAmount, String status, String filingDate, String assessorNotes) {
        this.claimId = claimId;
        this.policyNumber = policyNumber;
        this.vehiclePlate = vehiclePlate;
        this.policyholderName = policyholderName;
        this.linkedAccidentId = linkedAccidentId;
        this.insuranceCompany = insuranceCompany;
        this.claimType = claimType;
        this.claimedAmount = claimedAmount;
        this.approvedAmount = approvedAmount;
        this.status = status;
        this.filingDate = filingDate;
        this.assessorNotes = assessorNotes;
    }

    public String getClaimId()           { return claimId; }
    public String getPolicyNumber()      { return policyNumber; }
    public String getVehiclePlate()      { return vehiclePlate; }
    public String getPolicyholderName()  { return policyholderName; }
    public String getLinkedAccidentId()  { return linkedAccidentId; }
    public String getInsuranceCompany()  { return insuranceCompany; }
    public String getClaimType()         { return claimType; }
    public double getClaimedAmount()     { return claimedAmount; }
    public double getApprovedAmount()    { return approvedAmount; }
    public String getStatus()            { return status; }
    public String getFilingDate()        { return filingDate; }
    public String getAssessorNotes()     { return assessorNotes; }

    public void setStatus(String status) { this.status = status; }
    public void setApprovedAmount(double amt) { this.approvedAmount = amt; }
    public void setAssessorNotes(String notes) { this.assessorNotes = notes; }
}

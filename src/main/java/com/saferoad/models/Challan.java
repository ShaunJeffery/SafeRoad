package com.saferoad.models;

// Challan model with encapsulation — used by controller and view
public class Challan {

    private static int counter = 1001;

    private String challanId;
    private String licensePlate;
    private String ownerName;
    private String vehicleType;
    private String violationType;
    private double fineAmount;
    private String status;  // Unpaid, Paid, Disputed
    private String issueDate;
    private String issuedBy;

    public Challan(String licensePlate, String ownerName, String vehicleType,
                   String violationType, double fineAmount, String issueDate, String issuedBy) {
        this.challanId     = "CH-" + (counter++);
        this.licensePlate  = licensePlate;
        this.ownerName     = ownerName;
        this.vehicleType   = vehicleType;
        this.violationType = violationType;
        this.fineAmount    = fineAmount;
        this.status        = "Unpaid";
        this.issueDate     = issueDate;
        this.issuedBy      = issuedBy;
    }

    // Getters & Setters (Encapsulation)
    public String getChallanId()     { return challanId; }
    public String getLicensePlate()  { return licensePlate; }
    public String getOwnerName()     { return ownerName; }
    public String getVehicleType()   { return vehicleType; }
    public String getViolationType() { return violationType; }
    public double getFineAmount()    { return fineAmount; }
    public String getStatus()        { return status; }
    public String getIssueDate()     { return issueDate; }
    public String getIssuedBy()      { return issuedBy; }
    public void setStatus(String s)  { this.status = s; }
}

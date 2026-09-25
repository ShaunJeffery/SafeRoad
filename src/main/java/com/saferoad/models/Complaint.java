package com.saferoad.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Complaint {
    private static int counter = 101;

    private String complaintId;
    private String vehiclePlate;
    private String category;    // Pothole/Road Hazard, Faulty Traffic Signal, Wrong Challan, Accident Tip, Other
    private String location;
    private String description;
    private String timestamp;
    private String status;      // Submitted, Under Review, Resolved

    public Complaint(String vehiclePlate, String category, String location, String description) {
        this.complaintId  = "CMP-" + (counter++);
        this.vehiclePlate = vehiclePlate;
        this.category     = category;
        this.location     = location;
        this.description  = description;
        this.timestamp    = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.status       = "Submitted";
    }

    public String getComplaintId()  { return complaintId; }
    public String getId()           { return complaintId; }
    public String getVehiclePlate() { return vehiclePlate; }
    public String getCategory()     { return category; }
    public String getIssueType()    { return category; }
    public String getLocation()     { return location; }
    public String getDescription()  { return description; }
    public String getTimestamp()    { return timestamp; }
    public String getStatus()       { return status; }
    public void setStatus(String s) { this.status = s; }
}

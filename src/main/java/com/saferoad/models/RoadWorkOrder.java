package com.saferoad.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// RoadWorkOrder — Highways Department (NHAI / PWD) Engineering & Hazard Redressal Model
public class RoadWorkOrder {

    private static int orderCounter = 3001;

    private String orderId;
    private String location;
    private String highwayStretch;
    private String issueCategory;
    private String description;
    private String assignedCrew;
    private double budgetInLakhs;
    private String priority; // CRITICAL, HIGH, MEDIUM, LOW
    private String status;   // PENDING, IN_PROGRESS, COMPLETED
    private String createdDate;
    private String targetCompletionDate;

    public RoadWorkOrder(String location, String highwayStretch, String issueCategory, 
                         String description, String assignedCrew, double budgetInLakhs, 
                         String priority, String targetCompletionDate) {
        this.orderId = "WO-" + (orderCounter++);
        this.location = location;
        this.highwayStretch = highwayStretch;
        this.issueCategory = issueCategory;
        this.description = description;
        this.assignedCrew = assignedCrew;
        this.budgetInLakhs = budgetInLakhs;
        this.priority = priority;
        this.status = "PENDING";
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.targetCompletionDate = targetCompletionDate;
    }

    public RoadWorkOrder(String orderId, String location, String highwayStretch, String issueCategory,
                         String description, String assignedCrew, double budgetInLakhs,
                         String priority, String status, String createdDate, String targetCompletionDate) {
        this.orderId = orderId;
        this.location = location;
        this.highwayStretch = highwayStretch;
        this.issueCategory = issueCategory;
        this.description = description;
        this.assignedCrew = assignedCrew;
        this.budgetInLakhs = budgetInLakhs;
        this.priority = priority;
        this.status = status;
        this.createdDate = createdDate;
        this.targetCompletionDate = targetCompletionDate;
    }

    public String getOrderId()              { return orderId; }
    public String getLocation()             { return location; }
    public String getHighwayStretch()       { return highwayStretch; }
    public String getIssueCategory()        { return issueCategory; }
    public String getDescription()          { return description; }
    public String getAssignedCrew()         { return assignedCrew; }
    public double getBudgetInLakhs()        { return budgetInLakhs; }
    public String getPriority()             { return priority; }
    public String getStatus()               { return status; }
    public String getCreatedDate()          { return createdDate; }
    public String getTargetCompletionDate() { return targetCompletionDate; }

    public void setStatus(String status)    { this.status = status; }
    public void setAssignedCrew(String crew){ this.assignedCrew = crew; }
}

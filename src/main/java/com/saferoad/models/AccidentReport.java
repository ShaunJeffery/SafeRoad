package com.saferoad.models;

public class AccidentReport {
    private static int counter = 1;

    private String reportId;
    private String location;
    private String timestamp;
    private String severity;
    private String involvedVehicles;
    private String status;
    private String reportedBy;
    private String description;

    public AccidentReport(String location, String timestamp, String severity,
                          String involvedVehicles, String reportedBy, String description) {
        this.reportId        = String.format("ACC-%04d", counter++);
        this.location        = location;
        this.timestamp       = timestamp;
        this.severity        = severity;
        this.involvedVehicles = involvedVehicles;
        this.status          = "Pending";
        this.reportedBy      = reportedBy;
        this.description     = description;
    }

    public String getReportId()         { return reportId; }
    public String getId()               { return reportId; }
    public String getLocation()         { return location; }
    public String getTimestamp()        { return timestamp; }
    public String getSeverity()         { return severity; }
    public String getInvolvedVehicles() { return involvedVehicles; }
    public String getVehiclesInvolved() { return involvedVehicles; }
    public String getStatus()           { return status; }
    public String getReportedBy()       { return reportedBy; }
    public String getDescription()      { return description; }
    public String getRoadCondition()    { return "Asphalt / Wet Surface"; }
    public String getWeatherCondition() { return "Daylight / Normal"; }
    public void setStatus(String s)     { this.status = s; }
}

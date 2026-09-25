package com.saferoad.models;

public class TrafficAlert {
    private static int counter = 1;

    private String alertId;
    private String type;       // RoadClosure, Construction, Weather, Accident
    private String title;
    private String location;
    private String message;
    private String severity;   // Low, Medium, High, Critical
    private String channels;   // SMS, Email, etc.
    private String timestamp;
    private boolean active;

    public TrafficAlert(String type, String title, String location, String message,
                        String severity, String channels, String timestamp) {
        this.alertId   = String.format("ALT-%03d", counter++);
        this.type      = type;
        this.title     = title;
        this.location  = location;
        this.message   = message;
        this.severity  = severity;
        this.channels  = channels;
        this.timestamp = timestamp;
        this.active    = true;
    }

    public String getAlertId()   { return alertId; }
    public String getType()      { return type; }
    public String getTitle()     { return title; }
    public String getLocation()  { return location; }
    public String getMessage()   { return message; }
    public String getSeverity()  { return severity; }
    public String getChannels()  { return channels; }
    public String getTimestamp() { return timestamp; }
    public boolean isActive()    { return active; }
    public void resolve()        { this.active = false; }
}

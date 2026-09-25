package com.saferoad.models;

// TrafficIntersection — Dynamic Signal Control with Area Jurisdiction & Officer Assignment
public class TrafficIntersection {

    public enum SignalState    { RED, YELLOW, GREEN }
    public enum TrafficDensity { LOW, MEDIUM, HIGH }

    private String location;
    private String zone;
    private String assignedOfficer;
    private String junctionType;
    private SignalState signalState;
    private TrafficDensity density;
    private int timerSeconds;
    private boolean emergencyActive;
    private boolean flashCaution;

    public TrafficIntersection(String location) {
        this(location, "Central CBD", "Inspector Raj", "4-Way Cross Signal");
    }

    public TrafficIntersection(String location, String zone, String assignedOfficer) {
        this(location, zone, assignedOfficer, "4-Way Cross Signal");
    }

    public TrafficIntersection(String location, String zone, String assignedOfficer, String junctionType) {
        this.location        = location;
        this.zone            = zone != null && !zone.isBlank() ? zone : "General Sector";
        this.assignedOfficer = assignedOfficer != null && !assignedOfficer.isBlank() ? assignedOfficer : "Duty Officer";
        this.junctionType    = junctionType != null && !junctionType.isBlank() ? junctionType : "4-Way Cross Signal";
        this.signalState     = SignalState.RED;
        this.density         = TrafficDensity.LOW;
        this.timerSeconds    = 30;
        this.emergencyActive = false;
        this.flashCaution    = false;
    }

    public String getLocation()        { return location; }
    public String getZone()            { return zone; }
    public String getAssignedOfficer() { return assignedOfficer; }
    public String getJunctionType()    { return junctionType; }
    public SignalState getSignalState() { return signalState; }
    public SignalState getCurrentSignal() { return signalState; }
    public String getDensity()         { return density != null ? density.name() : "LOW"; }
    public int getTimerSeconds()       { return timerSeconds; }
    public int getCycleTimeSeconds()   { return timerSeconds; }
    public boolean isEmergencyActive() { return emergencyActive; }
    public boolean isFlashCaution()    { return flashCaution; }
    public boolean isCautionFlashing() { return flashCaution; }

    public void setZone(String zone) { this.zone = zone; }
    public void setAssignedOfficer(String officer) { this.assignedOfficer = officer; }
    public void setJunctionType(String type) { this.junctionType = type; }

    public String getSignalStateStr()  {
        if (flashCaution) return "CAUTION_AMBER";
        return signalState != null ? signalState.name() : "RED";
    }

    public void setDensity(TrafficDensity d) {
        this.density = d;
        this.timerSeconds = switch (d) {
            case LOW    -> 30;
            case MEDIUM -> 45;
            case HIGH   -> 60;
        };
    }

    public void cycleSignal() {
        this.flashCaution = false;
        signalState = switch (signalState) {
            case RED    -> SignalState.GREEN;
            case GREEN  -> SignalState.YELLOW;
            case YELLOW -> SignalState.RED;
        };
    }

    public void activateGreenCorridor() {
        this.flashCaution    = false;
        this.signalState     = SignalState.GREEN;
        this.timerSeconds    = 120;
        this.emergencyActive = true;
    }

    public void resetEmergency() {
        this.emergencyActive = false;
        this.timerSeconds = switch (this.density) {
            case LOW    -> 30;
            case MEDIUM -> 45;
            case HIGH   -> 60;
        };
    }

    public void toggleEmergencyCorridor() {
        if (this.emergencyActive) {
            resetEmergency();
        } else {
            activateGreenCorridor();
        }
    }

    public void toggleCaution() {
        this.flashCaution = !this.flashCaution;
        if (this.flashCaution) {
            this.signalState = SignalState.YELLOW;
            this.emergencyActive = false;
        }
    }
}

package com.saferoad.models;

public class EmergencyVehicle extends Vehicle {
    private String emergencyType;

    public EmergencyVehicle(String plate, String owner, String emergencyType, String expiry) {
        super(plate, owner, "Emergency - " + emergencyType, expiry, true);
        this.emergencyType = emergencyType;
    }

    public String getEmergencyType() { return emergencyType; }

    @Override
    public double calculateFine(String v) {
        return 0.0; // Emergency vehicles fully exempt
    }
}

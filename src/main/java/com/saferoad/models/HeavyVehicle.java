package com.saferoad.models;

public class HeavyVehicle extends Vehicle {
    public HeavyVehicle(String plate, String owner, String expiry, boolean insured) {
        super(plate, owner, "Heavy Vehicle", expiry, insured);
    }
    @Override
    public double calculateFine(String v) {
        return switch (v.toLowerCase()) {
            case "speeding"    -> 2000.0;
            case "overloading" -> 3000.0;
            case "red-light"   -> 2500.0;
            case "no-fitness"  -> 1500.0;
            default            -> 1000.0;
        };
    }
}

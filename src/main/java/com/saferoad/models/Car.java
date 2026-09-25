package com.saferoad.models;

public class Car extends Vehicle {
    public Car(String plate, String owner, String expiry, boolean insured) {
        super(plate, owner, "Car", expiry, insured);
    }
    @Override
    public double calculateFine(String v) {
        return switch (v.toLowerCase()) {
            case "speeding"    -> 1000.0;
            case "red-light"   -> 1500.0;
            case "no-seatbelt" -> 500.0;
            case "wrong-lane"  -> 400.0;
            default            -> 500.0;
        };
    }
}

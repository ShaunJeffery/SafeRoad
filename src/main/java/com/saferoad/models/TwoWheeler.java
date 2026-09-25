package com.saferoad.models;

public class TwoWheeler extends Vehicle {
    public TwoWheeler(String plate, String owner, String expiry, boolean insured) {
        super(plate, owner, "Two Wheeler", expiry, insured);
    }
    @Override
    public double calculateFine(String v) {
        return switch (v.toLowerCase()) {
            case "speeding"   -> 800.0;
            case "no-helmet"  -> 500.0;
            case "red-light"  -> 700.0;
            case "wrong-lane" -> 300.0;
            default           -> 300.0;
        };
    }
}

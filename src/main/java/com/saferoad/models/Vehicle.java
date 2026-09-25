package com.saferoad.models;

// Abstract base class: Vehicle (OOP - Inheritance root)
public abstract class Vehicle {

    // Encapsulation: private fields
    private String licensePlate;
    private String ownerName;
    private String vehicleType;
    private String registrationExpiry;
    private boolean insured;
    private boolean fitnessValid;
    private String pucExpiry;
    private int meritPoints; // Out of 12

    public Vehicle(String licensePlate, String ownerName, String vehicleType,
                   String registrationExpiry, boolean insured) {
        this.licensePlate = licensePlate;
        this.ownerName = ownerName;
        this.vehicleType = vehicleType;
        this.registrationExpiry = registrationExpiry;
        this.insured = insured;
        this.fitnessValid = true;
        this.pucExpiry = "2027-04-15";
        this.meritPoints = 12;
    }

    // Getters & Setters (Encapsulation)
    public String getLicensePlate()       { return licensePlate; }
    public String getOwnerName()          { return ownerName; }
    public String getVehicleType()        { return vehicleType; }
    public String getVehicleClass()       { return vehicleType; }
    public String getRegistrationExpiry() { return registrationExpiry; }
    public boolean isInsured()            { return insured; }
    public boolean getInsured()           { return insured; }
    public boolean isFitnessValid()       { return fitnessValid; }
    public boolean getFitnessValid()      { return fitnessValid; }
    public String getPucExpiry()          { return pucExpiry; }
    public String getPucExpiryDate()      { return pucExpiry; }
    public int getMeritPoints()           { return meritPoints; }
    public int getDriverMeritPoints()     { return meritPoints; }
    public String getFuelType()           { return "BS-VI Petrol/Diesel"; }
    public String getEngineNumber()       { return "ENG-" + Math.abs(licensePlate != null ? licensePlate.hashCode() % 1000000 : 102938); }
    public String getChassisNumber()      { return "CHS-" + Math.abs(licensePlate != null ? (licensePlate.hashCode() * 31) % 1000000 : 982341); }

    public void setInsured(boolean insured) { this.insured = insured; }
    public void setFitnessValid(boolean valid) { this.fitnessValid = valid; }
    public void setRegistrationExpiry(String expiry) { this.registrationExpiry = expiry; }
    public void setPucExpiry(String puc) { this.pucExpiry = puc; }
    public void deductMeritPoints(int points) { this.meritPoints = Math.max(0, this.meritPoints - points); }
    public void resetMeritPoints() { this.meritPoints = 12; }

    // Abstract polymorphic method — each subclass overrides this
    public abstract double calculateFine(String violationType);
}

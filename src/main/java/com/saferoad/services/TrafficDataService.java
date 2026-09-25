package com.saferoad.services;

import com.saferoad.models.*;
import org.springframework.stereotype.Service;
import java.util.*;

// Central data service — acts as an in-memory database
@Service
public class TrafficDataService {

    // Vehicle Registry (Polymorphic list — stores all Vehicle subtypes)
    private List<Vehicle> vehicles = new ArrayList<>(Arrays.asList(
        new Car("KA-01-AB-1234", "Rahul Sharma",   "2027-06-10", true),
        new TwoWheeler("MH-12-CD-5678", "Priya Singh",   "2026-11-22", true),
        new HeavyVehicle("DL-09-GH-3456", "Ram Logistics",  "2025-12-31", false),
        new EmergencyVehicle("DL-10-EM-9999", "City Hospital", "AMBULANCE", "2028-09-05"),
        new Car("TN-07-XY-5500", "Anita Mehta",    "2029-01-15", true)
    ));

    // Challans
    private List<Challan> challans = new ArrayList<>(Arrays.asList(
        new Challan("KA-01-AB-1234","Rahul Sharma","Car","Over Speeding",      1000.0,"2026-09-24","Speed Radar Cam #04"),
        new Challan("KA-01-AB-1234","Rahul Sharma","Car","Red Light Violation",1500.0,"2026-09-22","CCTV Cam #18"),
        new Challan("KA-01-AB-1234","Rahul Sharma","Car","Illegal Parking",     500.0,"2026-09-20","Inspector Raj"),
        new Challan("MH-12-CD-5678","Priya Singh","Two Wheeler","No Helmet",    500.0,"2026-09-23","Officer Meena"),
        new Challan("DL-09-GH-3456","Ram Logistics","Heavy Vehicle","Overloading",3000.0,"2026-09-21","Inspector Raj")
    ));

    // Traffic Intersections with Area Sector and Officer Assignment
    private List<TrafficIntersection> intersections = new ArrayList<>(Arrays.asList(
        new TrafficIntersection("MG Road Junction", "Central CBD", "Inspector Raj", "4-Way Main Signal"),
        new TrafficIntersection("Brigade Road Cross", "Central CBD", "Officer Meena", "Pedestrian & 3-Way Signal"),
        new TrafficIntersection("Silk Board Flyover", "Tech Corridor", "Officer Kumar", "High-Volume Flyover Ramp"),
        new TrafficIntersection("Hebbal Interchange", "North Sector", "Officer Rao", "Airport Expressway Inflow"),
        new TrafficIntersection("Marathahalli Bridge", "East Sector", "Officer Priya", "Outer Ring Road Underpass"),
        new TrafficIntersection("Whitefield Main Rd", "Tech Corridor", "Officer Dev", "ITPL Main Gate Signal"),
        new TrafficIntersection("Indiranagar 100ft Rd", "East Sector", "Officer Meena", "Commercial Cross Signal"),
        new TrafficIntersection("Koramangala 80ft Rd", "South Sector", "Inspector Raj", "Sony World Circle Signal"),
        new TrafficIntersection("Electronic City Phase 1", "Tech Corridor", "Officer Kumar", "Expressway Toll Exit"),
        new TrafficIntersection("Jayanagar 4th Block", "South Sector", "Officer Rao", "Complex Roundabout Signal")
    ));

    // Accident Reports
    private List<AccidentReport> accidents = new ArrayList<>(Arrays.asList(
        new AccidentReport("MG Road, Bengaluru","2026-08-28 09:30","SEVERE","KA-01-AB-1234, DL-09-GH-3456","Citizen","Multi-vehicle collision near Trinity Circle."),
        new AccidentReport("NICE Road KM-24","2026-08-29 14:15","MODERATE","TN-07-XY-5500","Officer Meena","Single vehicle accident, minor injuries.")
    ));

    // Traffic Alerts
    private List<TrafficAlert> alerts = new ArrayList<>(Arrays.asList(
        new TrafficAlert("RoadClosure","MG Road Closed","MG Road, Bengaluru","MG Road between Trinity and Residency closed for cable work.","Critical","SMS, Email","2026-08-31 08:00"),
        new TrafficAlert("Construction","Metro Construction","Silk Board","Heavy construction near Silk Board. Expect 30-45 min delays.","High","SMS, VMS","2026-08-30 14:30"),
        new TrafficAlert("Weather","Heavy Rainfall Alert","North Bengaluru","IMD orange alert. Flooding expected on underpasses.","High","App, SMS","2026-08-31 06:00")
    ));

    // --- Vehicle methods ---
    public List<Vehicle> getVehicles() { return vehicles; }
    public void addVehicle(Vehicle v)  { vehicles.add(v); }

    // --- Challan methods ---
    public List<Challan> getChallans() { return challans; }
    public List<Challan> getChallansForVehicle(String plate) {
        if (plate == null || plate.isBlank() || plate.equalsIgnoreCase("citizen") || plate.equalsIgnoreCase("admin")) {
            return challans;
        }
        List<Challan> matched = challans.stream()
            .filter(c -> c.getLicensePlate().replaceAll("[^A-Za-z0-9]", "").equalsIgnoreCase(plate.replaceAll("[^A-Za-z0-9]", "")))
            .toList();
        return matched.isEmpty() ? challans : matched;
    }
    public void addChallan(Challan c)  { challans.add(c); }
    public Challan findChallan(String id) {
        return challans.stream().filter(c -> c.getChallanId().equals(id)).findFirst().orElse(null);
    }

    // Polymorphic fine calculation — dispatches to right Vehicle subclass
    public double calculateFine(String vehicleType, String violation) {
        Vehicle v = switch (vehicleType) {
            case "Car"           -> new Car("X","X","X",true);
            case "Two Wheeler"   -> new TwoWheeler("X","X","X",true);
            case "Heavy Vehicle" -> new HeavyVehicle("X","X","X",true);
            default              -> new Car("X","X","X",true);
        };
        return v.calculateFine(violation);
    }

    // --- Intersection methods ---
    public List<TrafficIntersection> getIntersections() { return intersections; }
    
    public TrafficIntersection findIntersection(String location) {
        return intersections.stream().filter(i -> i.getLocation().equalsIgnoreCase(location.trim())).findFirst().orElse(null);
    }

    public void addIntersection(TrafficIntersection i) {
        // Prevent duplicate location names
        if (findIntersection(i.getLocation()) == null) {
            intersections.add(0, i);
        }
    }

    public void removeIntersection(String location) {
        intersections.removeIf(i -> i.getLocation().equalsIgnoreCase(location.trim()));
    }

    public List<TrafficIntersection> filterIntersections(String query, String zone, String officer) {
        return intersections.stream().filter(i -> {
            boolean matchesQuery = query == null || query.isBlank()
                || i.getLocation().toLowerCase().contains(query.toLowerCase().trim())
                || i.getZone().toLowerCase().contains(query.toLowerCase().trim())
                || i.getAssignedOfficer().toLowerCase().contains(query.toLowerCase().trim())
                || i.getJunctionType().toLowerCase().contains(query.toLowerCase().trim());

            boolean matchesZone = zone == null || zone.isBlank() || zone.equalsIgnoreCase("ALL")
                || i.getZone().equalsIgnoreCase(zone.trim());

            boolean matchesOfficer = officer == null || officer.isBlank() || officer.equalsIgnoreCase("ALL")
                || i.getAssignedOfficer().toLowerCase().contains(officer.toLowerCase().trim());

            return matchesQuery && matchesZone && matchesOfficer;
        }).toList();
    }

    public List<String> getAvailableZones() {
        return List.of("ALL", "Central CBD", "Tech Corridor", "East Sector", "North Sector", "South Sector");
    }

    // --- Accident methods ---
    public List<AccidentReport> getAccidents()  { return accidents; }
    public void addAccident(AccidentReport r)   { accidents.add(r); }
    public AccidentReport findAccident(String id) {
        return accidents.stream().filter(a -> a.getReportId().equals(id)).findFirst().orElse(null);
    }

    // --- Alert methods ---
    public List<TrafficAlert> getAlerts()      { return alerts; }
    public void addAlert(TrafficAlert alert)   { alerts.add(alert); }
    public TrafficAlert findAlert(String id) {
        return alerts.stream().filter(a -> a.getAlertId().equals(id)).findFirst().orElse(null);
    }

    // --- Complaints / Grievances ---
    private List<Complaint> complaints = new ArrayList<>(Arrays.asList(
        new Complaint("KA-01-AB-1234", "Pothole / Road Hazard", "Trinity Circle Outer Lane", "Deep pothole causing sudden braking and near collisions."),
        new Complaint("MH-12-CD-5678", "Faulty Traffic Signal", "Brigade Road Cross", "Signal timer stuck on Red for over 4 minutes during peak hours.")
    ));

    public List<Complaint> getComplaints() { return complaints; }
    public List<Complaint> getComplaintsForVehicle(String plate) {
        if (plate == null || plate.isBlank() || plate.equalsIgnoreCase("citizen") || plate.equalsIgnoreCase("admin")) {
            return complaints;
        }
        List<Complaint> matched = complaints.stream()
            .filter(c -> c.getVehiclePlate().replaceAll("[^A-Za-z0-9]", "").equalsIgnoreCase(plate.replaceAll("[^A-Za-z0-9]", "")))
            .toList();
        return matched.isEmpty() ? complaints : matched;
    }
    public void addComplaint(Complaint c) { complaints.add(0, c); }

    // ============================================================
    // 3. HIGHWAYS DEPARTMENT (NHAI / PWD Work Orders & Blackspots)
    // ============================================================
    private List<RoadWorkOrder> workOrders = new ArrayList<>(Arrays.asList(
        new RoadWorkOrder("WO-3001", "Trinity Circle Underpass", "NH-44 KM 12.4", "Pothole & Surface Erosion", "Deep 15cm road crater causing multi-vehicle swerving.", "NHAI Rapid Asphalt Crew #02", 4.5, "CRITICAL", "IN_PROGRESS", "2026-09-20", "2026-09-28"),
        new RoadWorkOrder("WO-3002", "Silk Board Flyover Ramp", "Hosur Road Junction", "Guardrail & Shock Attenuator", "Damaged high-tension steel crash barrier post accident.", "L&T Highway Infrastructure", 8.2, "HIGH", "PENDING", "2026-09-22", "2026-10-05"),
        new RoadWorkOrder("WO-3003", "NICE Road Interchange KM-24", "NICE Expressway", "Drainage & Water Stagnation", "Recurrent hydroplaning zone during sudden downpours.", "NICE Highway Maintenance", 6.0, "MEDIUM", "COMPLETED", "2026-09-15", "2026-09-23")
    ));

    public List<RoadWorkOrder> getWorkOrders() { return workOrders; }
    public RoadWorkOrder findWorkOrder(String id) {
        return workOrders.stream().filter(w -> w.getOrderId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
    public void addWorkOrder(RoadWorkOrder order) { workOrders.add(0, order); }
    public void updateWorkOrderStatus(String id, String status) {
        RoadWorkOrder wo = findWorkOrder(id);
        if (wo != null) wo.setStatus(status);
    }

    // ============================================================
    // 4. HEALTH DEPARTMENT (Hospital Trauma Registry & Golden Hour)
    // ============================================================
    private List<TraumaCase> traumaCases = new ArrayList<>(Arrays.asList(
        new TraumaCase("TRM-5001", "Rohan Verma", 28, "Male", "ACC-1001", "Victoria Super-Specialty Hospital", "CRITICAL_TRIAGE", "Active Golden Hour (18m remaining)", "Ambulance BLS-09", true, "ICU_ADMITTED", "2026-09-25 11:30"),
        new TraumaCase("TRM-5002", "Sunita Nair", 34, "Female", "ACC-1002", "Manipal Trauma Center", "SEVERE_CARE", "Window Expired (Stabilized)", "Ambulance ALS-04", false, "STABILIZED", "2026-09-24 16:45"),
        new TraumaCase("TRM-5003", "Deepak Rao", 42, "Male", "Direct Arrival", "Apollo Emergency Care", "STABLE_MINOR", "Minor Outpatient Care", "Ambulance BLS-02", false, "DISCHARGED", "2026-09-23 09:15")
    ));

    public List<TraumaCase> getTraumaCases() { return traumaCases; }
    public TraumaCase findTraumaCase(String id) {
        return traumaCases.stream().filter(t -> t.getCaseId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
    public void addTraumaCase(TraumaCase tc) { traumaCases.add(0, tc); }
    public void updateTraumaStatus(String id, String status) {
        TraumaCase tc = findTraumaCase(id);
        if (tc != null) tc.setStatus(status);
    }

    // ============================================================
    // 5. INSURANCE AGENCY (MACT Claims, FIR Verification, Fraud)
    // ============================================================
    private List<InsuranceClaim> insuranceClaims = new ArrayList<>(Arrays.asList(
        new InsuranceClaim("CLM-7001", "POL-984421", "KA-01-AB-1234", "Rahul Sharma", "ACC-1001", "National General Insurance", "THIRD_PARTY_LIABILITY", 85000.0, 78000.0, "UNDER_AUDIT", "2026-09-24", "Police FIR ACC-1001 inspected. Verified damage matching speed radar evidence."),
        new InsuranceClaim("CLM-7002", "POL-332910", "TN-07-XY-5500", "Anita Mehta", "ACC-1002", "ICICI Lombard", "OWN_DAMAGE", 42000.0, 42000.0, "APPROVED_SETTLED", "2026-09-21", "Single vehicle guardrail collision. Spot survey and surveyor estimate approved."),
        new InsuranceClaim("CLM-7003", "POL-119284", "DL-09-GH-3456", "Ram Logistics", "ACC-1001", "HDFC ERGO", "COMPREHENSIVE_TOTAL_LOSS", 250000.0, 0.0, "REJECTED_FRAUD", "2026-09-18", "Commercial vehicle overloading violation active (CHL-1005). Claim repudiated under Section 146 MV Act.")
    ));

    public List<InsuranceClaim> getInsuranceClaims() { return insuranceClaims; }
    public InsuranceClaim findInsuranceClaim(String id) {
        return insuranceClaims.stream().filter(c -> c.getClaimId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
    public void addInsuranceClaim(InsuranceClaim claim) { insuranceClaims.add(0, claim); }
    public void updateClaimStatus(String id, String status, Double approvedAmt, String notes) {
        InsuranceClaim claim = findInsuranceClaim(id);
        if (claim != null) {
            claim.setStatus(status);
            if (approvedAmt != null) claim.setApprovedAmount(approvedAmt);
            if (notes != null && !notes.isBlank()) claim.setAssessorNotes(notes);
        }
    }

    // --- Transport / Vehicle Specific actions ---
    public void renewVehicleFitness(String plate) {
        vehicles.stream()
            .filter(v -> v.getLicensePlate().equalsIgnoreCase(plate.trim()))
            .findFirst()
            .ifPresent(v -> {
                v.setFitnessValid(true);
                v.setRegistrationExpiry("2030-12-31");
                v.setPucExpiry("2028-06-30");
            });
    }

    public void adjustDriverMeritPoints(String plate, int points) {
        vehicles.stream()
            .filter(v -> v.getLicensePlate().equalsIgnoreCase(plate.trim()))
            .findFirst()
            .ifPresent(v -> v.deductMeritPoints(points));
    }

    // --- Dashboard stats ---
    public long getTotalAccidents()  { return accidents.size(); }
    public long getPendingAccidents(){ return accidents.stream().filter(a -> a.getStatus().equals("Pending")).count(); }
    public long getTotalChallans()   { return challans.size(); }
    public long getUnpaidChallans()  { return challans.stream().filter(c -> c.getStatus().equals("Unpaid")).count(); }
    public long getActiveAlerts()    { return alerts.stream().filter(TrafficAlert::isActive).count(); }
    public long getTotalVehicles()   { return vehicles.size(); }
    public long getTotalComplaints() { return complaints.size(); }
    public long getTotalWorkOrders() { return workOrders.size(); }
    public long getTotalTraumaCases(){ return traumaCases.size(); }
    public long getTotalClaims()     { return insuranceClaims.size(); }
}

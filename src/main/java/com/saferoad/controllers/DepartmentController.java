package com.saferoad.controllers;

import com.saferoad.models.*;
import com.saferoad.services.TrafficDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DepartmentController {

    @Autowired
    private TrafficDataService dataService;

    // ============================================================
    // 1. POLICE DEPARTMENT PORTAL (/police)
    // ============================================================
    @GetMapping("/police")
    public String policePortal(
            @RequestParam(required = false, defaultValue = "signals") String tab,
            Authentication auth,
            Model model) {
        String officerName = auth != null ? auth.getName() : "Officer";
        model.addAttribute("officerName", officerName);
        model.addAttribute("activeTab", tab);
        model.addAttribute("accidents", dataService.getAccidents());
        model.addAttribute("challans", dataService.getChallans());
        model.addAttribute("intersections", dataService.getIntersections());
        model.addAttribute("totalAccidents", dataService.getTotalAccidents());
        model.addAttribute("pendingAccidents", dataService.getPendingAccidents());
        model.addAttribute("totalChallans", dataService.getTotalChallans());
        model.addAttribute("unpaidChallans", dataService.getUnpaidChallans());
        return "police";
    }

    // ============================================================
    // 2. TRANSPORT DEPARTMENT (RTO) PORTAL (/transport)
    // ============================================================
    @GetMapping("/transport")
    public String transportPortal(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String vtype,
            @RequestParam(required = false, defaultValue = "registry") String tab,
            Authentication auth,
            Model model) {

        String officerName = auth != null ? auth.getName() : "RTO Inspector";
        model.addAttribute("officerName", officerName);
        model.addAttribute("activeTab", tab);
        model.addAttribute("vehicles", dataService.getVehicles());
        model.addAttribute("challans", dataService.getChallans());
        model.addAttribute("totalVehicles", dataService.getTotalVehicles());
        
        long heavyCount = dataService.getVehicles().stream().filter(v -> v instanceof HeavyVehicle).count();
        long commercialViolations = dataService.getChallans().stream().filter(c -> "Heavy Vehicle".equalsIgnoreCase(c.getVehicleType())).count();
        model.addAttribute("heavyCount", heavyCount);
        model.addAttribute("commercialViolations", commercialViolations);

        return "transport";
    }

    @PostMapping("/transport/fitness/renew")
    public String renewFitness(@RequestParam String plate) {
        dataService.renewVehicleFitness(plate);
        return "redirect:/transport?tab=registry&renewed=" + plate;
    }

    @PostMapping("/transport/merit/deduct")
    public String deductMerit(@RequestParam String plate, @RequestParam int points) {
        dataService.adjustDriverMeritPoints(plate, points);
        return "redirect:/transport?tab=demerit&meritUpdated=" + plate;
    }

    // ============================================================
    // 3. HIGHWAYS DEPARTMENT (NHAI / PWD) PORTAL (/highways)
    // ============================================================
    @GetMapping("/highways")
    public String highwaysPortal(
            @RequestParam(required = false, defaultValue = "workorders") String tab,
            Authentication auth,
            Model model) {
        String officerName = auth != null ? auth.getName() : "NHAI Engineer";
        model.addAttribute("officerName", officerName);
        model.addAttribute("activeTab", tab);
        model.addAttribute("workOrders", dataService.getWorkOrders());
        model.addAttribute("complaints", dataService.getComplaints());
        model.addAttribute("alerts", dataService.getAlerts());
        model.addAttribute("accidents", dataService.getAccidents());
        model.addAttribute("totalWorkOrders", dataService.getTotalWorkOrders());
        
        long inProgressCount = dataService.getWorkOrders().stream().filter(w -> "IN_PROGRESS".equalsIgnoreCase(w.getStatus())).count();
        long pendingGrievances = dataService.getComplaints().stream().filter(c -> "Submitted".equalsIgnoreCase(c.getStatus())).count();
        model.addAttribute("inProgressCount", inProgressCount);
        model.addAttribute("pendingGrievances", pendingGrievances);

        return "highways";
    }

    @PostMapping("/highways/workorder/create")
    public String createWorkOrder(
            @RequestParam String location,
            @RequestParam String highwayStretch,
            @RequestParam String issueCategory,
            @RequestParam String description,
            @RequestParam String assignedCrew,
            @RequestParam double budgetInLakhs,
            @RequestParam String priority,
            @RequestParam String targetCompletionDate) {

        RoadWorkOrder wo = new RoadWorkOrder(location, highwayStretch, issueCategory, description, assignedCrew, budgetInLakhs, priority, targetCompletionDate);
        dataService.addWorkOrder(wo);
        return "redirect:/highways?tab=workorders&created=true";
    }

    @PostMapping("/highways/workorder/status")
    public String updateWorkOrderStatus(@RequestParam String orderId, @RequestParam String status) {
        dataService.updateWorkOrderStatus(orderId, status);
        return "redirect:/highways?tab=workorders&statusUpdated=" + orderId;
    }

    // ============================================================
    // 4. HEALTH DEPARTMENT (EMS / HOSPITAL) PORTAL (/health)
    // ============================================================
    @GetMapping("/health")
    public String healthPortal(
            @RequestParam(required = false, defaultValue = "trauma") String tab,
            Authentication auth,
            Model model) {
        String officerName = auth != null ? auth.getName() : "Chief Medical Officer";
        model.addAttribute("officerName", officerName);
        model.addAttribute("activeTab", tab);
        model.addAttribute("traumaCases", dataService.getTraumaCases());
        model.addAttribute("accidents", dataService.getAccidents());
        model.addAttribute("intersections", dataService.getIntersections());
        model.addAttribute("totalTraumaCases", dataService.getTotalTraumaCases());

        long criticalCount = dataService.getTraumaCases().stream().filter(t -> "CRITICAL_TRIAGE".equalsIgnoreCase(t.getTriageLevel())).count();
        long activeCorridors = dataService.getIntersections().stream().filter(TrafficIntersection::isEmergencyActive).count();
        model.addAttribute("criticalCount", criticalCount);
        model.addAttribute("activeCorridors", activeCorridors);

        return "health";
    }

    @PostMapping("/health/trauma/admit")
    public String admitTraumaCase(
            @RequestParam String patientName,
            @RequestParam int patientAge,
            @RequestParam String gender,
            @RequestParam(required = false) String linkedAccidentId,
            @RequestParam String hospitalName,
            @RequestParam String triageLevel,
            @RequestParam String ambulanceUnit,
            @RequestParam(defaultValue = "false") boolean corridorRequested) {

        TraumaCase tc = new TraumaCase(patientName, patientAge, gender, linkedAccidentId, hospitalName, triageLevel, ambulanceUnit, corridorRequested);
        dataService.addTraumaCase(tc);
        return "redirect:/health?tab=trauma&admitted=true";
    }

    @PostMapping("/health/trauma/status")
    public String updateTraumaCaseStatus(@RequestParam String caseId, @RequestParam String status) {
        dataService.updateTraumaStatus(caseId, status);
        return "redirect:/health?tab=trauma&statusUpdated=" + caseId;
    }

    @PostMapping("/health/corridor/request")
    public String requestCorridorFromHealth(@RequestParam String location) {
        TrafficIntersection t = dataService.findIntersection(location);
        if (t != null) t.activateGreenCorridor();
        return "redirect:/health?tab=corridors&corridorActivated=" + location;
    }

    @PostMapping("/health/corridor/toggle")
    public String toggleCorridorFromHealth(@RequestParam String location) {
        TrafficIntersection t = dataService.findIntersection(location);
        if (t != null) t.toggleEmergencyCorridor();
        return "redirect:/health?tab=corridors&toggled=" + location;
    }

    @PostMapping("/health/corridor/reset")
    public String resetCorridorFromHealth(@RequestParam String location) {
        TrafficIntersection t = dataService.findIntersection(location);
        if (t != null) t.resetEmergency();
        return "redirect:/health?tab=corridors&reset=" + location;
    }

    // ============================================================
    // 5. INSURANCE AGENCY (MACT & CLAIMS) PORTAL (/insurance)
    // ============================================================
    @GetMapping("/insurance")
    public String insurancePortal(
            @RequestParam(required = false, defaultValue = "claims") String tab,
            Authentication auth,
            Model model) {
        String officerName = auth != null ? auth.getName() : "Claims Surveyor / Assessor";
        model.addAttribute("officerName", officerName);
        model.addAttribute("activeTab", tab);
        model.addAttribute("claims", dataService.getInsuranceClaims());
        model.addAttribute("accidents", dataService.getAccidents());
        model.addAttribute("vehicles", dataService.getVehicles());
        model.addAttribute("totalClaims", dataService.getTotalClaims());

        long underAuditCount = dataService.getInsuranceClaims().stream().filter(c -> "UNDER_AUDIT".equalsIgnoreCase(c.getStatus())).count();
        long settledCount = dataService.getInsuranceClaims().stream().filter(c -> "APPROVED_SETTLED".equalsIgnoreCase(c.getStatus())).count();
        model.addAttribute("underAuditCount", underAuditCount);
        model.addAttribute("settledCount", settledCount);

        return "insurance";
    }

    @PostMapping("/insurance/claims/approve")
    public String approveClaim(@RequestParam String claimId, @RequestParam double approvedAmount, @RequestParam(required = false) String assessorNotes) {
        dataService.updateClaimStatus(claimId, "APPROVED_SETTLED", approvedAmount, assessorNotes != null ? assessorNotes : "Settlement approved post MACT FIR audit.");
        return "redirect:/insurance?settled=" + claimId;
    }

    @PostMapping("/insurance/claims/reject")
    public String rejectClaim(@RequestParam String claimId, @RequestParam String assessorNotes) {
        dataService.updateClaimStatus(claimId, "REJECTED_FRAUD", 0.0, assessorNotes);
        return "redirect:/insurance?rejected=" + claimId;
    }

    @PostMapping("/insurance/claims/file")
    public String fileClaim(
            @RequestParam String policyNumber,
            @RequestParam String vehiclePlate,
            @RequestParam String policyholderName,
            @RequestParam(required = false) String linkedAccidentId,
            @RequestParam String insuranceCompany,
            @RequestParam String claimType,
            @RequestParam double claimedAmount,
            @RequestParam(required = false) String assessorNotes) {

        InsuranceClaim claim = new InsuranceClaim(policyNumber, vehiclePlate, policyholderName, linkedAccidentId, insuranceCompany, claimType, claimedAmount, 0.0, "UNDER_AUDIT", assessorNotes);
        dataService.addInsuranceClaim(claim);
        return "redirect:/insurance?filed=true";
    }
}

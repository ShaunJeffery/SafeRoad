package com.saferoad.controllers;

import com.saferoad.services.TrafficDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private TrafficDataService dataService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model, org.springframework.security.core.Authentication auth) {
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CITIZEN"))) {
            return "redirect:/citizen";
        }
        model.addAttribute("totalAccidents",   dataService.getTotalAccidents());
        model.addAttribute("pendingAccidents", dataService.getPendingAccidents());
        model.addAttribute("totalChallans",    dataService.getTotalChallans());
        model.addAttribute("unpaidChallans",   dataService.getUnpaidChallans());
        model.addAttribute("activeAlerts",     dataService.getActiveAlerts());
        model.addAttribute("totalVehicles",    dataService.getTotalVehicles());
        model.addAttribute("recentAccidents",  dataService.getAccidents());
        model.addAttribute("recentChallans",   dataService.getChallans());
        return "dashboard";
    }

    @GetMapping({"/citizen", "/citizen/challans"})
    public String citizenPortal(@org.springframework.web.bind.annotation.RequestParam(name = "tab", required = false, defaultValue = "challans") String tab,
                                Model model, org.springframework.security.core.Authentication auth) {
        String plate = (auth != null) ? auth.getName() : "KA-01-AB-1234";
        var challans = dataService.getChallansForVehicle(plate);
        double unpaidSum = challans.stream()
            .filter(c -> "Unpaid".equalsIgnoreCase(c.getStatus()))
            .mapToDouble(c -> c.getFineAmount())
            .sum();

        model.addAttribute("activeTab", tab);
        model.addAttribute("vehiclePlate", plate);
        model.addAttribute("challans", challans);
        model.addAttribute("unpaidAmount", unpaidSum);
        model.addAttribute("alerts", dataService.getAlerts());
        model.addAttribute("activeAlertCount", dataService.getActiveAlerts());
        model.addAttribute("complaints", dataService.getComplaintsForVehicle(plate));
        return "citizen";
    }

    @GetMapping("/citizen/alerts")
    public String citizenAlerts() {
        return "redirect:/citizen?tab=alerts";
    }

    @GetMapping("/citizen/complaints")
    public String citizenComplaints() {
        return "redirect:/citizen?tab=complaints";
    }

    @org.springframework.web.bind.annotation.PostMapping("/citizen/complaint")
    public String fileComplaint(@org.springframework.web.bind.annotation.RequestParam String category,
                                @org.springframework.web.bind.annotation.RequestParam String location,
                                @org.springframework.web.bind.annotation.RequestParam String description,
                                org.springframework.security.core.Authentication auth) {
        String plate = (auth != null) ? auth.getName() : "KA-01-AB-1234";
        com.saferoad.models.Complaint c = new com.saferoad.models.Complaint(plate, category, location, description);
        dataService.addComplaint(c);
        return "redirect:/citizen?tab=complaints&submitted=true";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

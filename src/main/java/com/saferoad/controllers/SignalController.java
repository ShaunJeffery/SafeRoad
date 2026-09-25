package com.saferoad.controllers;

import com.saferoad.models.TrafficIntersection;
import com.saferoad.services.TrafficDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/signals")
public class SignalController {

    @Autowired
    private TrafficDataService dataService;

    @GetMapping
    public String listSignals(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String zone,
            @RequestParam(required = false) String officer,
            Authentication authentication,
            Model model) {

        String currentOfficer = authentication != null ? authentication.getName() : "Officer";
        List<TrafficIntersection> filtered = dataService.filterIntersections(query, zone, officer);
        long activeCorridors = dataService.getIntersections().stream().filter(TrafficIntersection::isEmergencyActive).count();

        model.addAttribute("intersections", filtered);
        model.addAttribute("allIntersections", dataService.getIntersections());
        model.addAttribute("totalCount", dataService.getIntersections().size());
        model.addAttribute("filteredCount", filtered.size());
        model.addAttribute("activeCorridors", activeCorridors);
        model.addAttribute("zones", dataService.getAvailableZones());
        model.addAttribute("currentOfficer", currentOfficer);
        model.addAttribute("query", query != null ? query : "");
        model.addAttribute("selectedZone", zone != null ? zone : "ALL");
        model.addAttribute("selectedOfficer", officer != null ? officer : "ALL");

        return "signals";
    }

    @PostMapping("/add")
    public String addIntersection(
            @RequestParam String location,
            @RequestParam(defaultValue = "Central CBD") String zone,
            @RequestParam(required = false) String assignedOfficer,
            @RequestParam(defaultValue = "4-Way Cross Signal") String junctionType,
            @RequestParam(defaultValue = "LOW") String density,
            Authentication authentication) {

        String officer = assignedOfficer != null && !assignedOfficer.isBlank()
                ? assignedOfficer
                : (authentication != null ? authentication.getName() : "Duty Officer");

        TrafficIntersection ti = new TrafficIntersection(location, zone, officer, junctionType);
        ti.setDensity(TrafficIntersection.TrafficDensity.valueOf(density));
        dataService.addIntersection(ti);

        return "redirect:/signals?query=" + UriUtils.encode(location, StandardCharsets.UTF_8);
    }

    @PostMapping("/delete")
    public String deleteIntersection(@RequestParam String location) {
        dataService.removeIntersection(location);
        return "redirect:/signals";
    }

    @PostMapping("/cycle")
    public String cycleSignal(@RequestParam String location, @RequestParam(required = false) String returnQuery) {
        TrafficIntersection t = dataService.findIntersection(location);
        if (t != null) t.cycleSignal();
        return "redirect:/signals" + (returnQuery != null && !returnQuery.isBlank() ? "?" + returnQuery : "");
    }

    @PostMapping("/emergency")
    public String emergencyOverride(@RequestParam String location, @RequestParam(required = false) String returnQuery) {
        TrafficIntersection t = dataService.findIntersection(location);
        if (t != null) t.toggleEmergencyCorridor();
        return "redirect:/signals" + (returnQuery != null && !returnQuery.isBlank() ? "?" + returnQuery : "");
    }

    @PostMapping("/emergency/reset")
    public String resetEmergency(@RequestParam String location, @RequestParam(required = false) String returnQuery) {
        TrafficIntersection t = dataService.findIntersection(location);
        if (t != null) t.resetEmergency();
        return "redirect:/signals" + (returnQuery != null && !returnQuery.isBlank() ? "?" + returnQuery : "");
    }

    @PostMapping("/caution")
    public String toggleCaution(@RequestParam String location, @RequestParam(required = false) String returnQuery) {
        TrafficIntersection t = dataService.findIntersection(location);
        if (t != null) t.toggleCaution();
        return "redirect:/signals" + (returnQuery != null && !returnQuery.isBlank() ? "?" + returnQuery : "");
    }

    @PostMapping("/density")
    public String setDensity(@RequestParam String location, @RequestParam String density, @RequestParam(required = false) String returnQuery) {
        TrafficIntersection t = dataService.findIntersection(location);
        if (t != null) t.setDensity(TrafficIntersection.TrafficDensity.valueOf(density));
        return "redirect:/signals" + (returnQuery != null && !returnQuery.isBlank() ? "?" + returnQuery : "");
    }
}

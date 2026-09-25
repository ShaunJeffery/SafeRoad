package com.saferoad.controllers;

import com.saferoad.models.*;
import com.saferoad.services.TrafficDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private TrafficDataService dataService;

    @GetMapping
    public String listVehicles(Model model) {
        model.addAttribute("vehicles", dataService.getVehicles());
        return "vehicles";
    }

    @PostMapping("/add")
    public String addVehicle(@RequestParam String plate,
                             @RequestParam String owner,
                             @RequestParam String type,
                             @RequestParam String expiry,
                             @RequestParam(defaultValue = "false") boolean insured) {
        Vehicle v = switch (type) {
            case "Two Wheeler"   -> new TwoWheeler(plate, owner, expiry, insured);
            case "Heavy Vehicle" -> new HeavyVehicle(plate, owner, expiry, insured);
            case "Emergency"     -> new EmergencyVehicle(plate, owner, "EMERGENCY", expiry);
            default              -> new Car(plate, owner, expiry, insured);
        };
        dataService.addVehicle(v);
        return "redirect:/vehicles";
    }
}

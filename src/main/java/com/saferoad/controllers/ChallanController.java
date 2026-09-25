package com.saferoad.controllers;

import com.saferoad.models.Challan;
import com.saferoad.services.TrafficDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/challans")
public class ChallanController {

    @Autowired
    private TrafficDataService dataService;

    @GetMapping
    public String listChallans(Model model) {
        model.addAttribute("challans", dataService.getChallans());
        return "challans";
    }

    // Issue a new challan — uses polymorphic calculateFine()
    @PostMapping("/issue")
    public String issueChallan(@RequestParam String plate,
                               @RequestParam String owner,
                               @RequestParam String vehicleType,
                               @RequestParam String violation) {
        double fine = dataService.calculateFine(vehicleType, violation); // Polymorphism
        Challan c = new Challan(plate, owner, vehicleType, violation, fine,
                                LocalDate.now().toString(), "Current Officer");
        dataService.addChallan(c);
        return "redirect:/challans";
    }

    @PostMapping("/pay/{id}")
    public String payChallan(@PathVariable String id, org.springframework.security.core.Authentication auth) {
        Challan c = dataService.findChallan(id);
        if (c != null) c.setStatus("Paid");
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CITIZEN"))) {
            return "redirect:/citizen";
        }
        return "redirect:/challans";
    }

    @PostMapping("/dispute/{id}")
    public String disputeChallan(@PathVariable String id, org.springframework.security.core.Authentication auth) {
        Challan c = dataService.findChallan(id);
        if (c != null) c.setStatus("Disputed");
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CITIZEN"))) {
            return "redirect:/citizen";
        }
        return "redirect:/challans";
    }
}

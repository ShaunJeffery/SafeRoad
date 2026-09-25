package com.saferoad.controllers;

import com.saferoad.models.TrafficAlert;
import com.saferoad.services.TrafficDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private TrafficDataService dataService;

    @GetMapping
    public String listAlerts(Model model) {
        model.addAttribute("alerts", dataService.getAlerts());
        return "alerts";
    }

    @PostMapping("/broadcast")
    public String broadcast(@RequestParam String type,
                            @RequestParam String title,
                            @RequestParam String location,
                            @RequestParam String message,
                            @RequestParam String severity,
                            @RequestParam String channels) {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        TrafficAlert alert = new TrafficAlert(type, title, location, message, severity, channels, ts);
        dataService.addAlert(alert);
        return "redirect:/alerts";
    }

    @PostMapping("/resolve/{id}")
    public String resolve(@PathVariable String id) {
        TrafficAlert a = dataService.findAlert(id);
        if (a != null) a.resolve();
        return "redirect:/alerts";
    }
}

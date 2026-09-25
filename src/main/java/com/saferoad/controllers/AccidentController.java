package com.saferoad.controllers;

import com.saferoad.models.AccidentReport;
import com.saferoad.services.TrafficDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/accidents")
public class AccidentController {

    @Autowired
    private TrafficDataService dataService;

    @GetMapping
    public String listAccidents(Model model) {
        model.addAttribute("accidents", dataService.getAccidents());
        return "accidents";
    }

    @PostMapping("/add")
    public String addAccident(@RequestParam String location,
                              @RequestParam String severity,
                              @RequestParam String vehicles,
                              @RequestParam String reportedBy,
                              @RequestParam String description) {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        AccidentReport r = new AccidentReport(location, ts, severity, vehicles, reportedBy, description);
        dataService.addAccident(r);
        return "redirect:/accidents";
    }

    @PostMapping("/investigate/{id}")
    public String investigate(@PathVariable String id) {
        AccidentReport r = dataService.findAccident(id);
        if (r != null) r.setStatus("Under Investigation");
        return "redirect:/accidents";
    }

    @PostMapping("/close/{id}")
    public String close(@PathVariable String id) {
        AccidentReport r = dataService.findAccident(id);
        if (r != null) r.setStatus("Closed");
        return "redirect:/accidents";
    }
}

# 🛡️ SafeRoad System — Smart Traffic & Incident Management Platform

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)](https://tailwindcss.com/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)](LICENSE)

> **SafeRoad System** is an integrated multi-agency digital platform designed to connect law enforcement, transport authorities, emergency medical responders, insurance providers, and citizens onto a single unified real-time traffic and road safety operations system.

---

## 📌 Problem Statement

Road traffic incidents, emergency response delays, and traffic rule violations are often exacerbated by isolated communication silos between administrative bodies:
- **Traffic Police** manage street enforcement and incident records independently.
- **Emergency Medical Services (EMS)** face critical delays reaching trauma victims during the "Golden Hour" due to uncoordinated traffic signals.
- **Regional Transport Offices (RTO)** lack instantaneous sync with on-road traffic violations and repeat offender history.
- **Insurance Agencies** suffer from lengthy claim investigation cycles and manual FIR verification.
- **Citizens** lack transparent access to view citations, report road hazards, or receive real-time localized advisories.

---

## 💡 The SafeRoad Solution

SafeRoad bridges these gaps by providing an interconnected digital infrastructure where an event in one department immediately activates real-time intelligence for the others.

```
                  +-----------------------------------+
                  |          SafeRoad Core            |
                  +-----------------------------------+
                   /          |             |        \
                  /           |             |         \
        +------------+  +------------+ +------------+ +------------+
        |   Police   |  | Transport  | | Health/EMS | | Insurance  |
        | Enforcement|  | (RTO Desk) | | (Trauma)   | | (Claims)   |
        +------------+  +------------+ +------------+ +------------+
                   \          |             |         /
                    \         |             |        /
                  +-----------------------------------+
                  |          Citizen Portal           |
                  +-----------------------------------+
```

---

## 🏛️ Integrated Department Modules

### 1. 👮 Traffic Police Operations
- **Live Signal Junction Controller:** Real-time cycle management for major intersections with manual emergency override capabilities.
- **Emergency Green Corridor Pre-emption:** Clears signal paths for ambulances and first-responders with a single click.
- **Automated E-Challan Enforcement:** Issues citations based on vehicle type and offense severity.
- **Digital FIR & Incident Registry:** Instant capture of collision telemetry, severity rankings, road conditions, and dispatched patrol units.

### 2. 🚗 Transport Department (RTO)
- **Centralized Vehicle Registry:** Instant lookup of vehicle ownership, fitness certification, fuel classification, and registration status.
- **Dynamic Demerit Points Tracking:** Cumulative point accumulation system to identify high-risk drivers and trigger license suspensions.
- **Commercial Fleet Compliance:** Automated tracking of commercial vehicle payload limits and permit validity.

### 3. 🚑 Health & Emergency Medical Services (EMS)
- **Trauma Care Intake Desk:** Real-time triage recording patient vitals, injury classification, and hospital bed allocation.
- **Active Ambulance Dispatcher:** Live fleet tracking with instant route clearance coordination.
- **Golden Hour Priority Feed:** Direct ingestion of accident FIR feeds to prepare trauma centers before patient arrival.

### 4. 📋 Insurance Claims & Audit Agency
- **Digital FIR Audit Integration:** Instant cross-verification of accident records directly from police logs to eliminate fraudulent claims.
- **Accident Claim Processing:** Automated settlement workflows tied directly to vehicle collision telemetry and repair estimates.
- **Risk Assessment Portfolio:** High-risk driver profiling based on historical citation data and accident frequency.

### 5. 👥 Citizen Safety & Grievance Portal
- **Vehicle E-Challan Portal:** Quick citation lookup and digital settlement status for registered vehicle owners.
- **Road Hazard & Grievance Redressal:** Direct citizen reporting of potholes, non-functional signals, and road safety hazards with tracking tickets.
- **Live Safety Advisories:** Broadcast feed for real-time weather warnings, detour routes, and traffic congestion alerts.

---

## 🏗️ Object-Oriented Architecture (OOP in Java)

SafeRoad is built in pure Java and demonstrates clean object-oriented design principles:

```
                      +-------------------+
                      |   Vehicle (Abs)   |
                      +-------------------+
                                ^
         +-------------+--------+--------+-------------+
         |             |                 |             |
   +-----+----+  +-----+------+    +-----+-----+ +-----+-----+
   |   Car    |  | TwoWheeler |    |HeavyVehic.| |EmergencyV.|
   +----------+  +------------+    +-----------+ +-----------+
```

- **Inheritance (Hierarchical):** Specialized vehicle models (`Car`, `TwoWheeler`, `HeavyVehicle`, `EmergencyVehicle`) inherit foundational attributes from an abstract base `Vehicle` class.
- **Polymorphism (Dynamic Method Dispatch):** Fine computation logic (`calculateFine(violation)`) is polymorphically overridden across subclasses, applying tiered penalty weights based on vehicle classification.
- **Encapsulation:** Domain models protect internal state through robust access modifiers and validated getters/setters.
- **Abstraction:** Business logic, telemetry synthesis, and database operations are segregated across dedicated service layers.

---

## 🎯 Impact & Key Outcomes

- ⏱️ **Reduces Emergency Response Time:** Green corridor coordination drastically shortens transit times for ambulances during life-critical medical emergencies.
- 📉 **Curtails Traffic Violations:** Integrated demerit tracking discourages repeat offenses and promotes accountable driving.
- 🔍 **Eliminates Bureaucratic Silos:** Multi-department synchronization ensures transparent, tamper-proof incident data across public services.
- 🤝 **Empowers Citizens:** Direct access to violation history and simplified road hazard reporting enhances public trust and urban safety.

---



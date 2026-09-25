# 🛡️ SafeRoad System — Smart Traffic & Incident Management Platform

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)](https://tailwindcss.com/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)](LICENSE)

> **SafeRoad** is an enterprise-grade Java web application inspired by the Government of India's **iRAD (Integrated Road Accident Database)** initiative. It unifies traffic police incident reporting, dynamic signal corridor pre-emption, automated polymorphic fine calculations, and a citizen grievance redressal portal.

---

## 🌟 Key Features

- 🚦 **Smart Traffic Signal Corridor Control:** Dynamic cycle switching and emergency green corridor priority for first-responders.
- 📄 **Polymorphic E-Challan Penalty Engine:** Dynamic fine calculation calculated based on vehicle classification hierarchy (`Car`, `TwoWheeler`, `HeavyVehicle`, `EmergencyVehicle`).
- 👤 **Citizen Safety & Grievance Portal:**
  - Dedicated **3-Tab View**: *My E-Challans*, *Traffic Advisories*, and *File a Complaint*.
  - Frictionless login using **Vehicle Registration Plate** (`KA-01-AB-1234`) and **Mobile Number**.
  - Real-time grievance tracking with ticket reference IDs (`CMP-101`).
- 🚨 **Incident Telemetry & FIR Logging:** Real-time logging of severity levels (`FATAL`, `SEVERE`, `MODERATE`, `MINOR`) and patrol unit dispatches.
- 📊 **Executive Command Center:** 6-card live KPI metrics tracking uncollected fines, active road closures, and registered fleet counts.

---

## 🏗️ Object-Oriented Architecture (OOP in Java)

SafeRoad demonstrates all core Java Object-Oriented Design Principles:

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

1. **Inheritance (Hierarchical):** Subclasses extend the abstract `Vehicle` class, inheriting common properties while encapsulating vehicle-specific attributes.
2. **Polymorphism (Dynamic Method Dispatch):** The `calculateFine(String violation)` method is overridden across subclasses to enforce tiered penalties.
3. **Encapsulation:** State variables across domain models (`Challan`, `AccidentReport`, `TrafficIntersection`, `Complaint`) are protected with strict access control.
4. **Abstraction:** Service contracts abstract persistence and analytics from the presentation layer.

## 🔑 Demo Login Credentials

| User Type / Department | Username / Plate | Password / Mobile | Target Portal |
|---|---|---|---|
| **Common User / Citizen** | `KA-01-AB-1234` | `9876543210` | **Citizen Portal (`/citizen`)** |
| **Traffic Police Officer** | `officer` | `officer123` | **Police Enforcement (`/police`)** |
| **Transport Dept (RTO)** | `transport` | `transport123` | **RTO Registry (`/transport`)** |
| **Health / EMS Dept** | `health` | `health123` | **Trauma Registry (`/health`)** |
| **Insurance Agency** | `insurance` | `insurance123` | **Claims Audit (`/insurance`)** |
| **Master Administrator** | `admin` | `admin` | **Central Overview (`/dashboard`)** |

---

## 🚀 Local Development Setup

### Prerequisites
- Java 17 or higher
- Git

### Running the App
1. Clone the repository:
   ```bash
   git clone https://github.com/ShaunJeffery/SafeRoad.git
   cd SafeRoad
   ```

2. Run on Windows:
   ```cmd
   run.bat
   ```
   *Or on Linux/macOS:*
   ```bash
   mvn spring-boot:run
   ```

3. Open your browser at:
   👉 **`http://localhost:8080`**

---

## ☁️ Cloud Deployment (Docker)

Build and run anywhere with Docker:
```bash
docker build -t saferoad-app .
docker run -p 8080:8080 saferoad-app
```

---

## 👥 Contributors & Hackathon Team
- **Project Lead & Developer:** Shaun
- **Domain:** Smart Governance, Urban Mobility, & Road Safety Analytics

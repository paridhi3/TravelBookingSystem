<!--
# Travel Booking System

Frontend: https://github.com/paridhi3/Frontend-Travel-Booking-System

## **System Functionalities Overview**

### **1. User Authentication & Management**
- **User Registration & Login** (Implemented using Spring Security)
- **Role-Based Access Control**:
  - **Passenger**: View profile, Book Flights/Trains/Buses, View Booking History
  - **Admin**: Manage flights, trains, and buses, view all bookings

### **2. Transport Management**
- **Flight Management**: Add, edit, delete flights & view available flights
- **Train Management**: Add, edit, delete trains & view available trains
- **Bus Management**: Add, edit, delete buses & view available buses

### **3. Search & Filter**
- Search and filter transport options based on:
  - **Source**
  - **Destination**
  - **Departure Date**
  - **Class (Economy, Business, etc.)**

## **4. Availability Management**
- When a new transport is added through post method, availability for the next **6 days** is automatically generated.
- A background job runs **every hour** to:
  - Remove transport availability for **departed journeys**.
  - Delete availability records for **previous days**.
  - Insert a **new daily availability record**, setting `availableSeats = totalSeats`.

### **5. Booking Management**
- **Book a Ticket**: (Flight, Bus, Train)
- **Seat Selection**: Choose preferred seats from a seat layout.
- **Availability Update**: `availableSeats` is reduced when a booking is confirmed.
- **View Booking History**

### **6. Admin Functionalities**
- **Manage Transport Services** (Create, Read, Update, Delete Flights, Trains, and Buses)
- **View All Bookings**
- **Predefined Admin Credentials**:
  - A single **admin account** exists with **predefined credentials**, which are **automatically inserted/updated into the database on application startup**.
-->
# 🏨 Travel Booking System  

A **Spring Boot-based Travel Booking System** that allows users to book Flights, Trains, and Buses with real-time availability management. It supports role-based authentication, transport management, and booking functionalities.

## 📋 Table of Contents  
1. [📄 Abstract](#-abstract)  
2. [🚀 Features](#-features)  
3. [🛠 Technologies Used](#-technologies-used)  
4. [👥 Roles in the System](#-roles-in-the-system)  
5. [🛢 Database Schema](#-database-schema)  
6. [🎨 System Architecture](#-system-architecture)  
7. [⚙️ Setup and Installation](#️-setup-and-installation)  
8. [🔗 API Endpoints](#-api-endpoints)  

## 📄 **Abstract**  

The **Travel Booking System** provides a seamless way to book flights, trains, and buses with an integrated search and filter mechanism. The system includes user authentication, seat selection, and automatic availability management. Admins can manage transport services and monitor bookings efficiently.

## 🚀 **Features**  

### **1. User Authentication & Management**  
- **User Registration & Login** (Implemented using Spring Security)  
- **Role-Based Access Control**:  
  - **Passenger**: Book transport, view profile, and booking history  
  - **Admin**: Manage flights, trains, and buses, and view all bookings  

### **2. Transport Management**  
- Add, edit, delete, and view transport services (Flights, Trains, Buses)  

### **3. Search & Filter**  
- Search by **Source, Destination, Departure Date, Class (Economy, Business, etc.)**  

### **4. Availability Management**  
- When a transport is added, availability for the next **6 days** is auto-generated.  
- A background job runs **hourly** to:  
  - Remove availability for **departed** journeys  
  - Delete outdated availability records  
  - Insert new daily availability with `availableSeats = totalSeats`  

### **5. Booking Management**  
- **Book a Ticket**: Flight, Bus, Train  
- **Seat Selection**: Choose preferred seats  
- **Availability Update**: `availableSeats` is reduced upon booking confirmation  
- **View Booking History**  

### **6. Admin Functionalities**  
- **Manage Transport Services**: Create, update, delete transport records  
- **View All Bookings**  
- **Predefined Admin Account**: Auto-inserted during startup  

---

## 🛠 **Technologies Used**  

- **Backend**: Java, Spring Boot, Spring Security  
- **Frontend**: [React-based frontend](https://github.com/paridhi3/Frontend-Travel-Booking-System)  
- **Database**: MySQL  
- **ORM**: Hibernate (JPA)  
- **Scheduler**: Spring Boot Scheduler  
- **Authentication**: Spring Security (BCrypt Password Encoder)

---

## 👥 **Roles in the System**  

1. **Admin** 🛠  
   - Add/Edit/Delete transport services  
   - View and manage all bookings  

2. **Passenger** 🎟  
   - Search and book transport  
   - Select seats  
   - View booking history  

---

## 🛢 **Database Schema**  

### **Users Table**  
| Column          | Type        | Description                  |
|----------------|------------|------------------------------|
| `user_id`      | INT (PK)    | Unique User ID               |
| `username`     | VARCHAR     | Login Username               |
| `password`     | VARCHAR     | Hashed Password              |
| `role`         | ENUM        | `ADMIN` / `PASSENGER`        |

### **Transport Table (Flight, Train, Bus)**  
| Column          | Type        | Description                  |
|----------------|------------|------------------------------|
| `transport_id` | INT (PK)    | Unique Transport ID          |
| `type`         | ENUM        | `FLIGHT` / `TRAIN` / `BUS`   |
| `source`       | VARCHAR     | Departure Location           |
| `destination`  | VARCHAR     | Arrival Location             |
| `departure`    | DATETIME    | Departure DateTime           |
| `arrival`      | DATETIME    | Arrival DateTime             |
| `totalSeats`   | INT         | Total Seats Available        |
| `availableSeats` | INT       | Available Seats for Booking  |

### **Bookings Table**  
| Column        | Type        | Description                        |
|--------------|------------|------------------------------------|
| `booking_id` | INT (PK)    | Unique Booking ID                 |
| `user_id`    | INT (FK)    | Passenger who booked              |
| `transport_id` | INT (FK)  | Transport booked                  |
| `status`     | ENUM        | `CONFIRMED` / `CANCELLED`         |
| `seatNumber` | INT         | Selected Seat Number              |

---

## 🎨 **System Architecture**  

<div align="center">
<img src="https://www.plantuml.com/plantuml/png/ZP91IiD048JxLmMIlqPY7NAJTL3D78Ai9CF9XtXdaO-6HZML3fwcuZAh3PhzjzGTnD_9iJP4SD_oKqoh72klgKojA9H4HyhtGm-ZcIqlJhCdmtHCPZXzU-Y43Iafl4CUNbnmYwsHGWBisPQeTmLrgx8HE7QOR7z6Z3wLbfVsLtTA5AB2pE_1rKqWxtffx5fEkZTRMYGizFg3m5xFpyKPnB0XVcGf5B9H9_FhVWJ7H0Z4n3wVwCNBkKb5hWJJh7M5MQWfO1J6OSU-dGO5XoNBm9f5E3dzgRZtuVNpx1ZKPyYZ5DPpxI2xE7F7lPskXx9QW00" alt="Architecture Diagram" width="90%" />
</div>

---

## ⚙️ **Setup and Installation**  

1. **Clone the repository**:  
   ```bash
   git clone https://github.com/paridhi3/Travel-Booking-System.git
   cd Travel-Booking-System
   
2. **Setup MySQL Database**  
   - Create a database `travel_db`  
   - Update `application.properties` with your MySQL credentials  

3. **Run the Spring Boot Application**  
   ```bash
   mvn spring-boot:run
   
4. **Frontend Setup**  
   - Clone the frontend repository  
   ```bash
   git clone https://github.com/paridhi3/Frontend-Travel-Booking-System.git
   cd Frontend-Travel-Booking-System
   npm install
   npm start

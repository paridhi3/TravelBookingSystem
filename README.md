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

# Ticket Printing System

## Overview

This is the coursework implemented for MODULE: (2023) 6SENG006C.1 Concurrent Programming module.The Ticket Printing System simulates a scenario where passengers share a printer to print their tickets. The system involves multiple passengers and two technicians. One technician is responsible for refilling paper when it runs out, while the other technician replaces the toner cartridge when needed. This system is implemented using Java concurrency features, including threads, thread groups, and a monitor.

## Part 1: FSP Processes

Develop Finite State Processes (FSP) to model the shared printer. The abstract FSP program will be the basis for the subsequent Java implementation. Ensure that the FSP processes accurately represent the interactions between passengers, the printer, and the two technicians.

## Part 2: Java Implementation

Translate the FSP model into a multi-threaded Java program. Leverage appropriate Java concurrency features to implement the FSP processes and the complete system outlined in Part 1. Create Java classes representing passengers, the printer, and the two technicians. Ensure synchronization and coordination among threads to model the expected behavior of the Ticket Printing System.

## Part 3: Documentation and Visualization

### Screenshots

<img src="https://github.com/JayanaGunaweera01/Concurrent-Ticket-Printing-system/assets/79576139/7a21561b-68d9-4a72-ba8d-1a9a718897c9" width="200">

### Usage

#### Development Environment

- Java JDK 21
- LTSA Tool (for FSP modeling)

#### How to Run

1. Clone the repository:

    ```bash
    git clone https://github.com/JayanaGunaweera01/Concurrent-Ticket-Printing-system.git
    ```

2. Navigate to the project directory:

    ```bash
    cd Concurrent-Ticket-Printing-system\src
    ```

3. Compile and run the Java program:

    ```bash
    javac PrintingSystem.java
    java PrintingSystem
    ```


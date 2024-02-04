/** *********************************************************************
 * File:      PrintingSystem.java
 * Author:    Jayana Gunaweera
 * Date:      31/12/2023
 * Version:   1.0
 * Contents:  6SENG006W_CW1
 *            This class contains the main method to simulate a printing system.
 *            It initializes a ticket machine, creates passenger and technician threads,
 *            and starts the threads to execute printing tasks.
 ************************************************************************ */

import Utils.Utilities;

public class PrintingSystem {
    public static void main(String[] args) {
        // Clearing the text file with previous logs
        Utilities.clearFile();

        // Creating thread groups
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Creating Thread Groups...", Utilities.MessageType.INFO);
        ThreadGroup passengers = new ThreadGroup("Passengers");
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Created Passenger Thread Group...finished", Utilities.MessageType.INFO);
        ThreadGroup technicians = new ThreadGroup("Technicians");
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Creating Technicians Thread Group...finished", Utilities.MessageType.INFO);

        // Initializing the TicketMachine object
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Initializing TicketMachine Object....", Utilities.MessageType.INFO);
        Ticket ticketMachine = new Ticket(0001, 40, 10, passengers);
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Initializing TicketMachine Object finished", Utilities.MessageType.INFO);

        // Creating Passenger threads
        Thread passenger1 = new Passenger(passengers, ticketMachine, "Jayana");
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Initialized Passenger [" + passenger1.getName() + "]", Utilities.MessageType.INFO);
        Thread passenger2 = new Passenger(passengers, ticketMachine, "Kalana");
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Initialized Passenger [" + passenger2.getName() + "]", Utilities.MessageType.INFO);
        Thread passenger3 = new Passenger(passengers, ticketMachine, "Lanka");
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Initialized Passenger [" + passenger3.getName() + "]", Utilities.MessageType.INFO);
        Thread passenger4 = new Passenger(passengers, ticketMachine, "Sudesh");
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Initialized Passenger [" + passenger4.getName() + "]", Utilities.MessageType.INFO);

        // Creating the technician threads
        Thread paperTechnician = new PaperTechnician(technicians, ticketMachine, "Paper Technician");
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Initialized Paper Technician", Utilities.MessageType.INFO);
        Thread tonerTechnician = new TonerTechnician(technicians, ticketMachine, "Toner Technician");
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Initialized Toner Technician", Utilities.MessageType.INFO);

        // Starting threads
        passenger1.start();
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Passenger Thread [" + passenger1.getName() + "] Started...", Utilities.MessageType.INFO);
        passenger2.start();
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Passenger Thread [" + passenger2.getName() + "] Started...", Utilities.MessageType.INFO);
        passenger3.start();
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Passenger Thread [" + passenger3.getName() + "] Started...", Utilities.MessageType.INFO);
        passenger4.start();
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Passenger Thread [" + passenger4.getName() + "] Started...", Utilities.MessageType.INFO);
        paperTechnician.start();
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Paper Technician Thread Started...", Utilities.MessageType.INFO);
        tonerTechnician.start();
        Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Toner Technician Thread Started...", Utilities.MessageType.INFO);

        try {
            passenger1.join();
            Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Passenger Thread [" + passenger1.getName() + "] Finished execution", Utilities.MessageType.INFO);
            passenger2.join();
            Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Passenger Thread [" + passenger2.getName() + "] Finished execution", Utilities.MessageType.INFO);
            passenger3.join();
            Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Passenger Thread [" + passenger3.getName() + "] Finished execution", Utilities.MessageType.INFO);
            passenger4.join();
            Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Passenger Thread [" + passenger4.getName() + "] Finished execution", Utilities.MessageType.INFO);
            paperTechnician.join();
            Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Paper Technician Thread Finished execution", Utilities.MessageType.INFO);
            tonerTechnician.join();
            Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "Toner Technician Thread Finished execution", Utilities.MessageType.INFO);

            Utilities.printLogs(Utilities.MessageOwner.PRINTING_SYSTEM, "All tasks successfully completed, Total Number of ticket pages printed : " + ticketMachine.getNumberOfPrintedPages()
                    + ", Final Status of TicketMachine " + ticketMachine.toString(), Utilities.MessageType.INFO);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

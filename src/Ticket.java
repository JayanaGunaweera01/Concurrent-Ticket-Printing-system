/** *********************************************************************
 * File:      Ticket.java
 * Author:    Jayana Gunaweera
 * Date:      31/12/2023
 * Contents:  6SENG006W_CW1
 *            This class implements the ServiceTicketMachine interface,
 *            representing a ticket machine entity. It manages paper and toner levels,
 *            tracks the number of printed documents and pages, and provides synchronized
 *            methods for toner cartridge replacement, paper refilling, and document printing.
 ************************************************************************ */

import Utils.Utilities;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ticket implements ServiceTicketMachine {
    private int id;
    private int currentPaperLevel;
    private int currentTonerLevel;
    private int numberOfDocumentsPrinted;
    private int numberOfPrintedPages;
    private ThreadGroup Passengers;
    static int paperPackReplacedCount;
    static int cartridgesReplacedCount;

    // Reentrant Lock with fairness enabled
    private Lock resourceLock = new ReentrantLock(true);
    private Condition TicketMachineCondition;

    public Ticket(int id, int initialPaperLevel, int initialTonerLevel, ThreadGroup Passengers) {
        this.id = id;
        this.currentPaperLevel = initialPaperLevel;
        this.currentTonerLevel = initialTonerLevel;
        this.Passengers = Passengers;
        this.numberOfDocumentsPrinted = 0;
        this.numberOfPrintedPages = 0;
        this.TicketMachineCondition = resourceLock.newCondition();
    }

    /*
     * Method to replace the toner cartridge of the TicketMachine.
     * The toner can only be replaced when the current toner level goes below the minimum toner level.
     * When the toner cartridge is replaced, the toner level goes up to the full toner level.
     */
    @Override
    public void replaceTonerCartridge() {
        // Block until the condition holds
        this.resourceLock.lock();

        try {
            while (currentTonerLevel > Minimum_Toner_Level) {
                // Check if the TicketMachine has finished serving all the threads in the Passengers Thread Group
                if (Passengers.activeCount() > 0) {
                    Utilities.printLogs(Utilities.MessageOwner.TONER_TECHNICIAN, "Checking toner...No need to replace the toner, Current toner Level is "
                            + currentTonerLevel, Utilities.MessageType.INFO);
                    // Set the thread to wait for up to 5 seconds
                    TicketMachineCondition.await(5, TimeUnit.SECONDS);
                } else {
                    Utilities.printLogs(Utilities.MessageOwner.TicketMachine, "No requests for printing Ticket documents....", Utilities.MessageType.INFO);
                    break;
                }
            }

            if (currentTonerLevel < Minimum_Toner_Level) {
                // Refill the toner cartridge
                Utilities.printLogs(Utilities.MessageOwner.TONER_TECHNICIAN, "Toner is low, current toner level is " + currentTonerLevel, Utilities.MessageType.WARN);
                currentTonerLevel += PagesPerTonerCartridge;
                cartridgesReplacedCount++;
                Utilities.printLogs(Utilities.MessageOwner.TONER_TECHNICIAN, "Toner is replaced, new toner level is " + currentTonerLevel, Utilities.MessageType.INFO);
            }
            // Wake up all waiting threads.
            this.TicketMachineCondition.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Release the resource lock
            this.resourceLock.unlock();
        }
    }

    /**
     * Method to refill the paper tray of the TicketMachine with paper.
     * The paper tray can only be refilled if the new paper level does not exceed the full (maximum) paper level.
     * Each paper refill increases the paper level by the number of papers in the pack.
     */
    @Override
    public void refillPaper() {
        // Block until the condition holds
        this.resourceLock.lock();

        try {
            // Check if the refill can exceed the full paper level
            boolean TicketMachineCannotBeRefilled = (currentPaperLevel + SheetsPerPack) > Full_Paper_Tray;
            while (TicketMachineCannotBeRefilled) {
                if (Passengers.activeCount() > 0) {
                    Utilities.printLogs(Utilities.MessageOwner.PAPER_TECHNICIAN, "Checking paper...No need to refill the papers, Current Paper Level is "
                            + currentPaperLevel, Utilities.MessageType.INFO);

                    // Set the thread to wait for up to 5 seconds
                    TicketMachineCondition.await(5, TimeUnit.SECONDS);
                } else {
                    Utilities.printLogs(Utilities.MessageOwner.TicketMachine, "No requests for printing ticket documents....", Utilities.MessageType.INFO);
                    break;
                }
            }
            if (currentPaperLevel + SheetsPerPack <= Full_Paper_Tray) {
                // Allow the paper technician to refill paper
                Utilities.printLogs(Utilities.MessageOwner.PAPER_TECHNICIAN, "Checking paper... Refilling TicketMachine with paper... ", Utilities.MessageType.INFO);
                currentPaperLevel += SheetsPerPack;
                this.paperPackReplacedCount++;
                Utilities.printLogs(Utilities.MessageOwner.PAPER_TECHNICIAN, "Refilled tray with a pack of paper. New Paper Level: " + currentPaperLevel, Utilities.MessageType.INFO);
            }
            // Wake up all waiting threads.
            this.TicketMachineCondition.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Release the resource lock
            this.resourceLock.unlock();
        }
    }

    /**
     * Method to print a document.
     * A document can be printed if there are enough paper and toner resources.
     * Printing a document reduces both the paper level and toner level by the number of pages in the document.
     */
    @Override
    public void printDocument(Document document) {
        // Block until the condition holds
        this.resourceLock.lock();

        try {
            String Passenger = document.getUserID();
            String docName = document.getDocumentName();
            int numberOfPages = document.getNumberOfPages();

            while (currentPaperLevel < numberOfPages || currentTonerLevel < numberOfPages) {
                // If both toner and paper level are low to print the number of pages
                if (currentPaperLevel < numberOfPages && currentTonerLevel < numberOfPages) {
                    Utilities.printLogs(Utilities.MessageOwner.TicketMachine, "Out of paper and toner. Current Paper Level is " + currentPaperLevel + " and Toner Level is " + currentTonerLevel, Utilities.MessageType.INFO);
                }
                // If the paper level is low
                else if (currentPaperLevel < numberOfPages) {
                    Utilities.printLogs(Utilities.MessageOwner.TicketMachine, "Out of paper. Current Paper Level is " + currentPaperLevel, Utilities.MessageType.WARN);
                }
                // If the toner level is low
                else {
                    Utilities.printLogs(Utilities.MessageOwner.TicketMachine, "Out of toner. Current Toner Level is " + currentTonerLevel, Utilities.MessageType.WARN);
                }
                // Set the thread to wait until it's called again
                TicketMachineCondition.await();
            }

            // If both paper and toner resources are enough to print the document
            if (currentPaperLevel > numberOfPages && currentTonerLevel > numberOfPages) {
                Utilities.printLogs(Utilities.MessageOwner.TicketMachine, Passenger + " is printing " + docName + " with page length " + numberOfPages, Utilities.MessageType.INFO);
                currentPaperLevel -= numberOfPages;
                currentTonerLevel -= numberOfPages;
                numberOfDocumentsPrinted++;
                this.numberOfPrintedPages += numberOfPages;
                Utilities.printLogs(Utilities.MessageOwner.TicketMachine, "Successfully printed the ticket document. New Paper Level is " + currentPaperLevel + " and Toner Level is " + currentTonerLevel, Utilities.MessageType.INFO);
                this.TicketMachineCondition.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.resourceLock.unlock();
        }
    }

    // Getting the total number of printed pages
    public int getNumberOfPrintedPages() {
        return numberOfPrintedPages;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "TicketMachineID: '" + id + '\'' + ", " +
                "Paper Level: " + currentPaperLevel + ", " +
                "Toner Level: " + currentTonerLevel + ", " +
                "Ticket Documents Printed: " + numberOfDocumentsPrinted +
                '}';
    }
}

/**
 * File:      Passenger.java
 * Author:    Jayana Gunaweera
 * Date:      31/12/2023
 * Version:   1.0
 * Contents:  6SENG006W_CW1
 *            This class extends Thread and represents a passenger interacting with a ticket machine to print documents.
 *            It encapsulates the behavior of a passenger, including generating random documents and printing them.
 ************************************************************************ */

import Utils.Utilities;

import java.util.Random;

public class Passenger extends Thread{
    private TicketMachine ticketMachine;

    /**
     * Constructor for the Passenger class.
     *
     * @param threadGroup The thread group to which this passenger belongs.
     * @param ticketMachine The ticket machine associated with this passenger.
     * @param name The name of the passenger.
     */
    public Passenger(ThreadGroup threadGroup, TicketMachine ticketMachine, String name) {
        super(threadGroup, name);
        this.ticketMachine = ticketMachine;
    }

    /**
     * The run method representing the execution of the passenger thread.
     * Generates random documents and sends them to the ticket machine for printing.
     */
    @Override
    public void run() {
        Random random = new Random();
        int numberOfDocumentsPerPassenger = 5;

        for (int i = 1; i <= numberOfDocumentsPerPassenger; i++) {

            int MINIMUM_NUMBER_OF_PAGE_PER_DOCUMENT = 1;
            int MAXIMUM_NUMBER_OF_PAGE_PER_DOCUMENT = 10;
            // Random number of pages per document, adding 1 to ensure the document is at least one page in length
            int numberOfPages = MINIMUM_NUMBER_OF_PAGE_PER_DOCUMENT +
                    random.nextInt(MAXIMUM_NUMBER_OF_PAGE_PER_DOCUMENT - MINIMUM_NUMBER_OF_PAGE_PER_DOCUMENT);
            String documentName = "ticket" + i;

            Document document = new Document(this.getName(), documentName, numberOfPages);
            ticketMachine.printDocument(document);

            // After printing the final document, no need to sleep the passenger thread
            boolean lastDocument = i == numberOfDocumentsPerPassenger;
            // Passenger should sleep for a random time between each attempt to print the documents.
            if (!lastDocument) {
                int MINIMUM_SLEEPING_TIME = 1000;
                int MAXIMUM_SLEEPING_TIME = 5000;
                int sleepingTime = MINIMUM_SLEEPING_TIME + random.nextInt(MAXIMUM_SLEEPING_TIME - MINIMUM_SLEEPING_TIME);
                try {
                    sleep(sleepingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Utilities.printLogs(Utilities.MessageOwner.PASSENGER, this.getName() + " was interrupted during sleeping time "
                            + sleepingTime + " after printing : " + documentName, Utilities.MessageType.ERROR);
                }
            }
        }

        Utilities.printLogs(Utilities.MessageOwner.PASSENGER, this.getName() + " Finished printing : " + numberOfDocumentsPerPassenger, Utilities.MessageType.INFO);
    }
}

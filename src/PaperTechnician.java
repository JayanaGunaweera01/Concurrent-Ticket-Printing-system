/** *********************************************************************
 * File:      PaperTechnician.java
 * Author:    Jayana Gunaweera
 * Date:      [Current Date]
 * Version:   1.0
 * Contents:  6SENG006W_CW1
 *            This class represents a Paper Technician entity responsible for
 *            refilling paper in a Service Ticket Machine.
 ************************************************************************ */

import Utils.Utilities;

import java.util.Random;

public class PaperTechnician extends Thread {
    private ServiceTicketMachine ticketMachine;

    /**
     * Constructs a Paper Technician with the specified thread group, Service Ticket Machine,
     * and name.
     *
     * @param group         the thread group
     * @param ticketMachine the Service Ticket Machine
     * @param name          the name of the Paper Technician
     */
    public PaperTechnician(ThreadGroup group, ServiceTicketMachine ticketMachine, String name) {
        super(group, name);
        this.ticketMachine = ticketMachine;
    }

    /**
     * Runs the Paper Technician thread, refilling paper in the Service Ticket Machine
     * for a specified number of refills with random sleeping intervals.
     */
    @Override
    public void run() {
        Random random = new Random();
        int numberOfRefills = 3;

        for (int i = 1; i <= numberOfRefills; i++) {
            ticketMachine.refillPaper();

            // Paper Technician should sleep for a random time between each attempt to refill the paper.
            int MINIMUM_SLEEPING_TIME = 1000;
            int MAXIMUM_SLEEPING_TIME = 5000;
            int sleepingTime = MINIMUM_SLEEPING_TIME + random.nextInt(MAXIMUM_SLEEPING_TIME - MINIMUM_SLEEPING_TIME);
            try {
                sleep(sleepingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Utilities.printLogs(Utilities.MessageOwner.PAPER_TECHNICIAN, "Paper Technician was interrupted during sleeping time " + sleepingTime +
                        ", after refilling paper pack no " + i, Utilities.MessageType.ERROR);
            }
        }

        Utilities.printLogs(Utilities.MessageOwner.PAPER_TECHNICIAN, "Paper Technician Finished, packs of paper used "
                + Ticket.paperPackReplacedCount, Utilities.MessageType.INFO);
    }
}

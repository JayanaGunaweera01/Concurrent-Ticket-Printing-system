/** *********************************************************************
 * File:      TonerTechnician.java
 * Author:    Jayana Gunaweera
 * Date:      31/12/2023
 * Version:   1.0
 * Contents:  6SENG006W_CW1
 *            This class defines a Toner Technician thread responsible for
 *            replacing toner cartridges in a ServiceTicketMachine.
 *            The technician attempts to replace toner cartridges a specific
 *            number of times with random sleeping intervals between attempts.
 ************************************************************************ */

import Utils.Utilities;
import java.util.Random;

public class TonerTechnician extends Thread {
    private ServiceTicketMachine ticketMachine;

    public TonerTechnician(ThreadGroup group, ServiceTicketMachine ticketMachine, String name) {
        super(group, name);
        this.ticketMachine = ticketMachine;
    }

    @Override
    public void run() {
        Random random = new Random();
        int numberOfRefills = 3;

        for (int i = 1; i <= numberOfRefills; i++) {
            ticketMachine.replaceTonerCartridge();

            // Toner Technician should sleep for a random time between each attempt to refill the toner.
            int MINIMUM_SLEEPING_TIME = 1000;
            int MAXIMUM_SLEEPING_TIME = 5000;
            int sleepingTime = MINIMUM_SLEEPING_TIME + random.nextInt(MAXIMUM_SLEEPING_TIME - MINIMUM_SLEEPING_TIME);
            try {
                sleep(sleepingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Utilities.printLogs(Utilities.MessageOwner.TONER_TECHNICIAN,
                        "Toner Technician was interrupted during sleeping time " + sleepingTime +
                                ", after replacing toner cartridge no " + i, Utilities.MessageType.ERROR);
            }
        }
        Utilities.printLogs(Utilities.MessageOwner.TONER_TECHNICIAN,
                "Toner Technician Finished, cartridges replaced " + Ticket.cartridgesReplacedCount, Utilities.MessageType.INFO);
    }
}

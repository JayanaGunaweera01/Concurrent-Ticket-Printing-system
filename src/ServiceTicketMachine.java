/** *********************************************************************
 * File:      ServiceTicketMachine.java
 * Author:    Jayana Gunaweera
 * Date:      31/12/2023
 * Version:   1.0
 * Contents:  6SENG006W_CW1
 *            This interface extends the TicketMachine interface and
 *            defines constants and methods specific to a serviceable ticket machine.
 ************************************************************************ */

public interface ServiceTicketMachine extends TicketMachine {

    // Constants for paper tray
    public final int Full_Paper_Tray  = 250;
    public final int SheetsPerPack    = 50;

    // Constants for toner cartridge
    public final int Full_Toner_Level        = 500;
    public final int Minimum_Toner_Level     = 10;
    public final int PagesPerTonerCartridge  = 500;

    // Technician methods

    /**
     * Replaces the toner cartridge in the serviceable ticket machine.
     */
    public void replaceTonerCartridge();

    /**
     * Refills paper in the paper tray of the serviceable ticket machine.
     */
    public void refillPaper();
}

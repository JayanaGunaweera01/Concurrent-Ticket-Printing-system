/**
 * Utilities.java (Class)
 * Author: Jayana Gunaweera
 * Date: [Current Date]
 * Version: 1.0
 * Contents: Utility class providing common functionalities for logging and console output.
 *           It includes methods for printing colored console logs, logging messages to a file,
 *           and clearing the log file.
 * Colors used:
 * - BLUE: Light Cyan
 * - PURPLE: Blue
 * - CYAN: Green
 * - WHITE: White
 * - RED: Red
 * - YELLOW: Yellow
 * - GREEN: Magenta
 * - RESET: Reset
 */

package Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    // Colored console text constants
    public static final String BLUE = "\u001B[36m";    // Light Cyan
    public static final String PURPLE = "\033[0;34m";   // Blue
    public static final String CYAN = "\033[0;32m";     // Green
    public static final String WHITE = "\033[0;37m";    // White
    public static final String RED = "\u001B[31m";      // Red
    public static final String YELLOW = "\u001B[33m";   // Yellow
    public static final String GREEN = "\u001B[35m";    // Magenta
    public static final String RESET = "\u001B[0m";     // Reset

    // Enums for message owner and message type
    public enum MessageOwner {
        TicketMachine,
        PASSENGER,
        TONER_TECHNICIAN,
        PAPER_TECHNICIAN,
        PRINTING_SYSTEM
    }

    public enum MessageType {
        INFO,
        ERROR,
        WARN
    }

    /**
     * Synchronized method to print colored logs to console and write to a file.
     * @param messageOwner Enum representing the source of the message.
     * @param message The actual log message.
     * @param infoType Enum representing the type of log message (INFO, ERROR, WARN).
     */
    public static synchronized void printLogs(MessageOwner messageOwner, String message, MessageType infoType) {

        Date date = new Date(System.currentTimeMillis());
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String currentTimeDate = formatter.format(date);

        String infoLog = "[" + messageOwner.toString().toUpperCase().replace("_", " ") + "]  [" + infoType.toString().toUpperCase() + "] ";
        String infoMessage = "[" + currentTimeDate + "]  " + message + ".";

        // Print colored output to console based on message owner
        switch (infoType) {
            case WARN:
                coloredOutput(YELLOW, messageOwner, infoLog, infoMessage);
                break;
            case INFO:
                coloredOutput(RESET, messageOwner, infoLog, infoMessage);
                break;
            case ERROR:
                coloredOutput(RED, messageOwner, infoLog, infoMessage);
                break;
            default:
                System.out.println(message);
                break;
        }

        // Write log into a file
        try {
            FileWriter myWriter = new FileWriter("TicketMachineOutput.txt", true);
            myWriter.write(infoLog + infoMessage + "\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Private method for colored console output based on message owner.
     * @param consoleColors Console color code.
     * @param messageOwner Enum representing the source of the message.
     * @param infoLog Log information including message owner and type.
     * @param infoMessage Actual log message.
     */
    private static void coloredOutput(String consoleColors, MessageOwner messageOwner, String infoLog, String infoMessage) {
        switch (messageOwner) {
            case PASSENGER:
                System.out.println(consoleColors + infoLog + GREEN + infoMessage + RESET);
                break;
            case TONER_TECHNICIAN:
                System.out.println(consoleColors + infoLog + PURPLE + infoMessage + RESET);
                break;
            case PAPER_TECHNICIAN:
                System.out.println(consoleColors + infoLog + CYAN + infoMessage + RESET);
                break;
            case TicketMachine:
                System.out.println(consoleColors + infoLog + infoMessage + RESET);
                break;
            default:
                System.out.println(infoLog + infoMessage);
        }
    }

    /**
     * Method to clear the log file.
     */
    public static void clearFile() {
        try {
            FileWriter fileWriter = new FileWriter("TicketMachineOutput.txt", false);
            PrintWriter printWriter = new PrintWriter(fileWriter, false);
            printWriter.flush();
            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

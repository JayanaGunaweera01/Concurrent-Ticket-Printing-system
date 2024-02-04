/** *********************************************************************
 * File:      Document.java
 * Author:    Jayana Gunaweera
 * Date:      31/12/2023
 * Version:   1.0
 * Contents:  6SENG006W_CW1
 *            This class represents an abstract document entity.
 *            It encapsulates essential details, including the user ID,
 *            document name, and the number of pages in the document.
 ************************************************************************ */

/**
 * Represents a document entity with essential details.
 * Includes user ID, document name, and the number of pages.
 */
class Document {
    private final String userID;
    private final String documentName;
    private final int numberOfPages;

    /**
     * Constructs a Document object with provided details.
     *
     * @param UID    The user ID associated with the document.
     * @param name   The name of the document.
     * @param length The number of pages in the document.
     */
    public Document(String UID, String name, int length) {
        this.userID = UID;
        this.documentName = name;
        this.numberOfPages = length;
    }

    /**
     * Gets the user ID associated with the document.
     *
     * @return The user ID.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Gets the name of the document.
     *
     * @return The document name.
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * Gets the number of pages in the document.
     *
     * @return The number of pages.
     */
    public int getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * Returns a string representation of the Document object.
     *
     * @return A string containing user ID, document name, and number of pages.
     */
    public String toString() {
        return new String("Document[ " +
                "UserID: " + userID + ", " +
                "Name: " + documentName + ", " +
                "Pages: " + numberOfPages +
                "]");
    }
}

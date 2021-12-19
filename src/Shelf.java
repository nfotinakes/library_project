import java.util.HashMap;
import java.util.Objects;

/**
 * Title: Shelf.java - Pt 3/4 of Library Project
 * Author: Nicholas Fotinakes
 * Description: This class represents a Shelf to store books and their counts based on subject
 * which will be used in the Library Project
 * Date: 11/8/2021
 */
public class Shelf {

    // Public static final fields to parse a string/array for info
    public static final int SHELF_NUMBER_ = 0;
    public static final int SUBJECT_ = 1;
    // Private fields to hold Shelf info
    private int shelfNumber;
    private String subject;
    private HashMap<Book, Integer> books;   // HashMap to hold what book is on Shelf and how many

    /**
     * Constructor takes no params but will create a new instance of HashMap
     */
    public Shelf(){
        books = new HashMap<>();
    }

    /**
     * This method gets the Shelf number
     * @return Shelf number
     */
    public int getShelfNumber() {
        return shelfNumber;
    }

    /**
     * This method sets the Shelf number
     * @param shelfNumber the number to set for Shelf
     */
    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    /**
     * This method gets the Shelf subject
     * @return Shelf's subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * This method sets the Shelf's subject
     * @param subject the subject to set Shelf to
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * This method returns the HashMap of books stored on Shelf
     * @return Shelf HashMap of Books
     */
    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    /**
     * This method sets a HashMap of books on Shelf
     * @param books the HashMap of books to set
     */
    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }

    /**
     * Overridden equals method to check Shelf equality
     * @param o the shelf to check
     * @return true or false answer
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        return getShelfNumber() == shelf.getShelfNumber() && getSubject().equals(shelf.getSubject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShelfNumber(), getSubject());
    }

    /**
     * Overridden toString method to display shelf number and subject
     * @return formatted toString
     */
    @Override
    public String toString() {
        return shelfNumber + " : " + subject;

    }

    public int getBookCount(Book book) {
        if(!books.containsKey(book)) {
            return -1;
        } else {
            return books.get(book);
        }
    }

    /**
     * This method adds a Book object to the shelf
     * @param book the Book to add
     * @return corresponding Code
     */
    public Code addBook(Book book) {
        // If Shelf already has book, increment the books corresponding count in HashMap
        if(books.containsKey(book)) {
            int count = books.get(book);
            count = count + 1;
            books.put(book, count);
            System.out.println(book + " added to shelf " + this);
            return Code.SUCCESS;
        }
        // If book is not on shelf, check subject and add if correct
        if(!books.containsKey(book) && book.getSubject().equals(subject)){
            books.put(book, 1);
            System.out.println(book + " added to shelf " + this);
            return Code.SUCCESS;
        } else {
            // If subject doesn't match return error
            return Code.SHELF_SUBJECT_MISMATCH_ERROR;
        }
    }

    /**
     * This method removes a book from Shelf
     * @param book the Book to remove
     * @return corresponding Code
     */
    public Code removeBook(Book book) {
        // Check if book isn't on shelf and return message if not
        if(!books.containsKey(book)) {
            System.out.println(book.getTitle() + " is not on shelf " + book.getSubject());
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }
        // Check if spot exists but no books on shelf
        if(books.containsKey(book) && books.get(book) == 0) {
            System.out.println("No copies of " + book.getTitle() + " remain on shelf " + book.getSubject());
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        } else {
            // If book is on shelf remove it and return Success Code
            int count = books.get(book) - 1;
            books.put(book, count);
            System.out.println(book + " successfully removed from shelf " + book.getSubject());
            return Code.SUCCESS;
        }
    }

    /**
     * This method returns a string listing of books on the Shelf
     * @return a String of books on Shelf
     */
    public String listBooks() {

        int count = 0;                              // Count to hold num of books on shelf
        String output;                              // String to hold output info
        StringBuilder str = new StringBuilder();    // StringBuilder to add Books
        // For each value in books map, add to count
        for(Book book : books.keySet()){
            count = count + books.get(book);
        }
        // Build a String of each book in Map to a StringBuilder
        for(Book book : books.keySet()) {
            str.append(book.toString()).append(" ").append(books.get(book)).append("\n");
        }
        // If only one book make output return singular version, otherwise plural
        if (count == 1){
            output = count + " book on shelf: " + getShelfNumber() + " : " + getSubject();
        } else {
            output = count + " books on shelf: " + getShelfNumber() + " : " + getSubject();
        }
        // As long as a book was on shelf, remove extra line from string builder and output
        if(str.length() > 1) {
            str.deleteCharAt(str.length() -1);
            return output + "\n" + str;
        }
        return output;  //Output if no books on Shelf
    }
}

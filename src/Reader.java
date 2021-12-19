import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Title: Reader.java - Pt 2/4 of Library Project
 * Author: Nicholas Fotinakes
 * Abstract: This class represents a Reader which will be used in the Library Project and
 * stores a reader's information and books they've checked out
 * Date: 11/8/2021
 */
public class Reader {

    // Public static final fields to parse file/array to obtain info
    public static final int CARD_NUMBER_ = 0;
    public static final int NAME_ = 1;
    public static final int PHONE_ = 2;
    public static final int BOOK_COUNT_ = 3;
    public static final int BOOK_START_ = 4;
    // Private fields to hold Reader info
    private int cardNumber;
    private String name;
    private String phone;
    private List<Book> books;   // This is the list of books Reader has checked out

    /**
     * Reader constructor. Set fields and initialize a List for books
     * @param cardNumber the reader's card number
     * @param name the reader's name
     * @param phone the reader's phone number
     */
    public Reader(int cardNumber, String name, String phone){
        this.cardNumber = cardNumber;
        this.name = name;
        this.phone = phone;
        books = new ArrayList<>();
    }

    /**
     * This method adds a book that a reader is checking out and checks if they already
     * have it
     * @param book the book to check out and add to list
     * @return the corresponding Code if successful or not
     */
    public Code addBook(Book book){
        // If book is not in Reader's List, add the book
        if(!books.contains(book)){
            books.add(book);
            return Code.SUCCESS;

        } else{
            // If user already has book, return this Code
            return Code.BOOK_ALREADY_CHECKED_OUT_ERROR;
        }
    }

    /**
     * This method removes a book from reader's List
     * @param book the book to remove
     * @return corresponding code
     */
    public Code removeBook(Book book){
        // If book isn't in List to be removed return error Code
        if(!books.contains(book)){
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        }
        // Otherwise, remove book and send success code
        if(books.contains(book)){
            books.remove(book);
            return Code.SUCCESS;
        } else{
            // Handle any other error
            return Code.READER_COULD_NOT_REMOVE_BOOK_ERROR;
        }
    }

    /**
     * This method checks if the Reader has a book on their list
     * @param book the book to check against reader's books
     * @return true or false if Reader has book in list
     */
    public boolean hasBook(Book book){
        return books.contains(book);
    }

    /**
     * This method returns the Reader's book count in list
     * @return the count of the reader's list
     */
    public int getBookCount(){
        return books.size();
    }

    /**
     * This method returns the reader's card number
     * @return reader's card number
     */
    public int getCardNumber() {
        return cardNumber;
    }

    /**
     * This method sets a reader's card number
     * @param cardNumber the card number to set
     */
    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * This method gets a reader's name
     * @return the reader's name
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets a reader's name
     * @param name the reader's name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method gets a reader's phone number
     * @return reader's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method sets the reader's phone number
     * @param phone the phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This method gets the Reader's book list
     * @return list of books
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * This method can set a List of books of reader
     * @param books the List of books to set
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Overridden equals method to check reader equality
     * @param o the other Reader to check
     * @return true or false if equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return getCardNumber() == reader.getCardNumber() && getName().equals(reader.getName()) && getPhone().equals(reader.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCardNumber(), getName(), getPhone());
    }

    /**
     * Overridden toString method to return formatted Reader output
     * @return formatted string about Reader's information
     */
    @Override
    public String toString() {
        // Use StringBuilder and iterate through List to remove Brackets
        StringBuilder str = new StringBuilder();
        for (Book book : books){
            str.append(book).append(", ");
        }
        // Remove extra space and comma at end
        if(str.length() > 1){
            str.setLength(str.length() - 2);
        }
        // Return formatted output
        String output = name + "(#" + cardNumber + ") has checked out {" + str + "}";
        return output;
    }
}

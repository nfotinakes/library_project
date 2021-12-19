import java.time.LocalDate;
import java.util.Objects;

/**
 * Title: Book.java - Pt 1/4 of Library Project
 * Author: Nicholas Fotinakes
 * Description: This class represents a book which will be used in the Library Project.
 * It stores descriptive information about a particular book.
 * Date: 11/8/2021
 */
public class Book {
    // Public static final integers to parse input from array
    public static final int ISBN_ = 0;
    public static final int TITLE_ = 1;
    public static final int SUBJECT_ = 2;
    public static final int PAGE_COUNT_ = 3;
    public static final int AUTHOR_ = 4;
    public static final int DUE_DATE_ = 5;
    // Private fields to store Book information
    private String isbn;
    private String title;
    private String subject;
    private int pageCount;
    private String author;
    private LocalDate dueDate;

    /**
     * Book Constructor
     * @param isbn The books ISBN
     * @param title The books title
     * @param subject The subject of the book
     * @param pageCount The page count of book
     * @param author Book's author
     * @param dueDate Book's due date
     */
    public Book(String isbn, String title, String subject, int pageCount, String author, LocalDate dueDate) {
        this.isbn = isbn;
        this.title = title;
        this.subject = subject;
        this.pageCount = pageCount;
        this.author = author;
        this.dueDate = dueDate;
    }

    /**
     * This method gets a book's ISBN
     * @return ISBN of book
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * This method sets a book's ISBN
     * @param isbn the ISBN to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * This method gets a book's title
     * @return title of book
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method sets a book's title
     * @param title the title to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method gets the books subject
     * @return subject of book
     */
    public String getSubject() {
        return subject;
    }

    /**
     * This method sets the book's subject
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * This method returns the page count of the book
     * @return the book's page count
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * This method sets the page count of a book
     * @param pageCount the page count to set
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * This method returns a book's author
     * @return the author of book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * This method sets the book's author
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * This method returns a book's due date
     * @return due date of book
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * This method sets the due date for a book
     * @param dueDate the date for book's due date
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * The overridden equals
     * @param o book to check
     * @return true or false if equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getPageCount() == book.getPageCount() && getIsbn().equals(book.getIsbn()) && getTitle().equals(book.getTitle()) && getSubject().equals(book.getSubject()) && getAuthor().equals(book.getAuthor());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getIsbn(), getTitle(), getSubject(), getPageCount(), getAuthor());
    }

    /**
     * This method is the overridden toString
     * @return formatted toString of the book
     */
    @Override
    public String toString(){
        return title + " by " + author + " ISBN: " + isbn;
    }
}


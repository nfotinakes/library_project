import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Title: BookTest.java
 * Author: Nicholas Fotinakes
 * Description: This class tests the methods and functionality of the Book class
 * Date: 11/8/2021
 */
class BookTest {
    // Fields used to set up and test methods
    String isbn;
    String title;
    String subject;
    int pageCount;
    String author;
    LocalDate dueDate;
    Book testbook;

    // Set up by filling out all fields with value
    @BeforeEach
    void setUp() {
        System.out.println("Processing setup...");
        isbn = "54321";
        title = "Test Title";
        subject = "Test Subject";
        pageCount = 555;
        author = "Edgar Allan Poe";
        dueDate = LocalDate.now();

    }

    // Teardown by setting everything to null
    @AfterEach
    void tearDown() {
        System.out.println("Processing teardown...");
        testbook = null;
        isbn = null;
        title = null;
        subject = null;
        pageCount = 0;
        author = null;
        dueDate = null;
    }

    // Check that book is null, then construct it and test it is no longer null
    @Test
    void Book() {
        assertNull(testbook);
        testbook = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertNotNull(testbook);
    }

    // Check that getter returns the ISBN
    @Test
    void getIsbn() {
        testbook = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertEquals(isbn, testbook.getIsbn());
    }

    // Check that setter properly changes ISBN
    @Test
    void setIsbn() {
        testbook = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertEquals(isbn, testbook.getIsbn());
        testbook.setIsbn("987");
        assertNotEquals(isbn, testbook.getIsbn());
    }

    // Check that getter returns title
    @Test
    void getTitle() {
        testbook = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertEquals(title, testbook.getTitle());

    }

    // Check that setter properly changes title
    @Test
    void setTitle() {
        testbook = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertEquals(title, testbook.getTitle());
        testbook.setTitle("New Title");
        assertNotEquals(title, testbook.getTitle());

    }

    // Test that getter returns subject
    @Test
    void getSubject() {
        testbook = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertEquals(subject, testbook.getSubject());
    }

    // Test that setter properly sets subject
    @Test
    void setSubject() {
        testbook = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertEquals(subject, testbook.getSubject());
        testbook.setSubject("New Subject");
        assertNotEquals(subject, testbook.getSubject());
    }

    // Test that getter returns page count
    @Test
    void getPageCount() {
        testbook = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertEquals(pageCount, testbook.getPageCount());
    }

    // Check that setter properly sets page count
    @Test
    void setPageCount() {
        testbook = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertEquals(pageCount, testbook.getPageCount());
        testbook.setPageCount(1);
        assertNotEquals(pageCount, testbook.getPageCount());
    }

    // Test that getter returns the author
    @Test
    void getAuthor() {
        testbook = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertEquals(author, testbook.getAuthor());
    }

    // Test that setter properly sets author value
    @Test
    void setAuthor() {
        testbook = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertEquals(author, testbook.getAuthor());
        testbook.setAuthor("New Author");
        assertNotEquals(author, testbook.getAuthor());
    }

    // Test that getter properly returns a due date
    @Test
    void getDueDate() {
        testbook = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertEquals(dueDate, testbook.getDueDate());
    }

    // Test that setter properly sets a Local Date
    @Test
    void setDueDate() {
        testbook = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertEquals(dueDate, testbook.getDueDate());
        testbook.setDueDate(LocalDate.parse("2007-12-03"));
        assertNotEquals(dueDate, testbook.getDueDate());
    }

    // Test that two book values are not identical, and then create identical book
    // and test that it is identical
    @Test
    void testEquals() {
        testbook = new Book(isbn, title, subject, pageCount, author, dueDate);
        Book testbook2 = new Book("45", "Tester", "SciFi", 55, "Me", LocalDate.now());
        assertNotEquals(testbook, testbook2);
        Book testbook3 = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertEquals(testbook, testbook3);
    }

    // Test that the toString properly outputs correctly formatted string
    @Test
    void testToString() {
        String testString = "Test Title by Edgar Allan Poe ISBN: 54321";
        testbook = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertEquals(testString, testbook.toString());
    }
}
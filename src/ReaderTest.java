import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Title: ReaderTest.java
 * Author: Nicholas Fotinakes
 * Description: This class tests the Reader class for the Library Project
 * Date: 11/8/2021
 */
class ReaderTest {

    // Fields used to set up and test methods
    int cardNumber;
    String name;
    String phone;
    List<Book> books;
    Reader testReader;
    Book testBook;

    // Set up by assigning values to fields
    @BeforeEach
    void setUp() {
        System.out.println("Processing setup...");
        cardNumber = 3;
        name = "Long John";
        phone = "805-555-1234";
        books = new ArrayList<>();
        testBook = new Book("123", "Deep Blue Sea", "sci-fi", 55, "Me", LocalDate.now());
    }

    // Tear down by setting everything back to null
    @AfterEach
    void tearDown() {
        System.out.println("Processing teardown...");
        cardNumber = 0;
        name = null;
        phone = null;
        books = null;
        testReader = null;
    }

    // Test constructor by first checking testReader is null, then constructing and
    // checking that it is no longer null
    @Test
    void Reader() {
        assertNull(testReader);
        testReader = new Reader(cardNumber, name, phone);
        assertNotNull(testReader);
    }

    // Test the addBook method by adding the testBook and checking that the proper
    // codes are returned
    @Test
    void addBook() {
        testReader = new Reader(cardNumber, name, phone);
        assertEquals(testReader.addBook(testBook), Code.SUCCESS);
        assertNotEquals(testReader.addBook(testBook), Code.SUCCESS);
        assertEquals(testReader.addBook(testBook), Code.BOOK_ALREADY_CHECKED_OUT_ERROR);
    }

    // Test the removeBook method by attempting to remove a book that is not added, and then
    // adding one and checking that proper codes are returned
    @Test
    void removeBook() {
        testReader = new Reader(cardNumber, name, phone);
        assertEquals(testReader.removeBook(testBook), Code.READER_DOESNT_HAVE_BOOK_ERROR);
        testReader.addBook(testBook);
        assertEquals(testReader.removeBook(testBook), Code.SUCCESS);
    }

    // Test the hasBook method by testing that a book isn't added, then adding it and
    // that it shows true
    @Test
    void hasBook() {
        testReader = new Reader(cardNumber, name, phone);
        assertFalse(testReader.hasBook(testBook));
        testReader.addBook(testBook);
        assertTrue(testReader.hasBook(testBook));
    }

    // Test getBookCount method by adding and removing a book and testing that the
    // proper count is returned
    @Test
    void getBookCount() {
        testReader = new Reader(cardNumber, name, phone);
        assertEquals(0, testReader.getBookCount());
        testReader.addBook(testBook);
        assertEquals(1, testReader.getBookCount());
        testReader.removeBook(testBook);
        assertEquals(0, testReader.getBookCount());
    }

    // Test getter by showing proper card number is returned
    @Test
    void getCardNumber() {
        testReader = new Reader(cardNumber, name, phone);
        assertEquals(cardNumber, testReader.getCardNumber());

    }

    // Test the setter by showing card number can be set
    @Test
    void setCardNumber() {
        testReader = new Reader(cardNumber, name, phone);
        assertEquals(cardNumber, testReader.getCardNumber());
        testReader.setCardNumber(5);
        assertNotEquals(cardNumber, testReader.getCardNumber());
    }

    // Test the getter returns proper name
    @Test
    void getName() {
        testReader = new Reader(cardNumber, name, phone);
        assertEquals(name, testReader.getName());
    }

    // Test that the setter properly sets the name
    @Test
    void setName() {
        testReader = new Reader(cardNumber, name, phone);
        assertEquals(name, testReader.getName());
        testReader.setName("Bilbo");
        assertNotEquals(name, testReader.getName());
    }

    // Test the getter returns the phone number
    @Test
    void getPhone() {
        testReader = new Reader(cardNumber, name, phone);
        assertEquals(phone, testReader.getPhone());
    }

    // Test that the setter can set the phone number
    @Test
    void setPhone() {
        testReader = new Reader(cardNumber, name, phone);
        assertEquals(phone, testReader.getPhone());
        testReader.setPhone("559-555-9874");
        assertNotEquals(phone, testReader.getPhone());
    }

    // Test the getBooks method returns the List of books
    @Test
    void getBooks() {
        testReader = new Reader(cardNumber, name, phone);
        testReader.addBook(testBook);
        books.add(testBook);
        assertEquals(books, testReader.getBooks());

    }

    // Test the setBooks can set the List of books properly by showing an initial list
    // equals the List set up, and then changing it
    @Test
    void setBooks() {
        testReader = new Reader(cardNumber, name, phone);
        assertEquals(books, testReader.getBooks());
        List<Book> testBookList = new ArrayList<>();
        Book testBook2 = new Book("45", "The Witcher", "history", 700, "Rupert", LocalDate.now());
        testBookList.add(testBook2);
        testReader.setBooks(testBookList);
        assertNotEquals(books, testReader.getBooks());
    }

    // Test that two Readers are equal
    @Test
    void testEquals() {
        testReader = new Reader(cardNumber, name, phone);
        Reader testReader2 = new Reader(88, "Luke", "999-888-7777");
        assertNotEquals(testReader, testReader2);
        Reader testReader3 = new Reader(cardNumber, name, phone);
        assertEquals(testReader, testReader3);
    }

    // Test that the proper toString String is returned
    @Test
    void testToString() {
        String outputTest = "Long John(#3) has checked out {Deep Blue Sea by Me ISBN: 123}";
        testReader = new Reader(cardNumber, name, phone);
        testReader.addBook(testBook);
        assertEquals(outputTest, testReader.toString());

    }
}
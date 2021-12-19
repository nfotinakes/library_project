import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Title: ShelfTest.java
 * Author: Nicholas Fotinakes
 * Abstract: This class tests the methods of the Shelf class for the Library Project
 * Date: 11/8/2021
 */
class ShelfTest {
    // Fields used to set up and test Shelf
    int shelfNumber;
    String subject;
    HashMap<Book, Integer> testMap;
    Book testBook;
    Shelf testShelf;

    // Set up by assigning values to fields
    @BeforeEach
    void setUp() {
        System.out.println("Processing setup...");
        shelfNumber = 1;
        subject = "sci-fi";
        testMap = new HashMap<>();
        testBook = new Book("123", "TNG", "sci-fi", 88, "Picard", LocalDate.now());
    }

    // Tear down by resetting to null
    @AfterEach
    void tearDown() {
        System.out.println("Processing teardown...");
        shelfNumber = 0;
        subject = null;
        testMap = null;
        testBook = null;
        testShelf = null;
    }

    // Test constructor by asserting first null, then constructing
    @Test
    void Shelf() {
        assertNull(testShelf);
        testShelf = new Shelf();
        assertNotNull(testShelf);
    }

    // Test shelf number getting to return proper shelf number which is 0 when first
    // constructed
    @Test
    void getShelfNumber() {
        testShelf = new Shelf();
        assertEquals(0, testShelf.getShelfNumber());

    }

    // Test setter to set the shelf number to 1
    @Test
    void setShelfNumber() {
        testShelf = new Shelf();
        testShelf.setShelfNumber(shelfNumber);
        assertEquals(shelfNumber, testShelf.getShelfNumber());
    }

    // Test getter to return subject field value which is null when constructed
    @Test
    void getSubject() {
        testShelf = new Shelf();
        assertNull(testShelf.getSubject());
    }

    // Test subject setter by checking null, changing with setter, and using
    // getter to check the change
    @Test
    void setSubject() {
        testShelf = new Shelf();
        assertNull(testShelf.getSubject());
        testShelf.setSubject("sci-fi");
        assertEquals("sci-fi", testShelf.getSubject());
    }

    // Test the getter to equal the null HashMap when constructed
    @Test
    void getBooks() {
        testShelf = new Shelf();
        assertEquals(testMap, testShelf.getBooks());
    }

    // Test setter by creating a new HashMap to set to, and checking it has been
    // changed
    @Test
    void setBooks() {
        HashMap<Book, Integer> newMap = new HashMap<>();
        newMap.put(testBook, 4);
        testShelf = new Shelf();
        testShelf.setBooks(newMap);
        assertEquals(newMap, testShelf.getBooks());
    }

    // Test for equality by creating two shelfs with same subject and book
    @Test
    void testEquals() {
        testShelf = new Shelf();
        testShelf.setSubject("sci-fi");
        Shelf testShelf2 = new Shelf();
        testShelf2.setSubject("sci-fi");
        testShelf.addBook(testBook);
        testShelf2.addBook(testBook);
        assertEquals(testShelf, testShelf2);
    }

    // Test that toString returns expected String value
    @Test
    void testToString() {
        String testOutput = "0 : sci-fi";
        testShelf = new Shelf();
        testShelf.setSubject("sci-fi");
        testShelf.addBook(testBook);
        assertEquals(testOutput, testShelf.toString());
    }

    // Test that the getBookCount method works by putting a random number of books
    // onto shelf and that the count matches, then remove one, check count again,
    // then remove all and check that shelf has zero copies
    @Test
    void getBookCount() {
        testShelf = new Shelf();
        testShelf.setSubject("sci-fi");
        Random rand = new Random();
        int randNum = rand.nextInt(6) + 2;
        System.out.println("Adding " + randNum + " copies of testBook...");
        for(int i = 0; i < randNum; i++){
            testShelf.addBook(testBook);
        }
        assertEquals(randNum, testShelf.getBookCount(testBook));
        testShelf.removeBook(testBook);
        randNum = randNum - 1;
        assertEquals(randNum, testShelf.getBookCount(testBook));

        for(int j = randNum; j > 0; j--){
            testShelf.removeBook(testBook);
        }
        assertEquals(0, testShelf.getBookCount(testBook));
        assertEquals(testShelf.removeBook(testBook), Code.BOOK_NOT_IN_INVENTORY_ERROR);
    }

    // Test that the addBook method works to add a book and return the proper Code
    @Test
    void addBook() {
        testShelf = new Shelf();
        testShelf.setSubject("sci-fi");
        assertEquals(testShelf.addBook(testBook), Code.SUCCESS);
        assertEquals(1, testShelf.getBookCount(testBook));
        testShelf.addBook(testBook);
        assertEquals(2, testShelf.getBookCount(testBook));
        Book testBook2 = new Book("77", "History of Montana", "history", 800, "Waldo", LocalDate.now());
        assertEquals(testShelf.addBook(testBook2), Code.SHELF_SUBJECT_MISMATCH_ERROR);
        assertEquals(-1, testShelf.getBookCount(testBook2));
    }

    // Test that the removeBook method works and returns the proper code
    @Test
    void removeBook() {
        testShelf = new Shelf();
        testShelf.setSubject("sci-fi");
        Book testBook2 = new Book("77", "History of Montana", "history", 800, "Waldo", LocalDate.now());
        assertEquals(testShelf.removeBook(testBook2), Code.BOOK_NOT_IN_INVENTORY_ERROR);
        testShelf.addBook(testBook);
        assertEquals(1, testShelf.getBookCount(testBook));
        assertEquals(testShelf.addBook(testBook), Code.SUCCESS);
        assertEquals(testShelf.removeBook(testBook), Code.SUCCESS);
        assertEquals(1, testShelf.getBookCount(testBook));
        testShelf.removeBook(testBook);
        assertEquals(testShelf.removeBook(testBook), Code.BOOK_NOT_IN_INVENTORY_ERROR);
        assertEquals(0, testShelf.getBookCount(testBook));
    }

    // Test that the listBooks method works by returning an expected String value
    @Test
    void listBooks() {
        String testString = "1 book on shelf: 0 : sci-fi\n" + "TNG by Picard ISBN: 123 1";
        testShelf = new Shelf();
        testShelf.setSubject("sci-fi");
        testShelf.addBook(testBook);
        assertEquals(testString, testShelf.listBooks());
    }
}
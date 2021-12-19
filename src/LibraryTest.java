import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Title: LibraryTest.java
 * Author: Nicholas Fotinakes
 * Abstract: Test file for the Library.java class
 * Date: 11-21-2021
 */
class LibraryTest {

    String name;
    List<Reader> readers;
    HashMap<String, Shelf> shelves;
    HashMap<Book, Integer> books;
    Library testLibrary;

    @BeforeEach
    void setUp() {
        System.out.println("Processing setup...");
        name = "Test Library";
        readers = new ArrayList<>();
        shelves = new HashMap<>();
        books = new HashMap<>();
    }

    @AfterEach
    void tearDown() {
        System.out.println("Processing teardown...");
        name = null;
        readers = null;
        shelves = null;
        books = null;
        testLibrary = null;
    }

    // Test that the constructor creates a Library object
    @Test
    void Library() {
        assertNull(testLibrary);
        testLibrary = new Library(name);
        assertNotNull(testLibrary);
    }

    // Test that init method reads a filename and parses for books, shelves and readers
    @Test
    void init() {
        testLibrary = new Library(name);
        // Check that calling the method with no file name returns error
        assertEquals(Code.FILE_NOT_FOUND_ERROR, testLibrary.init("Wrong.csv"));

        // Pass the test file and check for success code after calling initBooks,
        // initShelves, and initReader
        assertEquals(Code.SUCCESS, testLibrary.init("TestLibrary.txt"));
    }

    // Test the addBook method of when a shelf exists or not to return a correct code
    @Test
    void addBook() {
        testLibrary = new Library(name);
        Book testBook = new Book("45", "Tester", "education", 55, "Me", LocalDate.now());

        // Try to addBook when no shelf exists to get error
        assertEquals(Code.SHELF_EXISTS_ERROR, testLibrary.addBook(testBook));

        // Parse file which creates education shelf, then try to add testBook
        testLibrary.init("TestLibrary.txt");
        assertEquals(Code.SUCCESS, testLibrary.addBook(testBook));

    }

    // Test the returnBook with two parameters
    @Test
    void returnBook() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");
        Reader testReader = testLibrary.getReaderByCard(1);
        Book testBook = testLibrary.getBookByISBN("1234");
        Book testBook2 = new Book("55", "Tester2", "education", 700, "Me", LocalDate.now());

        // First test that with a book the reader doesn't have
        assertEquals(Code.READER_DOESNT_HAVE_BOOK_ERROR, testLibrary.returnBook(testReader, testBook2));

        // Check for returning a book the reader does have
        assertEquals(Code.SUCCESS, testLibrary.returnBook(testReader, testBook));
    }

    // Testing returnBook with only book parameter against when a shelf exists
    // or not
    @Test
    void testReturnBook() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");
        Book testBook2 = testLibrary.getBookByISBN("1234");
        Book testBook3 = new Book("55", "Tester2", "sci-fi", 700, "Me", LocalDate.now());
        // Try to return when shelf doesn't exist
        assertEquals(Code.SHELF_EXISTS_ERROR, testLibrary.returnBook(testBook3));

        // Try to return book when shelf exists
        assertEquals(Code.SUCCESS, testLibrary.returnBook(testBook2));
    }

    // Test the listBooks method to return the correct number of books
    @Test
    void listBooks() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");
        // test that 1 is returned when called
        assertEquals(1, testLibrary.listBooks());
    }

    // Test the checkOutBook method from a user not registered, and then one who is registered to Library
    @Test
    void checkOutBook() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");
        Book testBook1 = testLibrary.getBookByISBN("1234");
        Reader testReader1 = new Reader(2,"Dudley", "805-555-5555");
        Reader testReader2 = testLibrary.getReaderByCard(1);

        // Attempt for a reader not registered to check out a book
        assertEquals(Code.READER_NOT_IN_LIBRARY_ERROR, testLibrary.checkOutBook(testReader1, testBook1));

        // Return the book testReader2 checks out during init, and attempt to check out again
        // Should receive Success code
        testLibrary.returnBook(testReader2, testBook1);
        assertEquals(Code.SUCCESS, testLibrary.checkOutBook(testReader2, testBook1));
    }

    // Test getBookByISBN to see if correct book is returned
    @Test
    void getBookByISBN() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");
        // Add a book to testLibrary
        Book testBook2 = new Book("45", "Tester", "education", 55, "Me", LocalDate.now());
        testLibrary.addBook(testBook2);
        // Use method to get and test if it is the same
        assertEquals(testBook2, testLibrary.getBookByISBN("45"));
    }

    // Test that listBooks returns Success code
    @Test
    void listShelves() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");

        // Test that when true, we get success code
        assertEquals(Code.SUCCESS, testLibrary.listShelves(true));
        // Test when false we get success code with alternate if statement
        assertEquals(Code.SUCCESS, testLibrary.listShelves(false));
    }

    // Test addShelf when trying to add a new shelf
    @Test
    void addShelf() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");
        Shelf testShelf = testLibrary.getShelf("education");
        Shelf testShelf2 = new Shelf();
        testShelf2.setSubject("sci-fi");

        // Try to add existing shelf to get error code
        assertEquals(Code.SHELF_EXISTS_ERROR, testLibrary.addShelf(testShelf));
        // Try to add a new shelf and get success code
        assertEquals(Code.SUCCESS, testLibrary.addShelf(testShelf2));
    }

    // Test addShelf by subject. I believe there may be a slight bug here.
    // The prompt calls to add 1 to shelf size for number, in doing so this will
    // allow for a shelf to be made with same subject. So will it will always be Success.
    @Test
    void testAddShelf() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");

        // Test to add a new shelf by subject
        assertEquals(Code.SUCCESS, testLibrary.addShelf("sci-fi"));
    }

    // Test getShelf method that passes shelf by shelf number
    @Test
    void getShelf() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");
        // Create matching shelf to test against
        Shelf testShelf = new Shelf();
        testShelf.setSubject("education");
        testShelf.setShelfNumber(1);

        // getShelf should return the same shelf when called with shelf number
        assertEquals(testShelf, testLibrary.getShelf(1));
        // check for null return when getShelf calls shelf that doesn't exist
        assertNull(testLibrary.getShelf(2));
    }

    // Test getShelf method that passes shelf by subject
    @Test
    void testGetShelf() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");
        // Create matching shelf to test against
        Shelf testShelf = new Shelf();
        testShelf.setSubject("education");
        testShelf.setShelfNumber(1);

        // getShelf should return the same shelf when called with shelf subject
        assertEquals(testShelf, testLibrary.getShelf("education"));
        // Should return null when passing shelf that doesn't exist
        assertNull(testLibrary.getShelf("sci-fi"));
    }

    // Test listReaders to return proper integer
    @Test
    void listReaders() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");

        // There should be 1 reader, test against this
        assertEquals(1, testLibrary.listReaders());
    }

    // Test listReaders with a boolean parameter
    @Test
    void testListReaders() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");
        // Test that it returns 1
        assertEquals(1, testLibrary.listReaders(true));
    }

    // Test getReaderByCard to return expected reader
    @Test
    void getReaderByCard() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");
        Reader testReader = new Reader(1, "Test Reader", "555-555-5555");

        // Should match the testReader created
        assertEquals(testReader, testLibrary.getReaderByCard(1));
        // Should return null when getting card number not existing
        assertNull(testLibrary.getReaderByCard(2));
    }

    // Test conditions when adding a new reader to library
    @Test
    void addReader() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");
        Reader testReader = new Reader(1, "Test Reader", "555-555-5555");
        Reader testReader2 = new Reader(1, "Test Reader 2", "444-444-4444");
        Reader testReader3 = new Reader(2, "Test Reader 2", "444-444-4444");

        // Test for error when reader exists
        assertEquals(Code.READER_ALREADY_EXISTS_ERROR, testLibrary.addReader(testReader));
        // Test for error when card numbers are the same
        assertEquals(Code.READER_CARD_NUMBER_ERROR, testLibrary.addReader(testReader2));
        // Test for successful add of new reader
        assertEquals(Code.SUCCESS, testLibrary.addReader(testReader3));

    }

    // Test removeReader against different conditions
    @Test
    void removeReader() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");
        Reader testReader = testLibrary.getReaderByCard(1);
        Reader testReader2 = new Reader(2, "Test Reader 2", "444-444-4444");
        Book testBook = testLibrary.getBookByISBN("1234");

        // Try to remove Reader when they still have books
        assertEquals(Code.READER_STILL_HAS_BOOKS_ERROR, testLibrary.removeReader(testReader));
        // Try to remove a Reader not registered
        assertEquals(Code.READER_NOT_IN_LIBRARY_ERROR, testLibrary.removeReader(testReader2));

        // Remove reader when they have no books, should be success
        testLibrary.returnBook(testReader, testBook);
        assertEquals(Code.SUCCESS, testLibrary.removeReader(testReader));

    }

    // Test that convertInt returns correct integer or int associated with Code error
    @Test
    void convertInt() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");

        // Test that correct integers are returned from Codes passed when String cannot be parsed
        assertEquals(-8, testLibrary.convertInt("Z", Code.PAGE_COUNT_ERROR));
        assertEquals(-2, testLibrary.convertInt("Z", Code.BOOK_COUNT_ERROR));
        assertEquals(-101, testLibrary.convertInt("Z", Code.DATE_CONVERSION_ERROR));

        // Test for successful integer return when string can be parsed
        assertEquals(15, testLibrary.convertInt("15", Code.PAGE_COUNT_ERROR));
        assertEquals(15, testLibrary.convertInt("15", Code.BOOK_COUNT_ERROR));
        assertEquals(15, testLibrary.convertInt("15", Code.DATE_CONVERSION_ERROR));
    }

    // Test that the convert date method parses dates
    @Test
    void convertDate() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");
        String defaultDate = "01-Jan-1970"; // The default date used
        String testDate = "1990-10-15";     // String for test Date passeed
        String testDateFinal = "19901015";  // Used to create formatted testdate for assertion
        // Formatters for dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");
        // Create dates for assertion tests
        LocalDate testDefaultDate = LocalDate.parse(defaultDate, formatter);
        LocalDate testDateConvert = LocalDate.parse(testDateFinal, formatter2);

        // test that default date is returned when 0000 is passed
        assertEquals(testDefaultDate, testLibrary.convertDate("0000", Code.DATE_CONVERSION_ERROR));
        // test that correctly parses from 1990-10-15
        assertEquals(testDateConvert, testLibrary.convertDate(testDate, Code.DATE_CONVERSION_ERROR));
    }

    @Test
    void getLibraryCardNumber() {
        testLibrary = new Library(name);
        testLibrary.init("TestLibrary.txt");
        Reader testReader = new Reader(2, "Test Reader 2", "444-444-4444");
        System.out.println(testLibrary.getLibraryCardNumber());
        testLibrary.addReader(testReader);
        testLibrary.listReaders(true);
        System.out.println(testLibrary.getLibraryCardNumber());
//        assertEquals(1, testLibrary.getLibraryCardNumber());
//        testLibrary.addReader(testReader);
//        System.out.println(testLibrary.getLibraryCardNumber());

    }
}
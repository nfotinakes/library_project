import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Title: Library.Java - Pt. 4/4 of Library Project
 * Author: Nicholas Fotinakes
 * Description: This class represents a Library that parses a file for books, shelves, and
 * readers. It can sort the books on shelves by subject and handle and keep track of different
 * Readers who return or check out books.
 * Date: 11/12/2021
 */
public class Library {


    public static final int LENDING_LIMIT = 5;  // The set limit of books a reader can check out
    private String name;                        // Hold library name
    private static int libraryCard;             // Max library card number
    private List<Reader> readers;               // A list of readers
    private HashMap<String, Shelf> shelves;     // Map of shelf values by subject
    private HashMap<Book, Integer> books;       // Map of Books and their count

    /**
     * Library constructor. Takes a name for library and initializes maps and lists
     * @param name the name to set for the Library
     */
    public Library(String name) {
        this.name = name;
        books = new HashMap<>();
        shelves = new HashMap<>();
        readers = new ArrayList<>();
    }

    /**
     * The init starts the sorting of the file into Books, Shelves, and Readers to begin
     * populating the Library
     * @param filename the file to parse
     * @return a Code if successful or not
     */
    public Code init(String filename) {
        File file = new File(filename); // Create file object from file to parse
        Scanner inputFile;              // Declare Scanner to use for parsing
        String input;                   // input to hold line from file
        int lineCount;                  // lineCount holds how many lines to parse

        // Try to open file and start parsing, otherwise catch the error and return Code
        try {
            inputFile = new Scanner(file);  // Create the Scanner object with file

            // Scanning Books
            input = inputFile.nextLine();           // Read the first line which should be a number
            lineCount = convertInt(input, Code.UNKNOWN_ERROR);    // Convert that to an integer
            initBooks(lineCount, inputFile);        // Send this to initBooks to the num of books
            // Print SUCCESS and call listBooks after parsing
            System.out.println("SUCCESS");
            listBooks();

            //Scanning Shelves
            input = inputFile.nextLine();           // Read next line after parsing books for shelf count
            lineCount = convertInt(input, Code.UNKNOWN_ERROR);    // Convert to integer
            initShelves(lineCount, inputFile);      // Send to initShelves to parse shelves
            // Print success and call listShelves after parsing shelves
            System.out.println("SUCCESS");
            listShelves(true);

            //Scanning Readers
            input = inputFile.nextLine();           // Read next line after parsing Shelves
            lineCount = convertInt(input, Code.UNKNOWN_ERROR);    // Convert to integer to check how many readers
            initReader(lineCount, inputFile);       // Send count and Scanner to initReader to parse Readers
            System.out.println("SUCCESS");

        // If exception thrown, return error
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file " + e);
            return Code.FILE_NOT_FOUND_ERROR;
        }
        return Code.SUCCESS;
    }

    /**
     * The initBooks method parses the file for books from the book count sent from init
     * @param bookCount the amount of lines(books) to parse
     * @param scan the Scanner using to parse
     * @return The Code value of result
     */
    private Code initBooks(int bookCount, Scanner scan) {
        // Declare fields to be used to parse books
        String input;          // Hold a string parsed from file
        String[] bookArray;    // Array to hold split values from line
        String isbn, title, subject, pageCount, author, dueDate; // Values to fill array
        int parsedPageCount;


        // If no lines of books return error
        if(bookCount < 1) {
            return Code.LIBRARY_ERROR;
        }
        // Begin parsing lines passed on bookCount and building Books
        System.out.println("Parsing " + bookCount + " books");
        for(int i = 0; i < bookCount; i++){
            // Read line and split into the array
            input = scan.nextLine();
            bookArray = input.split(",");
            // Fill the array from Books final static int variables
            isbn = bookArray[Book.ISBN_];
            title = bookArray[Book.TITLE_];
            subject = bookArray[Book.SUBJECT_];
            pageCount = bookArray[Book.PAGE_COUNT_];
            author = bookArray[Book.AUTHOR_];
            dueDate = bookArray[Book.DUE_DATE_];

            // Convert pageCount to integer and dueDate to a LocalDate using
            // corresponding methods, and check for 0 or null
            parsedPageCount = convertInt(pageCount, Code.PAGE_COUNT_ERROR);
            if (parsedPageCount <= 0) {
                return Code.PAGE_COUNT_ERROR;
            }
            LocalDate parsedDate = convertDate(dueDate, Code.DATE_CONVERSION_ERROR);
            if (parsedDate == null) {
                return Code.DATE_CONVERSION_ERROR;
            }

            System.out.println("Parsing book: " + input);
            // Call addBook method to addBook to Library
            addBook(new Book(isbn, title, subject, parsedPageCount, author, parsedDate));

        }
        // After parsing all books return success code
        return Code.SUCCESS;
    }

    /**
     * The initShelves method takes a count of how many lines to parse to create Shelf objects
     * and add to Library and populate with books
     * @param shelfCount the amount of lines/shelves to parse
     * @param scan the Scanner to use for parsing
     * @return Corresponding code
     */
    private Code initShelves(int shelfCount, Scanner scan) {
        String input;                   // To hold line read by scanner
        String[] shelfArray;            // Array to hold line values split by comma
        String shelfNumber, subject;    // Fields to store in array
        int parsedShelfNumber;

        // First check that count sent isn't zero
        if(shelfCount < 1) {
            return Code.SHELF_COUNT_ERROR;
        }

        // Parse each line based on shelfCount to create shelves from file
        System.out.println("Parsing " + shelfCount + " shelves");
        for(int i = 0; i < shelfCount; i++) {
            // Read line and split into array
            input = scan.nextLine();
            shelfArray = input.split(",");
            // Fill array based on static fields from Shelf class
            shelfNumber = shelfArray[Shelf.SHELF_NUMBER_];
            subject = shelfArray[Shelf.SUBJECT_];
            parsedShelfNumber = convertInt(shelfNumber, Code.SHELF_COUNT_ERROR);
            // Create a Shelf based on parsed line and set subject and number using methods
            Shelf testShelf = new Shelf();
            testShelf.setSubject(subject);
            testShelf.setShelfNumber(parsedShelfNumber);

            System.out.println("Parsing Shelf: " + input);

            // Call addShelf to add to Library
            addShelf(testShelf);
        }

        // Check that we parsed the correct amount of shelves
        if(shelves.size() == shelfCount) {
            return Code.SUCCESS;
        }else{
            return Code.SHELF_NUMBER_PARSE_ERROR;
        }
    }

    /**
     * The initReader method takes a count of how many lines to parse to create Reader objects
     * and then add to Library and add any books the Reader has
     * @param readerCount the amount of lines/readers to parse
     * @param scan the Scanner used for parsing
     * @return Corresponding code
     */
    private Code initReader(int readerCount, Scanner scan) {
        // Declare variables to be used to create reader
        String input;
        String[] readerArray;
        String cardNumber, name, phone, bookCount, bookStart, isbn;
        LocalDate date;

        // Check that count is greater than 0 before parsing
        if(readerCount <= 0) {
            return Code.READER_COUNT_ERROR;
        }

        // Parse the set amount of lines to create Readers
        for (int i = 0; i < readerCount; i++) {
            // Read the next line and split into an array
            input = scan.nextLine();
            readerArray = input.split(",");
            // Set array values based on Reader static values
            cardNumber = readerArray[Reader.CARD_NUMBER_];
            name = readerArray[Reader.NAME_];
            phone = readerArray[Reader.PHONE_];
            bookCount = readerArray[Reader.BOOK_COUNT_];
            bookStart = readerArray[Reader.BOOK_START_];

            // Create a new reader and add to readers List
            Reader reader = new Reader(Integer.parseInt(cardNumber), name, phone);
            readers.add(reader);

            int bookCountInt = Integer.parseInt(bookCount); // The amount of books Reader has
            int check = 0;  // Used to check if counted all readers books to break loop

            // Check if the Reader line has any books already from input line
            // Staring from the BOOK_START index
            for (int j = Reader.BOOK_START_; j < readerArray.length; j++ ){
                // The first index will be isbn
                isbn = readerArray[j];
                // Next will be the dueDate, parsed using convertDate method
                date = convertDate(readerArray[j + 1], Code.DATE_CONVERSION_ERROR);
                // increment number of books checked
                check++;
                // Use checkOutBook method to check the book out for reader based on isbn
                if(!books.containsKey(getBookByISBN(isbn))){
                    System.out.println("ERROR");
                } else {
                    checkOutBook(reader, getBookByISBN(isbn));
                    getBookByISBN(isbn).setDueDate(date);
                    System.out.println("SUCCESS");
                }
                // If we count all books break loop to avoid array error
                if(check == bookCountInt){
                    break;
                }
                j++;
            }
        }
        return Code.SUCCESS;
    }

    /**
     * The addBook method adds a book object to the books hashMap in Library to keep track of
     * books registered to the Library and the book count
     * @param newBook the book to add
     * @return Corresponding code
     */
    public Code addBook(Book newBook) {
        // Check if book has already been registered
        // if so increase copy count and print a statement
        if(books.containsKey(newBook)){
            int count;
            count = books.get(newBook) + 1;
            books.put(newBook, count);
            System.out.println(count + " copies of " + newBook + " in the stacks");
        // If book hasn't been registered, add it to books HashMap
        } else if(!books.containsKey(newBook)) {
            books.put(newBook, 1);
            System.out.println(newBook + " added to the stacks.");
        }

        // If a shelf exists already with matching subject of book add it to the shelf
        // Otherwise, return a statement saying no shelf exists yet
        if(shelves.containsKey(newBook.getSubject())){
            shelves.get(newBook.getSubject());
            return Code.SUCCESS;
        } else {
            System.out.println("No shelf for " + newBook.getSubject() + " books");
        }
        return Code.SHELF_EXISTS_ERROR;
    }

    /**
     * The returnBook method returns a book that a Reader has checked out
     * @param reader the Reader returning a book
     * @param book the book being returned
     * @return Corresponding code
     */
    public Code returnBook(Reader reader, Book book) {
        // First check that reader actually has the book
        if(!reader.hasBook(book)){
            System.out.println(reader.getName() + " doesn't have " + book.getTitle() + " checked out");
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        // If so, return the book using removeBook method
        } else if (reader.hasBook(book)){
            System.out.println(reader.getName() + " is returning " + book);
            reader.removeBook(book);
            returnBook(book);
            return Code.SUCCESS;
        }
        // If any other error return this
        System.out.println("Could not return " + book);
        return Code.READER_COULD_NOT_REMOVE_BOOK_ERROR;
    }

    /**
     * This returnBook method returns a Book object by calling addBook method
     * @param book the book to be returned
     * @return Corresponding Code
     */
    public Code returnBook(Book book) {
        // Check if a shelf exists for the book by subject
        if(!shelves.containsKey(book.getSubject())){
            System.out.println("No shelf for " + book);
            return Code.SHELF_EXISTS_ERROR;
        }
        // If so call addBook to return book
        return shelves.get(book.getSubject()).addBook(book);
    }

    /**
     * The addBookToShelf method attempts to return a book and add it back to a Shelf
     * @param book the book to return
     * @param shelf the shelf to add the book to
     * @return Corresponding code
     */
    private Code addBookToShelf(Book book, Shelf shelf) {
        // Attempt to return book and save the code returned, check if successful
        Code code = returnBook(book);
        if(code == Code.SUCCESS) {
            return Code.SUCCESS;
        }
        // If shelf and book subject don't match return error
        if(!shelf.getSubject().equals(book.getSubject())){
            return Code.SHELF_SUBJECT_MISMATCH_ERROR;
        }
        // Attempt to add book to shelf and check if successful
        code = shelf.addBook(book);
        if(code == Code.SUCCESS){
            System.out.println(book + " added to shelf");
            return Code.SUCCESS;
        }
        // If it could not be added to shelf return error
        System.out.println("Could not add " + book + " to shelf");
        return code;
    }

    /**
     * The listBooks method lists all Books registered to the Library
     * @return a print statement with all copies of books
     */
    public int listBooks() {
        int totalCount = 0; // Keep track of total books in library
        // Iterate through all books registered and display the book and
        // how many are registered. Add to totalCount
        for(Book book : books.keySet()){
            totalCount = totalCount + books.get(book);
            System.out.println(books.get(book) + " copies of " + book);
        }
        // Returns the total number of books
        return totalCount;
    }

    /**
     * The checkOutBook method checks that a book is available in Library and then checks
     * that book out for a reader if possible
     * @param reader the Reader to check out book
     * @param book the book to checkout
     * @return Corresponding code
     */
    public Code checkOutBook(Reader reader, Book book) {
        // If reader isn't registered to Library show error
        if (!readers.contains(reader)) {
            System.out.println(reader.getName() + " doesn't have an account here");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        // If reader already has lending limit of books, return error
        } else if (readers.contains(reader) && reader.getBookCount() == LENDING_LIMIT){
            System.out.println(reader.getName() + " has reached the lending limit, (" + LENDING_LIMIT + ")");
            return Code.BOOK_LIMIT_REACHED_ERROR;
        // If book hasn't been registered to library return error
        } else if (readers.contains(reader) && !books.containsKey(book)){
            System.out.println("ERROR: could not find " + book);
        // If shelf isn't made to hold book, return error
        } else if (readers.contains(reader) && !shelves.containsKey(book.getSubject())){
            System.out.println("no shelf for " + book.getSubject() + "books!");
            return Code.SHELF_EXISTS_ERROR;
        // If no copies of book are available, return error
        } else if (readers.contains(reader) && books.get(book) < 1) {
            System.out.println("ERROR: no copies of " + book + " remain");
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }
        // Try to add a Book to the Readers list of books they have checked out
        Code code = reader.addBook(book);
        // If success code returned, then call removeBook and check the book out
        if(!code.equals(Code.SUCCESS)){
            System.out.println("Couldn't checkout " + book);
        }
        code = shelves.get(book.getSubject()).removeBook(book);
        if (code.equals(Code.SUCCESS)){
            System.out.println(book + " checked out successfully");
        }
        return code;
    }

    /**
     * The getBookByISBN method checks if book has been registered based by isbn number
     * and returns book if so
     * @param isbn the ISBN to check for a book
     * @return the Book if found, or null if not
     */
    public Book getBookByISBN(String isbn) {
        // Iterate though the books registered, return the book if found by ISBN
        for(Book book : books.keySet()){
            if(book.getIsbn().equals(isbn)){
                return book;
            }
        }
        // If not found, return null
        System.out.println("ERROR: Could not find a book with isbn: " + isbn);
        return null;
    }

    /**
     * The listShelves method takes a boolean and either calls a listBooks method
     * to display or if boolean is false, it will print the Shelves registered using
     * toString
     * @param showbooks the boolean to determine what to print
     * @return Success Code after printing
     */
    public Code listShelves(boolean showbooks) {
        if(showbooks){
            for(String s : shelves.keySet()){
                System.out.println(shelves.get(s).listBooks());
                System.out.println();
            }
        } else {
            for(String s : shelves.keySet()) {
                System.out.println(shelves.get(s).toString());
            }
        }
        return Code.SUCCESS;
    }

    /**
     * The addShelf method adds a Shelf based on subject
     * @param shelfSubject the subject to add shelf with
     * @return Corresponding code
     */
    public Code addShelf(String shelfSubject) {
        // Create the shelf and set number and subject
        Shelf newShelf = new Shelf();
        newShelf.setShelfNumber(shelves.size() + 1);
        newShelf.setSubject(shelfSubject);
        // Use addShelf method that accepts Shelf object to add shelf and return code
        return addShelf(newShelf);
    }

    /**
     * This addShelf takes a shelf object and adds it to the Library
     * @param shelf the Shelf to add
     * @return the corresponding Code
     */
    public Code addShelf(Shelf shelf) {
        // If the shelf has already been added to Library shelves HashMap
        // print a statement and return error
        if(shelves.containsValue(shelf)){
            System.out.println("Error: Shelf already exists " + shelf);
            return Code.SHELF_EXISTS_ERROR;
        }
        // Add shelf to Map
        shelves.put(shelf.getSubject(), shelf);
        // Check all books registered and check if subject matches the shelf
        // If so add it to the shelf
        for(Book book : books.keySet()) {
            if(book.getSubject().equals(shelf.getSubject())){
                for(int i = 0; i < books.get(book); i++) {
                    shelf.addBook(book);
                }
            }
        }
        return Code.SUCCESS;
    }

    /**
     * The getShelf method returns a Shelf based on the integer associated with the
     * Shelf number
     * @param shelfNumber the number to check for a Shelf
     * @return the Shelf if found, otherwise null
     */
    public Shelf getShelf(Integer shelfNumber) {
        // Check each shelf in shelves
        for(String s : shelves.keySet()) {
            // If shelf number matches integer searching return the shelf
            if(shelves.get(s).getShelfNumber() == shelfNumber){
                return shelves.get(s);
            }
        }
        // If no shelf number matches, return null
        System.out.println("No shelf number " + shelfNumber + " found");
        return null;
    }

    /**
     * The getShelf method returns a Shelf based on the subject of the shelf
     * @param subject the subject to check a shelf to get
     * @return the shelf if subject matches
     */
    public Shelf getShelf(String subject) {
        // Iterate through the shelves registered
        for(String s : shelves.keySet()) {
            // If the registered shelf matches the subject passed in parameter
            // return that shelf
            if(shelves.get(s).getSubject().equals(subject)){
                return shelves.get(s);
            }
        }
        // Otherwise, return null
        System.out.println("No shelf for " + subject + " books");
        return null;
    }

    /**
     * The listReaders method prints all the Readers registered as well as returns
     * the total amount of Readers
     * @return total count of readers
     */
    public int listReaders() {
        // Iterate through each reader registered and print their corresponding toString
        for(Reader reader : readers) {
            System.out.println(reader);
        }
        // Return reader count
        return readers.size();
    }

    /**
     * The listReaders method prints Readers registered. Two different prints are possible
     * depending on boolean passed as parameter
     * @param showBooks the boolean to print differing statement
     * @return the total amount of readers
     */
    public int listReaders(boolean showBooks) {
        // If true is passed print a custom statement for each reader
        if(showBooks){
            for(Reader reader : readers) {
                System.out.println(reader.getName() + "(#" + reader.getCardNumber() +
                        ") has the following books:");
                System.out.println(reader.getBooks());
            }
            // Return reader count
            return readers.size();
        }
        // If false is passed, print each reader's toString
        for(Reader reader : readers) {
            System.out.println(reader.toString());
        }
        // Return reader count
        return readers.size();
    }

    /**
     * The getReaderByCard method returns a Reader based on the card number associated with
     * that Reader object
     * @param cardNumber the card number of a possible reader
     * @return Reader if card number found
     */
    public Reader getReaderByCard(int cardNumber) {
        // Iterate through each registered Reader
        for(Reader reader : readers) {
            // If card number passed matches a Reader's card number return the reader
            if(reader.getCardNumber() == cardNumber){
                System.out.println("Returning Reader " + reader);
                return reader;
            }
        }
        // If no card number matches, return null
        System.out.println("Could not find a reader with card#" + cardNumber);
        return null;
    }

    /**
     * The addReader method checks tries to add a reader to the library. It checks that there isn't
     * already an existing reader account or existing card number
     * @param reader the Reader to add
     * @return Corresponding Code
     */
    public Code addReader(Reader reader) {
        // If reader already registered return error
        if (readers.contains(reader)){
            System.out.println(reader.getName() + " already has an account!");
            return Code.READER_ALREADY_EXISTS_ERROR;
        }
        // Check registered Readers card numbers, return error if matches the reader
        // card number that trying to add
        for (Reader value : readers) {
            if (value.getCardNumber() == reader.getCardNumber()) {
                System.out.println(value.getName() + " and " + reader.getName() +
                        " have the same card number!");
                return Code.READER_CARD_NUMBER_ERROR;
            }
        }
        // If checks passed add the reader and check the libraryCard number
        readers.add(reader);
        System.out.println(reader.getName() + " added to the library!");
        if (reader.getCardNumber() > libraryCard){
            libraryCard = reader.getCardNumber();
        }
        return Code.SUCCESS;
    }

    /**
     * The removeReader method removes a reader from the Library as long as they have been
     * registered and do not have any books checked out
     * @param reader the Reader to remove
     * @return Corresponding Code
     */
    public Code removeReader(Reader reader) {
        // Check that the registered reader doesn't have books checked out, if so
        // return error
        if(readers.contains(reader) && reader.getBookCount() > 0){
            System.out.println(reader.getName() + " must return all books!");
            return Code.READER_STILL_HAS_BOOKS_ERROR;
        }
        // If reader not registered, return error
        if(!readers.contains(reader)){
            System.out.println(reader.getName() + " is not part of this Library");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }
        // Remove reader and return success code
        readers.remove(reader);
        return Code.SUCCESS;
    }

    /**
     * The convertInt method converts a String to integer and returns corresponding error
     * depending on what String we are trying to parse
     * @param recordCountString the String to convert
     * @param code the corresponding code associated with string being converted
     * @return the int value
     */
    public int convertInt(String recordCountString, Code code) {
        int returnCount; // holder for converted integer
        // Try to convert String to integer
        // Depending on code passed, if conversion fails return corresponding error code and the
        // int value associated with it and a message
        try {
            returnCount = Integer.parseInt(recordCountString);
        } catch (NumberFormatException e) {
            if(code == Code.PAGE_COUNT_ERROR){
                System.out.println("Error: Could not parse page count");
                return Code.PAGE_COUNT_ERROR.getCode();
            } else if(code == Code.BOOK_COUNT_ERROR) {
                System.out.println("Error: Could not read number of books");
                return Code.BOOK_COUNT_ERROR.getCode();
            } else if(code == Code.DATE_CONVERSION_ERROR) {
                System.out.println("Error: Could not parse date component");
                return Code.DATE_CONVERSION_ERROR.getCode();
            } else {
                System.out.println("Error: Unknown conversion error");
                return Code.UNKNOWN_ERROR.getCode();
            }
        }
        return returnCount;
    }

    /**
     * The convertDate converts a String into a LocalDate
     * @param date the date to be converted
     * @param errorCode the code associated with date conversion
     * @return the LocalDate
     */
    public LocalDate convertDate(String date, Code errorCode) {
        // Declare array to hold year, month, and day values
        String[] dateArray;
        String defaultDate = "01-Jan-1970";     // Default date if needed
        //Create formatters to check against String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-uuuu");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");
        String year, month, day;    // Strings to hold values for array

        // If date sent is zeros, return the default date value
        if(date.equals("0000")){
            return LocalDate.parse(defaultDate, formatter);
        // Otherwise, split string by hyphen and check each value
        } else {
            try{
                dateArray = date.split("-");
                year = dateArray[0];
                month = dateArray[1];
                day = dateArray[2];
                // Check year, month, and date are greater than 0 or return default date
                if(convertInt(year, errorCode) < 0){
                    System.out.println("Error converting date: Year " + dateArray[0]);
                    System.out.println("Using default date (01-jan-1970)");
                    return LocalDate.parse(defaultDate, formatter);
                } else if (convertInt(month, errorCode) < 0) {
                    System.out.println("Error converting date: Month " + dateArray[1]);
                    System.out.println("Using default date (01-jan-1970)");
                    return LocalDate.parse(defaultDate, formatter);
                } else if (convertInt(day, errorCode) < 0) {
                    System.out.println("Error converting date: Day " + dateArray[2]);
                    System.out.println("Using default date (01-jan-1970)");
                    return LocalDate.parse(defaultDate, formatter);
                }
            // If problem with the array parsing, use default date
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("ERROR: date conversion error, could not parse " + date);
                System.out.println("Using default date (01-jan-1970)");
                return LocalDate.parse(defaultDate, formatter);
            }
        }
        // After checking values, convert to localDate using formatter and return
        return LocalDate.parse(year + month + day, formatter2);
    }

    /**
     * The getLibraryCard number returns Library card number
     * @return libraryCard
     */
    public int getLibraryCardNumber() {
        return libraryCard + 1;
    }

    /**
     * The errorCode method checks Codes in enum and if the number matches then
     * return that code
     * @param codeNumber the Code number to check for
     * @return the Code if number matches
     */
    private Code errorCode(int codeNumber) {
        //Iterate through Codes checking for codeNumber
        for(Code code: Code.values()) {
            // Return code if number matches
            if(code.getCode() == codeNumber){
                return code;
            }
        }
        // If no match, return error
        return Code.UNKNOWN_ERROR;
    }
}

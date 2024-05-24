package MyOwnCreatedPackage;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookServiceImpl implements BookServiceInterface {
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";

    private Scanner sc = new Scanner(System.in);
    private Validator validator = new Validator();
    private List<Book> books = new ArrayList<>();

    public BookServiceImpl() {
        // Initialize books from file if necessary
        this.books = loadBooksFromFile();
    }

    @Override
    public synchronized void addBook() {
        String bookId = validator.validateId();
        for (Book existingBook : books) {
            if (existingBook.getId().equals(bookId)) {
                System.out.println(RED + "Error: Book with ID " + bookId + " already exists. Please enter a unique ID." + RESET);
                return;
            }
        }
        String author = validator.validateAuthorTitle("Author");
        String title = validator.validateAuthorTitle("Title");
        String year = validator.validatePublishYear();

        // Get current date and time
        LocalDateTime borrowedDateTime = LocalDateTime.now();

        // Create a User object
        System.out.println("Enter User Name: ");
        String userName = sc.nextLine();
        User user = new User(userName);

        // Create a new Book instance
        Book book = new Book(bookId, title, author, year, "Available", user, borrowedDateTime);
        books.add(book);
        System.out.println(GREEN + "Book Added Successfully !!!" + RESET);
    }

    @Override
    public synchronized void showAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-30s %-30s %-15s %-15s %-15s\n", "ID", "TITLE", "AUTHOR", "PUBLISH YEAR", "STATUS", "USER");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");

        for (Book book : books) {
            System.out.printf("%-15s %-30s %-30s %-15s %-15s %-15s\n",
                    book.getId(), book.getTitle(), book.getAuthor(), book.getPublishYear(), book.getStatus(),
                    (book.getUser() == null ? "-" : book.getUser().getName())); // Display user name or '-' if null
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public synchronized void showAllAvailableBooks() {
        boolean flag = false;
        System.out.println("\n---------------------------------------------------------------------------------------------------------------------");
        System.out.format(CYAN + "%-15s%-30s%-30s%-20s%-15s", "ID", "TITLE", "AUTHOR", "PUBLISH YEAR", "STATUS" + RESET);
        System.out.println("\n---------------------------------------------------------------------------------------------------------------------");

        if (books.size() > 0) {
            for (Book book : books) {
                if ("Available".equals(book.getStatus())) {
                    System.out.format("%-15s%-30s%-30s%-20s%-15s", book.getId(), book.getTitle(), book.getAuthor(), book.getPublishYear(), book.getStatus());
                    System.out.println();
                    flag = true;
                }
            }
        } else {
            System.out.println(RED + "No Books Available in the library" + RESET);
        }
        System.out.println("\n----------------------------------------------------------------------------------------------------------------------");
        if (!flag)
            System.out.println(RED + "There are no books with status Available" + RESET);
    }

    @Override
    public synchronized void borrowBook() throws BookNotFoundException, BookUnavailableException {
        System.out.println("Enter the book ID: ");
        String bookId = sc.nextLine();
        boolean found = false;

        for (Book book : this.books) {
            if (book.getId().equals(bookId)) {
                found = true;
                if ("Available".equals(book.getStatus())) {
                    System.out.println("Enter User Name: ");
                    String userName = sc.nextLine();
                    User user = new User(userName);
                    book.setUser(user);
                    book.setStatus("Not Available");
                    book.setBorrowedDateTime(LocalDateTime.now()); // Set the borrowed date
                    System.out.println(GREEN + "Book Borrowed Successfully !!!" + RESET);
                    displayBorrowedBookDetails(book);
                    return;
                } else {
                    throw new BookUnavailableException("This book is currently unavailable");
                }
            }
        }

        if (!found) {
            throw new BookNotFoundException("This book is not available in the library");
        }
    }

    private void displayBorrowedBookDetails(Book book) {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-30s %-30s %-15s %-15s %-20s %-30s\n", "ID", "TITLE", "AUTHOR", "PUBLISH YEAR", "STATUS", "USER", "BORROWED DATE");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-30s %-30s %-15s %-15s %-20s %-30s\n",
                book.getId(), book.getTitle(), book.getAuthor(), book.getPublishYear(), book.getStatus(),
                (book.getUser() == null ? "-" : book.getUser().getName()),
                (book.getBorrowedDateTime() == null ? "-" :  book.getBorrowedDateTime().toString())); // Display borrowed date or '-' if null
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public synchronized void returnBook() {
        boolean found = false;
        System.out.println("Enter the book ID: ");
        String bookId = sc.nextLine();

        for (Book book : books) {
            if (book.getId().equals(bookId) && "Not Available".equals(book.getStatus())) {
                found = true;
                // Calculate fine
                double fine = calculateFine(book.getId(), book.getUser().getName());
                long daysPassed = calculateDaysPassed(book.getBorrowedDateTime());

                // Update book status
                book.setStatus("Available");
                book.setUser(null); // Clear the user when the book is returned
                book.setBorrowedDateTime(null); // Clear the borrowed date

                // Display success message and book details in a formatted table
                System.out.println(GREEN + "Book Returned Successfully !!!" + RESET);
                displayReturnedBookDetails(book, fine, daysPassed);

                break; // Exit the loop after finding and updating the book
            }
        }

        if (!found) {
            System.out.println(RED + "We cannot return this book. Either it is not borrowed or it does not exist." + RESET);
        }
    }

    private void displayReturnedBookDetails(Book book, double fine, long daysPassed) {
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-30s %-30s %-15s %-15s %-20s %-30s %-15s\n", "ID", "TITLE", "AUTHOR", "PUBLISH YEAR", "STATUS", "USER", "BORROWED DATE", "FINE");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-30s %-30s %-15s %-15s %-20s %-30s %-15s\n",
                book.getId(), book.getTitle(), book.getAuthor(), book.getPublishYear(), book.getStatus(),
                (book.getUser() == null ? "-" : book.getUser().getName()),
                (book.getBorrowedDateTime() == null ? "-" :  book.getBorrowedDateTime().toString()),
                (fine > 0 ? "$" + fine : "No fine after " + daysPassed + " day(s)"));
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private long calculateDaysPassed(LocalDateTime borrowedDateTime) {
        LocalDateTime now = LocalDateTime.now();
        return ChronoUnit.DAYS.between(borrowedDateTime, now);
    }

    @Override
    public double calculateFine(String bookId, String userName) {
        LocalDateTime now = LocalDateTime.now();
        double fine = 0.0;

        for (Book book : books) {
            if (book.getId().equals(bookId) && book.getUser().getName().equals(userName) && book.getBorrowedDateTime() != null) {
                long daysPassed = ChronoUnit.DAYS.between(book.getBorrowedDateTime(), now);
                if (daysPassed > 1) { // Fine starts after 1 day
                    fine = (daysPassed - 1) * 2; // $2 fine for each day after the first day
                }
                break;
            }
        }

        return fine;
    }

    @Override
    public void saveBooksToFile() {
        FileHandler fileHandler = new FileHandler();
        fileHandler.saveBooksToFile(books);
    }

    @Override
    public List<Book> loadBooksFromFile() {
        FileHandler fileHandler = new FileHandler();
        this.books = fileHandler.loadBooksFromFile();
        return this.books;
    }
    @Override
    public synchronized void clearAllBooks() {
        books.clear();
        saveBooksToFile(); // Ensure the cleared list is saved to the file
        System.out.println("All book data has been cleared.");
    }

    //@Override
    public void exit() {
        saveBooksToFile();
        System.out.println("Exiting...");
        System.exit(0);
    }


}

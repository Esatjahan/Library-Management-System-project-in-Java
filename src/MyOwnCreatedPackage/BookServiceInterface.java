package MyOwnCreatedPackage;

import java.util.List;

public interface BookServiceInterface {
    void addBook();
    void showAllBooks();
    void showAllAvailableBooks();
    void borrowBook() throws BookNotFoundException, BookUnavailableException;
    void returnBook();
    void saveBooksToFile();
    List<Book> loadBooksFromFile();
    void clearAllBooks();  // Updated method name
    double calculateFine(String bookId, String userName);
    // void exit();  // Note: This method is commented out in the original code
}

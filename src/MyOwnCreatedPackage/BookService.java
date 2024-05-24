package MyOwnCreatedPackage;

import java.util.List;

public class BookService extends BookServiceImpl {
    private List<Book> books;

    public void searchBookByTitle(String title) {
        boolean found = false;
        List<Book> books = getBooks(); // Access books via superclass method
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Book found:");
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Book with title '" + title + "' not found.");
        }
    }

    // Implement the getBooks() method to return the list of books
    public List<Book> getBooks() {
        return books;
    }
}

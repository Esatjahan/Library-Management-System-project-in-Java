import MyOwnCreatedPackage.BookServiceImpl;

// Add import statement for MyOwnCreatedPackage.Book
import MyOwnCreatedPackage.Book;

public class BookService extends BookServiceImpl {

    private Book[] books;

    public void searchBookByTitle(String title) {
        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println("MyOwnCreatedPackage.Book found:");
                System.out.println(book);
                found = true;
                break; // Once found, no naeed to continue searching
            }
        }
        if (!found) {
            System.out.println("MyOwnCreatedPackage.Book with title '" + title + "' not found.");
        }
    }
}

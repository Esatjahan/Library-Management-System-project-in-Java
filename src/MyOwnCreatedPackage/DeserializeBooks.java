package MyOwnCreatedPackage;

import MyOwnCreatedPackage.Book;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class DeserializeBooks {
    public static void main(String[] args) {
        List<Book> books = null;

        try (FileInputStream fileIn = new FileInputStream("books.txt");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            books = (ArrayList<Book>) in.readObject();
            System.out.println("Books have been deserialized from books.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (books != null) {
            for (Book book : books) {
                System.out.println(book);
            }
        } else {
            System.out.println("No books found.");
        }
    }
}

package MyOwnCreatedPackage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SerializeBooks {
    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            System.out.println("Enter book details:");
            System.out.print("ID: ");
            String id = scanner.nextLine();

            System.out.print("Title: ");
            String title = scanner.nextLine();

            System.out.print("Author: ");
            String author = scanner.nextLine();

            System.out.print("Publish Year: ");
            String publishYear = scanner.nextLine();

            System.out.print("Status: ");
            String status = scanner.nextLine();

            System.out.print("User Name: ");
            String userName = scanner.nextLine();
            User user = new User(userName);

            LocalDateTime borrowedDateTime = LocalDateTime.now();

            books.add(new Book(id, title, author, publishYear, status, user, borrowedDateTime));

            System.out.print("Do you want to add another book? (yes/no): ");
            choice = scanner.nextLine();
        } while (choice.equalsIgnoreCase("yes"));

        FileHandler fileHandler = new FileHandler();
        fileHandler.saveBooksToFile(books);
        System.out.println("Books have been serialized to books.json");
    }
}

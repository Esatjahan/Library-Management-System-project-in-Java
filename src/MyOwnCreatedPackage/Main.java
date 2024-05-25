package MyOwnCreatedPackage;

import java.io.*;
import java.util.Scanner;

public class Main {
    private static final String CREDENTIALS_FILE = "credentials.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookServiceInterface bookService = new BookServiceImpl();

        System.out.println("Books loaded from file successfully.");

        if (!checkCredentialsFile()) {
            initializeCredentials();
        }

        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        if (validateLogin(username, password)) {
            System.out.println("Login successful!");

            boolean continueRunning = true;
            while (continueRunning) {
                System.out.println("\nWelcome to Book Management Application");
                System.out.println("1. Add Book");
                System.out.println("2. Show All Books");
                System.out.println("3. Show Available Books");
                System.out.println("4. Borrow Book");
                System.out.println("5. Return Book");
                System.out.println("6. Save Books to File");
                System.out.println("7. Calculate Fine");
                System.out.println("8. Clear All Book Data");
                System.out.println("9. Change Credentials");
                System.out.println("10. Logout");

                System.out.print("\nEnter Your Choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // Consume the newline

                switch (choice) {
                    case 1:
                        bookService.addBook();
                        break;
                    case 2:
                        bookService.showAllBooks();
                        break;
                    case 3:
                        bookService.showAllAvailableBooks();
                        break;
                    case 4:
                        try {
                            bookService.borrowBook();
                        } catch (BookNotFoundException | BookUnavailableException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 5:
                        bookService.returnBook();
                        break;
                    case 6:
                        bookService.saveBooksToFile();
                        break;
                    case 7:
                        System.out.print("Enter the book ID: ");
                        String bookId = sc.nextLine();
                        System.out.print("Enter the User Name: ");
                        String userName = sc.nextLine();
                        double fine = bookService.calculateFine(bookId, userName);
                        System.out.println("Fine for the book: $" + fine);
                        break;
                    case 8:
                        bookService.clearAllBooks();
                        break;
                    case 9:
                        changeCredentials(sc);
                        break;
                    case 10:
                        continueRunning = false;
                        System.out.println("Logged out successfully!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }

                if (continueRunning) {
                    System.out.print("\nDo you want to continue? (1 for yes, 10 for no): ");
                    int continueChoice = sc.nextInt();
                    sc.nextLine(); // Consume the newline
                    if (continueChoice != 1) {
                        continueRunning = false;
                        System.out.println("Logged out successfully!");
                    }
                }
            }
        } else {
            System.out.println("Login failed. Please check your credentials.And try Again.");
        }

        sc.close();
    }

    private static boolean validateLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            String storedUsername = reader.readLine();
            String storedPassword = reader.readLine();
            return storedUsername.equals(username) && storedPassword.equals(password);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean checkCredentialsFile() {
        File file = new File(CREDENTIALS_FILE);
        return file.exists();
    }

    private static void initializeCredentials() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE))) {
            writer.write("admin\n");
            writer.write("password\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void changeCredentials(Scanner sc) {
        System.out.print("Enter new Username: ");
        String newUsername = sc.nextLine();
        System.out.print("Enter new Password: ");
        String newPassword = sc.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE))) {
            writer.write(newUsername + "\n");
            writer.write(newPassword + "\n");
            System.out.println("Credentials changed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

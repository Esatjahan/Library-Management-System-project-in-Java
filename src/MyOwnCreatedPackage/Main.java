package MyOwnCreatedPackage;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookServiceInterface bookService = new BookServiceImpl();

        System.out.println("Books loaded from file successfully.");
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        // Assume you have a method to validate the login credentials
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
                System.out.println("9. Logout");

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
                        // Assuming there's a method for calculating fine
                        System.out.print("Enter the book ID: ");
                        String bookId = sc.nextLine();
                        System.out.print("Enter the User Name: ");
                        String userName = sc.nextLine();
                        double fine = bookService.calculateFine(bookId, userName);
                        System.out.println("Fine for the book: $" + fine);
                        break;
                    case 8:
                        // Implement clearAllBooks method here
                        bookService.clearAllBooks();
                        break;
                    case 9:
                        continueRunning = false;
                        System.out.println("Logged out successfully!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }

                if (continueRunning) {
                    System.out.print("\nDo you want to continue? (1 for yes, 9 for no): ");
                    int continueChoice = sc.nextInt();
                    sc.nextLine(); // Consume the newline
                    if (continueChoice != 1) {
                        continueRunning = false;
                        System.out.println("Logged out successfully!");
                    }
                }
            }
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }

        sc.close();
    }

    private static boolean validateLogin(String username, String password) {
        // Replace this with actual login validation logic
        return "admin".equals(username) && "password".equals(password);
    }
}

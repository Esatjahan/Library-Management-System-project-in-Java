package MyOwnCreatedPackage;

import java.util.Scanner;

public class Validator {
    private Scanner scanner = new Scanner(System.in);

    public String validateId() {
        System.out.println("Enter Book ID: ");
        return scanner.nextLine();
    }

    public String validateAuthorTitle(String type) {
        System.out.println("Enter " + type + ": ");
        return scanner.nextLine();
    }

    public String validatePublishYear() {
        System.out.println("Enter Publish Year: ");
        return scanner.nextLine();
    }

    public String validatePassword() {
        System.out.println("Enter Password: ");
        return scanner.nextLine();
    }
}

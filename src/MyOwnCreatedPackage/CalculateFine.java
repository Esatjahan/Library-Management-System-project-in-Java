package MyOwnCreatedPackage;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculateFine {
    public static int calculateFine(User user, String bookTitle, List<Book> books) {
        for (Book book : books) {
            if (book.getUser() != null && book.getUser().getName().equals(user.getName()) && book.getTitle().equals(bookTitle)) {
                LocalDateTime borrowedDateTime = book.getBorrowedDateTime();
                LocalDateTime currentDateTime = LocalDateTime.now();
                long daysBetween = ChronoUnit.DAYS.between(borrowedDateTime, currentDateTime);

                if (daysBetween <= 1) {
                    return 0;
                } else {
                    long extraDays = daysBetween - 1;
                    return (int) extraDays * 2; // Assuming fine is $2 per extra day
                }
            }
        }
        return -1; // Book was not borrowed by the user
    }
}

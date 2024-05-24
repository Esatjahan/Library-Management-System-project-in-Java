package MyOwnCreatedPackage;

public class Borrowing {
    private Book book;
    private User user;
    private int daysLeft;

    public Borrowing(Book book, User user, int daysLeft) {
        this.book = book;
        this.user = user;
        this.daysLeft = daysLeft;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }
}


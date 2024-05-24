package MyOwnCreatedPackage;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    public Object getBorrowedDateTime;
    private String id;
    private String title;
    private String author;
    private String publishYear;
    private String status;
    private User user;
    private LocalDateTime borrowedDateTime;

    public Book(String id, String title, String author, String publishYear, String status, User user, LocalDateTime borrowedDateTime) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.status = status;
        this.user = user; // Set to the provided user
        this.borrowedDateTime = borrowedDateTime; // Set to the provided borrowedDateTime
    }



    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getBorrowedDateTime() {
        return borrowedDateTime;
    }

    public void setBorrowedDateTime(LocalDateTime borrowedDateTime) {
        this.borrowedDateTime = borrowedDateTime;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishYear='" + publishYear + '\'' +
                ", status='" + status + '\'' +
                ", user=" + (user != null ? user.getName() : "-") +
                ", borrowedDateTime=" + borrowedDateTime +
                '}';
    }
}

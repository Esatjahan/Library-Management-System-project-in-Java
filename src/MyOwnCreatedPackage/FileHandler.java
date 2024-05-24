package MyOwnCreatedPackage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String FILENAME = "books.json"; // Change the file extension to .json
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public void saveBooksToFile(List<Book> books) {
        try (Writer writer = new FileWriter(FILENAME)) {
            gson.toJson(books, writer);
            System.out.println("Books saved to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Book> loadBooksFromFile() {
        try (Reader reader = new FileReader(FILENAME)) {
            Book[] bookArray = gson.fromJson(reader, Book[].class);
            List<Book> books = new ArrayList<>(List.of(bookArray));
            System.out.println("Books loaded from file successfully.");
            return books;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException {
            if (value == null) {
                out.nullValue(); // Serialize null if the LocalDateTime is null
            } else {
                out.value(value.toString()); // Serialize LocalDateTime to String
            }
        }

        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            String value = in.nextString();
            if (value == null || value.isEmpty()) {
                return null; // Deserialize null or empty string as null LocalDateTime
            } else {
                return LocalDateTime.parse(value); // Deserialize LocalDateTime from String
            }
        }
    }

    public static void main(String[] args) {
        // Test your code here
    }
}

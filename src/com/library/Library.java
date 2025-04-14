package com.library;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Library {

    private ArrayList<Book> books;
    private static final String FILE_NAME = "books.txt";

    public Library() {
        books = new ArrayList<>();
        loadBooks();
    }

    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        saveBooks();
    }

    public void removeBook(int index) {
        if (index >= 0 && index < books.size()) {
            books.remove(index);
            saveBooks();
        }
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void clearAllBooks() {
        books.clear();
        saveBooks();
    }

    public void sortByTitle() {
        books.sort(Comparator.comparing(Book::getTitle));
        saveBooks();
    }

    public void sortByAuthor() {
        books.sort(Comparator.comparing(Book::getAuthor));
        saveBooks();
    }

    public void exportToCSV(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Book book : books) {
                writer.write(book.getTitle() + "," + book.getAuthor());
                writer.newLine();
            }
            System.out.println("Export successful!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importFromCSV(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    books.add(new Book(data[0].trim(), data[1].trim()));
                }
            }
            saveBooks();
            System.out.println("Import successful!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBooks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Book book : books) {
                writer.write(book.getTitle() + "," + book.getAuthor());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBooks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] bookData = line.split(",");
                if (bookData.length == 2) {
                    books.add(new Book(bookData[0], bookData[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("No previous data found. Starting with an empty library.");
        }
    }
}

// Book class
class Book {
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}

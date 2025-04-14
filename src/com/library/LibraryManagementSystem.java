package com.library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Comparator;

public class LibraryManagementSystem extends JFrame {

    private JTextField txtTitle, txtAuthor, txtSearch;
    private JButton btnAdd, btnRemove, btnSearch, btnSortTitle, btnSortAuthor, btnClear, btnExport, btnImport;
    private JTable bookTable;
    private DefaultTableModel model;
    private Library library;

    public LibraryManagementSystem() {
        library = new Library();

        setTitle("Library Management System");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout());

        txtTitle = new JTextField(10);
        txtAuthor = new JTextField(10);
        txtSearch = new JTextField(10);
        btnAdd = new JButton("Add Book");
        btnRemove = new JButton("Remove Book");
        btnSearch = new JButton("Search");
        btnSortTitle = new JButton("Sort by Title");
        btnSortAuthor = new JButton("Sort by Author");
        btnClear = new JButton("Clear All");
        btnExport = new JButton("Export CSV");
        btnImport = new JButton("Import CSV");

        panel.add(new JLabel("Title:"));
        panel.add(txtTitle);
        panel.add(new JLabel("Author:"));
        panel.add(txtAuthor);
        panel.add(btnAdd);
        panel.add(btnRemove);
        panel.add(new JLabel("Search:"));
        panel.add(txtSearch);
        panel.add(btnSearch);
        panel.add(btnSortTitle);
        panel.add(btnSortAuthor);
        panel.add(btnClear);
        panel.add(btnExport);
        panel.add(btnImport);

        model = new DefaultTableModel();
        model.addColumn("Title");
        model.addColumn("Author");

        bookTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(bookTable);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {
            String title = txtTitle.getText();
            String author = txtAuthor.getText();
            if (!title.isEmpty() && !author.isEmpty()) {
                library.addBook(title, author);
                updateTable(library.getBooks());
                txtTitle.setText("");
                txtAuthor.setText("");
            }
        });

        btnRemove.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow != -1) {
                library.removeBook(selectedRow);
                updateTable(library.getBooks());
            }
        });

        btnSearch.addActionListener(e -> {
            String searchQuery = txtSearch.getText().toLowerCase();
            ArrayList<Book> searchResults = new ArrayList<>();
            for (Book book : library.getBooks()) {
                if (book.getTitle().toLowerCase().contains(searchQuery) ||
                    book.getAuthor().toLowerCase().contains(searchQuery)) {
                    searchResults.add(book);
                }
            }
            updateTable(searchResults);
        });

        btnSortTitle.addActionListener(e -> {
            library.sortByTitle();
            updateTable(library.getBooks());
        });

        btnSortAuthor.addActionListener(e -> {
            library.sortByAuthor();
            updateTable(library.getBooks());
        });

        btnClear.addActionListener(e -> {
            library.clearAllBooks();
            updateTable(library.getBooks());
        });

        btnExport.addActionListener(e -> {
            String filename = JOptionPane.showInputDialog("Enter export filename (e.g., export.csv):");
            if (filename != null && !filename.isEmpty()) {
                library.exportToCSV(filename);
            }
        });

        btnImport.addActionListener(e -> {
            String filename = JOptionPane.showInputDialog("Enter import filename (e.g., import.csv):");
            if (filename != null && !filename.isEmpty()) {
                library.importFromCSV(filename);
                updateTable(library.getBooks());
            }
        });
    }

    private void updateTable(ArrayList<Book> books) {
        model.setRowCount(0);
        for (Book book : books) {
            model.addRow(new Object[]{book.getTitle(), book.getAuthor()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryManagementSystem().setVisible(true));
    }
}

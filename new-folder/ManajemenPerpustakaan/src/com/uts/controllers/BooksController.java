package com.uts.controllers;

import java.util.Optional;

import com.uts.model.Book;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class BooksController {
    
    @FXML private TextField bookIdField;
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField yearField;
    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;
    
    @FXML private TableView<Book> booksTable;
    @FXML private TableColumn<Book, String> idColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> yearColumn;
    
    private ObservableList<Book> booksList = FXCollections.observableArrayList();
    private boolean isEditMode = false;
    private Book bookBeingEdited;

    public ObservableList<Book> getBooksList() {
        return booksList;
    }
    
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("yearPublished"));
        
        booksTable.setItems(booksList);
        
        booksTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            editButton.setDisable(newSelection == null);
            deleteButton.setDisable(newSelection == null);
        });
        
        editButton.setDisable(true);
        deleteButton.setDisable(true);
    }
    
    @FXML
    private void handleAddBook() {
        if (isEditMode) {
            updateBook();
        } else {
            addNewBook();
        }
    }
    
    private void addNewBook() {
        String id = bookIdField.getText().trim();
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String year = yearField.getText().trim();

        if (id.isEmpty() || title.isEmpty()) {
            handleMandatoryField();
            return;
        }
        
        // Check for duplicate ID
        boolean idExists = booksList.stream().anyMatch(book -> book.getId().equals(id));
        if (idExists) {
            handleIdExists();
            return;
        }
        
        // Add book to list
        Book newBook = new Book(id, title, author, year);
        booksList.add(newBook);
        
        clearFields();
    }

    private void handleMandatoryField() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText("Book ID and Title are mandatory fields.");
        alert.showAndWait();
    }

    private void handleIdExists() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText("Book ID already exists.");
        alert.showAndWait();
    }

    @FXML
    private void handleEditBook() {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            isEditMode = true;
            bookBeingEdited = selectedBook;
            
            bookIdField.setText(selectedBook.getId());
            titleField.setText(selectedBook.getTitle());
            authorField.setText(selectedBook.getAuthor());
            yearField.setText(selectedBook.getYearPublished());
            
            bookIdField.setDisable(true);
            deleteButton.setDisable(true);
            
            addButton.setText("Update Book");
        }
    }

    private void updateBook() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String year = yearField.getText().trim();

        
        if (title.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText("Book title is required");
            alert.showAndWait();
            return;
        }

        
        bookBeingEdited.setTitle(title);
        bookBeingEdited.setAuthor(author);
        bookBeingEdited.setYearPublished(year);
        
        booksTable.refresh();
        
        exitEditMode();
    }
    
    @FXML
    private void handleDeleteBook() {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            Alert confirmDialog = new Alert(AlertType.CONFIRMATION);
            confirmDialog.setTitle("Delete Confirmation");
            confirmDialog.setHeaderText(null);
            confirmDialog.setContentText("Are you sure you want to delete this book?");
            
            Optional<ButtonType> result = confirmDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                booksList.remove(selectedBook);
            }
        }
    }
    
    private void clearFields() {
        bookIdField.clear();
        titleField.clear();
        authorField.clear();
        yearField.clear();
    }
    
    private void exitEditMode() {
        isEditMode = false;
        bookBeingEdited = null;
        bookIdField.setDisable(false);
        addButton.setText("Add Book");

        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        deleteButton.setDisable(selectedBook == null);

        clearFields();
    }
}
package com.uts.controllers;

import com.uts.model.Book;
import com.uts.model.Member;
import com.uts.model.TransactionRecord;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

public class TransactionsController {

    @FXML
    private ComboBox<Member> memberComboBox;
    @FXML
    private ComboBox<Book> bookComboBox;
    @FXML
    private Button borrowButton;

    @FXML
    private TableView<TransactionRecord> transactionsTable;
    @FXML
    private TableColumn<TransactionRecord, String> memberColumn;
    @FXML
    private TableColumn<TransactionRecord, String> bookColumn;
    @FXML
    private TableColumn<TransactionRecord, String> dateColumn;

    private ObservableList<TransactionRecord> transactionsList = FXCollections.observableArrayList();
    
    private MembersController membersController;
    private BooksController booksController;

    public void setMembersController(MembersController controller) {
        this.membersController = controller;
        if (memberComboBox != null) {
            updateMemberComboBox();
        }
    }
    
    public void setBooksController(BooksController controller) {
        this.booksController = controller;
        if (bookComboBox != null) {
            updateBookComboBox();
        }
    }
    
    private void updateMemberComboBox() {
        if (membersController != null) {
            memberComboBox.setItems(membersController.getMembersList());
        }
    }
    
    private void updateBookComboBox() {
        if (booksController != null) {
            // Get the books list from the BooksController
            bookComboBox.setItems(booksController.getBooksList());
        }
    }

    @FXML
    public void initialize() {
        // Setup member combo box
        memberComboBox.setCellFactory(param -> new ListCell<Member>() {
            @Override
            protected void updateItem(Member item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName() + " (" + item.getId() + ")");
            }
        });
        memberComboBox.setConverter(new StringConverter<Member>() {
            @Override
            public String toString(Member member) {
                return member == null ? "" : member.getName() + " (" + member.getId() + ")";
            }

            @Override
            public Member fromString(String string) {
                return null; // Not needed for this use case
            }
        });

        // Setup book combo box
        bookComboBox.setCellFactory(param -> new ListCell<Book>() {
            @Override
            protected void updateItem(Book item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getTitle() + " (" + item.getId() + ")");
            }
        });
        bookComboBox.setConverter(new StringConverter<Book>() {
            @Override
            public String toString(Book book) {
                return book == null ? "" : book.getTitle() + " (" + book.getId() + ")";
            }

            @Override
            public Book fromString(String string) {
                return null; 
            }
        });

        memberColumn.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        bookColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDateFormatted"));

        transactionsTable.setItems(transactionsList);
        
        updateMemberComboBox();
        updateBookComboBox();
    }

    @FXML
    private void handleBorrow() {
        Member selectedMember = memberComboBox.getValue();
        Book selectedBook = bookComboBox.getValue();

        if (selectedMember == null || selectedBook == null) {
            showValidationError("Selection Error", "Please select both a member and a book");
            return;
        }

        // Check if member has already borrowed this book
        boolean alreadyBorrowed = transactionsList.stream()
                .anyMatch(t -> t.getMember().getId().equals(selectedMember.getId())
                        && t.getBook().getId().equals(selectedBook.getId()));

        if (alreadyBorrowed) {
            showValidationError("Validation Error", "This member has already borrowed this book");
            return;
        }

        // Check if member has reached the limit of 3 borrowed books
        long borrowedCount = transactionsList.stream()
                .filter(t -> t.getMember().getId().equals(selectedMember.getId()))
                .count();

        if (borrowedCount >= 3) {
            showValidationError("Validation Error", "Member has reached the maximum limit of 3 books");
            return;
        }

        TransactionRecord transaction = new TransactionRecord(selectedMember, selectedBook);
        transactionsList.add(transaction);

        memberComboBox.setValue(null);
        bookComboBox.setValue(null);
    }

    @FXML
    private void handleReturn() {
        TransactionRecord selectedTransaction = transactionsTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            transactionsList.remove(selectedTransaction);
        } else {
            showValidationError("Selection Error", "Please select a transaction to return");
        }
    }


    
    private void showValidationError(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // Method to refresh comboboxes when tab is selected
    public void refreshData() {
        updateMemberComboBox();
        updateBookComboBox();
    }
}
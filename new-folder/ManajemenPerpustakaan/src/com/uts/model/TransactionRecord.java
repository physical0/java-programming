package com.uts.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransactionRecord {
    private Member member;
    private Book book;
    private LocalDate borrowDate;
    
    public TransactionRecord(Member member, Book book) {
        this.member = member;
        this.book = book;
        this.borrowDate = LocalDate.now();
    }
    public Member getMember() {
        return member;
    }
    public Book getBook() {
        return book;
    }
    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    public String getBorrowDateFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return borrowDate.format(formatter);
    }
    public String getMemberName() {
        return member.getName();
    }
    public String getBookTitle() {
        return book.getTitle();
    }
}
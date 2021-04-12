package com.example.unicodelibraryapp;

import android.net.Uri;

public class Book
{
    private String name,author,subject,isbn,summary;
    private boolean isavailable,waiting;
    private String bookImageUrl;
    Book(String name,String author,String subject,String isbn,String summary,boolean isavailable,boolean waiting,String bookImageUrl){
        this.name = name;
        this.author= author;
        this.subject = subject;
        this.isbn = isbn;
        this.summary = summary;
        this.isavailable = isavailable;
        this.waiting = waiting;
        this.bookImageUrl = bookImageUrl;
    }
    Book(String name,String author,String subject){
        this.name = name;
        this.author= author;
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isIsavailable() {
        return isavailable;
    }

    public void setIsavailable(boolean isavailable) {
        this.isavailable = isavailable;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String newUrl) {
        this.bookImageUrl = newUrl;
    }
}
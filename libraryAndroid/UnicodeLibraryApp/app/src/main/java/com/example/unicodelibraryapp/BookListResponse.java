package com.example.unicodelibraryapp;

public class BookListResponse
{
    private Book books[];

    BookListResponse(Book list[])
    {
        books = list;
    }

    public Book[] getBooks()
    {
        return books;
    }
}

package com.example.unicodelibraryapp;

public class ListData {
    private String name;
    private String bookId;


    public ListData(String name, String quantity){
        this.name = name;
        this.bookId = quantity;
    }



    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setBookId(String bookId){
        this.bookId = bookId;
    }

    public String getBookId(){
        return bookId;
    }


}


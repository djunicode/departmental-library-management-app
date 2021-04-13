package com.example.unicodelibraryapp;

public class User
{
    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsersap() {
        return usersap;
    }

    public void setUsersap(String usersap) {
        this.usersap = usersap;
    }

    public String getFine_amount() {
        return fine_amount;
    }

    public void setFine_amount(String fine_amount) {
        this.fine_amount = fine_amount;
    }

    public String getOverdue() {
        return overdue;
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }

    public String getBook_image() {
        return book_image;
    }

    public void setBook_image(String book_image) {
        this.book_image = book_image;
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

    //Role enum
    public enum Role {
        Student
        {
            public String toString()
            {
                return "Student";
            }
        },
        Teacher
        {
            public String toString()
            {
                return "Teacher";
            }
        },
        Librarian
        {
            public String toString()
            {
                return "Librarian";
            }
        },
        Default
        {
            public String toString()
            {
                return "Default";
            }
        }};

    private String token;
    private Role role;

    User(String token, Role role)
    {
        this.token = token;
        this.role = role;
    }
    private String bookname,bookid,username,usersap,fine_amount,overdue,book_image,author,subject;
    User(String token, Role role,String bookname,String bookid,String username,String usersap,String fine_amount,String overdue,String book_image)

    {
        this.token = token;
        this.role = role;
        this.bookname = bookname;
        this.bookid = bookid;
        this.username = username;
        this.usersap = usersap;
        this.fine_amount= fine_amount;
        this.overdue = overdue;
        this.book_image= book_image;
    }
    User(String bookname,String bookid,String username,String usersap,String fine_amount,String overdue,String book_image)
    {

        this.bookname = bookname;
        this.bookid = bookid;
        this.username = username;
        this.usersap = usersap;
        this.fine_amount= fine_amount;
        this.overdue = overdue;
        this.book_image= book_image;
    }

    User(String token, Role role,String bookname,String author,String subject)
    {
        this.token = token;
        this.role = role;
        this.bookname = bookname;
        this.author = author;
        this.subject = subject;
    }



    User(String token, String role)
    {
        this.token = token;

        //Setting the role
        for(Role enumRole : Role.values())
        {
            if(role.equals(enumRole.toString()))
            {
                this.role = enumRole;
                break;
            }
        }
    }

    User()
    {
        this.role = null;
        this.token = null;
    }

    public String getToken()
    {
        return this.token;
    }

    public Role getRole()
    {
        return this.role;
    }

}

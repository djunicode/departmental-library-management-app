package com.example.unicodelibraryapp;

public class User
{
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

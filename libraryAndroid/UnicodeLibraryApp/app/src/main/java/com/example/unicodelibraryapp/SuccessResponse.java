package com.example.unicodelibraryapp;

public class SuccessResponse
{
    private boolean success;

    SuccessResponse(boolean success)
    {
        this.success = success;
    }

    public boolean getSuccess()
    {
        return this.success;
    }

}

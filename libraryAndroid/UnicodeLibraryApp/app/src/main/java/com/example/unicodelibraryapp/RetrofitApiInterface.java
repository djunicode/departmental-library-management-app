package com.example.unicodelibraryapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitApiInterface
{
    @FormUrlEncoded
    @POST("login")
    Call<AuthResponse> loginUser(@Field("username") String username, @Field("password") String password);
}

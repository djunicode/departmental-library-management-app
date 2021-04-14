package com.example.unicodelibraryapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitApiInterface
{
    @FormUrlEncoded
    @POST("login")
    Call<AuthResponse> loginUser(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("addbook")
    Call<Void> addNewBook(@Header("token") String librarianToken, @Field("isbn") String isbnNum, @Field("name") String bookName, @Field("publisher") String publisherName,
                          @Field("author") String authorName, @Field("publish_year") String publishYear, @Field("subject") String subject, @Field("dscr") String description);

    @FormUrlEncoded
    @POST("ascsort")
    Call<BookListResponse> getAscendingBooks(@Header("token") String token, @Field("page") int pageId);

    @FormUrlEncoded
    @POST("dscsort")
    Call<BookListResponse> getDescendingBooks(@Header("token") String token, @Field("page") int pageId);

    @FormUrlEncoded
    @POST("searchoption")
    Call<BookListResponse> searchForBook(@Header("token") String token, @Field("title") String bookTitle);

    @FormUrlEncoded
    @POST("sendbarcode")
    Call<SuccessResponse> addBookByBarcode(@Header("token") String token, @Field("barcode") String barcode);

    @GET("exists")
    Call<SuccessResponse> isbnAlreadyExists(@Header("token") String token, @Query("isbn") String isbn);

    @GET("copies")
    Call<SuccessResponse> addBookCopies(@Header("token") String token, @Query("isbn") String isbn, @Query("quantity") int newCopies);
}

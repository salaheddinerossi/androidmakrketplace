package com.example.marketplace;

import com.example.marketplace.dto.LoginDto;
import com.example.marketplace.dto.ProductDto;
import com.example.marketplace.dto.UserDto;
import com.example.marketplace.response.JwtResponse;
import com.example.marketplace.response.ProductResponse;
import com.example.marketplace.response.ProductUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("login")
    Call<JwtResponse> loginUser(@Body LoginDto loginDto);
    @POST("/register")
    Call<Void> registerUser(@Body UserDto userDto);

    @POST("/product/")
    Call<Void> createProduct(@Header("Authorization") String authToken, @Body ProductDto productDto);

    @GET("/product/")
    Call<List<ProductResponse>> getAllProducts();

    @GET("/product/seller")
    Call<List<ProductResponse>> getProductsBySeller(@Header("Authorization") String authToken);

    @GET("/product/{id}")
    Call<ProductUser> getProduct(@Path("id") Long id);

    @DELETE("/product/{id}")
    Call<Void> deleteProduct(@Path("id") Long id, @Header("Authorization") String authToken);


}

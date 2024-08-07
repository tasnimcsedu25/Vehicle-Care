package com.example.vehiclecare;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface APIService {
    @GET("users")
    Call<List<User>> getUsers();

    @GET("users/{UserID}")
    Call<User> getUserById(@Path("UserID") String userID);

    @POST("users")
    Call<Void> createUser(@Body User user);

    @PUT("users/{UserID}")
    Call<Void> updateUser(@Path("UserID") String userID, @Body User user);

    @DELETE("users/{UserID}")
    Call<Void> deleteUser(@Path("UserID") String userID);

    // Add these methods to APIService.java
    @POST("vehicles")
    Call<Void> createVehicle(@Body Vehicle vehicle);

    @GET("vehicles")
    Call<List<Vehicle>> getVehicles();

}

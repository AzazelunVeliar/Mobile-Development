package ru.mirea.khudyakovma.mireaproject.ui.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenMeteoApi {
    @GET("v1/forecast?current_weather=true")
    Call<WeatherResponse> getCurrentWeather(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("timezone") String timezone
    );
}

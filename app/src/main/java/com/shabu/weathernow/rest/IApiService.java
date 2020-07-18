package com.shabu.weathernow.rest;



import com.shabu.weathernow.models.WeatherCard;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface IApiService {


  @POST("current")
  Observable<WeatherCard> get_current_weather(
          @Query("access_key") String key,
          @Query("query") String city
  );
}

package com.shabu.weathernow.rest;


import com.shabu.weathernow.models.WeatherCard;

import java.util.List;

import io.reactivex.Observable;

public class ApiService implements IApiService {

  @Override
  public Observable<WeatherCard> get_current_weather(String key, String city) {
    return ApiClient
        .getRestClient()
        .get_current_weather(key, city);
  }
}

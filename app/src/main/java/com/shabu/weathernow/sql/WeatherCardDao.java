package com.shabu.weathernow.sql;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.shabu.weathernow.models.WeatherCard;

import java.util.List;

@Dao
public interface WeatherCardDao {
    @Query("SELECT * FROM WeatherCard")
    List<WeatherCard> getAllCards();

    @Query("SELECT name FROM WeatherCard")
    List<String> getAllName();

    @Insert
    void insert(WeatherCard card);

    @Update
    void update(WeatherCard card);

    @Delete
    void delete(WeatherCard card);
}

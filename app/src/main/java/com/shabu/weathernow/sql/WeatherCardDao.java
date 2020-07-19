package com.shabu.weathernow.sql;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.shabu.weathernow.models.WeatherCard;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface WeatherCardDao {
    @Query("SELECT * FROM WeatherCard")
    List<WeatherCard> getAllCards();

    //@Query("SELECT * FROM WeatherCard WHERE id = :id")
    //WeatherCard getById(long id);

    @Query("SELECT name FROM WeatherCard")
    Flowable<List<String>> getAllName();

    @Insert
    void insert(WeatherCard card);

    @Update
    void update(WeatherCard card);

    @Delete
    void delete(WeatherCard card);
}

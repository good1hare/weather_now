package com.shabu.weathernow.sql;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.shabu.weathernow.models.WeatherCard;

@Dao
public interface WeatherCardDao {
   // @Query("SELECT * FROM card")
   // List<> getAll();

    //@Query("SELECT * FROM card WHERE id = :id")
   // WeatherCard getById(long id);

    @Insert
    void insert(WeatherCard card);

    @Update
    void update(WeatherCard card);

    @Delete
    void delete(WeatherCard card);
}

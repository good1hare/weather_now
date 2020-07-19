package com.shabu.weathernow.sql;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.shabu.weathernow.models.WeatherCard;

@Database(entities = {WeatherCard.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WeatherCardDao getWeatherCardDao();
}

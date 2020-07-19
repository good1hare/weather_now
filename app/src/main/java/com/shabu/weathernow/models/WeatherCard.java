package com.shabu.weathernow.models;


import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WeatherCard
{

  @PrimaryKey(autoGenerate = true)
  public long id;

  @Embedded
  private Request request;

  @Embedded
  private Current current;

  @Embedded
  private Location location;

  public long getId ()
  {
    return id;
  }

  public void setId (long id)
  {
    this.id = id;
  }

  public Request getRequest ()
  {
    return request;
  }

  public void setRequest (Request request)
  {
    this.request = request;
  }

  public Current getCurrent ()
  {
    return current;
  }

  public void setCurrent (Current current)
  {
    this.current = current;
  }

  public Location getLocation ()
  {
    return location;
  }

  public void setLocation (Location location)
  {
    this.location = location;
  }

}

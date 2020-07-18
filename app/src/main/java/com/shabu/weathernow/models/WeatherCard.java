package com.shabu.weathernow.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherCard
{

  private Request request;

  private Current current;

  private Location location;

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

  @Override
  public String toString()
  {
    return "ClassPojo [request = "+request+", current = "+current+", location = "+location+"]";
  }
}

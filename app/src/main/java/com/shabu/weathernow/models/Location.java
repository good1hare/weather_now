package com.shabu.weathernow.models;

public class Location
{
    private String localtime;

    private String utc_offset;

    private String country;

    private String localtime_epoch;

    private String name;

    private String timezone_id;

    private String lon;

    private String region;

    private String lat;

    public String getLocaltime ()
    {
        return localtime;
    }

    public void setLocaltime (String localtime)
    {
        this.localtime = localtime;
    }

    public String getUtc_offset ()
    {
        return utc_offset;
    }

    public void setUtc_offset (String utc_offset)
    {
        this.utc_offset = utc_offset;
    }

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    public String getLocaltime_epoch ()
    {
        return localtime_epoch;
    }

    public void setLocaltime_epoch (String localtime_epoch)
    {
        this.localtime_epoch = localtime_epoch;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getTimezone_id ()
    {
        return timezone_id;
    }

    public void setTimezone_id (String timezone_id)
    {
        this.timezone_id = timezone_id;
    }

    public String getLon ()
    {
        return lon;
    }

    public void setLon (String lon)
    {
        this.lon = lon;
    }

    public String getRegion ()
    {
        return region;
    }

    public void setRegion (String region)
    {
        this.region = region;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }
}

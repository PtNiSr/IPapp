package com.ni___ckel.ipapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ip_information")
public class IPaddress {

    @PrimaryKey (autoGenerate = true)
    private int id;
    private String IP;
    private String city;
    private String region;
    private String country;
    private String org;
    private String postal;
    private String timezone;

    public IPaddress(int id, String IP, String city, String region, String country, String org, String postal, String timezone) {
        this.id = id;
        this.IP = IP;
        this.city = city;
        this.region = region;
        this.country = country;
        this.org = org;
        this.postal = postal;
        this.timezone = timezone;
    }

    public int getId() {
        return id;
    }

    public String getIP() {
        return IP;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getOrg() {
        return org;
    }

    public String getPostal() {
        return postal;
    }

    public String getTimezone() {
        return timezone;
    }

}

package com.alphabgammainc.nestfinder.Classes;

import android.app.Activity;
import android.location.Location;
import android.widget.ImageView;

import com.alphabgammainc.nestfinder.MapsActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-04-25.
 */
public class Locations implements Serializable{
    private Double lon;
    private Double lat;
    private String adTitle;
    private int bedRooms;
    private int bathRooms;
    private boolean isFurnished;
    private boolean pets;
    private ArrayList<String> rentImage;
    private Double price;
    private String address;
    private Address mAddress;
    private String description;

    public Locations() {

    }

    public Locations(Double lon, Double lat,String adTitle,Address mAddress,ArrayList<String> images,
                     int bedRooms,int bathRooms, boolean isFurnished,boolean pets,Double price, String description){
        this.lon = lon;
        this.lat = lat;
        this.adTitle = adTitle;
        this.mAddress=mAddress;
        this.rentImage =images;
        this.bedRooms=bedRooms;
        this.bathRooms =bathRooms;
        this.isFurnished =isFurnished;
        this.pets =pets;
        this.price = price;
        this.description = description;

        this.address =address;  //for the time being

    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public ArrayList<String> getRentImage() {
        return rentImage;
    }

    public void pushBackImage(String key){
        if(rentImage == null)rentImage  = new ArrayList<>();
        rentImage.add(key);
    }

    public void setRentImage(ArrayList<String> rentImage) {
        this.rentImage = rentImage;
    }

    public Address getmAddress() {
        return mAddress;
    }

    public void setmAddress(Address mAddress) {
        this.mAddress = mAddress;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAdTitle() { return adTitle; }

    public void setAdTitle(String adTitle) { this.adTitle = adTitle; }

    public int getBedRooms() {
        return bedRooms;
    }

    public void setBedRooms(int bedRooms) {
        this.bedRooms = bedRooms;
    }

    public int getBathRooms() {
        return bathRooms;
    }

    public void setBathRooms(int bathRooms) {
        this.bathRooms = bathRooms;
    }

    public boolean isFurnished() {
        return isFurnished;
    }

    public void setFurnished(boolean furnished) {
        isFurnished = furnished;
    }

    public boolean isPets() {
        return pets;
    }

    public void setPets(boolean pets) {
        this.pets = pets;
    }

    // the locations

    // for the time being
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

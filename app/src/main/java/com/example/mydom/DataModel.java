package com.example.mydom;

public class DataModel {
    String name;
    String information;
    String city;
    int image;

    public DataModel() {
    }

    public DataModel(String name_, String information_, String city_, int image_) {
        this.name = name_;
        this.information = information_;
        this.city = city_;
        this.image = image_;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return information;
    }

    public int getImage() {
        return image;
    }

    public String getCity() {
        return city;
    }
}

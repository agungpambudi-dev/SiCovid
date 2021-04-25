package com.example.sicovid.Model;

public class Hospital {

    String name;
    String area;
    String address;
    String phone;
    double lat;
    double lon;

    public Hospital(String name, String area, String address, String phone, double lat, double lon) {
        this.name = name;
        this.area = area;
        this.address = address;
        this.phone = phone;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}

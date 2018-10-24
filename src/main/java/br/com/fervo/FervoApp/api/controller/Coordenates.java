package br.com.fervo.FervoApp.api.controller;

public class Coordenates {

    private String latitude;
    private String longitude;
    private String city;
    private String state;
    private String country;

    public Coordenates(String latitude, String longitude, String city, String state, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

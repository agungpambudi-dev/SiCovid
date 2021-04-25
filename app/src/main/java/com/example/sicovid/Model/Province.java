package com.example.sicovid.Model;

public class Province {

    private String id;
    private String name;
    private String positive;
    private String recovered;
    private String deaths;

    public Province(String id, String name, String positive, String recovered, String deaths) {
        this.id = id;
        this.name = name;
        this.positive = positive;
        this.recovered = recovered;
        this.deaths = deaths;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPositive() {
        return positive;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }
}

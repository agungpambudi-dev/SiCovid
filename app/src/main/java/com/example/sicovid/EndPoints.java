package com.example.sicovid;

public class EndPoints {

    public static String url_hospital = "https://services5.arcgis.com/VS6HdKS0VfIhv8Ct/arcgis/rest/services/RS_Rujukan_Update_May_2020/FeatureServer/0/query?where=1%3D1&outFields=*&outSR=4326&f=json";
    public static String url_province = "https://services5.arcgis.com/VS6HdKS0VfIhv8Ct/arcgis/rest/services/COVID19_Indonesia_per_Provinsi/FeatureServer/0/query?where=1%3D1&outFields=*&outSR=4326&f=json";

    public static final String URL_DATA_INDONESIA = "https://covid-api.com/api/reports?iso=IDN";
    public static final String URL_DATA_GLOBAL = "https://covid-api.com/api/reports/total?date=";
    public static final String URL_DATA_PROVINCE = url_province;
    public static final String URL_DATA_HOSPITAL = url_hospital;
}

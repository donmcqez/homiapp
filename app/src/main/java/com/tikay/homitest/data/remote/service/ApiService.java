package com.tikay.homitest.data.remote.service;

public class ApiService {
    private static final String BASE_URL_API = "https://homitv.traytontech.com/";

    public static HomiRemoteService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(HomiRemoteService.class);
    }
}

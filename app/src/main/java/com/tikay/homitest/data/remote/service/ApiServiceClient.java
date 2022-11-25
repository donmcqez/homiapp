package com.tikay.homitest.data.remote.service;

public class ApiServiceClient {
    private static final String BASE_URL_API = "https://homitv.traytontech.com/";

    public static ApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(ApiService.class);
    }
}

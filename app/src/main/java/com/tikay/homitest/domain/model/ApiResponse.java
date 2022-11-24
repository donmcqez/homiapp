package com.tikay.homitest.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Series> seriesData;


    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Series> getMediaDataList() {
        return seriesData;
    }


	@Override
 	public String toString(){
		return
			"Response{" +
			"status = '" + status + '\'' +
			",message = '" + message + '\'' +
			",data = '" + seriesData + '\'' +
			"}";
		}
}
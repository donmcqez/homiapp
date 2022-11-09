package com.tikay.homitest.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Media> mediaData;


    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Media> getMediaDataList() {
        return mediaData;
    }


	@Override
 	public String toString(){
		return
			"Response{" +
			"status = '" + status + '\'' +
			",message = '" + message + '\'' +
			",data = '" + mediaData + '\'' +
			"}";
		}
}
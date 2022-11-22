package com.tikay.homitest.domain.model;

import com.google.gson.annotations.SerializedName;

public class Episode{

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("banner")
	private String banner;

	@SerializedName("trailer")
	private String trailer;

	@SerializedName("movie")
	private String movie;

	@SerializedName("status")
	private String status;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("deleted_at")
	private Object deletedAt;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setBanner(String banner){
		this.banner = banner;
	}

	public String getBanner(){
		return banner;
	}

	public void setTrailer(String trailer){
		this.trailer = trailer;
	}

	public String getTrailer(){
		return trailer;
	}

	public void setMovie(String movie){
		this.movie = movie;
	}

	public String getMovie(){
		return movie;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setDeletedAt(Object deletedAt){
		this.deletedAt = deletedAt;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Episode)) return false;
		Episode episode = (Episode) o;
		return id == episode.id;
	}

	@Override
 	public String toString(){
		return 
			"Season{" +
			"id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",banner = '" + banner + '\'' + 
			",trailer = '" + trailer + '\'' + 
			",movie = '" + movie + '\'' + 
			",status = '" + status + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",deleted_at = '" + deletedAt + '\'' + 
			"}";
		}
}
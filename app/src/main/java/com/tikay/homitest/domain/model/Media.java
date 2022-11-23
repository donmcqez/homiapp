package com.tikay.homitest.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Media {

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

	@SerializedName("category")
	private String category;

	@SerializedName("status")
	private String status;

	@SerializedName("rating")
	private String rating;

	@SerializedName("staring")
	private String staring;

	@SerializedName("views")
	private int views;

	@SerializedName("premium")
	private String premium;

	@SerializedName("released")
	private int released;

	@SerializedName("synopsis")
	private String synopsis;

	@SerializedName("seasons")
	private Seasons seasons;

	@SerializedName("suggestions")
	private List<Suggestion> suggestions;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("deleted_at")
	private Object deletedAt;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getStaring() {
		return staring;
	}

	public void setStaring(String staring) {
		this.staring = staring;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public Boolean isPremium() {
		return Objects.equals(premium, "Yes");
	}

	public String getPremium() {
		return premium;
	}

	public void setPremium(String premium) {
		this.premium = premium;
	}

	public int getReleased() {
		return released;
	}

	public void setReleased(int released) {
		this.released = released;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public Seasons getSeasons() {
		return seasons;
	}

	public void setSeasons(Seasons seasons) {
		this.seasons = seasons;
	}

	public List<Suggestion> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<Suggestion> suggestions) {
		this.suggestions = suggestions;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Object getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Object deletedAt) {
		this.deletedAt = deletedAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Media)) return false;
		Media media = (Media) o;
		return id == media.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
 	public String toString(){
		return 
			"Series{" +
			"id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",banner = '" + banner + '\'' + 
			",trailer = '" + trailer + '\'' + 
			",movie = '" + movie + '\'' + 
			",category = '" + category + '\'' + 
			",status = '" + status + '\'' + 
			",rating = '" + rating + '\'' + 
			",staring = '" + staring + '\'' + 
			",views = '" + views + '\'' + 
			",premium = '" + premium + '\'' + 
			",released = '" + released + '\'' + 
			",synopsis = '" + synopsis + '\'' + 
			",seasons = '" + seasons + '\'' + 
			",suggestions = '" + suggestions + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",deleted_at = '" + deletedAt + '\'' + 
			"}";
		}
}
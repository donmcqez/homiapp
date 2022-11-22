package com.tikay.homitest.domain.model;


import com.google.gson.annotations.SerializedName;

public class Suggestion {

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

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("deleted_at")
    private Object deletedAt;

//    public Suggestion() {}
//    public Suggestion(int id, String title, String banner, String trailer, String movie, String category, String status, String rating, String staring, int views, String premium, int released, String synopsis, String createdAt, String updatedAt, String deletedAt) {
//        this.id = id;
//        this.title = title;
//        this.banner = banner;
//        this.trailer = trailer;
//        this.movie = movie;
//        this.category = category;
//        this.status = status;
//        this.rating = rating;
//        this.staring = staring;
//        this.views = views;
//        this.premium = premium;
//        this.released = released;
//        this.synopsis = synopsis;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.deletedAt = deletedAt;
//    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBanner() {
        return banner;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getMovie() {
        return movie;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setStaring(String staring) {
        this.staring = staring;
    }

    public String getStaring() {
        return staring;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getViews() {
        return views;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getPremium() {
        return premium;
    }

    public void setReleased(int released) {
        this.released = released;
    }

    public int getReleased() {
        return released;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Suggestion)) return false;
        Suggestion suggestion = (Suggestion) o;
        return id == suggestion.id;
    }

    @Override
    public String toString() {
        return "Suggestion{" +
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
                        ",created_at = '" + createdAt + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",deleted_at = '" + deletedAt + '\'' +
                        "}";
    }


}
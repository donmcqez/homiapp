package com.tikay.homitest.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User implements Parcelable {
    private int id;
    private String name;
    private String userName;
    private String email;
    private String phone;
    private String password;
    private boolean isPremium;

//    public User(){}

    public User(int id, String name, String userName, String email, String phone, String password, boolean isPremium) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.isPremium = isPremium;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public User(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.userName = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
        this.password = in.readString();
        this.isPremium = in.readInt() == 1;
    }
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
        public User createFromParcel(Parcel in){
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.isPremium ? 1 : 0);
        dest.writeString(this.name);
        dest.writeString(this.name);
        dest.writeString(this.userName);
        dest.writeString(this.email);
        dest.writeString(this.phone);
        dest.writeString(this.password);
    }



}

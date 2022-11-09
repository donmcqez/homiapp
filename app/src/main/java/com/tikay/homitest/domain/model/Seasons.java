package com.tikay.homitest.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Seasons {
	@SerializedName("S1")
	private List<Episode> season1;

	@SerializedName("S2")
	private List<Episode> season2;

	public void setSeason1(List<Episode> season1){
		this.season1 = season1;
	}

	public List<Episode> getSeason1(){
		return season1;
	}

	public void setSeason2(List<Episode> season2){
		this.season2 = season2;
	}

	public List<Episode> getSeason2(){
		return season2;
	}

	@Override
 	public String toString(){
		return 
			"Seasons{" + 
			"s1 = '" + season1 + '\'' +
			",s2 = '" + season2 + '\'' +
			"}";
		}
}
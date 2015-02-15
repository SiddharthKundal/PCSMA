package com.pcsma.videoservlet;

public class CL_Video {
	
	String videoName, videoLength, videoAuthor, videoLink;
	int videoRating;
	
	public CL_Video(String videoName, String videoLength, String videoAuthor, String videoLink, int videoRating){
		this.videoName = videoName;
		this.videoAuthor = videoAuthor;
		this.videoLength = videoLength;
		this.videoLink = videoLink;
		this.videoRating = videoRating;
	}
	
	public String getVideoName() {
		return videoName;
	}
	
	public void setVideoRating(int videoRating) {
		this.videoRating = videoRating;
	}
	
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}
	
	public void setVideoLength(String videoLength) {
		this.videoLength = videoLength;
	}
	
	public void setVideoAuthor(String videoAuthor) {
		this.videoAuthor = videoAuthor;
	}
	
	public int getVideoRating() {
		return videoRating;
	}
	
	public String getVideoLink() {
		return videoLink;
	}
	
	public String getVideoLength() {
		return videoLength;
	}
	
	public String getVideoAuthor() {
		return videoAuthor;
	}
}

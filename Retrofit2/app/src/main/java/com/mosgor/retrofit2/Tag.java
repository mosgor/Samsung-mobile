package com.mosgor.retrofit2;

public class Tag {
	String id;
	Float latitude;
	Float longitude;
	String description;
	String image;
	Integer likes;
	Boolean is_liked;
	User user;

	@Override
	public String toString() {
		return "Tag{" +
				"id='" + id + '\'' +
				", latitude=" + latitude +
				", longitude=" + longitude +
				", description='" + description + '\'' +
				", image='" + image + '\'' +
				", likes=" + likes +
				", is_liked=" + is_liked +
				", user=" + user +
				'}';
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Boolean getIs_liked() {
		return is_liked;
	}

	public void setIs_liked(Boolean is_liked) {
		this.is_liked = is_liked;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

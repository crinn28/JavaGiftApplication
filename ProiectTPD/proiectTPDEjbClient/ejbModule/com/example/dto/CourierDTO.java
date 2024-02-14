package com.example.dto;

public class CourierDTO {
	private int id;
	private boolean deleted;
	private UserDTO user;

	public CourierDTO(UserDTO user) {
		super();
		this.user = user;
	}

	public CourierDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "CourierDTO [id=" + id + ", deleted=" + deleted + ", user=" + user + "]";
	}

}

package com.example.dto;

import java.io.Serializable;

public class ShopDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String address;
	private String contact;
	private boolean deleted;
	private String name;

	private UserDTO user;

	public ShopDTO() {
	}
	
	public ShopDTO(String name, String address, String contact, UserDTO user) {
		this.name = name;
		this.address = address;
		this.contact = contact;
		this.user = user;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserDTO getUser() {
		return this.user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "ShopDTO [id=" + id + ", name=" + name + ", address=" + address + ", user=" + user + "]";
	}
}

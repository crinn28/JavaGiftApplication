package com.example.dto;

public class ShoppingCartDTO {
	private int id;

	private boolean deleted;

	private boolean isValid;

	private UserDTO user;
	
	private double totalPrice;

	public ShoppingCartDTO(boolean isValid, UserDTO user) {
		super();
		this.isValid = isValid;
		this.user = user;
	}

	public ShoppingCartDTO() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}

	public UserDTO getUser() {
		return this.user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}


	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "ShoppingCartDTO [id=" + id + ", isValid=" + isValid + ", user=" + user
				+ "]";
	}
}
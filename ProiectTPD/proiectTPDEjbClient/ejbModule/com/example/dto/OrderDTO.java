package com.example.dto;

import java.util.List;

public class OrderDTO {
	private int id;
	private boolean deleted;
	private CourierDTO courier;
	private ReceiverDTO receiver;
	private ShoppingCartDTO shoppingcart;
	private StatusDTO status;
	private UserDTO user;
	private List<ShoppingCartProductDTO> shoppingCartProducts;

	public OrderDTO(ReceiverDTO receiver, ShoppingCartDTO shoppingcart, StatusDTO status, UserDTO user) {
		super();
		this.receiver = receiver;
		this.shoppingcart = shoppingcart;
		this.status = status;
		this.user = user;
	}

	public OrderDTO() {
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

	public CourierDTO getCourier() {
		return courier;
	}

	public void setCourier(CourierDTO courier) {
		this.courier = courier;
	}

	public ReceiverDTO getReceiver() {
		return receiver;
	}

	public void setReceiver(ReceiverDTO receiver) {
		this.receiver = receiver;
	}

	public ShoppingCartDTO getShoppingcart() {
		return shoppingcart;
	}

	public void setShoppingcart(ShoppingCartDTO shoppingcart) {
		this.shoppingcart = shoppingcart;
	}

	public StatusDTO getStatus() {
		return status;
	}

	public void setStatus(StatusDTO status) {
		this.status = status;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public List<ShoppingCartProductDTO> getShoppingCartProducts() {
		return shoppingCartProducts;
	}

	public void setShoppingCartProducts(List<ShoppingCartProductDTO> shoppingCartProducts) {
		this.shoppingCartProducts = shoppingCartProducts;
	}

	@Override
	public String toString() {
		return "OrderDTO [id=" + id + ", deleted=" + deleted + ", courier=" + courier + ", receiver=" + receiver
				+ ", shoppingcart=" + shoppingcart + ", status=" + status + ", user=" + user + ", shoppingCartProducts="
				+ shoppingCartProducts + "]";
	}

}

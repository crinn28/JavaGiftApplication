package com.example.dto;

public class ShoppingCartProductDTO {
	private int id;

	private int productQuantity;

	private boolean deleted;

	private ProductDTO product;

	private ShoppingCartDTO shoppingCartDTO;

	private double totalItemPrice;

	private boolean productIsInStock;

	public ShoppingCartProductDTO(ProductDTO product, int productQuantity, ShoppingCartDTO shoppingCartDTO) {
		super();
		this.productQuantity = productQuantity;
		this.product = product;
		this.shoppingCartDTO = shoppingCartDTO;
		this.productIsInStock = true;
	}

	public ShoppingCartProductDTO() {
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

	public ProductDTO getProduct() {
		return this.product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public int getProductQuantity() {
		return this.productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public ShoppingCartDTO getShoppingCartDTO() {
		return shoppingCartDTO;
	}

	public void setShoppingCartDTO(ShoppingCartDTO shoppingCartDTO) {
		this.shoppingCartDTO = shoppingCartDTO;
	}

	public double getTotalItemPrice() {
		return totalItemPrice;
	}

	public void setTotalItemPrice(double totalItemPrice) {
		this.totalItemPrice = totalItemPrice;
	}

	public boolean isProductIsInStock() {
		return productIsInStock;
	}

	public void setProductIsInStock(boolean productIsInStock) {
		this.productIsInStock = productIsInStock;
	}

	@Override
	public String toString() {
		return "ShoppingCartProductDTO [id=" + id + ", productQuantity=" + productQuantity + ", deleted=" + deleted
				+ ", product=" + product + ", shoppingCartDTO=" + shoppingCartDTO + ", totalItemPrice=" + totalItemPrice
				+ "]";
	}

}
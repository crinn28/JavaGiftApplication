package com.example.dto;

public class ProductShoppingCartDTO {
	private int id;
	private boolean deleted;
	private String description;
	private String name;
	private CategoryDTO category;
	private ShopDTO shop;
	private double price;
	private int quantity;
	private int desiredQuantity;

	// private List<Shoppingcartproduct> shoppingcartproducts;

	public ProductShoppingCartDTO() {
	}

	public ProductShoppingCartDTO(String name, String description, CategoryDTO category, ShopDTO shop, double price, int quantity) {
		super();
		this.description = description;
		this.name = name;
		this.category = category;
		this.shop = shop;
		this.price = price;
		this.quantity = quantity;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public CategoryDTO getCategory() {
		return this.category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	public ShopDTO getShop() {
		return shop;
	}

	public void setShop(ShopDTO shop) {
		this.shop = shop;
	}

	public int getDesiredQuantity() {
		return desiredQuantity;
	}

	public void setDesiredQuantity(int desiredQuantity) {
		this.desiredQuantity = desiredQuantity;
	}

	@Override
	public String toString() {
		return "ProductShoppingCartDTO [id=" + id + ", name=" + name + ", description=" + description + ", categoryId="
				+ category.getId() + ", desiredQuantity="
						+ desiredQuantity + "]";
	}
}

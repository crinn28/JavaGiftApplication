package com.example.dto;

import java.util.List;

public class CategoryDTO {
	private int id;
	private boolean deleted;
	private String name;

	private List<ProductDTO> products;

	public CategoryDTO() {
	}

	public CategoryDTO(String name) {
		this.name = name;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductDTO> getProducts() {
		return this.products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	public ProductDTO addProduct(ProductDTO product) {
		getProducts().add(product);
		product.setCategory(this);

		return product;
	}

	public ProductDTO removeProduct(ProductDTO product) {
		getProducts().remove(product);
		product.setCategory(null);

		return product;
	}

	@Override
	public String toString() {
		return "CategoryDTO [id=" + id + ", name=" + name + "]";
	}
}

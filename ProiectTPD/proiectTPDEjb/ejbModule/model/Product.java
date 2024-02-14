package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the products database table.
 * 
 */
@Entity
@Table(name = "products")
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
@NamedQuery(name = "findProductsByShopId", query = "SELECT p FROM Product p where p.shop.id =:shopId")
@NamedQuery(name = "searchProducts", query = "SELECT p FROM Product p WHERE p.shop.id =:shopId AND LOWER(p.name) LIKE :searchKeyword")
//@NamedQuery(name = "findSpecifiedProduct", query = "SELECT p FROM Product p where p.name=:name and p.description=:description and p.category.id=:categoryId")

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private boolean deleted;

	@Lob
	private String description;

	@Lob
	private String name;

	private double price;

	private int quantity;

	// bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;

	// bi-directional many-to-one association to Shop
	@ManyToOne
	@JoinColumn(name = "shopId")
	private Shop shop;

	// bi-directional many-to-one association to Shoppingcartproduct
	@OneToMany(mappedBy = "product")
	private List<Shoppingcartproduct> shoppingcartproducts;

	public Product() {
	}

	public Product(String name, String description, Category category, Shop shop, double price, int quantity) {
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

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Shop getShop() {
		return this.shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", categoryId="
				+ category.getId() + "]";
	}

}
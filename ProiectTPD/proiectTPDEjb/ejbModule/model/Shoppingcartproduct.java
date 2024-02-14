package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the shoppingcartproducts database table.
 * 
 */
@Entity
@Table(name = "shoppingcartproducts")
@NamedQuery(name = "Shoppingcartproduct.findAll", query = "SELECT s FROM Shoppingcartproduct s")
@NamedQuery(name = "findShoppingCartProductsByShoppingCartId", query = "SELECT s FROM Shoppingcartproduct s where s.shoppingcart.id =:shoppingCartId")
public class Shoppingcartproduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int productQuantity;

	private boolean deleted;

	// bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;

	// bi-directional many-to-one association to Shoppingcart
	@ManyToOne
	@JoinColumn(name = "shoppingCartId")
	private Shoppingcart shoppingcart;

	public Shoppingcartproduct(Product product, int productQuantity, Shoppingcart shoppingcart) {
		super();
		this.productQuantity = productQuantity;
		this.product = product;
		this.shoppingcart = shoppingcart;
	}

	public Shoppingcartproduct() {
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

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Shoppingcart getShoppingcart() {
		return this.shoppingcart;
	}

	public void setShoppingcart(Shoppingcart shoppingcart) {
		this.shoppingcart = shoppingcart;
	}

	public int getProductQuantity() {
		return this.productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

}
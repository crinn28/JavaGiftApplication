package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the shoppingcarts database table.
 * 
 */
@Entity
@Table(name = "shoppingcarts")
@NamedQuery(name = "Shoppingcart.findAll", query = "SELECT s FROM Shoppingcart s")
@NamedQuery(name = "findValidShoppingcart", query = "SELECT s FROM Shoppingcart s where s.isValid =true and s.user.id =:userId")
public class Shoppingcart implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private boolean deleted;

	private boolean isValid;

	// bi-directional many-to-one association to Shoppingcartproduct
	@OneToMany(mappedBy = "shoppingcart")
	private List<Shoppingcartproduct> shoppingcartproducts;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	public Shoppingcart(boolean isValid, User user) {
		super();
		this.isValid = isValid;
		this.user = user;
	}

	public Shoppingcart() {
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

	public List<Shoppingcartproduct> getShoppingcartproducts() {
		return this.shoppingcartproducts;
	}

	public void setShoppingcartproducts(List<Shoppingcartproduct> shoppingcartproducts) {
		this.shoppingcartproducts = shoppingcartproducts;
	}

	public Shoppingcartproduct addShoppingcartproduct(Shoppingcartproduct shoppingcartproduct) {
		getShoppingcartproducts().add(shoppingcartproduct);
		shoppingcartproduct.setShoppingcart(this);

		return shoppingcartproduct;
	}

	public Shoppingcartproduct removeShoppingcartproduct(Shoppingcartproduct shoppingcartproduct) {
		getShoppingcartproducts().remove(shoppingcartproduct);
		shoppingcartproduct.setShoppingcart(null);

		return shoppingcartproduct;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
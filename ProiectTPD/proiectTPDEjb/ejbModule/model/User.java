package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
@NamedQuery(name = "findUserByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
@NamedQuery(name = "getAllWaitingUsers", query = "SELECT u FROM User u WHERE u.approved = 0")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private boolean approved;

	@Lob
	private String email;

	@Lob
	private String firstname;

	@Lob
	private String lastname;

	@Lob
	private String password;

	// bi-directional many-to-one association to Courier
	@OneToMany(mappedBy = "user")
	private List<Courier> couriers;

	// bi-directional many-to-one association to Order
	@OneToMany(mappedBy = "user")
	private List<Order> orders;

	// bi-directional many-to-one association to Shoppingcart
	@OneToMany(mappedBy = "user")
	private List<Shoppingcart> shoppingcarts;

	// bi-directional many-to-one association to Shop
	@OneToMany(mappedBy = "user")
	private List<Shop> shops;

	// bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name = "roleId")
	private Role role;

	public User() {
	}

	public User(String email, String password) {
		super();
		this.password = password;
		this.email = email;
	}

	public User(String email, String password, String firstname, String lastname, Role role) {
		super();
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role = role;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getApproved() {
		return this.approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Courier> getCouriers() {
		return this.couriers;
	}

	public void setCouriers(List<Courier> couriers) {
		this.couriers = couriers;
	}

	public Courier addCourier(Courier courier) {
		getCouriers().add(courier);
		courier.setUser(this);

		return courier;
	}

	public Courier removeCourier(Courier courier) {
		getCouriers().remove(courier);
		courier.setUser(null);

		return courier;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setUser(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setUser(null);

		return order;
	}

	public List<Shoppingcart> getShoppingcarts() {
		return this.shoppingcarts;
	}

	public void setShoppingcarts(List<Shoppingcart> shoppingcarts) {
		this.shoppingcarts = shoppingcarts;
	}

	public List<Shop> getShops() {
		return shops;
	}

	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Shoppingcart addShoppingcart(Shoppingcart shoppingcart) {
		getShoppingcarts().add(shoppingcart);
		shoppingcart.setUser(this);

		return shoppingcart;
	}

	public Shoppingcart removeShoppingcart(Shoppingcart shoppingcart) {
		getShoppingcarts().remove(shoppingcart);
		shoppingcart.setUser(null);

		return shoppingcart;
	}

}
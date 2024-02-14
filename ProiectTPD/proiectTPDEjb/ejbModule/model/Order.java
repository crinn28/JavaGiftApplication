package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the orders database table.
 * 
 */
@Entity
@Table(name = "orders")
@NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o")
@NamedQuery(name = "getOrdersByUserId", query = "SELECT o FROM Order o where o.user.id=:userId")
@NamedQuery(name = "getNewOrders", query = "SELECT o FROM Order o where o.status.id=1")
@NamedQuery(name = "getMyOrders", query = "SELECT o FROM Order o where o.courier.id=:courierId")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private boolean deleted;

	// bi-directional many-to-one association to Courier
	@ManyToOne
	@JoinColumn(name = "courierId")
	private Courier courier;

	// bi-directional many-to-one association to Receiver
	@ManyToOne
	@JoinColumn(name = "receiverId")
	private Receiver receiver;

	// bi-directional many-to-one association to Shoppingcart
	@ManyToOne
	@JoinColumn(name = "shoppingCartId")
	private Shoppingcart shoppingcart;

	// bi-directional many-to-one association to Status
	@ManyToOne
	@JoinColumn(name = "statusId")
	private Status status;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	public Order(Receiver receiver, Shoppingcart shoppingcart, Status status, User user) {
		super();
		this.receiver = receiver;
		this.shoppingcart = shoppingcart;
		this.status = status;
		this.user = user;
	}

	public Order() {
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

	public Courier getCourier() {
		return this.courier;
	}

	public void setCourier(Courier courier) {
		this.courier = courier;
	}

	public Receiver getReceiver() {
		return this.receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public Shoppingcart getShoppingcart() {
		return this.shoppingcart;
	}

	public void setShoppingcart(Shoppingcart shoppingcart) {
		this.shoppingcart = shoppingcart;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", deleted=" + deleted + ", courier=" + courier + ", receiver=" + receiver
				+ ", shoppingcart=" + shoppingcart + ", status=" + status + ", user=" + user + "]";
	}

}
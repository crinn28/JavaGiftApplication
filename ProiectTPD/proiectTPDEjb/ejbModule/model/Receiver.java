package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the receivers database table.
 * 
 */
@Entity
@Table(name="receivers")
@NamedQuery(name="Receiver.findAll", query="SELECT r FROM Receiver r")
public class Receiver implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	private String address;

	private boolean deleted;

	@Lob
	private String firstname;

	@Lob
	private String lastname;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="receiver")
	private List<Order> orders;

	public Receiver() {
	}
	
	public Receiver(String address, String firstname, String lastname) {
		super();
		this.address = address;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setReceiver(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setReceiver(null);

		return order;
	}

	@Override
	public String toString() {
		return "Receiver [id=" + id + ", address=" + address + ", deleted=" + deleted + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", orders=" + orders + "]";
	}

}
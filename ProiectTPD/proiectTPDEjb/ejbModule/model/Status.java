package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the statuses database table.
 * 
 */
@Entity
@Table(name = "statuses")
@NamedQuery(name = "Status.findAll", query = "SELECT s FROM Status s")
public class Status implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private boolean deleted;

	@Lob
	private String name;

	// bi-directional many-to-one association to Order
	@OneToMany(mappedBy = "status")
	private List<Order> orders;

	public Status(String name) {
		super();
		this.name = name;
	}

	public Status(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Status() {
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

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setStatus(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setStatus(null);

		return order;
	}

	@Override
	public String toString() {
		return "Status [id=" + id + ", deleted=" + deleted + ", name=" + name + ", orders=" + orders + "]";
	}

}
package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the roles database table.
 * 
 */
@Entity
@Table(name = "roles")
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
@NamedQuery(name = "findRoleById", query = "SELECT r FROM Role r WHERE r.id = :id")
@NamedQuery(name = "getAllRolesWithoutAdmin", query = "SELECT r FROM Role r WHERE r.id != 4")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private boolean deleted;

	@Lob
	private String name;

	// bi-directional many-to-one association to User
	@OneToMany(mappedBy = "role")
	private List<User> users;

	public Role() {
	}

	public Role(String name) {
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

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setRole(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setRole(null);

		return user;
	}
	
	@Override
	public String toString() {
		return "RoleDTO [id=" + id + ", name=" + name + "]";
	}

}
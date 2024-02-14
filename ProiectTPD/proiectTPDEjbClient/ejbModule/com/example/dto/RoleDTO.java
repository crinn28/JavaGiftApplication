package com.example.dto;

import java.io.Serializable;
import java.util.Objects;

public class RoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private boolean deleted;

	private String name;

	public RoleDTO() {

	}

	public RoleDTO(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	@Override
	public String toString() {
		return "RoleDTO [id=" + id + ", name=" + name + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    RoleDTO roleDTO = (RoleDTO) obj;
	    return id == roleDTO.id;
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id);
	}
}

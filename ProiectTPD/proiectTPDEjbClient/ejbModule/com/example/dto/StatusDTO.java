package com.example.dto;

public class StatusDTO {
	private int id;
	private boolean deleted;
	private String name;

	public StatusDTO(String name) {
		super();
		this.name = name;
	}

	public StatusDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "StatusDTO [id=" + id + ", deleted=" + deleted + ", name=" + name + "]";
	}

}

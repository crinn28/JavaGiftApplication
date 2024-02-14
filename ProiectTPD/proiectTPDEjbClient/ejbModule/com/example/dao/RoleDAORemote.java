package com.example.dao;

import java.util.List;

import javax.ejb.Remote;

import com.example.dto.RoleDTO;

@Remote
public interface RoleDAORemote extends GenericDAO<RoleDTO> {

	List<RoleDTO> getAllRolesWithoutAdmin();

}

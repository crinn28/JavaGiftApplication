package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.example.dto.RoleDTO;
import com.example.util.DtoToEntity;
import com.example.util.EntityToDTO;

import model.Role;

@Stateless
@LocalBean
public class RoleDao implements RoleDAORemote {

	static final Logger LOGGER = Logger.getLogger(RoleDao.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	public RoleDao() {

	}

	private EntityToDTO entityToDTO = new EntityToDTO();

	private DtoToEntity dtoToEntity = new DtoToEntity();

	@Override
	public RoleDTO findById(int id) {
		Role role = entityManager.createNamedQuery("findRoleById", Role.class)
				.setParameter("id", id).getSingleResult();
		RoleDTO roleDTO = entityToDTO.convertRole(role);
		return roleDTO;
	}

	@Override
	public List<RoleDTO> findAll() {
		Query query = entityManager.createQuery("SELECT r FROM Role r");
		@SuppressWarnings("unchecked")
		List<Role> roles = query.getResultList();

		List<RoleDTO> dtoRoles = new ArrayList<>();
		for (Role role : roles) {
			dtoRoles.add(entityToDTO.convertRole(role));
		}
		return dtoRoles;
	}

	@Override
	public RoleDTO create(RoleDTO roleDTO) {
		Role role = dtoToEntity.convertRole(roleDTO);
		entityManager.persist(role);
		entityManager.flush();
		roleDTO.setId(role.getId());
		return roleDTO;
	}

	@Override
	public RoleDTO update(RoleDTO roleDTO) {
		Role role = dtoToEntity.convertRole(roleDTO);
		role.setId(roleDTO.getId());
		role = entityManager.merge(role);
		return roleDTO;
	}

	@Override
	public void delete(int id) {
		Role role = entityManager.find(Role.class, id);
		entityManager.remove(role);
	}
	
	@Override
	public List<RoleDTO> getAllRolesWithoutAdmin() {
		List<Role> roles = entityManager.createNamedQuery("getAllRolesWithoutAdmin", Role.class).getResultList();
		
		List<RoleDTO> dtoRoles = new ArrayList<>();
		for (Role role : roles) {
			dtoRoles.add(entityToDTO.convertRole(role));
		}
		return dtoRoles;
	}

}

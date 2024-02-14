package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.example.dto.StatusDTO;
import com.example.util.DtoToEntity;
import com.example.util.EntityToDTO;

import model.Status;

@Stateless
@LocalBean
public class StatusDao implements StatusDAORemote {

	static final Logger LOGGER = Logger.getLogger(StatusDao.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	public StatusDao() {

	}

	private EntityToDTO entityToDTO = new EntityToDTO();

	private DtoToEntity dtoToEntity = new DtoToEntity();

	@Override
	public StatusDTO findById(int id) {
		Status status = entityManager.find(Status.class, id);
		StatusDTO statusDTO = entityToDTO.convertStatus(status);
		return statusDTO;
	}

	@Override
	public List<StatusDTO> findAll() {
		Query query = entityManager.createQuery("SELECT c FROM Status c");
		@SuppressWarnings("unchecked")
		List<Status> categories = query.getResultList();

		List<StatusDTO> dtoCategories = new ArrayList<>();
		for (Status status : categories) {
			dtoCategories.add(entityToDTO.convertStatus(status));
		}
		return dtoCategories;
	}

	@Override
	public StatusDTO create(StatusDTO statusDTO) {
		Status status = dtoToEntity.convertStatus(statusDTO);
		entityManager.persist(status);
		entityManager.flush();
		statusDTO.setId(status.getId());
		return statusDTO;
	}

	@Override
	public StatusDTO update(StatusDTO statusDTO) {
		Status status = dtoToEntity.convertStatus(statusDTO);
		status.setId(statusDTO.getId());
		status = entityManager.merge(status);
		return statusDTO;
	}

	@Override
	public void delete(int id) {
		Status status = entityManager.find(Status.class, id);
		entityManager.remove(status);
	}

}

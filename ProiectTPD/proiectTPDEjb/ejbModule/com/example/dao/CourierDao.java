package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.example.dto.CourierDTO;
import com.example.util.DtoToEntity;
import com.example.util.EntityToDTO;

import model.Courier;

@Stateless
@LocalBean
public class CourierDao implements CourierDAORemote {

	static final Logger LOGGER = Logger.getLogger(CourierDao.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	public CourierDao() {

	}

	private EntityToDTO entityToDTO = new EntityToDTO();

	private DtoToEntity dtoToEntity = new DtoToEntity();

	@Override
	public CourierDTO findById(int id) {
		Courier courier = entityManager.find(Courier.class, id);
		CourierDTO courierDTO = entityToDTO.convertCourier(courier);
		return courierDTO;
	}

	@Override
	public List<CourierDTO> findAll() {
		Query query = entityManager.createQuery("SELECT c FROM Courier c");
		@SuppressWarnings("unchecked")
		List<Courier> categories = query.getResultList();

		List<CourierDTO> dtoCategories = new ArrayList<>();
		for (Courier courier : categories) {
			dtoCategories.add(entityToDTO.convertCourier(courier));
		}
		return dtoCategories;
	}

	@Override
	public CourierDTO create(CourierDTO courierDTO) {
		Courier courier = dtoToEntity.convertCourier(courierDTO);
		entityManager.persist(courier);
		entityManager.flush();
		courierDTO.setId(courier.getId());
		return courierDTO;
	}

	@Override
	public CourierDTO update(CourierDTO courierDTO) {
		Courier courier = dtoToEntity.convertCourier(courierDTO);
		courier.setId(courierDTO.getId());
		courier = entityManager.merge(courier);
		return courierDTO;
	}

	@Override
	public void delete(int id) {
		Courier courier = entityManager.find(Courier.class, id);
		entityManager.remove(courier);
	}

	@Override
	public CourierDTO findCourierByUserId(int userId) {
		CourierDTO courierDTO = new CourierDTO();
		Courier courier = new Courier();
		try {
			courier = entityManager.createNamedQuery("findCourierByUserId", Courier.class)
					.setParameter("userId", userId).getSingleResult();
		} catch (NoResultException e) {
			throw new NoResultException("Bad request!");
		}

		courierDTO = entityToDTO.convertCourier(courier);
		return courierDTO;
	}

}

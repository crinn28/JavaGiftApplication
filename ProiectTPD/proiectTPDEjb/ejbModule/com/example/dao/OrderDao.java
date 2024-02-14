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
import com.example.dto.OrderDTO;
import com.example.util.DtoToEntity;
import com.example.util.EntityToDTO;

import model.Order;
import model.Status;

@Stateless
@LocalBean
public class OrderDao implements OrderDAORemote {

	static final Logger LOGGER = Logger.getLogger(OrderDao.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	public OrderDao() {

	}

	private EntityToDTO entityToDTO = new EntityToDTO();

	private DtoToEntity dtoToEntity = new DtoToEntity();

	@Override
	public OrderDTO findById(int id) {
		Order order = entityManager.find(Order.class, id);
		OrderDTO orderDTO = entityToDTO.convertOrder(order);
		return orderDTO;
	}

	@Override
	public List<OrderDTO> findAll() {
		Query query = entityManager.createQuery("SELECT r FROM Order r");
		@SuppressWarnings("unchecked")
		List<Order> orders = query.getResultList();

		List<OrderDTO> dtoOrders = new ArrayList<>();
		for (Order order : orders) {
			dtoOrders.add(entityToDTO.convertOrder(order));
		}
		return dtoOrders;
	}

	@Override
	public OrderDTO create(OrderDTO orderDTO) {
		Order order = dtoToEntity.convertOrder(orderDTO);
		order.setStatus(new Status(1, "NEW"));
		entityManager.persist(order);
		entityManager.flush();
		orderDTO.setId(order.getId());
		return orderDTO;
	}

	@Override
	public OrderDTO update(OrderDTO orderDTO) {
		Order order = dtoToEntity.convertOrder(orderDTO);
		order.setId(orderDTO.getId());
		order = entityManager.merge(order);
		return orderDTO;
	}

	@Override
	public void delete(int id) {
		Order order = entityManager.find(Order.class, id);
		entityManager.remove(order);
	}

	@Override
	public List<OrderDTO> getOrdersByUserId(int userId) {
		List<OrderDTO> ordersDTO = new ArrayList<>();
		List<Order> orders = new ArrayList<>();
		try {
			orders = entityManager.createNamedQuery("getOrdersByUserId", Order.class).setParameter("userId", userId)
					.getResultList();
		} catch (NoResultException e) {
			throw new NoResultException("Bad request!");
		}

		for (Order order : orders) {
			ordersDTO.add(entityToDTO.convertOrder(order));
		}
		return ordersDTO;
	}

	@Override
	public List<OrderDTO> getNewOrders() {
		List<OrderDTO> ordersDTO = new ArrayList<>();
		List<Order> orders = new ArrayList<>();
		try {
			orders = entityManager.createNamedQuery("getNewOrders", Order.class).getResultList();
		} catch (NoResultException e) {
			throw new NoResultException("Bad request!");
		}

		for (Order order : orders) {
			ordersDTO.add(entityToDTO.convertOrder(order));
		}
		return ordersDTO;
	}

	@Override
	public List<OrderDTO> getMyOrders(CourierDTO courierDTO) {
		List<OrderDTO> ordersDTO = new ArrayList<>();
		List<Order> orders = new ArrayList<>();
		try {
			orders = entityManager.createNamedQuery("getMyOrders", Order.class)
					.setParameter("courierId", courierDTO.getId()).getResultList();
		} catch (NoResultException e) {
			throw new NoResultException("Bad request!");
		}

		for (Order order : orders) {
			ordersDTO.add(entityToDTO.convertOrder(order));
		}
		return ordersDTO;
	}

}

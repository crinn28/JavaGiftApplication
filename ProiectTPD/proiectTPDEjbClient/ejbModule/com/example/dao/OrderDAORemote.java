package com.example.dao;

import java.util.List;

import javax.ejb.Remote;

import com.example.dto.CourierDTO;
import com.example.dto.OrderDTO;

@Remote
public interface OrderDAORemote extends GenericDAO<OrderDTO> {

	List<OrderDTO> getOrdersByUserId(int userId);

	List<OrderDTO> getNewOrders();

	List<OrderDTO> getMyOrders(CourierDTO courierDTO);

}

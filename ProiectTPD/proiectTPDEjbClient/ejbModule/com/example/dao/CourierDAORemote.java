package com.example.dao;

import javax.ejb.Remote;

import com.example.dto.CourierDTO;

@Remote
public interface CourierDAORemote extends GenericDAO<CourierDTO> {

	CourierDTO findCourierByUserId(int userId);

}

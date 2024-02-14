package com.example.dao;

import javax.ejb.Remote;

import com.example.dto.ShopDTO;
import com.example.dto.UserDTO;

@Remote
public interface ShopDAORemote extends GenericDAO<ShopDTO> {
	
	ShopDTO getShopByUser(UserDTO userDTO);
}

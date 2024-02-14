package com.example.dao;

import java.util.List;

import javax.ejb.Remote;

import com.example.dto.ShoppingCartDTO;
import com.example.dto.ShoppingCartProductDTO;

@Remote
public interface ShoppingCartProductDAORemote extends GenericDAO<ShoppingCartProductDTO> {

	List<ShoppingCartProductDTO> findShoppingCartProductsByShoppingCartId(ShoppingCartDTO shoppingCartDTO);
	
}
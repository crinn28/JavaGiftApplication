package com.example.dao;

import javax.ejb.Remote;

import com.example.dto.ProductShoppingCartDTO;
import com.example.dto.ShoppingCartDTO;

@Remote
public interface ShoppingCartDAORemote extends GenericDAO<ShoppingCartDTO> {

	ShoppingCartDTO addProductToShoppingCart(ProductShoppingCartDTO productDTO, ShoppingCartDTO shopDTO);
	ShoppingCartDTO findValidShoppingcart(int userId);
	
}
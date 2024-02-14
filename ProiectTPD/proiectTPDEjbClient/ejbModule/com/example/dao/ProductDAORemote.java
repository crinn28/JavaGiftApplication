package com.example.dao;

import java.util.List;

import javax.ejb.Remote;

import com.example.dto.ProductDTO;
import com.example.dto.ProductShoppingCartDTO;

@Remote
public interface ProductDAORemote extends GenericDAO<ProductDTO> {

	List<ProductDTO> getShopProducts(int shopId);

	List<ProductShoppingCartDTO> getShoppinCartProducts(int shopId);

	List<ProductShoppingCartDTO> searchProducts(int shopId, String searchKeyword);
}

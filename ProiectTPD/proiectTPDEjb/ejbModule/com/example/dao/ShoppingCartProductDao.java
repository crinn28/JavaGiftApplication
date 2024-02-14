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

import com.example.dto.ShoppingCartDTO;
import com.example.dto.ShoppingCartProductDTO;
import com.example.util.DtoToEntity;
import com.example.util.EntityToDTO;

import model.Shoppingcartproduct;

@Stateless
@LocalBean
public class ShoppingCartProductDao implements ShoppingCartProductDAORemote {

	static final Logger LOGGER = Logger.getLogger(ShoppingCartProductDao.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	public ShoppingCartProductDao() {

	}

	private EntityToDTO entityToDTO = new EntityToDTO();

	private DtoToEntity dtoToEntity = new DtoToEntity();

	@Override
	public ShoppingCartProductDTO findById(int id) {
		Shoppingcartproduct shop = entityManager.find(Shoppingcartproduct.class, id);
		ShoppingCartProductDTO shopDTO = entityToDTO.convertShoppingCartProduct(shop);
		return shopDTO;
	}

	@Override
	public List<ShoppingCartProductDTO> findAll() {
		Query query = entityManager.createQuery("SELECT s FROM Shoppingcartproduct s");
		@SuppressWarnings("unchecked")
		List<Shoppingcartproduct> shops = query.getResultList();
		List<ShoppingCartProductDTO> dtoShops = new ArrayList<>();
		for (Shoppingcartproduct shop : shops) {
			dtoShops.add(entityToDTO.convertShoppingCartProduct(shop));
		}
		return dtoShops;
	}

	@Override
	public ShoppingCartProductDTO create(ShoppingCartProductDTO shopDTO) {
		Shoppingcartproduct shop = dtoToEntity.convertShoppingCartProduct(shopDTO);
		entityManager.persist(shop);
		entityManager.flush();
		shopDTO.setId(shop.getId());

		return shopDTO;
	}

	@Override
	public ShoppingCartProductDTO update(ShoppingCartProductDTO shopDTO) {
		Shoppingcartproduct shop = dtoToEntity.convertShoppingCartProduct(shopDTO);
		shop.setId(shopDTO.getId());
		shop = entityManager.merge(shop);
		return entityToDTO.convertShoppingCartProduct(shop);
	}

	@Override
	public void delete(int id) {
		Shoppingcartproduct shop = entityManager.find(Shoppingcartproduct.class, id);
		entityManager.remove(shop);
	}

	@Override
	public List<ShoppingCartProductDTO> findShoppingCartProductsByShoppingCartId(ShoppingCartDTO shoppingCartDTO) {
		List<ShoppingCartProductDTO> shoppingCartProductsDTO = new ArrayList<>();
		List<Shoppingcartproduct> shoppingCartProducts = new ArrayList<>();
		try {
			shoppingCartProducts = entityManager
					.createNamedQuery("findShoppingCartProductsByShoppingCartId", Shoppingcartproduct.class)
					.setParameter("shoppingCartId", shoppingCartDTO.getId()).getResultList();
		} catch (NoResultException e) {
			throw new NoResultException("Bad request!");
		}

		for (Shoppingcartproduct shoppingcartproduct : shoppingCartProducts) {
			shoppingCartProductsDTO.add(entityToDTO.convertShoppingCartProduct(shoppingcartproduct));
		}
		return shoppingCartProductsDTO;
	}

}

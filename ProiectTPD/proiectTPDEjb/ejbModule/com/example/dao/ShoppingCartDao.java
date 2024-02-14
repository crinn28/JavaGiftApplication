package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.example.dto.ProductShoppingCartDTO;
import com.example.dto.ShoppingCartDTO;
import com.example.util.DtoToEntity;
import com.example.util.EntityToDTO;

import model.Shoppingcart;

@Stateless
@LocalBean
public class ShoppingCartDao implements ShoppingCartDAORemote {

	static final Logger LOGGER = Logger.getLogger(ShoppingCartDao.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	public ShoppingCartDao() {

	}

	private EntityToDTO entityToDTO = new EntityToDTO();

	private DtoToEntity dtoToEntity = new DtoToEntity();

	@Override
	public ShoppingCartDTO findById(int id) {
		Shoppingcart shop = entityManager.find(Shoppingcart.class, id);
		ShoppingCartDTO shopDTO = entityToDTO.convertShoppingCart(shop);
		return shopDTO;
	}

	@Override
	public List<ShoppingCartDTO> findAll() {
		Query query = entityManager.createQuery("SELECT s FROM Shoppingcart s");
		@SuppressWarnings("unchecked")
		List<Shoppingcart> shops = query.getResultList();
		List<ShoppingCartDTO> dtoShops = new ArrayList<>();
		for (Shoppingcart shop : shops) {
			dtoShops.add(entityToDTO.convertShoppingCart(shop));
		}
		return dtoShops;
	}

	@Override
	public ShoppingCartDTO create(ShoppingCartDTO shopDTO) {
		Shoppingcart shop = dtoToEntity.convertShoppingCart(shopDTO);
		shop.setIsValid(true);
		entityManager.persist(shop);
		entityManager.flush();
		shopDTO.setId(shop.getId());

		return shopDTO;
	}

	@Override
	public ShoppingCartDTO addProductToShoppingCart(ProductShoppingCartDTO productDTO, ShoppingCartDTO shopDTO) {
		Shoppingcart shop = dtoToEntity.convertShoppingCartWithProduct(productDTO, shopDTO);
		entityManager.persist(shop);
		entityManager.flush();
		shopDTO.setId(shop.getId());

		return shopDTO;
	}

	@Override
	public ShoppingCartDTO update(ShoppingCartDTO shopDTO) {
		Shoppingcart shop = dtoToEntity.convertShoppingCart(shopDTO);
		shop.setId(shopDTO.getId());
		shop = entityManager.merge(shop);
		return entityToDTO.convertShoppingCart(shop);
	}

	@Override
	public void delete(int id) {
		Shoppingcart shop = entityManager.find(Shoppingcart.class, id);
		entityManager.remove(shop);
	}

	@Override
	public ShoppingCartDTO findValidShoppingcart(int userId) {
		Shoppingcart shoppingCart = new Shoppingcart();
		try {
			shoppingCart = entityManager.createNamedQuery("findValidShoppingcart", Shoppingcart.class).setParameter("userId", userId)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}

		ShoppingCartDTO shoppingCartDTO = entityToDTO.convertShoppingCart(shoppingCart);
		return shoppingCartDTO;
	}

}

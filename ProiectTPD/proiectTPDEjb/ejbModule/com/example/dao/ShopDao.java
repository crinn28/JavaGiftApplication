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

import com.example.dto.ShopDTO;
import com.example.dto.UserDTO;
import com.example.exception.InvalidPhoneNumberException;
import com.example.util.DtoToEntity;
import com.example.util.EntityToDTO;
import com.example.util.Validators;

import model.Shop;

@Stateless
@LocalBean
public class ShopDao implements ShopDAORemote {

	static final Logger LOGGER = Logger.getLogger(ShopDao.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	public ShopDao() {

	}

	private EntityToDTO entityToDTO = new EntityToDTO();

	private DtoToEntity dtoToEntity = new DtoToEntity();

	private Validators validators = new Validators();

	@Override
	public ShopDTO findById(int id) {
		Shop shop = entityManager.find(Shop.class, id);
		ShopDTO shopDTO = entityToDTO.convertShop(shop);
		return shopDTO;
	}

	@Override
	public List<ShopDTO> findAll() {
		Query query = entityManager.createQuery("SELECT s FROM Shop s");
		@SuppressWarnings("unchecked")
		List<Shop> shops = query.getResultList();
		List<ShopDTO> dtoShops = new ArrayList<>();
		for (Shop shop : shops) {
			dtoShops.add(entityToDTO.convertShop(shop));
		}
		return dtoShops;
	}

	@Override
	public ShopDTO create(ShopDTO shopDTO) throws InvalidPhoneNumberException {
		if (!validators.isValidPhoneNumber(shopDTO.getContact())) {
			throw new InvalidPhoneNumberException("The phone number is invalid! It should contain 10 digits!");
		}

		Shop shop = dtoToEntity.convertShop(shopDTO);

		entityManager.persist(shop);
		entityManager.flush();
		shopDTO.setId(shop.getId());

		return shopDTO;
	}

	@Override
	public ShopDTO update(ShopDTO shopDTO) throws InvalidPhoneNumberException {
		if (!validators.isValidPhoneNumber(shopDTO.getContact())) {
			throw new InvalidPhoneNumberException("The phone number is invalid! It should contain 10 digits!");
		}

		Shop shop = dtoToEntity.convertShop(shopDTO);
		shop.setId(shopDTO.getId());
		shop = entityManager.merge(shop);
		return entityToDTO.convertShop(shop);
	}

	@Override
	public void delete(int id) {
		Shop shop = entityManager.find(Shop.class, id);
		entityManager.remove(shop);
	}

	@Override
	public ShopDTO getShopByUser(UserDTO userDTO) {
		Shop shop = new Shop();
		try {
			shop = entityManager.createNamedQuery("findShopByUserId", Shop.class)
					.setParameter("userId", userDTO.getId()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

		ShopDTO shopDTO = entityToDTO.convertShop(shop);
		return shopDTO;
	}
}

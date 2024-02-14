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

import com.example.dto.ProductDTO;
import com.example.dto.ProductShoppingCartDTO;
import com.example.exception.InvalidQuantityException;
import com.example.util.DtoToEntity;
import com.example.util.EntityToDTO;

import model.Product;

@Stateless
@LocalBean
public class ProductDao implements ProductDAORemote {

	static final Logger LOGGER = Logger.getLogger(ProductDao.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	public ProductDao() {

	}

	private EntityToDTO entityToDTO = new EntityToDTO();

	private DtoToEntity dtoToEntity = new DtoToEntity();

	@Override
	public ProductDTO findById(int id) {
		Product product = entityManager.find(Product.class, id);
		ProductDTO productDTO = entityToDTO.convertProduct(product);
		return productDTO;
	}

	@Override
	public List<ProductDTO> findAll() {
		Query query = entityManager.createQuery("SELECT p FROM Product p");
		@SuppressWarnings("unchecked")
		List<Product> products = query.getResultList();

		List<ProductDTO> dtoProducts = new ArrayList<>();
		for (Product product : products) {
			dtoProducts.add(entityToDTO.convertProduct(product));
		}
		return dtoProducts;
	}

	@Override
	public ProductDTO create(ProductDTO productDTO) throws InvalidQuantityException{
		if (productDTO.getQuantity() < 0) {
			throw new InvalidQuantityException("The quantity is invalid! It should be grater or equal to 0!");
		}
		
		Product product = dtoToEntity.convertProduct(productDTO);
		entityManager.persist(product);
		entityManager.flush();
		productDTO.setId(product.getId());
		return productDTO;
	}

	@Override
	public ProductDTO update(ProductDTO productDTO) throws InvalidQuantityException{

		if (productDTO.getQuantity() < 0) {
			throw new InvalidQuantityException("The quantity is invalid! It should be grater or equal to 0!");
		}
		
		Product product = dtoToEntity.convertProduct(productDTO);
		product.setId(productDTO.getId());
		product = entityManager.merge(product);
		return productDTO;
	}

	@Override
	public void delete(int id) {
		Product product = entityManager.find(Product.class, id);
		entityManager.remove(product);
	}
	
	@Override
	public List<ProductDTO> getShopProducts(int shopId) {
		List<Product> products = new ArrayList<>();
		try {
			products = entityManager.createNamedQuery("findProductsByShopId", Product.class)
					.setParameter("shopId", shopId).getResultList();
		} catch (NoResultException e) {
			throw new NoResultException("Products not found!");
		}

		List<ProductDTO> dtoProducts = new ArrayList<>();
		for (Product product : products) {
			dtoProducts.add(entityToDTO.convertProduct(product));
		}
		return dtoProducts;
	}
	
	@Override
	public List<ProductShoppingCartDTO> getShoppinCartProducts(int shopId) {
		List<Product> products = new ArrayList<>();
		try {
			products = entityManager.createNamedQuery("findProductsByShopId", Product.class)
					.setParameter("shopId", shopId).getResultList();
		} catch (NoResultException e) {
			throw new NoResultException("Products not found!");
		}

		List<ProductShoppingCartDTO> dtoProducts = new ArrayList<>();
		for (Product product : products) {
			dtoProducts.add(entityToDTO.convertProductToShoppingCart(product));
		}
		return dtoProducts;
	}
	
	@Override
    public List<ProductShoppingCartDTO> searchProducts(int shopId, String searchKeyword) {
		List<Product> products = new ArrayList<>();
		try {
			String searchParam = "%" + searchKeyword.toLowerCase() + "%";
			products = entityManager.createNamedQuery("searchProducts", Product.class)
					.setParameter("shopId", shopId).setParameter("searchKeyword", searchParam).getResultList();
		} catch (NoResultException e) {
			throw new NoResultException("Products not found!");
		}

		List<ProductShoppingCartDTO> dtoProducts = new ArrayList<>();
		for (Product product : products) {
			dtoProducts.add(entityToDTO.convertProductToShoppingCart(product));
		}
		return dtoProducts;
    }

}

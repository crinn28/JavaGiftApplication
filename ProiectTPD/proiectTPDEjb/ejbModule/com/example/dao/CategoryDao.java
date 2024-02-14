package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.example.dto.CategoryDTO;
import com.example.util.DtoToEntity;
import com.example.util.EntityToDTO;

import model.Category;

@Stateless
@LocalBean
public class CategoryDao implements CategoryDAORemote {

	static final Logger LOGGER = Logger.getLogger(CategoryDao.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	public CategoryDao() {

	}

	private EntityToDTO entityToDTO = new EntityToDTO();

	private DtoToEntity dtoToEntity = new DtoToEntity();

	@Override
	public CategoryDTO findById(int id) {
		Category category = entityManager.find(Category.class, id);
		CategoryDTO categoryDTO = entityToDTO.convertCategory(category);
		return categoryDTO;
	}

	@Override
	public List<CategoryDTO> findAll() {
		Query query = entityManager.createQuery("SELECT c FROM Category c");
		@SuppressWarnings("unchecked")
		List<Category> categories = query.getResultList();

		List<CategoryDTO> dtoCategories = new ArrayList<>();
		for (Category category : categories) {
			dtoCategories.add(entityToDTO.convertCategory(category));
		}
		return dtoCategories;
	}

	@Override
	public CategoryDTO create(CategoryDTO categoryDTO) {
		Category category = dtoToEntity.convertCategory(categoryDTO);
		entityManager.persist(category);
		entityManager.flush();
		categoryDTO.setId(category.getId());
		return categoryDTO;
	}

	@Override
	public CategoryDTO update(CategoryDTO categoryDTO) {
		Category category = dtoToEntity.convertCategory(categoryDTO);
		category.setId(categoryDTO.getId());
		category = entityManager.merge(category);
		return categoryDTO;
	}

	@Override
	public void delete(int id) {
		Category category = entityManager.find(Category.class, id);
		entityManager.remove(category);
	}

}

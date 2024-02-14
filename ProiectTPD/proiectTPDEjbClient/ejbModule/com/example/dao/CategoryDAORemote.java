package com.example.dao;

import javax.ejb.Remote;

import com.example.dto.CategoryDTO;

@Remote
public interface CategoryDAORemote extends GenericDAO<CategoryDTO> {

}

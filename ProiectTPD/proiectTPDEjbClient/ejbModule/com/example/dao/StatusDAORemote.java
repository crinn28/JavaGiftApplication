package com.example.dao;

import javax.ejb.Remote;

import com.example.dto.StatusDTO;

@Remote
public interface StatusDAORemote extends GenericDAO<StatusDTO> {

}

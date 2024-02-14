package com.example.dao;

import javax.ejb.Remote;

import com.example.dto.ReceiverDTO;

@Remote
public interface ReceiverDAORemote extends GenericDAO<ReceiverDTO> {

}

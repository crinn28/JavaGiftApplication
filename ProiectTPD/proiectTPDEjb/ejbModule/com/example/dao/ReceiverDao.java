package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.example.dto.ReceiverDTO;
import com.example.util.DtoToEntity;
import com.example.util.EntityToDTO;

import model.Receiver;


@Stateless
@LocalBean
public class ReceiverDao implements ReceiverDAORemote {

	static final Logger LOGGER = Logger.getLogger(ReceiverDao.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	public ReceiverDao() {

	}

	private EntityToDTO entityToDTO = new EntityToDTO();

	private DtoToEntity dtoToEntity = new DtoToEntity();

	@Override
	public ReceiverDTO findById(int id) {
		Receiver receiver = entityManager.find(Receiver.class, id);
		ReceiverDTO receiverDTO = entityToDTO.convertReceiver(receiver);
		return receiverDTO;
	}

	@Override
	public List<ReceiverDTO> findAll() {
		Query query = entityManager.createQuery("SELECT r FROM Receiver r");
		@SuppressWarnings("unchecked")
		List<Receiver> receivers = query.getResultList();

		List<ReceiverDTO> dtoReceivers = new ArrayList<>();
		for (Receiver receiver : receivers) {
			dtoReceivers.add(entityToDTO.convertReceiver(receiver));
		}
		return dtoReceivers;
	}

	@Override
	public ReceiverDTO create(ReceiverDTO receiverDTO) {
		Receiver receiver = dtoToEntity.convertReceiver(receiverDTO);
		entityManager.persist(receiver);
		entityManager.flush();
		receiverDTO.setId(receiver.getId());
		return receiverDTO;
	}

	@Override
	public ReceiverDTO update(ReceiverDTO receiverDTO) {
		Receiver receiver = dtoToEntity.convertReceiver(receiverDTO);
		receiver.setId(receiverDTO.getId());
		receiver = entityManager.merge(receiver);
		return receiverDTO;
	}

	@Override
	public void delete(int id) {
		Receiver receiver = entityManager.find(Receiver.class, id);
		entityManager.remove(receiver);
	}

}


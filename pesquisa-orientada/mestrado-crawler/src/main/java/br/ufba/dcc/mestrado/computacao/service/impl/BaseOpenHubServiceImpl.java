package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.BaseRepository;
import br.ufba.dcc.mestrado.computacao.service.base.BaseOpenHubService;

public abstract class BaseOpenHubServiceImpl<ID extends Number, E extends BaseEntity<ID>>
		implements BaseOpenHubService<ID, E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 382951858584438559L;
	
	private Class<E> entityClass;
	protected BaseRepository<ID, E> repository;
	
	public BaseOpenHubServiceImpl(
			BaseRepository<ID, E> baseRepository,
			Class<E> entityClass) {
		this.repository = baseRepository;
		this.entityClass = entityClass;
	}
	
	public Class<E> getEntityClass() {
		return entityClass;
	}
	
	protected BaseRepository<ID, E> getRepository() {
		return repository;
	}

	@Transactional(readOnly = true)
	public Long countAll() {
		return repository.countAll();
	}

	@Transactional(readOnly = true)
	public List<E> findAll() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public List<E> findAll(String orderBy) {
		return repository.findAll(orderBy);
	}

	@Transactional(readOnly = true)
	public List<E> findAll(Integer startAt, Integer offset) {
		return repository.findAll(startAt, offset);
	}

	@Transactional(readOnly = true)
	public List<E> findAll(Integer startAt, Integer offset, String orderBy) {
		return repository.findAll(startAt, offset, orderBy);
	}

	@Transactional(readOnly = true)
	public E findById(ID id) {
		return repository.findById(id);
	}

	@Transactional
	public E save(E entity) throws Exception {
		return repository.save(entity);
	}

	@Transactional
	public E add(E entity) throws Exception {
		return repository.add(entity);
	}

	@Transactional
	public E update(E entity) throws Exception {
		return repository.update(entity);
	}

	@Transactional
	public E delete(E entity) throws Exception {
		return repository.delete(entity);
	}

	
}

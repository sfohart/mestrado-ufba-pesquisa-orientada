package br.ufba.dcc.mestrado.computacao.service.mahout.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.BaseRepository;
import br.ufba.dcc.mestrado.computacao.service.mahout.base.BasicRecommenderService;

public abstract class BasicRecommenderServiceImpl<ID extends Number, E extends BaseEntity<ID>> implements BasicRecommenderService<ID, E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3941225883926270394L;
	
	private Class<E> entityClass;
	private BaseRepository<ID, E> repository;
	
	public BasicRecommenderServiceImpl(BaseRepository<ID, E> repository, Class<E> entityClass) {
		this.repository = repository;
		this.entityClass = entityClass;
	}
	
	public BaseRepository<ID, E> getRepository() {
		return repository;
	}
	
	public void setRepository(BaseRepository<ID, E> repository) {
		this.repository = repository;
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

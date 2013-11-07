package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.BaseRepository;

public class BaseRepositoryImpl<ID extends Number, E extends BaseEntity<ID>>
	implements BaseRepository<ID, E> {
	
	private static Logger logger = Logger.getLogger(BaseRepositoryImpl.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 3711025622105052268L;

	@PersistenceContext
	private EntityManager entityManager;
	
	private Class<E> entityClass;
	
	public BaseRepositoryImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public Class<E> getEntityClass() {
		return entityClass;
	}
		
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	public List<E> findAll(){
		return findAll(null);
	}
	
	@Transactional
	public List<E> findAll(String orderBy){
		TypedQuery<E> query = null;
		if (orderBy == null || "".equals(orderBy)) {
			query = createSelectAllQuery();
		} else {
			query = createSelectAllQuery(orderBy);
		}
		
		List<E> result = query.getResultList();
		
		return result;
	}

	protected TypedQuery<E> createSelectAllQuery() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		
		Root<E> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);
		
		TypedQuery<E> query = entityManager.createQuery(criteriaQuery);
		return query;
	}
	
	protected TypedQuery<E> createSelectAllQuery(String orderBy) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		
		Root<E> root = criteriaQuery.from(entityClass);
		CriteriaQuery<E> select = criteriaQuery.select(root);
		select.orderBy(criteriaBuilder.asc(root.get(orderBy)));
		
		TypedQuery<E> query = entityManager.createQuery(criteriaQuery);
		return query;		
	}
	
	@Transactional
	public List<E> findAll(Integer startAt, Integer offset, String orderBy) {
		TypedQuery<E> query = null;
		if (orderBy == null || "".equals(orderBy)) {
			query = createSelectAllQuery();
		} else {
			query = createSelectAllQuery(orderBy);
		}
		
		query
			.setMaxResults(offset)
			.setFirstResult(startAt);
		
		List<E> result = query.getResultList();
		
		return result;
	}
	@Transactional
	public List<E> findAll(Integer startAt, Integer offset) {
		return findAll(startAt, offset, null);
	}
	
	@Transactional
	public E findById(ID id) {
		return entityManager.find(entityClass, id);
	}
	
	@Transactional
	public E save(E entity) {
		if (entity != null) {
			if (entity.getId() != null) {
				logger.info(String.format("Salvando entidade do tipo %s com id %d", getEntityClass().getSimpleName(), entity.getId()));
				return update(entity);
			} else {
				return add(entity);
			}
		}		
		
		return entity;
	}
	
	@Transactional
	public E add(E entity) {
		entityManager.persist(entity);
		return entity;
	}
	
	@Transactional
	public E update(E entity) {
		return entityManager.merge(entity);
	}
	
	@Transactional
	public E delete(E entity) {
		entityManager.remove(entity);
		return entity;
	}

	@Override
	@Transactional
	public Long countAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(entityClass)));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}


}

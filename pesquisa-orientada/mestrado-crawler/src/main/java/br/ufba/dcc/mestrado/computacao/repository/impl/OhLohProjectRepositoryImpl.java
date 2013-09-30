package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohProjectRepository;

@Repository
public class OhLohProjectRepositoryImpl extends BaseRepositoryImpl<Long, OhLohProjectEntity>
		implements OhLohProjectRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public OhLohProjectRepositoryImpl() {
		super(OhLohProjectEntity.class);
	}

	@Override
	@Transactional(readOnly = true)
	public OhLohProjectEntity findById(Long id) {		
		OhLohProjectEntity result = super.findById(id);
		
		if (result != null) {
			result.getOhLohLicenses();
			result.getOhLohTags();
		}
		
		return result;
	}
	
	@Override
	@Transactional(readOnly = true)
	public OhLohProjectEntity findByName(String name) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<OhLohProjectEntity> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
		
		Root<OhLohProjectEntity> root = criteriaQuery.from(getEntityClass());
		criteriaQuery.select(root);
		
		Predicate predicate = criteriaBuilder.equal(root.get("name"), name);
		criteriaQuery.where(predicate);
		
		TypedQuery<OhLohProjectEntity> query = getEntityManager().createQuery(criteriaQuery);
		
		OhLohProjectEntity projectEntity = query.getSingleResult();
		
		return projectEntity;
	}
	
}

package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubProjectActivityIndexEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubTagEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.ProjectActivityIndexRepository;

@Repository(ProjectActivityIndexRepositoryImpl.BEAN_NAME)
public class ProjectActivityIndexRepositoryImpl
		extends BaseRepositoryImpl<Long, OpenHubProjectActivityIndexEntity>
		implements ProjectActivityIndexRepository {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7439883331872434690L;
	
	public static final String BEAN_NAME =  "projectActivityIndexRepository";
	
	public ProjectActivityIndexRepositoryImpl() {
		super(OpenHubProjectActivityIndexEntity.class);
	}

	@Override
	public OpenHubProjectActivityIndexEntity findByValue(Long value) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<OpenHubProjectActivityIndexEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<OpenHubProjectActivityIndexEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<OpenHubProjectActivityIndexEntity> select = criteriaQuery.select(root);

		Predicate namePredicate = criteriaBuilder.equal(root.get("value"), value);
		select.where(namePredicate);

		TypedQuery<OpenHubProjectActivityIndexEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		OpenHubProjectActivityIndexEntity result = null;

		try {
			result = query.getSingleResult();
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {

		}

		return result;
	}


}

package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubLicenseEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.LicenseRepository;

@Repository(LicenseRepositoryImpl.BEAN_NAME)
public class LicenseRepositoryImpl extends BaseRepositoryImpl<Long, OpenHubLicenseEntity>
		implements LicenseRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "licenseRepository";

	public LicenseRepositoryImpl() {
		super(OpenHubLicenseEntity.class);
	}

	@Override
	public OpenHubLicenseEntity findByName(String name) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<OpenHubLicenseEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<OpenHubLicenseEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<OpenHubLicenseEntity> select = criteriaQuery.select(root);

		Predicate namePredicate = criteriaBuilder.equal(root.get("name"), name);
		select.where(namePredicate);

		TypedQuery<OpenHubLicenseEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		OpenHubLicenseEntity result = null;

		try {
			result = query.getSingleResult();
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {

		}

		return result;
	}
}

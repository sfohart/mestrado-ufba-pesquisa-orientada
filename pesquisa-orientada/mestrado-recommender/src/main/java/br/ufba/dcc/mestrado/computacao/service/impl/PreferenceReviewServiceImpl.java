package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceReviewEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.PreferenceReviewRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.PreferenceReviewRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.PreferenceReviewService;

public class PreferenceReviewServiceImpl extends BaseOhLohServiceImpl<Long, PreferenceReviewEntity>
	implements PreferenceReviewService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5344008813432153172L;

	@Autowired
	public PreferenceReviewServiceImpl(
			@Qualifier(PreferenceReviewRepositoryImpl.BEAN_NAME)
			PreferenceReviewRepository repository) {
		super(repository, PreferenceReviewEntity.class);
	}

	@Override
	public Long countAllLastReviewsByProject(OhLohProjectEntity project) {		
		return ((PreferenceReviewRepository) getRepository()).countAllLastReviewsByProject(project);
	}

	@Override
	public List<PreferenceReviewEntity> findAllLastReviewsByProject(
			OhLohProjectEntity project) {		
		return ((PreferenceReviewRepository) getRepository()).findAllLastReviewsByProject(project);
	}

	@Override
	public List<PreferenceReviewEntity> findAllLastReviewsByProject(
			OhLohProjectEntity project, Integer startAt, Integer offset) {
		
		return ((PreferenceReviewRepository) getRepository()).findAllLastReviewsByProject(project, startAt, offset);
	}

	@Override
	public PreferenceReviewEntity findMostHelpfulFavorableReview(
			OhLohProjectEntity project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreferenceReviewEntity findMostHelpfulCriticalReview(
			OhLohProjectEntity project) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

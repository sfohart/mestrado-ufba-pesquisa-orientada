package br.ufba.dcc.mestrado.computacao.service.core.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceReviewEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.PreferenceReviewRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.PreferenceReviewRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.core.base.PreferenceReviewService;
import br.ufba.dcc.mestrado.computacao.service.impl.BaseServiceImpl;

@Service(PreferenceReviewServiceImpl.BEAN_NAME)
public class PreferenceReviewServiceImpl 
		extends BaseServiceImpl<Long, PreferenceReviewEntity>
		implements PreferenceReviewService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8680807756374933838L;
	
	public static final String BEAN_NAME =  "preferenceReviewService";
	
	@Autowired
	public PreferenceReviewServiceImpl(@Qualifier(PreferenceReviewRepositoryImpl.BEAN_NAME) PreferenceReviewRepository repository) {
		super(repository, PreferenceReviewEntity.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Long countAllLastReviewsByProject(Long projectId) {
		return ((PreferenceReviewRepository) getRepository()).countAllLastReviewsByProject(projectId);
	}

	@Override
	@Transactional(readOnly = true)
	public Long countAllLastReviewsByUser(Long userId) {
		return ((PreferenceReviewRepository) getRepository()).countAllLastReviewsByUser(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PreferenceReviewEntity> findAllLastReviewsByProject(
			Long projectId) {
		return ((PreferenceReviewRepository) getRepository()).findAllLastReviewsByProject(projectId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PreferenceReviewEntity> findAllLastReviewsByProject(
			Long projectId, 
			Integer startAt, 
			Integer offset) {
		return ((PreferenceReviewRepository) getRepository()).findAllLastReviewsByProject(projectId, startAt, offset);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PreferenceReviewEntity> findAllLastReviewsByProject(
			Long projectId, 
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt, 
			boolean orderByReviewRanking) {
		
		return ((PreferenceReviewRepository) getRepository()).findAllLastReviewsByProject(
				projectId, 
				startAt, 
				offset,
				orderByRegisteredAt,
				orderByReviewRanking);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PreferenceReviewEntity> findAllLastReviewsByUser(
			Long userId,
			Integer startAt, 
			Integer offset, 
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking) {
		
		return ((PreferenceReviewRepository) getRepository()).findAllLastReviewsByUser(
				userId, 
				startAt, 
				offset,
				orderByRegisteredAt,
				orderByReviewRanking);
	}

}

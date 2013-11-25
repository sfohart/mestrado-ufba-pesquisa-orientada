package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.account.OhLohAccountEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OverallPreferenceRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OverallPreferenceService;

@Service(OverallPreferenceServiceImpl.BEAN_NAME)
public class OverallPreferenceServiceImpl extends BaseOhLohServiceImpl<Long, PreferenceEntity> implements OverallPreferenceService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8096253973073871352L;
	
	public static final String BEAN_NAME =  "overallPreferenceService";
	
	@Autowired
	public OverallPreferenceServiceImpl(@Qualifier("overallPreferenceRepository") OverallPreferenceRepository repository) {
		super(repository, PreferenceEntity.class);
	}

	@Override
	public Long countAllByProject(OhLohProjectEntity project) {
		return ((OverallPreferenceRepository) getRepository()).countAllByProject(project);
	}

	@Override
	public Double averagePreferenceByProject(OhLohProjectEntity project) {
		return ((OverallPreferenceRepository) getRepository()).averagePreferenceByProject(project);
	}
	
	@Override
	public Long countAllByProjectAndAccount(OhLohProjectEntity project, OhLohAccountEntity account) {
		return ((OverallPreferenceRepository) getRepository()).countAllByProjectAndAccount(project, account);
	}
	
	@Override
	public List<PreferenceEntity> findAllByProject(OhLohProjectEntity project) {
		return ((OverallPreferenceRepository) getRepository()).findAllByProject(project);
	}
	
	@Override
	public List<PreferenceEntity> findAllByProjectAndAccount(OhLohProjectEntity project, OhLohAccountEntity account) {
		return ((OverallPreferenceRepository) getRepository()).findAllByProjectAndAccount(project, account);
	}
}

package br.ufba.dcc.mestrado.computacao.repository.base;

import java.io.Serializable;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.account.OhLohAccountEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;

public interface OverallPreferenceRepository extends BaseRepository<Long, PreferenceEntity>, Serializable {

	
	Long countAllByProject(OhLohProjectEntity project);
	
	Double averagePreferenceByProject(OhLohProjectEntity project);
	
	Long countAllByProjectAndAccount(OhLohProjectEntity project, OhLohAccountEntity account);
	
	List<PreferenceEntity> findAllByProject(OhLohProjectEntity project);
	
	List<PreferenceEntity> findAllByProjectAndAccount(OhLohProjectEntity project, OhLohAccountEntity account);
	
}

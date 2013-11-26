package br.ufba.dcc.mestrado.computacao.repository.base;

import java.io.Serializable;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

public interface OverallPreferenceRepository extends BaseRepository<Long, PreferenceEntity>, Serializable {

	
	Long countAllByProject(OhLohProjectEntity project);
	
	Double averagePreferenceByProject(OhLohProjectEntity project);
	
	Long countAllByProjectAndUser(OhLohProjectEntity project, UserEntity user);
	
	List<PreferenceEntity> findAllByProject(OhLohProjectEntity project);
	
	List<PreferenceEntity> findAllByProjectAndUser(OhLohProjectEntity project, UserEntity user);
	
}

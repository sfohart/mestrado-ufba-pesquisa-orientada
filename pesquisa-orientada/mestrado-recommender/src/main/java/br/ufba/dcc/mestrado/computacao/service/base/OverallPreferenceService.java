package br.ufba.dcc.mestrado.computacao.service.base;

import java.io.Serializable;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

public interface OverallPreferenceService extends BaseOhLohService<Long, PreferenceEntity> , Serializable {

	/**
	 * Conta quantos usuários opinaram sobre um determinado projeto
	 * @param project Projeto sobre os quais os usuários opinaram
	 * @return
	 */
	Long countAllByProject(OhLohProjectEntity project);
	
	/**
	 * Retorna a média dos valores gerais das avaliações dos usuários sobre um
	 * determinado projeto
	 * @param project
	 * @return
	 */
	Double averagePreferenceByProject(OhLohProjectEntity project);
	
	/**
	 * Conta quantas avaliações existem para um determinado usuário e projeto.
	 */
	Long countAllByProjectAndUser(OhLohProjectEntity project, UserEntity user);
	
	/**
	 * Retorna todas as avaliações para um dado projeto
	 */
	List<PreferenceEntity> findAllByProject(OhLohProjectEntity project);
	
	/**
	 * Retorna todas as avaliações para um dado projeto e usuário
	 */
	List<PreferenceEntity> findAllByProjectAndUser(OhLohProjectEntity project, UserEntity user);
	
}

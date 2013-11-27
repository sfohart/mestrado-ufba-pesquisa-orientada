package br.ufba.dcc.mestrado.computacao.service.base;

import java.io.Serializable;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

public interface OverallPreferenceService extends BaseOhLohService<Long, PreferenceEntity> , Serializable {

	/**
	 * Conta quantas avalições existem ao todo.
	 * Caso um usuário tenha avaliado o mesmo
	 * projeto mais de uma vez, conta apenas a avaliação mais recente
	 * @return
	 */
	Long countAllLast();
	
	/**
	 * Conta quantas avaliações existem para um dado projeto. Caso um usuário tenha avaliado o mesmo
	 * projeto mais de uma vez, conta apenas a avaliação mais recente
	 * @param project
	 * @return
	 */
	Long countAllLastByProject(OhLohProjectEntity project);
	
	
	/**
	 * 
	 * @param project
	 * @return
	 */
	Long countAllLastReviewsByProject(OhLohProjectEntity project);
	
	
	/**
	 * Calcula o valor médio das avaliações gerais para cada projeto.
	 * Conta apenas a avaliação mais recente de cada usuário 
	 * @param project
	 * @return
	 */
	Double averagePreferenceByProject(OhLohProjectEntity project);
	
	/**
	 * 
	 * @param project
	 * @param user
	 * @return
	 */
	Long countAllByProjectAndUser(OhLohProjectEntity project, UserEntity user);
	
	/**
	 * Retorna todos os últimos registros de {@link PreferenceEntity}, com base no campo <code>registeredAt</code>,
	 * para cada projeto
	 * 
	 * @param project Projeto usado como filtro
	 * @return
	 */
	List<PreferenceEntity> findAllLastByProject(OhLohProjectEntity project);
	
	/**
	 * Retorna todos os últimos registros de {@link PreferenceEntity}, com base no campo <code>registeredAt</code>,
	 * para cada projeto. Utiliza parâmetros para paginação.
	 *  
	 * @param project
	 * @param startAt
	 * @param offset
	 * @return
	 */
	List<PreferenceEntity> findAllLastByProject(OhLohProjectEntity project, Integer startAt, Integer offset);
	
	
	/**
	 * 
	 * @param project
	 * @return
	 */
	List<PreferenceEntity> findAllLastReviewsByProject(OhLohProjectEntity project);
	
	/**
	 * 
	 * @param project
	 * @param startAt
	 * @param offset
	 * @return
	 */
	List<PreferenceEntity> findAllLastReviewsByProject(OhLohProjectEntity project, Integer startAt, Integer offset);
	
	
	/**
	 * Retorna todas as avaliações feitas por um usuário para um determinado projeto
	 * 
	 * @param project
	 * @param user
	 * @return
	 */
	List<PreferenceEntity> findAllByProjectAndUser(OhLohProjectEntity project, UserEntity user);
	
}

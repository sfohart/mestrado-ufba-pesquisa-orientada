package br.ufba.dcc.mestrado.computacao.repository.base;

import java.io.Serializable;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;

/**
 * 
 * @author leandro.ferreira
 *
 */
public interface OverallPreferenceRepository extends BaseRepository<Long, PreferenceEntity>, Serializable {

	/**
	 * Conta quantas avalições existem ao todo.
	 * Caso um usuário tenha avaliado o mesmo
	 * projeto mais de uma vez, conta apenas a avaliação mais recente
	 * @return
	 */
	Long countAllLast();
	
	/**
	 * 
	 * Conta quantas avaliações existem para um dado projeto. Caso um usuário tenha avaliado o mesmo
	 * projeto mais de uma vez, conta apenas a avaliação mais recente
	 * 
	 * @param projectId
	 * @return
	 */
	Long countAllLastByProject(Long projectId);
	
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	Long countAllLastReviewsByProject(Long projectId);
	
	
	/**
	 * 
	 * Calcula o valor médio das avaliações gerais para cada projeto.
	 * Conta apenas a avaliação mais recente de cada usuário  
	 * 
	 * @param projectId
	 * @return
	 */
	Double averagePreferenceByProject(Long projectId);
	
	/**
	 * 
	 * @param projectId
	 * @param userId
	 * @return
	 */
	Long countAllByProjectAndUser(Long projectId, Long userId);
	
	/**
	 * Retorna todos os últimos registros de {@link PreferenceEntity}, com base no campo <code>registeredAt</code>,
	 * para cada projeto
	 * @param projectId
	 * @return
	 */
	List<PreferenceEntity> findAllLastByProject(Long projectId);
	
	/**
	 * Retorna todos os últimos registros de {@link PreferenceEntity}, com base no campo <code>registeredAt</code>,
	 * para cada projeto. Utiliza parâmetros para paginação.
	 * @param projectId
	 * @param startAt
	 * @param offset
	 * @return
	 */
	List<PreferenceEntity> findAllLastByProject(Long projectId, Integer startAt, Integer offset);
	
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	List<PreferenceEntity> findAllLastReviewsByProject(Long projectId);
	
	/**
	 * 
	 * @param projectId
	 * @param startAt
	 * @param offset
	 * @return
	 */
	List<PreferenceEntity> findAllLastReviewsByProject(Long projectId, Integer startAt, Integer offset);
	
	
	/**
	 * 
	 * @param projectId
	 * @param startAt
	 * @param offset
	 * @param orderByRegisteredAt
	 * @param orderByUsefullCount
	 * @return
	 */
	List<PreferenceEntity> findAllLastReviewsByProject(
			Long projectId, 
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking);
	
	/**
	 * 
	 * @param projectId
	 * @param userId
	 * @return
	 */
	List<PreferenceEntity> findAllByProjectAndUser(Long projectId, Long userId);
	
	
	/**
	 * 
	 * @param projectId
	 * @param userId
	 * @return
	 */
	PreferenceEntity findLastByProjectAndUser(Long projectId, Long userId);
	
}

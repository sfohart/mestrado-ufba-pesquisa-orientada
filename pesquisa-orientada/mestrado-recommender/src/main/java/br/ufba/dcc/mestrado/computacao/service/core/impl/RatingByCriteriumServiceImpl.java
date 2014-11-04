package br.ufba.dcc.mestrado.computacao.service.core.impl;

import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.repository.base.RatingByCriteriumRepository;
import br.ufba.dcc.mestrado.computacao.service.core.base.RatingByCriteriumService;

@Service(RatingByCriteriumServiceImpl.BEAN_NAME)
public class RatingByCriteriumServiceImpl implements RatingByCriteriumService {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1244321149899308620L;
	
	public static final String BEAN_NAME =  "ratingByCriteriumService";
	
	@Autowired
	private RatingByCriteriumRepository ratingByCriteriumRepository;

	public RatingByCriteriumRepository getRatingByCriteriumRepository() {
		return ratingByCriteriumRepository;
	}

	public void setRatingByCriteriumRepository(
			RatingByCriteriumRepository ratingByCriteriumRepository) {
		this.ratingByCriteriumRepository = ratingByCriteriumRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Double averagePreferenceByItemAndCriterium(
			Long itemId,
			Long criteriumId) {
		return getRatingByCriteriumRepository().averagePreferenceByItemAndCriterium(itemId, criteriumId);
	}

	@Override
	@Transactional(readOnly = true)
	public Map<ImmutablePair<Long, Long>, Double> findAllLastPreferenceByCriterium(
			Long criteriumId) {
		return getRatingByCriteriumRepository().findAllLastPreferenceByCriterium(criteriumId);
	}

	@Override
	@Transactional(readOnly = true)
	public Map<ImmutablePair<Long, Long>, Double> findAllLastPreferenceByCriteriumAndItem(
			Long criteriumId, 
			Long itemId) {
		return getRatingByCriteriumRepository().findAllLastPreferenceByCriteriumAndItem(criteriumId, itemId);
	}

	@Override
	@Transactional(readOnly = true)
	public Long countAllLastByCriterium(Long criteriumId) {
		return getRatingByCriteriumRepository().countAllLastByCriterium(criteriumId);
	}

}

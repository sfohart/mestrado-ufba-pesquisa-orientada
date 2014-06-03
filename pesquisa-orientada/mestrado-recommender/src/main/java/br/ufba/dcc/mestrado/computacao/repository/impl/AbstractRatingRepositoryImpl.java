package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Tuple;

import org.apache.commons.lang3.tuple.ImmutablePair;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.recommender.preference.PreferenceEntity;

public abstract class AbstractRatingRepositoryImpl extends BaseRepositoryImpl<Long, PreferenceEntity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8216148577482807135L;

	public AbstractRatingRepositoryImpl() {
		super(PreferenceEntity.class);
	}
	
	/**
	 * 
	 * @param tupleList Tupla do tipo <userId,itemId,value>
	 * @return
	 */
	protected Map<ImmutablePair<Long, Long>, Double> createImmutablePairMap(
			List<Tuple> tupleList) {

		Map<ImmutablePair<Long, Long>, Double> resultMap = new TreeMap<>();
		
		if (tupleList != null && ! tupleList.isEmpty()) {
			for (Tuple tuple : tupleList) {
				ImmutablePair<Long, Long> userItemPair = new ImmutablePair<Long, Long>(
						tuple.get(0, Long.class), 
						tuple.get(1, Long.class));
				
				Double value = tuple.get(2, Double.class);
				
				resultMap.put(userItemPair, value);
			}
		}
		
		return resultMap;
	}
	
}

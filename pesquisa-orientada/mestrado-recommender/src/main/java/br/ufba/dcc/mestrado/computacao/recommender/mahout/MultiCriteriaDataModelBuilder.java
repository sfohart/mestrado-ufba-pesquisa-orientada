package br.ufba.dcc.mestrado.computacao.recommender.mahout;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;

public interface MultiCriteriaDataModelBuilder {
	
	/**
	 * 
	 * @param trainingData mapeia cada critério do tipo {@link Long} em um {@link FastByIDMap}
	 * contendo {@link PreferenceArray}s
	 * 
	 * @return um mapa de critérios do tipo {@link Long} para seus respectivos {@link DataModel}
	 */
	FastByIDMap<DataModel> buildDataModel(FastByIDMap<FastByIDMap<PreferenceArray>> trainingData);
	
}

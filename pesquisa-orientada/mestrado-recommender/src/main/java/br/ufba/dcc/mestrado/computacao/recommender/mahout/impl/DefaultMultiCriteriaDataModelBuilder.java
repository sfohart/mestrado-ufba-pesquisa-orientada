package br.ufba.dcc.mestrado.computacao.recommender.mahout.impl;

import java.util.Map;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;

import br.ufba.dcc.mestrado.computacao.recommender.mahout.MultiCriteriaDataModelBuilder;

public class DefaultMultiCriteriaDataModelBuilder implements MultiCriteriaDataModelBuilder {

	@Override
	public FastByIDMap<DataModel> buildDataModel(FastByIDMap<FastByIDMap<PreferenceArray>> trainingData) {
		FastByIDMap<DataModel> dataModelMap = new FastByIDMap<>();
		
		//para cada critério, um mapa com arrays de preferência
		for (Map.Entry<Long,FastByIDMap<PreferenceArray>> criteriaEntry : trainingData.entrySet()) {
			DataModel dataModel = new GenericDataModel(criteriaEntry.getValue());
			dataModelMap.put(criteriaEntry.getKey(), dataModel);
		}
		
		return dataModelMap;
	}

}

package br.ufba.dcc.mestrado.computacao.service.mahout.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.BooleanPreference;
import org.apache.mahout.cf.taste.impl.model.BooleanUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.service.mahout.base.MahoutDataModelService;

@Service(MahoutDataModelServiceImpl.BEAN_NAME)
public class MahoutDataModelServiceImpl implements MahoutDataModelService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2037343704858122530L;
	
	public static final String BEAN_NAME =  "mahoutDataModelService";

	@Override
	public GenericBooleanPrefDataModel buildBooleanDataModel(
			List<BooleanPreference> preferenceList) {
		
		Map<Long, List<BooleanPreference>> userPreferenceMap = new HashMap<>();
		
		if (preferenceList != null) {
			for (BooleanPreference pref : preferenceList) {
				List<BooleanPreference> preferenceListByUser = userPreferenceMap.get(pref.getUserID());
				
				if (preferenceListByUser == null) {
					preferenceListByUser = new ArrayList<>();
				}
				
				preferenceListByUser.add(pref);
				
				userPreferenceMap.put(pref.getUserID(), preferenceListByUser);
			}
		}
		
		FastByIDMap<PreferenceArray> userData = new FastByIDMap<>(userPreferenceMap.size());
		for (Map.Entry<Long, List<BooleanPreference>> entry : userPreferenceMap.entrySet()) {
			PreferenceArray array = new BooleanUserPreferenceArray(entry.getValue());
			userData.put(entry.getKey(), array);
		}
		
		GenericBooleanPrefDataModel dataModel = new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(userData));
		return dataModel;
		
	}

}

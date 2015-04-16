package br.ufba.dcc.mestrado.computacao.service.mahout.base;

import java.io.Serializable;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.BooleanPreference;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.model.Preference;

public interface MahoutDataModelService extends Serializable {
	
	GenericBooleanPrefDataModel buildBooleanDataModel(List<BooleanPreference> preferenceList);
	
	GenericDataModel buildDataModelByUser(List<Preference> preferenceList);

}

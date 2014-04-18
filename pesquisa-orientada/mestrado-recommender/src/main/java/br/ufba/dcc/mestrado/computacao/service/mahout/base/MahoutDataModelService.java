package br.ufba.dcc.mestrado.computacao.service.mahout.base;

import java.io.Serializable;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.BooleanPreference;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;

public interface MahoutDataModelService extends Serializable {
	
	GenericBooleanPrefDataModel buildBooleanDataModel(List<BooleanPreference> preferenceList);

}

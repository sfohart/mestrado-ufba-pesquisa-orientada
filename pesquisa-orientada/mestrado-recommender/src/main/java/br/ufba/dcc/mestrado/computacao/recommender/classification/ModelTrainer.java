package br.ufba.dcc.mestrado.computacao.recommender.classification;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ModelTrainer {

	void train() throws Exception;
	void storePredictionModel() throws IOException;
	void loadPreditionModel() throws FileNotFoundException, IOException;
	

}
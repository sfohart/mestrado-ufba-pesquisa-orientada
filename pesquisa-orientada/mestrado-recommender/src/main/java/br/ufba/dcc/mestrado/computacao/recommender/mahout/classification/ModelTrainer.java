package br.ufba.dcc.mestrado.computacao.recommender.mahout.classification;

import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Map;
import org.apache.commons.lang3.tuple.ImmutablePair;

public interface ModelTrainer {

	void train() throws IOException;
	void storePredictionModel() throws IOException;
	void loadPreditionModel() throws FileNotFoundException, IOException;
	

}
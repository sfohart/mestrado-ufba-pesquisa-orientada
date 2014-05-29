package br.ufba.dcc.mestrado.computacao.recommender.mahout.classification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.mahout.classifier.sgd.L2;
import org.apache.mahout.classifier.sgd.AdaptiveLogisticRegression;
import org.apache.mahout.common.RandomUtils;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RatingByCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RecommenderCriteriumService;
import br.ufba.dcc.mestrado.computacao.recommender.mahout.classification.ModelTrainer;
import br.ufba.dcc.mestrado.computacao.spring.RecommenderAppConfig;

import org.apache.mahout.classifier.sgd.CrossFoldLearner;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import org.apache.mahout.classifier.sgd.ModelSerializer;

@Component
public class LogisticRegressionModelTrainer implements ModelTrainer {

	public static Logger logger = Logger.getLogger(LogisticRegressionModelTrainer.class.getName());
	
	@Autowired
	private RatingByCriteriumService ratingByCriteriumService;
	
	@Autowired
	private RecommenderCriteriumService recommenderCriteriumService;
	
	@Autowired
	private OverallRatingService overallRatingService;
	
	private AdaptiveLogisticRegression adaptiveLogisticRegression;
	private CrossFoldLearner crossFoldLearner;
	
	@Value("${prediction.model.path}")
	private String modelPath;
	
	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}
	
	public String getModelPath() {
		return modelPath;
	}
	
	@Autowired
	public LogisticRegressionModelTrainer(
			RatingByCriteriumService ratingByCriteriumService,
			RecommenderCriteriumService recommenderCriteriumService,
			OverallRatingService overallRatingService) {
		super();
		this.ratingByCriteriumService = ratingByCriteriumService;
		this.recommenderCriteriumService = recommenderCriteriumService;
		this.overallRatingService = overallRatingService;
	}
	
	public void storePredictionModel() throws IOException {		
		if (crossFoldLearner != null) {
			ModelSerializer.writeBinary(modelPath, crossFoldLearner);
		}
	}
	
	public void loadPreditionModel() throws FileNotFoundException, IOException {
		InputStream inputStream = new FileInputStream(modelPath);
		crossFoldLearner = ModelSerializer.readBinary(inputStream,CrossFoldLearner.class);
		inputStream.close();
	}
	
	public Double estimateAveragePreference(Long userId, Long itemId) {
		Double estimatedAverageValue = null;
		
		return estimatedAverageValue;
	}

	/**
	 *	Treina um modelo de regressão logístico utilizando programação linear para obter o melhor modelo preditivo possível
	 *	para a função de agregação
	 *
	 *	O vetor de entrada para o algoritmo de regressão é construído do seguinte modo:
	 *	[userId,valorCriterio1,valorCriterio2,...,valorCriterioN,itemId]
	 * 
	 *	Temos 10 critérios
	 */
	public void train() throws IOException {		
		List<Vector> vectorList = new ArrayList<>();
		
		List<RecommenderCriteriumEntity> criteriumList = recommenderCriteriumService.findAll();
		
		Map<ImmutablePair<Long, Long>, DenseVector> userMap = new HashMap<>();
		
		//criando vetores de entrada para o algoritmo de regressão
		System.out.println("Criando vetores de entrada com base em " + criteriumList.size() + " criterios de avaliação.");
		if (criteriumList != null) {
			for (RecommenderCriteriumEntity criterium : criteriumList) {
				Map<ImmutablePair<Long, Long>, Double> criteriumMap = ratingByCriteriumService.findAllLastPreferenceByCriterium(criterium.getId());
				System.out.println(criteriumMap.size() + " valores de preferencia encontrados para o criterio " + criterium.getName());
				if (criteriumMap != null) {
					for (Map.Entry<ImmutablePair<Long, Long>, Double> entry : criteriumMap.entrySet()) {
						DenseVector vector = userMap.get(entry.getKey());
						if (vector == null ){
							vector = new DenseVector(12);
							vector.set(0, entry.getKey().getLeft());
							vector.set(11, entry.getKey().getRight());
						}
						
						vector.set(criterium.getId().intValue(), entry.getValue());
						userMap.put(entry.getKey(), vector);
					}
				}
			}
		}
		
		List<Double> target = new ArrayList<>();
		List<DenseVector> data = new ArrayList<>(userMap.values());
		
		//coletando o valor geral para cada par [userId,itemId]
		System.out.println("Criando alvos baseado em " + userMap.size() + " entradas");
		for (Map.Entry<ImmutablePair<Long, Long>, DenseVector> entry : userMap.entrySet()) {
			PreferenceEntity preference = overallRatingService
					.findLastOverallPreferenceByUserAndItem(
							entry.getKey().getLeft(), 
							entry.getKey().getRight());
			
			target.add(preference.getValue());
		}
		
		//criando uma lista de id's para cada tupla [entrada,saida], para fins de treinamento
		System.out.println("Criando conjunto de treinamento e de teste");
		List<Integer> orderList = new ArrayList<>();
		for (int i = 0; i < userMap.size(); i++) {
			orderList.add(i);
		}
		
		//embaralhando lista de tuplas
		Random random = RandomUtils.getRandom();
		Collections.shuffle(orderList, random);
		
		//dividindo a lista de tuplas entre conjunto de treinamento e conjunto de teste
		List<Integer> train = orderList.subList(0, Math.round(orderList.size() * (float) 0.7));
		List<Integer> test = orderList.subList(Math.round(orderList.size() * (float) 0.7), orderList.size());
		
		System.out.println("Treinamento: " + train.size() + " | Teste: " + test.size());
		
		//treinando modelo de predição
		System.out.println("Treinando modelo com " + train.size() + " entradas.");
		this.adaptiveLogisticRegression = new AdaptiveLogisticRegression(6, 12, new L2(1));
		for (int pass = 0; pass < 30; pass++ ){
			Collections.shuffle(train, random);
			for (int k : train) {				
				adaptiveLogisticRegression.train(target.get(k).intValue(), data.get(k));				
			}
			
			System.out.println("AOC: " + String.valueOf(adaptiveLogisticRegression.auc()));
		}
		
		//obtendo melhor modelo preditivo
		System.out.println("Obtendo o melhor modelo preditivo");
		this.crossFoldLearner = adaptiveLogisticRegression.getBest().getPayload().getLearner();
		
		System.out.println("Salvando o modelo preditivo obtido");
		storePredictionModel();
	}
	
	public static void main(String[] args) throws IOException {		
		ApplicationContext context = new AnnotationConfigApplicationContext(RecommenderAppConfig.class);

		LogisticRegressionModelTrainer main = context.getBean(LogisticRegressionModelTrainer.class);

		if (main != null) {
			main.train();
			System.exit(0);
		}
	}

}

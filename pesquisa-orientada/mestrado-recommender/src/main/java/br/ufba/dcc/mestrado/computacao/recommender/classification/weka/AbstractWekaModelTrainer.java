
package br.ufba.dcc.mestrado.computacao.recommender.classification.weka;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.recommender.classification.AbstractModelTrainer;
import br.ufba.dcc.mestrado.computacao.service.core.base.OverallRatingService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RatingByCriteriumService;
import br.ufba.dcc.mestrado.computacao.service.core.base.RecommenderCriteriumService;

public abstract class AbstractWekaModelTrainer extends AbstractModelTrainer {

	private Classifier classifier;
	private boolean useNumericToNominalFilter;
	
	
	public AbstractWekaModelTrainer(
			RatingByCriteriumService ratingByCriteriumService,
			RecommenderCriteriumService recommenderCriteriumService,
			OverallRatingService overallRatingService) {
		
		super(ratingByCriteriumService, recommenderCriteriumService, overallRatingService);
	}
	
	protected abstract Classifier initializeClassifier() throws Exception;
	
	protected Classifier getClassifier() {
		return classifier;
	}

	protected void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}

	
	protected boolean isUseNumericToNominalFilter() {
		return useNumericToNominalFilter;
	}

	protected void setUseNumericToNominalFilter(boolean useNumericToNominalFilter) {
		this.useNumericToNominalFilter = useNumericToNominalFilter;
	}

	protected Instances createDataSet() throws Exception {
		
		FastVector attributeList = new FastVector();
		
		Attribute userAttribute = new Attribute("user");
		attributeList.addElement(userAttribute);
		
		Attribute projectAttribute = new Attribute("project");
		attributeList.addElement(projectAttribute);
		
		Attribute overallAttribute = new Attribute("overall");
		attributeList.addElement(overallAttribute);
				
		Map<RecommenderCriteriumEntity, Attribute> criteriumAttributeMap = new LinkedHashMap<>();
		
		List<RecommenderCriteriumEntity> criteriumList = recommenderCriteriumService.findAll();
		
		Collections.sort(criteriumList, new Comparator<RecommenderCriteriumEntity>() {

			@Override
			public int compare(RecommenderCriteriumEntity criterium,
					RecommenderCriteriumEntity other) {
				
				return criterium.getId().compareTo(other.getId());
			}
		});
		
		for (RecommenderCriteriumEntity criterium : criteriumList) {
			Attribute criteriumAttribute = new Attribute(
					String.format("criterium%d", criterium.getId())
				);
			attributeList.addElement(criteriumAttribute);
			
			criteriumAttributeMap.put(criterium, criteriumAttribute);
		}
		
		Instances dataset = new Instances("overallPredictionModel", attributeList, 100);
		
		Map<ImmutablePair<Long, Long>, Instance> instanceMap = new LinkedHashMap<>();
				
		if (criteriumList != null) {
			
			System.out.println("Criando vetores de entrada com base em " + criteriumList.size() + " criterios de avalia��o.");
			
			for (RecommenderCriteriumEntity criterium : criteriumList) {
				Map<ImmutablePair<Long, Long>, Double> criteriumMap = ratingByCriteriumService.findAllLastPreferenceByCriterium(criterium.getId());
				System.out.println(criteriumMap.size() + " valores de preferencia encontrados para o criterio " + criterium.getName());
				
				if (criteriumMap != null) {
					for (Map.Entry<ImmutablePair<Long, Long>, Double> entry : criteriumMap.entrySet()) {
						
						Instance instance = instanceMap.get(entry.getKey());
						if (instance == null) {
							instance = new Instance(3 + criteriumList.size());
							
							instance.setValue(userAttribute, entry.getKey().getLeft());
							instance.setValue(projectAttribute, entry.getKey().getRight());
							
							instanceMap.put(entry.getKey(), instance);							
						}
						
						Attribute criteriumAttribute = criteriumAttributeMap.get(criterium);
						instance.setValue(criteriumAttribute, entry.getValue());
						
						if (instance.isMissing(overallAttribute)) {
							PreferenceEntity preference = overallRatingService
									.findLastOverallPreferenceByUserAndItem(
											entry.getKey().getLeft(), 
											entry.getKey().getRight());
							
							instance.setValue(overallAttribute, preference.getValue());
						}
						
					}
				}
			}
		}
		
		for (Map.Entry<ImmutablePair<Long, Long>, Instance> entry : instanceMap.entrySet()) {
			dataset.add(entry.getValue());
		}
		
		//quero criar um modelo preditivo para estimar este atributo.
		dataset.setClass(overallAttribute);
		
		return dataset;
	}
	
	protected Filter configureNumericToNominalFilter(Instances dataset) throws Exception {
		//a avalia��o geral do usu�rio para o item deve ser um atributo nominal para usar os algoritmos de classifica��o
		NumericToNominal attributeFilter = new NumericToNominal();
		
		String[] options = new String[2];
		options[0] = "-R";
		options[1] = "first-last";
		
		attributeFilter.setOptions(options);
		attributeFilter.setInputFormat(dataset);
		
		return attributeFilter;
	}
	
	public void train() throws Exception {
		System.out.println("------------------------------------------------------------------------");
		System.out.println("Criando dataset");
		System.out.println("------------------------------------------------------------------------");
		Instances dataset = createDataSet();
		
				
		//a avalia��o geral do usu�rio para o item deve ser um atributo nominal para usar os algoritmos de classifica��o
		if (isUseNumericToNominalFilter()) {
			Filter filter = configureNumericToNominalFilter(dataset);		
			dataset = Filter.useFilter(dataset, filter);
		}
		
		setClassifier(initializeClassifier());
		
		System.out.println("------------------------------------------------------------------------");
		System.out.println("Avaliando classificador");
		System.out.println("------------------------------------------------------------------------");
		evaluateClassifier(dataset);
		
		System.out.println("------------------------------------------------------------------------");
		System.out.println("Salvando modelo");
		System.out.println("------------------------------------------------------------------------");
		getClassifier().buildClassifier(dataset);
		storePredictionModel();
	}

	public void evaluateClassifier(Instances dataset) throws Exception {
		Evaluation evaluation = new Evaluation(dataset);
		
		System.out.println(getClassifier().getClass().getName());
		
		Random random = new Random(1);  
		int numFolds = 10;
		
		evaluation.crossValidateModel(getClassifier(), dataset, numFolds, random);
		
		String summary = evaluation.toSummaryString(true);
		
		double coveragePercent = evaluation.correct() / (evaluation.correct() + evaluation.incorrect() );
		String coverage = String.format("Coverage of cases (0.95 level): %f %%", coveragePercent * 100);
		
		summary += "\n\n" + coverage + "\n\n";
		
		System.out.println(summary);
		
		if (isUseNumericToNominalFilter()){
			String details = evaluation.toClassDetailsString();
			System.out.println(details);
		}
		
		
		
		
		
		if (isUseNumericToNominalFilter()){
			String confusionMatrix = evaluation.toMatrixString();
			System.out.println(confusionMatrix);
		}
	}

	@Override
	public void storePredictionModel() throws IOException {
		try {
			SerializationHelper.write(getModelPath(), getClassifier());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	@Override
	public void loadPreditionModel() throws FileNotFoundException, IOException {
		try {
			this.classifier = (Classifier) SerializationHelper.read(getModelPath());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
	
	

}


package br.ufba.dcc.mestrado.computacao.recommender.eval;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.mahout.cf.taste.common.NoSuchItemException;
import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.FullRunningAverageAndStdDev;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.common.RunningAverageAndStdDev;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.common.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.ufba.dcc.mestrado.computacao.exception.InvalidMultiCriteriaDataModelException;
import br.ufba.dcc.mestrado.computacao.recommender.MultiCriteriaDataModelBuilder;
import br.ufba.dcc.mestrado.computacao.recommender.MultiCriteriaRecommender;
import br.ufba.dcc.mestrado.computacao.recommender.MultiCriteriaRecommenderBuilder;
import br.ufba.dcc.mestrado.computacao.recommender.PreferenceAggregatorStrategy;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public abstract class AbstractDifferenceMultiCriteriaRecommenderEvaluator implements
		MultiCriteriaRecommenderEvaluator {

	private static final Logger log = LoggerFactory
			.getLogger(AbstractDifferenceMultiCriteriaRecommenderEvaluator.class);

	private final Random random;
	private float maxPreference;
	private float minPreference;
	private PreferenceAggregatorStrategy preferenceAggregatorStrategy;

	public AbstractDifferenceMultiCriteriaRecommenderEvaluator(PreferenceAggregatorStrategy preferenceAggregatorStrategy) {
		this.random = RandomUtils.getRandom();
		this.maxPreference = Float.NaN;
		this.minPreference = Float.NaN;
		this.preferenceAggregatorStrategy = preferenceAggregatorStrategy;
	}

	public float getMaxPreference() {
		return maxPreference;
	}

	public void setMaxPreference(float maxPreference) {
		this.maxPreference = maxPreference;
	}

	public float getMinPreference() {
		return minPreference;
	}

	public void setMinPreference(float minPreference) {
		this.minPreference = minPreference;
	}

	/**
	 * Valida se o numero de usuários é o mesmo em todos os {@link DataModel}.
	 * 
	 * 
	 * @param dataModelMap Mapeia cada critério (representado por um {@link Long} a um {@link DataModel} 
	 * @return O número de usuários contido em cada {@link DataModel} de dataModelMap
	 * @throws TasteException
	 */
	protected Integer validateNumUsers(FastByIDMap<DataModel> dataModelMap) throws TasteException {
		Integer numUsers = 0;
		for (DataModel dataModel : dataModelMap.values()) {
			if (numUsers == 0) {
				numUsers = dataModel.getNumUsers();
			} else if (numUsers != dataModel.getNumUsers()) {
				throw new InvalidMultiCriteriaDataModelException();
			}
		}
				
		//TODO: checar porque está dando erro aqui.
		Preconditions.checkArgument(numUsers != 0, "Invalid DataModel map{}", dataModelMap);
		
		return numUsers;
	}
	
	/**
	 * 
	 * @param dataModelMap Mapeia cada critério (representado por um {@link Long} a um {@link DataModel}
	 * @return um {@link LongPrimitiveIterator} para o primeiro {@link DataModel} de dataModelMap
	 * @throws TasteException
	 */
	private LongPrimitiveIterator getUserIDs(FastByIDMap<DataModel> dataModelMap) throws TasteException {
		LongPrimitiveIterator iterator = null;
		
		Preconditions.checkArgument(dataModelMap.size() > 0);
		
		for (Map.Entry<Long, DataModel> entry : dataModelMap.entrySet()) {
			iterator = entry.getValue().getUserIDs();
			break;
		}
		
		return iterator;
	}
	
	@Override
	public double evaluate(
			MultiCriteriaRecommenderBuilder multiCriteriaRecommenderBuilder,
			MultiCriteriaDataModelBuilder multiCriteriaDataModelBuilder,
			FastByIDMap<DataModel> dataModelMap, 
			FastByIDMap<Float> criteriaWeightMap,
			double trainingPercentage,
			double evaluationPercentage) throws TasteException {

		Preconditions.checkNotNull(multiCriteriaRecommenderBuilder);
		Preconditions.checkNotNull(multiCriteriaDataModelBuilder);
		Preconditions.checkArgument(
				trainingPercentage >= 0.0
				&& trainingPercentage <= 1.0, 
				"Invalid trainingPercentage: " + trainingPercentage);
		Preconditions.checkArgument(
				evaluationPercentage >= 0.0
				&& evaluationPercentage <= 1.0,
				"Invalid evaluationPercentage: " + evaluationPercentage);

		log.info("Beginning evaluation using {} of {}", trainingPercentage,
				dataModelMap);

		int numUsers = validateNumUsers(dataModelMap);
		
		FastByIDMap<FastByIDMap<PreferenceArray>> trainingPrefs = new FastByIDMap<FastByIDMap<PreferenceArray>>(
	        1 + (int) (evaluationPercentage * numUsers));
		FastByIDMap<FastByIDMap<PreferenceArray>> testPrefs = new FastByIDMap<FastByIDMap<PreferenceArray>>(
	        1 + (int) (evaluationPercentage * numUsers));

		LongPrimitiveIterator it = getUserIDs(dataModelMap);
		
		while (it.hasNext()) {
			long userID = it.nextLong();
			if (random.nextDouble() < evaluationPercentage) {
				splitOneUsersPrefs(trainingPercentage, trainingPrefs, testPrefs, userID, dataModelMap);
			}
		}
	    
	    FastByIDMap<DataModel> trainingModel = multiCriteriaDataModelBuilder.buildDataModel(trainingPrefs);
	    
	    MultiCriteriaRecommender recommender = multiCriteriaRecommenderBuilder.buildRecommender(trainingModel, criteriaWeightMap);
	    
	    double result = getEvaluation(testPrefs, recommender);
	    log.info("Evaluation result: {}", result);
	    return result;
	    
	}
	
	/**
	 * 
	 * @param trainingPercentage
	 * @param trainingPrefs
	 * @param testPrefs
	 * @param userID
	 * @param dataModelMap
	 * @throws TasteException
	 */
	private void splitOneUsersPrefs(double trainingPercentage,
			FastByIDMap<FastByIDMap<PreferenceArray>> trainingPrefs,
			FastByIDMap<FastByIDMap<PreferenceArray>> testPrefs,
            long userID,
            FastByIDMap<DataModel> dataModelMap) throws TasteException {
		
		FastByIDMap<List<Preference>> oneUserTrainingPrefs = new FastByIDMap<>();
		FastByIDMap<List<Preference>> oneUserTestPrefs = new FastByIDMap<>();
		
		for (Map.Entry<Long,DataModel> entry : dataModelMap.entrySet()) {
			
			DataModel dataModel = entry.getValue();
			PreferenceArray prefs = dataModel.getPreferencesFromUser(userID);
			
			int size = prefs.length();
			for (int i = 0; i < size; i++) {
				Preference newPref = new GenericPreference(userID, prefs.getItemID(i), prefs.getValue(i));
				
				//dividindo as preferências para determinado critério entre conjunto de treinamento e de testes.
				if (random.nextDouble() < trainingPercentage) {
					List<Preference> prefList = oneUserTrainingPrefs.get(entry.getKey());
					
					if (prefList == null) {
						prefList = Lists.newArrayListWithCapacity(3);
					}
					
					prefList.add(newPref);
					
					oneUserTrainingPrefs.put(entry.getKey(), prefList);
				} else {
					List<Preference> prefList = oneUserTestPrefs.get(entry.getKey());
					
					if (prefList == null) {
						prefList = Lists.newArrayListWithCapacity(3);
					}
					
					prefList.add(newPref);
					
					oneUserTestPrefs.put(entry.getKey(), prefList);
				}
			}
			
			//mapeando cada critério, de cada usuário, para um conjunto de treino
			if (oneUserTrainingPrefs.get(entry.getKey()) != null) {
				PreferenceArray prefTrainingArray = new GenericUserPreferenceArray(oneUserTrainingPrefs.get(entry.getKey()));
				
				FastByIDMap<PreferenceArray> criteriumTrainingMap = trainingPrefs.get(userID);
				if (criteriumTrainingMap == null) {
					criteriumTrainingMap = new FastByIDMap<>();
				}
				
				criteriumTrainingMap.put(entry.getKey(), prefTrainingArray);
				trainingPrefs.put(userID, criteriumTrainingMap);
				
				//mapeando cada critério, de cada usuário, para um conjunto de testes
				if (oneUserTestPrefs.get(entry.getKey()) != null) {
					PreferenceArray prefTestingArray = new GenericUserPreferenceArray(oneUserTestPrefs.get(entry.getKey()));
					
					FastByIDMap<PreferenceArray> criteriumTestingMap = trainingPrefs.get(userID);
					if (criteriumTestingMap == null) {
						criteriumTestingMap = new FastByIDMap<>();
					}
					
					criteriumTestingMap.put(entry.getKey(), prefTestingArray);
					testPrefs.put(userID, criteriumTestingMap);
				}
			}
		}
	}
	
	/**
	 * Apenas trunca o valor estimado para que ele fique entre
	 * a preferência máxima e a preferência mínima
	 * 
	 * @param estimate
	 * @return
	 */
	private float capEstimatedPreference(float estimate) {
		if (estimate > maxPreference) {
			return maxPreference;
		}
	
		if (estimate < minPreference) {
			return minPreference;
		}
		
		return estimate;
	}
	
	/**
	 * 
	 * @param testPrefs
	 * @param recommender
	 * @return
	 * @throws TasteException
	 */
	private double getEvaluation(FastByIDMap<FastByIDMap<PreferenceArray>> testPrefs, MultiCriteriaRecommender recommender)
		    throws TasteException {
		
		//método abstrato
		reset();
		
		Collection<Callable<Void>> estimateCallables = Lists.newArrayList();
	    AtomicInteger noEstimateCounter = new AtomicInteger();
	    
	    for (Map.Entry<Long, FastByIDMap<PreferenceArray>> entry : testPrefs.entrySet()) {
	    	Callable<Void> callable = new PreferenceEstimateCallable(recommender, entry.getKey(), entry.getValue(), noEstimateCounter);
	    	estimateCallables.add(callable);
	    }
	    
	    //TODO: a implementar
	    
	    log.info("Beginning evaluation of {} users", estimateCallables.size());
	    RunningAverageAndStdDev timing = new FullRunningAverageAndStdDev();
	    execute(estimateCallables, noEstimateCounter, timing);
		
	    //método abstrato
		return computeFinalEvaluation();
	}
	
	/**
	 * Executa cada {@link Callable} dentro de um {@link Future} e processa seus resultados
	 * 
	 * @param callables
	 * @param noEstimateCounter
	 * @param timing
	 * @throws TasteException
	 */
	protected static void execute(
			Collection<Callable<Void>> callables,
			AtomicInteger noEstimateCounter, 
			RunningAverageAndStdDev timing)
			throws TasteException {

		Collection<Callable<Void>> wrappedCallables = wrapWithStatsCallables(callables, noEstimateCounter, timing);
		int numProcessors = Runtime.getRuntime().availableProcessors();
		
		ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
		threadFactoryBuilder.setPriority(Thread.MIN_PRIORITY);
		
		ThreadFactory threadFactory = threadFactoryBuilder.build();
		
		ExecutorService executor = Executors.newFixedThreadPool(numProcessors, threadFactory);
		
		log.info(
				"Starting timing of {} tasks in {} threads",
				wrappedCallables.size(), 
				numProcessors);
		
		try {
			List<Future<Void>> futures = executor.invokeAll(wrappedCallables);
			// Go look for exceptions here, really
			for (Future<Void> future : futures) {
				future.get();
			}
		} catch (InterruptedException ie) {
			throw new TasteException(ie);
		} catch (ExecutionException ee) {
			throw new TasteException(ee.getCause());
		}
		
		executor.shutdown();
	}
	
	/**
	 * Apenas faz cada {@link Callable} escrever no log a cada conjunto de x iterações
	 * 
	 * @param callables
	 * @param noEstimateCounter
	 * @param timing
	 * @return
	 */
	private static Collection<Callable<Void>> wrapWithStatsCallables(
			Iterable<Callable<Void>> callables,
			AtomicInteger noEstimateCounter, RunningAverageAndStdDev timing) {
		
		Collection<Callable<Void>> wrapped = Lists.newArrayList();
		int count = 0;
		
		for (Callable<Void> callable : callables) {
			boolean logStats = count++ % 1000 == 0; // log every 1000 or so
													// iterations
			wrapped.add(new StatsCallable(callable, logStats, timing, noEstimateCounter));
		}
		return wrapped;
	}
	
	protected abstract void reset();
	
	protected void processOneEstimate(float estimatedPreference, FastByIDMap<Preference> realPrefs) {
		Preference realPref = preferenceAggregatorStrategy.aggregatePreferences(realPrefs);
		processOneEstimate(estimatedPreference, realPref);
	}
	
	protected abstract void processOneEstimate(float estimatedPreference, Preference realPref);
	
	protected abstract double computeFinalEvaluation();
	
	/**
	 * Estima a preferência de um usuário dentro de uma thread
	 *
	 */
	public final class PreferenceEstimateCallable implements Callable<Void> {

		private final Recommender recommender;
		private final long testUserID;
		private final FastByIDMap<PreferenceArray> prefsByCriteria;
		private final AtomicInteger noEstimateCounter;

		public PreferenceEstimateCallable(
				Recommender recommender,
				long testUserID, 
				FastByIDMap<PreferenceArray> prefsByCriteria,
				AtomicInteger noEstimateCounter) {
			this.recommender = recommender;
			this.testUserID = testUserID;
			this.prefsByCriteria = prefsByCriteria;
			this.noEstimateCounter = noEstimateCounter;
		}
		
		/**
		 * Captura todos os valores de preferência do usuário userID para o item itemID
		 * @param userID
		 * @param itemID
		 * @return
		 */
		private FastByIDMap<Preference> buildCriteriaPreferenceMap(Long userID, Long itemID) {
			FastByIDMap<Preference> map = new FastByIDMap<Preference>();
			
			for (Map.Entry<Long, PreferenceArray> entry : prefsByCriteria.entrySet()) {
				Iterator<Preference> iterator = entry.getValue().iterator();
				
				while (iterator.hasNext()) {
					Preference preference = iterator.next();
					
					if (userID == preference.getUserID() && itemID == preference.getItemID()) {
						map.put(entry.getKey(), preference);
						break;
					}
				}
			}
			
			return map;
		}

		/**
		 * Como fazer para executar apenas uma vez a preferência estimada de
		 * um usuario U para um item I, levando em consideração cada critério C?
		 * 
		 * Estou assumindo (não sei como garantir) que, para cada critério C, existe o mesmo
		 * conjunto de usuários e itens. Assim, eu poderia pegar apenas o primeiro critério,
		 * e usar estes conjuntos.
		 */
		@Override
		public Void call() throws TasteException {
			Preconditions.checkArgument(prefsByCriteria.size() > 0);
			LongPrimitiveIterator criteriaIterator = prefsByCriteria.keySetIterator();
			
			Preconditions.checkArgument(criteriaIterator.hasNext());
			
			PreferenceArray prefs = prefsByCriteria.get(criteriaIterator.nextLong());
			
			for (Preference realPref : prefs) {
				float estimatedPreference = Float.NaN;
				
				try {
					estimatedPreference = recommender.estimatePreference(testUserID, realPref.getItemID());
				} catch (NoSuchUserException nsue) {
					// It's possible that an item exists in the test data but
					// not training data in which case
					// NSEE will be thrown. Just ignore it and move on.
					log.info("User exists in test data but not training data: {}", testUserID);
				} catch (NoSuchItemException nsie) {
					log.info("Item exists in test data but not training data: {}", realPref.getItemID());
				}
				
				if (Float.isNaN(estimatedPreference)) {
					noEstimateCounter.incrementAndGet();
				} else {
					estimatedPreference = capEstimatedPreference(estimatedPreference);
					
					//método abstrato
					processOneEstimate(estimatedPreference, buildCriteriaPreferenceMap(testUserID, realPref.getItemID()));
				}
			}
			
			return null;
		}

	}
	
}

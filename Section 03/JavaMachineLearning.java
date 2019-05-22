import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.distance.PearsonCorrelationCoefficient;
import net.sf.javaml.featureselection.ranking.RecursiveFeatureEliminationSVM;
import net.sf.javaml.featureselection.scoring.GainRatio;
import net.sf.javaml.featureselection.subset.GreedyForwardSelection;
import net.sf.javaml.tools.data.FileHandler;

public class JavaMachineLearning {

	public static void main(String[] args) throws IOException{
		Dataset data = FileHandler.loadDataset(new File("C:/Users/asifa.PACKTPUB/Downloads/UCI-small/UCI-small/iris/iris.data"), 4, ",");
		System.out.println(data);
		FileHandler.exportDataset(data, new File("C:/javaml-output.txt"));
		data = FileHandler.loadDataset(new File("c:/javaml-output.txt"), 0,"\t");
		System.out.println(data);
		
		Clusterer km = new KMeans();
		Dataset[] clusters = km.cluster(data);
		for(Dataset cluster:clusters){
			System.out.println("Cluster: " + cluster);
		}
		
		ClusterEvaluation sse= new SumOfSquaredErrors();
		double score = sse.score(clusters);
		System.out.println(score);
		
		Classifier knn = new KNearestNeighbors(5);
		knn.buildClassifier(data);
		
		CrossValidation cv = new CrossValidation(knn);
		Map<Object, PerformanceMeasure> cvEvaluation = cv.crossValidation(data);
		System.out.println(cvEvaluation);
		
		Dataset testData = FileHandler.loadDataset(new File("C:/Users/asifa.PACKTPUB/Downloads/UCI-small/UCI-small/iris/iris.data"), 4, ",");
		Map<Object, PerformanceMeasure> testEvaluation = EvaluateDataset.testDataset(knn, testData);
		for(Object classVariable:testEvaluation.keySet()){
			System.out.println(classVariable + " class has "+testEvaluation.get(classVariable).getAccuracy());
		
		
		GainRatio gainRatio = new GainRatio();
		gainRatio.build(data);
		for (int i = 0; i < gainRatio.noAttributes(); i++){
			 System.out.println(gainRatio.score(i));
		}
		
		RecursiveFeatureEliminationSVM featureRank = new RecursiveFeatureEliminationSVM(0.2);
		featureRank.build(data);
		for (int i = 0; i < featureRank.noAttributes(); i++){
			 System.out.println(featureRank.rank(i));
		}
		
		GreedyForwardSelection featureSelection = new GreedyForwardSelection(5, new PearsonCorrelationCoefficient());
		featureSelection.build(data);
		System.out.println(featureSelection.selectedAttributes());

	}

}

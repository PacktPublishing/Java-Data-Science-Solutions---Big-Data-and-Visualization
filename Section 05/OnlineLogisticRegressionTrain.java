package science.data;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.mahout.classifier.sgd.CsvRecordFactory;
import org.apache.mahout.classifier.sgd.LogisticModelParameters;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;

public class OnlineLogisticRegressionTrain {

	public static void main(String[] args) throws IOException {
		String inputFile = "data/weather.numeric.csv";
		String outputFile = "model/model.csv";
		List<String> features =Arrays.asList("outlook", "temperature","humidity", "windy", "play");
		List<String> featureType = Arrays.asList("w", "n", "n", "w", "w");
		LogisticModelParameters params = new LogisticModelParameters();
		params.setTargetVariable("play");
		params.setMaxTargetCategories(2);
		params.setNumFeatures(4);
		params.setUseBias(false);
		params.setTypeMap(features,featureType);
		params.setLearningRate(0.5);
		int passes = 10;
		OnlineLogisticRegression olr;
		CsvRecordFactory csv = params.getCsvRecordFactory();
		olr = params.createRegression();
		for (int pass = 0; pass < passes; pass++) {
			BufferedReader in = new BufferedReader(new FileReader(inputFile));
			csv.firstLine(in.readLine());
			String row = in.readLine();
			while (row != null) {
				System.out.println(row);
				Vector input = new RandomAccessSparseVector(params.getNumFeatures());
				int targetValue = csv.processLine(row, input);
				olr.train(targetValue, input);
				row = in.readLine();
			}
			in.close();
		}
		OutputStream modelOutput = new FileOutputStream(outputFile);
		try {
			params.saveTo(modelOutput);
		} finally {
			modelOutput.close();
		}
	}
}




  <dependency>
	<groupId>org.apache.mahout</groupId>
	<artifactId>mahout-core</artifactId>
	<version>0.9</version>
  </dependency>
  <dependency>
	<groupId>org.apache.mahout</groupId>
	<artifactId>mahout-examples</artifactId>
	<version>0.9</version>
  </dependency>
  <dependency>
	<groupId>org.apache.mahout</groupId>
	<artifactId>mahout-math</artifactId>
	<version>0.9</version>
  </dependency>

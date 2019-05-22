package science.data;

import com.google.common.base.Charsets;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.SequentialAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.classifier.evaluation.Auc;
import org.apache.mahout.classifier.sgd.CsvRecordFactory;
import org.apache.mahout.classifier.sgd.LogisticModelParameters;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
 
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Locale;

public class OnlineLogisticRegressionTest {
	
	private static String inputFile = "data/weather.numeric.test.csv";
	private static String modelFile = "model/model.csv";
	
	public static void main(String[] args) throws Exception {
		Auc auc = new Auc();
		LogisticModelParameters params = LogisticModelParameters.loadFrom(new File(modelFile));
		CsvRecordFactory csv = params.getCsvRecordFactory();
		OnlineLogisticRegression olr = params.createRegression();
		InputStream in = new FileInputStream(new File(inputFile));
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charsets.UTF_8));
		String line = reader.readLine();
		csv.firstLine(line);
		line = reader.readLine();
		PrintWriter output=new PrintWriter(new OutputStreamWriter(System.out, Charsets.UTF_8), true);
		output.println("\"class\",\"model-output\",\"log-likelihood\"");
		while (line != null) {
			Vector vector = new SequentialAccessSparseVector(params.getNumFeatures());
			int classValue = csv.processLine(line, vector);
			double score = olr.classifyScalarNoLink(vector);
			output.printf(Locale.ENGLISH, "%d,%.3f,%.6f%n", classValue, score, olr.logLikelihood(classValue, vector));
			auc.add(classValue, score);
			line = reader.readLine();
		}
		reader.close();
		output.printf(Locale.ENGLISH, "AUC = %.2f%n", auc.auc());
		Matrix matrix = auc.confusion();
		output.printf(Locale.ENGLISH, "confusion: [[%.1f, %.1f], [%.1f, %.1f]]%n", matrix.get(0, 0), matrix.get(1, 0), matrix.get(0, 1), matrix.get(1, 1));
		matrix = auc.entropy();
		output.printf(Locale.ENGLISH, "entropy: [[%.1f, %.1f], [%.1f, %.1f]]%n", matrix.get(0, 0), matrix.get(1, 0), matrix.get(0, 1), matrix.get(1, 1));
	}
}
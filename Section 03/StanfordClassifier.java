
import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.ling.Datum;
import edu.stanford.nlp.objectbank.ObjectBank;

public class StanfordClassifier {
  public static void main(String[] args) throws Exception {
    ColumnDataClassifier columnDataClassifier = new ColumnDataClassifier("C:/Users/asifa.PACKTPUB/Downloads/stanford-classifier-2016-10-31/stanford-classifier-2016-10-31/examples/cheese2007.prop");
    Classifier<String,String> classifier =
        columnDataClassifier.makeClassifier(columnDataClassifier.readTrainingExamples("C:/Users/asifa.PACKTPUB/Downloads/stanford-classifier-2016-10-31/stanford-classifier-2016-10-31/examples/cheeseDisease.train"));
    for (String line : ObjectBank.getLineIterator("C:/Users/asifa.PACKTPUB/Downloads/stanford-classifier-2016-10-31/stanford-classifier-2016-10-31/examples/cheeseDisease.test", "utf-8")) {
      Datum<String,String> d = columnDataClassifier.makeDatumFromLine(line);
      System.out.println(line + "  ==>  " + classifier.classOf(d));
    }
  }
}


package fr.univtours.polytech.indexing_engine_mapreduce.query.mapred;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

/**
 * 
 * @author Jérôme Heissler & Francois Senis
 */
public class QueryReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {


	@Override
	public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
		
		double normeDoc = 0;
		double scalaire = 0;
		double tfidfQuestion = 1;
		double normeQuestion = 1;
		
		while (values.hasNext()) {
			Text val = values.next();
			/* val is 1-2, the first is df and tf */
			String[] tfAndDf = val.toString().split("-");
			int df = Integer.parseInt(tfAndDf[0]);
			int tf = Integer.parseInt(tfAndDf[1]);
			
			double tfidf = Math.log10(2/df);
			
			/* calcule du produit scalaire */
			scalaire = scalaire + (tfidf*tfidfQuestion);
			/* calcule de la norme de la question */
			normeQuestion = normeQuestion + Math.pow(tfidfQuestion,2);
			
			/* calcule de la norme du document */
			normeDoc = normeDoc + Math.pow(tfidf,2);
			
		}
		normeQuestion = Math.sqrt(normeQuestion);
		normeDoc = Math.sqrt(normeDoc);
		double number = scalaire/(normeQuestion*normeDoc);
		
		output.collect(key, new Text(number+""));

	}
}
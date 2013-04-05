package fr.univtours.polytech.indexing_engine_mapreduce.query.mapred;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import fr.univtours.polytech.indexing_engine_mapreduce.query.job.QueryJob;

/**
 * 
 * @author Jérôme Heissler & Francois Senis
 */
public class QueryReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {


	@Override
	public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
		
		double normeDoc = 0;
		double scalaire = 0;
		double normeQuestion = 0;
		
		while (values.hasNext()) {
			Text val = values.next();
						
			/* val is word-1-2, the first is df and tf */
			String[] WordAnddfAndtf = val.toString().split("-");
			int df = Integer.parseInt(WordAnddfAndtf[1]);
			int tf = Integer.parseInt(WordAnddfAndtf[2]);	
			double idf = Math.log10(QueryJob.documents.size()/df);
			double tfidf = tf*idf;
			
			if(QueryJob.question.containsKey(WordAnddfAndtf[0])){
				double tfQuestion = QueryJob.question.get(WordAnddfAndtf[0])/QueryJob.question.size();
				double tfidfQuestion = tfQuestion*idf;
				
				/* calcule de la norme de la question */
				normeQuestion = normeQuestion + Math.pow(tfidfQuestion,2);
				
				/* calcule du produit scalaire */
				scalaire = scalaire + (tfidf*tfidfQuestion);
			}
			
			/* calcule de la norme du document */
			normeDoc = normeDoc + Math.pow(tfidf,2);
		}
		double number = 0;
		if(normeDoc != 0 && normeQuestion != 0){
			normeQuestion = Math.sqrt(normeQuestion);
			normeDoc = Math.sqrt(normeDoc);
			number = scalaire/(normeQuestion*normeDoc);
		}
		output.collect(key, new Text(number+""));

	}
}
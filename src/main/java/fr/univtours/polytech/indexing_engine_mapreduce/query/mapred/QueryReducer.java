package fr.univtours.polytech.indexing_engine_mapreduce.query.mapred;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * 
 * @author Jérôme Heissler & Francois Senis
 */
public class QueryReducer extends Reducer<Text, Text, Text, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			
		String params = context.getJobName();
		String[] subparam = params.split(";");
		int nbDoc = Integer.parseInt(subparam[1]);
		
		double normeDoc = 0;
		double scalaire = 0;
		double normeQuestion = 0;
		
		HashMap<String, Double> question = null;
		
		for (Text text : values) {
			String val = text.toString();
			
			if(question == null)	{
				question = new HashMap<String, Double>();
				String[] questionValues = subparam[0].split(",");
				for(String item : questionValues)	{
					String[] keyVal = item.split("->");
					question.put(keyVal[0], Double.parseDouble(keyVal[1]));
				}
			}
						
			/* val is word-1-2, the first is df and tf */
			String[] WordAnddfAndtf = val.split("-");
			int df = Integer.parseInt(WordAnddfAndtf[1]);
			int tf = Integer.parseInt(WordAnddfAndtf[2]);	
			double idf = Math.log10(nbDoc/df);
			double tfidf = tf*idf;
			
			if(question.containsKey(WordAnddfAndtf[0])){
				double tfQuestion = question.get(WordAnddfAndtf[0])/question.size();
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
		context.write(key, new Text(number+""));

	}
}
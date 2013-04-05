package fr.univtours.polytech.indexing_engine_mapreduce.query.mapred;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 
 * @author Jérôme Heissler & Francois Senis
 *
 */
public class QueryMapper extends Mapper<LongWritable, Text, Text, Text> {

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		String[] content = line.split("\t");
		String[] lstDoc = content[2].split(" ");
		for(String s : lstDoc)	{
			String[] keyVal = s.split("=>");
			context.write(new Text(keyVal[0]), new Text(content[0]+"-"+content[1]+"-"+keyVal[1]));
		}		
	}
}
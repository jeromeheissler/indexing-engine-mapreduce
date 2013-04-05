package fr.univtours.polytech.indexing_engine_mapreduce.query.mapred;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import fr.univtours.polytech.indexing_engine_mapreduce.query.job.QueryJob;


public class QueryMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

		String line = value.toString();

		String[] content = line.split("\t");
		String[] lstDoc = content[2].split(" ");
		for(String s : lstDoc)	{
			String[] keyVal = s.split("=>");
			QueryJob.documents.add(keyVal[0]);
			output.collect(new Text(keyVal[0]), new Text(content[0]+"-"+content[1]+"-"+keyVal[1]));
		}
		
	}
}
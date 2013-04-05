/*
 * IndexJob.java
 *
 * Created on May 15, 2012, 6:49:49 AM
 */

package fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Text.Comparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;

import fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.mapred.IndexMapper;
import fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.mapred.IndexReducer;
import fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.writable.DocSumWritable;

/**
 * 
 * @author Jérôme Heissler & Francois Senis
 */
public class IndexJob extends Configured implements Tool {
		
	private String extractorClassName;
	private String listFilter;
	public IndexJob(String extractorClassName, String listFilter)	{
		this.extractorClassName = extractorClassName;
		this.listFilter = listFilter;
	}

	public int run(String[] args) throws Exception {
		Configuration conf = getConf();

		Job runningJob = new Job(conf, "Creating the inverted index");

		runningJob.setOutputKeyClass(Text.class);
		runningJob.setOutputValueClass(DocSumWritable.class);
		
		runningJob.setMapperClass(IndexMapper.class);
		runningJob.setReducerClass(IndexReducer.class);
		
		/* autogenere l'initialisation */
		runningJob.setInputFormatClass(TextInputFormat.class);
		runningJob.setOutputFormatClass(TextOutputFormat.class);
		
		FileInputFormat.setInputPaths(runningJob, new Path(args[0]));
		FileOutputFormat.setOutputPath(runningJob, new Path(args[1]));
		
	    
		runningJob.setMapOutputKeyClass(Text.class);
		runningJob.setMapOutputValueClass(Text.class);		

		runningJob.setOutputKeyClass(Comparator.class);
			
		runningJob.setJobName(extractorClassName+";"+listFilter);
		
		runningJob.submit();

		return runningJob.waitForCompletion(true) ? 0 : 1;
	}

}

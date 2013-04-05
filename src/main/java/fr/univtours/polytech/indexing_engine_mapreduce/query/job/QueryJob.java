package fr.univtours.polytech.indexing_engine_mapreduce.query.job;

import javax.xml.soap.Text;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text.Comparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;

public class QueryJob extends Configured implements Tool {
	
	private String question;
	public QueryJob(String question)	{
		this.question = question;
	}

	public int run(String[] args) throws Exception {
		Configuration conf = getConf();

		Job runningJob = new Job(conf, "Quering the invertedIndex");

		runningJob.setOutputKeyClass(Text.class);
		runningJob.setOutputFormatClass(TextOutputFormat.class);
	
		FileInputFormat.setInputPaths(runningJob, new Path(args[0]));
		FileOutputFormat.setOutputPath(runningJob, new Path(args[1]));
		
		//Getting the number of documents from the original input directory.
        Path inputPath = new Path("input");
		FileSystem fs = inputPath.getFileSystem(conf);
        FileStatus[] stat = fs.listStatus(inputPath);
        
        runningJob.setInputFormatClass(TextInputFormat.class);
        runningJob.setOutputFormatClass(TextOutputFormat.class);
	    
        runningJob.setReducerClass(fr.univtours.polytech.indexing_engine_mapreduce.query.mapred.QueryReducer.class);
        runningJob.setMapperClass(fr.univtours.polytech.indexing_engine_mapreduce.query.mapred.QueryMapper.class);

        runningJob.setMapOutputKeyClass(org.apache.hadoop.io.Text.class);
        runningJob.setMapOutputValueClass(org.apache.hadoop.io.Text.class);

		runningJob.setOutputKeyClass(Comparator.class);
		     
        runningJob.setJobName(question+";"+stat.length);
        
        runningJob.submit();

		return runningJob.waitForCompletion(true) ? 0 : 1;
	}

}

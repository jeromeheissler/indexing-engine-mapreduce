/*
 * IndexJob.java
 *
 * Created on May 15, 2012, 6:49:49 AM
 */

package fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.job;

import java.util.List;
import java.util.concurrent.Callable;

import javax.xml.soap.Text;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RunningJob;

import fr.univtours.polytech.indexing_engine_mapreduce.filter.Filter;
import fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.writable.DocSumWritable;
import fr.univtours.polytech.indexing_engine_mapreduce.signextractors.SignExtractor;

/**
 * 
 * @author Jérôme Heissler & Francois Senis
 */
public class IndexJob implements Callable<String> {
	

	private static List<Filter> filters;

	public static void setListFilters(List<Filter> filters) {
		IndexJob.filters = filters;
	}

	public static String filterSign(final String sign) {
		if (filters.size() == 0) {
			return sign;
		} else {
			String res = sign;
			for (int i = 0; i < filters.size(); ++i) {
				res = filters.get(i).filter(res);
				if (res == null) {
					break;
				}
			}
			return res;
		}
	}

	private static SignExtractor extractor;

	/**
	 * Permet d'obtenir l'extracteur de signes associés à la base de données.
	 * 
	 * @return l'extracteur de signes
	 */
	public static SignExtractor getSignExtractor() {
		return extractor;
	}

	public static void setSignExtractor(SignExtractor extractor) {
		IndexJob.extractor = extractor;
	}

	private String inputPaths;

	public void setInputPaths(String inputPaths) {
		this.inputPaths = inputPaths;
	}

	public String getInputPaths() {
		return inputPaths;
	}

	private String outputPath;

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	private RunningJob runningJob;

	public RunningJob getRunningJob() {
		return runningJob;
	}

	public String call() throws Exception {
		JobConf job = new JobConf();
		job.setJarByClass(getClass());

		/* autogenere l'initialisation */
		initJobConf(job);

		/* custom initialisation */
		initCustom(job);

		/* This is an example of how to set input and output. */
		FileInputFormat.setInputPaths(job, getInputPaths());
		if (getOutputPath() != null)
			FileOutputFormat.setOutputPath(job, new Path(getOutputPath()));

		//on envoie le job
		JobClient client = new JobClient(job);
		this.runningJob = client.submitJob(job);
		return runningJob.getID().toString();
	}

	/**
	 * This method is executed by the workflow
	 */
	public static void initCustom(JobConf conf) {
		// Ajoute le custom initialisation
		conf.setOutputValueClass(DocSumWritable.class);

	}

	public static void initJobConf(JobConf conf) {
		
		conf.setInputFormat(org.apache.hadoop.mapred.TextInputFormat.class);

		conf.setMapperClass(fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.mapred.IndexMapper.class);

		conf.setMapOutputKeyClass(org.apache.hadoop.io.Text.class);
		conf.setMapOutputValueClass(org.apache.hadoop.io.Text.class);

		conf.setPartitionerClass(org.apache.hadoop.mapred.lib.HashPartitioner.class);

		conf.setOutputKeyComparatorClass(org.apache.hadoop.io.Text.Comparator.class);


		conf.setReducerClass(fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.mapred.IndexReducer.class);

		conf.setNumReduceTasks(1);
		conf.setOutputKeyClass(Text.class);

		conf.setOutputFormat(org.apache.hadoop.mapred.TextOutputFormat.class);

	}

}

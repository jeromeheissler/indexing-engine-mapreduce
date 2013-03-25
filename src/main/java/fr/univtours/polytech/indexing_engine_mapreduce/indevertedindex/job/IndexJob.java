/*
 * IndexJob.java
 *
 * Created on May 15, 2012, 6:49:49 AM
 */

package fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.job;

import fr.univtours.polytech.indexing_engine_mapreduce.filter.Filter;
import fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.writable.DocSumWritable;
import fr.univtours.polytech.indexing_engine_mapreduce.signextractors.SignExtractor;

import java.util.List;
import java.util.concurrent.Callable;

import javax.xml.soap.Text;
// import org.apache.commons.logging.Log;
// import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.JobID;
import org.apache.hadoop.mapred.RunningJob;

/**
 * 
 * @author arifn
 */
public class IndexJob implements Callable<String> {
	// The Karmasphere Studio Workflow Log displays logging from Apache Commons
	// Logging, for example:
	// private static final Log LOG = LogFactory.getLog("job.IndexJob");

	/*
	 * Arguments which are only known at runtime, and might change from one run
	 * to another can be added to this file as Properties. In Netbeans,
	 * right-click, select "Insert Code..." and "Property".
	 * 
	 * Two example properties are given here, for simple jobs.
	 * 
	 * Hint: If you use obscure types, register a PropertyEditor for them.
	 */

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

		/* Autogenerated initialization. */
		initJobConf(job);

		/* Custom initialization, if any. */
		initCustom(job);

		/* This is an example of how to set input and output. */
		FileInputFormat.setInputPaths(job, getInputPaths());
		if (getOutputPath() != null)
			FileOutputFormat.setOutputPath(job, new Path(getOutputPath()));

		/* You can now do any other job customization. */
		// job.setXxx(...);

		/*
		 * And finally, we submit the job. If you run the job from within
		 * Karmasphere Studio, this returned RunningJob or JobID is given to the
		 * monitoring UI.
		 */
		JobClient client = new JobClient(job);
		this.runningJob = client.submitJob(job);
		return runningJob.getID().toString();
	}

	/**
	 * This method is executed by the workflow
	 */
	public static void initCustom(JobConf conf) {
		// Add custom initialisation here, you may have to rebuild your project
		// before
		// changes are reflected in the workflow.
		conf.setOutputValueClass(DocSumWritable.class);

	}

	/**
	 * This method is called from within the constructor to initialize the job.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Job Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initJobConf
	public static void initJobConf(JobConf conf) {
		// Generating code using Karmasphere Protocol for Hadoop 0.18
		// CG_GLOBAL

		// CG_INPUT_HIDDEN
		conf.setInputFormat(org.apache.hadoop.mapred.TextInputFormat.class);

		// CG_MAPPER_HIDDEN
		conf.setMapperClass(id.web.arifn.distindexing.mapred.IndexMapper.class);

		// CG_MAPPER
		conf.setMapOutputKeyClass(org.apache.hadoop.io.Text.class);
		conf.setMapOutputValueClass(org.apache.hadoop.io.Text.class);

		// CG_PARTITIONER_HIDDEN
		conf.setPartitionerClass(org.apache.hadoop.mapred.lib.HashPartitioner.class);

		// CG_PARTITIONER

		// CG_COMPARATOR_HIDDEN
		conf.setOutputKeyComparatorClass(org.apache.hadoop.io.Text.Comparator.class);

		// CG_COMPARATOR

		// CG_COMBINER_HIDDEN

		// CG_REDUCER_HIDDEN
		conf.setReducerClass(id.web.arifn.distindexing.mapred.IndexReducer.class);

		// CG_REDUCER
		conf.setNumReduceTasks(1);
		conf.setOutputKeyClass(Text.class);

		// CG_OUTPUT_HIDDEN
		conf.setOutputFormat(org.apache.hadoop.mapred.TextOutputFormat.class);

		// CG_OUTPUT

		// Others
	}

	// </editor-fold>//GEN-END:initJobConf
}

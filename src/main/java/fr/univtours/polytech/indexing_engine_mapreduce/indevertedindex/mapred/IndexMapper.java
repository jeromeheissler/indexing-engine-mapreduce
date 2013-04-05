/*
 * IndexMapper.java
 *
 * Created on May 15, 2012, 6:24:34 AM
 */

package fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.mapred;

import fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.job.IndexJob;
import fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.writable.DocSumWritable;
import fr.univtours.polytech.indexing_engine_mapreduce.signextractors.SignExtractor;

import java.io.IOException;
import java.util.StringTokenizer;
// import org.apache.commons.logging.Log;
// import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

/**
 * 
 * @author arifn
 */
public class IndexMapper extends MapReduceBase implements
		Mapper<LongWritable, Text, Text, Text> {
	// The Karmasphere Studio Workflow Log displays logging from Apache Commons
	// Logging, for example:
	// private static final Log LOG = LogFactory.getLog("mapred.IndexMapper");

	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

		FileSplit filesplit = (FileSplit) reporter.getInputSplit();
		String fileName = filesplit.getPath().getName();
		
		String line = value.toString();

		SignExtractor extractor = IndexJob.getSignExtractor();
		extractor.setContent(line);
		
		String sign = extractor.nextToken();
		while (sign != null) {
			String signFiltered = IndexJob.filterSign(sign);
			if(signFiltered.compareTo("") != 0)	{
				output.collect(new Text(signFiltered), new Text(fileName));
			}
			sign = extractor.nextToken();
		}

	}
}

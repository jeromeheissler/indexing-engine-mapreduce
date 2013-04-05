/*
 * IndexMapper.java
 *
 * Created on May 15, 2012, 6:24:34 AM
 */

package fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.mapred;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import fr.univtours.polytech.indexing_engine_mapreduce.filter.Filter;
import fr.univtours.polytech.indexing_engine_mapreduce.signextractors.SignExtractor;

/**
 * 
 * @author Jérôme Heissler & Francois Senis
 */
public class IndexMapper extends Mapper<LongWritable, Text, Text, Text> {
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String params = context.getJobName();
		String[] subline = params.split(";");
		SignExtractor extractor = getSignExtractor(subline[0]);
				
		FileSplit filesplit = (FileSplit) context.getInputSplit();
		String fileName = filesplit.getPath().getName();
		
		String line = value.toString();	
		extractor.setContent(line);		
		String sign = extractor.nextToken();
		while (sign != null) {
			String signFiltered = filterSign(subline[1], sign);
			if(signFiltered.compareTo("") != 0)	{
				//System.out.println(signFiltered+"-"+fileName);
				context.write(new Text(signFiltered), new Text(fileName));
			}
			sign = extractor.nextToken();
		}

	}
	
	public void run (Context context) throws IOException, InterruptedException {
        setup(context);
        while (context.nextKeyValue()) {
              map(context.getCurrentKey(), context.getCurrentValue(), context);
            }
        cleanup(context);
  }
	
	private SignExtractor getSignExtractor(String extractorClassName)	{
		String[] param = extractorClassName.split("-");
		SignExtractor extract = null;
		if (param.length == 2) {
			extractorClassName = param[0];
			Class<?> clazz;
			try {
				clazz = Class.forName(extractorClassName);
				extract = (SignExtractor) clazz.getConstructor(int.class)
						.newInstance(param[1]);
			} catch (Exception e) {
				System.out
						.println("Unable to load extractor, please check configuration file");
			}
		} else {
			try {
				extract = (SignExtractor) Class.forName(extractorClassName)
						.newInstance();
			} catch (Exception e) {
				System.out.println("Unable to load extractor \""+extractorClassName+ "\", please check configuration file");
			}
		}
		return extract;
	}
	
	private String filterSign(String classList, String sign)	{
		String tmp = classList.substring(1, classList.length()-1);
		String[] filterList = tmp.split(",");
		
		ArrayList<Filter> fil = new ArrayList<Filter>();
		for (String className : filterList) {
			try {
				fil.add((Filter) Class.forName(className).newInstance());
			} catch (Exception e) {
				System.out.println("Unable to load filter \"" + className + "\", please check configuration file");
			}
		}
		
		String signfiltered = null;
		if (fil.size() == 0) {
			signfiltered = sign;
		} else {
			String res = sign;
			for (int i = 0; i < fil.size(); ++i) {
				res = fil.get(i).filter(res);
				if (res == null) {
					break;
				}
			}
			signfiltered = res;
		}
		return signfiltered;
	}
}

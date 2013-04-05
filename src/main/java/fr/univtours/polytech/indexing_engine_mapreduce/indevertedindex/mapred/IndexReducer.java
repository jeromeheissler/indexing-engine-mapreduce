/*
 * IndexReducer.java
 *
 * Created on May 15, 2012, 6:29:45 AM
 */

package fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.mapred;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.writable.DocSumWritable;

/**
 * 
 * @author Jérôme Heissler & Francois Senis
 */
public class IndexReducer extends Reducer<Text, Text, Text, DocSumWritable> {

	public IndexReducer()	{
		System.out.println("hello");
	}
	
	private HashMap<String, Integer> map;

	private void add(String tag) {
		Integer val;
		if (map.get(tag) != null) {
			val = map.get(tag);
			map.remove(tag);
		} else {
			val = 0;
		}
		map.put(tag, val + 1);
	}
	
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		map = new HashMap<String, Integer>();
		for (Text val : values) {
			String sign = val.toString();
			add(sign);
		}

		context.write(key, new DocSumWritable(map));

	}
}

package fr.univtours.polytech.tf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class Reduce extends Reducer<Text, Text, Text, Text> {

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
    	
    	HashMap<String, Integer> tmp = new HashMap<String, Integer>();
    	for(Text map : values)	{
    		String t = map.toString();
    		String[] ts = t.split("-");
    			if(tmp.containsKey(ts[0]))	{
    				tmp.put(ts[0], tmp.get(ts[0]) + 1);
    			}else	{
    				tmp.put(ts[0], 1);
    			}
    		
    	}
        
    	String output = "";
    	for(Entry<String, Integer> t : tmp.entrySet())	{
    		output += " "+t.getKey()+"-"+t.getValue();
    	}
    	
        context.write(key, new Text(output));
    }

}

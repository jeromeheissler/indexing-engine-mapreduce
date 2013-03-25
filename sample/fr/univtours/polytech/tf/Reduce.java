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
    	
    	StringBuffer buf = new StringBuffer();
    	for(Text text : values)	{
    		buf.append(text.toString()+" ");
    	}
    	
        context.write(key, new Text(buf.toString()));
    }

}

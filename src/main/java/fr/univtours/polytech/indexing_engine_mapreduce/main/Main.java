package fr.univtours.polytech.indexing_engine_mapreduce.main;

import java.io.IOException;
import java.util.ArrayList;

import fr.univtours.polytech.indexing_engine_mapreduce.filter.AccentFilter;
import fr.univtours.polytech.indexing_engine_mapreduce.filter.CaseFilter;
import fr.univtours.polytech.indexing_engine_mapreduce.filter.Filter;
import fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.job.IndexJob;
import fr.univtours.polytech.indexing_engine_mapreduce.signextractors.WordExtractor;

public class Main {

	public static void main(String[] args)	{
		IndexJob.setSignExtractor(new WordExtractor());
		ArrayList<Filter> fil = new ArrayList<Filter>();
		fil.add(new CaseFilter());
		fil.add(new AccentFilter());
		IndexJob.setListFilters(fil);
		
		IndexJob job = new IndexJob();
		if (args.length >= 1)
			job.setInputPaths(args[0]);
		if (args.length >= 2)
			job.setOutputPath(args[1]);
		try {
			job.call();
			job.getRunningJob().waitForCompletion();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

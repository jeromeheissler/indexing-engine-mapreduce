package fr.univtours.polytech.indexing_engine_mapreduce.main;

import java.io.IOException;
import java.util.ArrayList;

import fr.univtours.polytech.indexing_engine_mapreduce.filter.AccentFilter;
import fr.univtours.polytech.indexing_engine_mapreduce.filter.CaseFilter;
import fr.univtours.polytech.indexing_engine_mapreduce.filter.Filter;
import fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.job.IndexJob;
import fr.univtours.polytech.indexing_engine_mapreduce.query.job.QueryJob;
import fr.univtours.polytech.indexing_engine_mapreduce.signextractors.WordExtractor;

public class Main {

	public static void main(String[] args) {
		IndexJob.setSignExtractor(new WordExtractor());
		ArrayList<Filter> fil = new ArrayList<Filter>();
		fil.add(new CaseFilter());
		fil.add(new AccentFilter());
		IndexJob.setListFilters(fil);

		IndexJob job = new IndexJob();
		job.setInputPaths(args[0]);
		job.setOutputPath("output/tmp");
		try {
			job.call();
			job.getRunningJob().waitForCompletion();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/**
		 * TODO recupèrer question puis appliquer sign extractor
		 */

		QueryJob query = new QueryJob();
		query.setInputPaths("output/tmp");
		query.setOutputPath(args[1]);
		
		try {
			query.call();
			query.getRunningJob().waitForCompletion();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}

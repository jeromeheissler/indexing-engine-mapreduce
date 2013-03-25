package fr.univtours.polytech.indexing_engine_mapreduce.main;

import java.io.IOException;

import fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.job.IndexJob;

public class Main {

	public static void main(String[] args)	{
		IndexJob job = new IndexJob();
		if (args.length >= 1)
			job.setInputPaths(args[0]);
		if (args.length >= 2)
			job.setOutputPath(args[1]);
		try {
			job.call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* Wish we didn't have to reproduce code from runJob() here. */
		try {
			job.getRunningJob().waitForCompletion();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

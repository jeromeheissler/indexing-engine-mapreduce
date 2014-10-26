import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.StringUtils;
import org.apache.hadoop.util.ToolRunner;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import fr.univtours.polytech.indexing_engine_mapreduce.filter.Filter;
import fr.univtours.polytech.indexing_engine_mapreduce.indevertedindex.job.IndexJob;
import fr.univtours.polytech.indexing_engine_mapreduce.query.job.QueryJob;
import fr.univtours.polytech.indexing_engine_mapreduce.signextractors.SignExtractor;

/**
 * 
 * @author Jérôme Heissler & Francois Senis
 * 
 */
public class Main {
	
	public static byte[] toByteArray(char[] array, Charset charset) {
		CharBuffer cbuf = CharBuffer.wrap(array);
		ByteBuffer bbuf = charset.encode(cbuf);
		return bbuf.array();
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length < 4) {
			System.out.println("Missing parameter. Correct use it moteurIndexation MODE CONF INPUT OUTPUT [QUERY]");
			return;
		}
		
		Path pt=new Path(args[1]);
        FileSystem fs = FileSystem.get(new Configuration());
        InputStreamReader in = new InputStreamReader(fs.open(pt));

        File f=new File("conf.txt");
        OutputStream out=new FileOutputStream(f);
        char buff[]=new char[1024];
        int len;
        while((len=in.read(buff))>0)	{
        	out.write(toByteArray(buff, Charset.defaultCharset()), 0, len);
        }
		/* load configuration file */
		Config conf = ConfigFactory.parseFile(f);

		/* load extractor from className */
		String extractorClassName = conf.getString("extractor");

		/* load filter from classname */
		List<String> filterList = conf.getStringList("filter");

		/* create the string represent the filter and extractor to use */
		List<String> buf = new ArrayList<String>();
		for (String s : filterList)
			buf.add(s);
		String filters = "("+StringUtils.join(",", buf)+")";

		/**
		 * The job is indexing all file
		 */
		if (args[0].compareTo("index") == 0) {
			String[] arg = {args[2], args[3]};
			try {
				ToolRunner.run(new Configuration(), new IndexJob(extractorClassName, filters), arg);
			} catch (Exception e) {
				e.printStackTrace();
			}

			/**
			 * The job is answer to a question
			 */
		} else if (args[0].compareTo("question") == 0) {

			String[] param = extractorClassName.split("-");
			SignExtractor extract = null;
			if (param.length == 2) {
				extractorClassName = param[0];
				Class<?> clazz;
				try {
					clazz = Class.forName(extractorClassName);
					extract = (SignExtractor) clazz.getConstructor(int.class).newInstance(param[1]);
				} catch (Exception e) {
					System.out
							.println("Unable to load extractor, please check configuration file");
				}
			} else {
				try {
					extract = (SignExtractor) Class.forName(extractorClassName)
							.newInstance();
				} catch (Exception e) {
					System.out.println("Unable to load extractor \""
							+ extractorClassName
							+ "\", please check configuration file");
				}
			}

			ArrayList<Filter> fil = new ArrayList<Filter>();
			for (String className : filterList) {
				try {
					fil.add((Filter) Class.forName(className).newInstance());
				} catch (Exception e) {
					System.out.println("Unable to load filter \"" + className + "\", please check configuration file");
				}
			}

			/* getting the question tf */
			String queryQuestion = args[4];
			HashMap<String, Double> mapQuestion = new HashMap<String, Double>();
			extract.setContent(queryQuestion);
			String sign = extract.nextToken();
			while (sign != null) {
				String signfiltered = "";
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
				if (signfiltered.compareTo("") != 0) {
					if (mapQuestion.containsKey(signfiltered))
						mapQuestion.put(signfiltered,
								mapQuestion.get(signfiltered) + 1);
					else
						mapQuestion.put(signfiltered, (double) 1);
				}
				sign = extract.nextToken();
			}
			
			List<String> item = new ArrayList<String>();
			for(Entry<String, Double> e : mapQuestion.entrySet())	{
				item.add(e.getKey()+"->"+e.getValue());
			}
			
			String question = StringUtils.join(",", item);					
			
			String[] arg = {args[2], args[3], args[5]};
			try {
				ToolRunner.run(new Configuration(), new QueryJob(question), arg);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			System.out.println("args[0] ( = Options ) are : index or question");

		}
	}
}

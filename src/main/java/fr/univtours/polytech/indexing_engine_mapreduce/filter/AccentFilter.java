package fr.univtours.polytech.indexing_engine_mapreduce.filter;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Classe de filtre permettant d'�liminer les accents et les caract�res sp�ciaux
 * d'une cha�ne de caract�res.
 * 
 * @author S�bastien Aupetit
 */
public class AccentFilter implements Filter {
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.univtours.polytech.di.multimedia.filters.Filter#filter(java.lang.String)
	 */
	@Override
	public String filter(final String sign) {
		String nfdNormalizedString = Normalizer.normalize(sign,Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}

}

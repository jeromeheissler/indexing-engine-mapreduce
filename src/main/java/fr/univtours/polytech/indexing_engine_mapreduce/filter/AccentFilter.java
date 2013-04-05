package fr.univtours.polytech.indexing_engine_mapreduce.filter;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Classe de filtre permettant d'eliminer les accents et les caracteres speciaux
 * d'une chaine de caracteres.
 * 
 * @author Jérôme Heissler & François Senis
 */
public class AccentFilter implements Filter {
	
	@Override
	public String filter(final String sign) {
		String nfdNormalizedString = Normalizer.normalize(sign,Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}

}

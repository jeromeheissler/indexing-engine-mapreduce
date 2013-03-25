package fr.univtours.polytech.indexing_engine_mapreduce.filter;

import java.text.Normalizer;

/**
 * Classe de filtre permettant d'�liminer les accents et les caract�res sp�ciaux
 * d'une cha�ne de caract�res.
 * @author S�bastien Aupetit
 */
public class AccentFilter implements Filter {

  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.filters.Filter#filter(java.lang.String)
   */
  @Override
  public String filter(final String sign) {
    final String result = Normalizer.normalize(sign, Normalizer.Form.NFD);
    return result.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
  }

}

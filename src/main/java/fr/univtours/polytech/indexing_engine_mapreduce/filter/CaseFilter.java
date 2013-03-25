package fr.univtours.polytech.indexing_engine_mapreduce.filter;

/**
 * Classe permettant de convertir un signe en minuscule.
 * @author S�bastien Aupetit
 */
public class CaseFilter implements Filter {

  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.filters.Filter#filter(java.lang.String)
   */
  @Override
  public String filter(final String sign) {
	// On retourne la cha�ne de caract�re en minuscule  
    return sign.toLowerCase();
  }

}

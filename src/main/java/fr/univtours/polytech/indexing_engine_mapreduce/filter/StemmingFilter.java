package fr.univtours.polytech.indexing_engine_mapreduce.filter;

/**
 * Classe de filtre r�alisant un stemming.
 * @author S�bastien Aupetit
 */
public class StemmingFilter implements Filter {

	// Cr�ation d'une liste contenant les fins de mot � supprimer
  public static String[] delLem = {"ance", "ique", "isme", "able", "iste", "eux",
	  "anxes", "iques", "ismes", "ables", "istes", "s", "x"}; 
	
  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.filters.Filter#filter(java.lang.String)
   */
  @Override
  public String filter(final String sign) {
	String ret = sign;
	// On parcourt la liste des fins de mot � supprimer
	for(String lem : delLem)	{
		// On enl�ve la fin du mot si cela fait partie de la liste
		ret = ret.replaceAll(lem+"$", "");
	}
    return ret;
  }

}

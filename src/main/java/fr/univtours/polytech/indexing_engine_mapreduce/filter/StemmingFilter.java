package fr.univtours.polytech.indexing_engine_mapreduce.filter;

/**
 * Classe de filtre realisant un stemming.
 * @author Jérôme Heissler & François Senis
 */
public class StemmingFilter implements Filter {

	// Creation d'une liste contenant les fins de mot a supprimer
  public static String[] delLem = {"ance", "ique", "isme", "able", "iste", "eux",
	  "anxes", "iques", "ismes", "ables", "istes", "s", "x"}; 
	
  
  @Override
  public String filter(final String sign) {
	String ret = sign;
	// On parcourt la liste des fins de mot a supprimer
	for(String lem : delLem)	{
		// On enleve la fin du mot si cela fait partie de la liste
		ret = ret.replaceAll(lem+"$", "");
	}
    return ret;
  }

}

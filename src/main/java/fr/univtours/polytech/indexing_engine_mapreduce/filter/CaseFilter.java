package fr.univtours.polytech.indexing_engine_mapreduce.filter;

/**
 * Classe permettant de convertir un signe en minuscule.
 * @author Jérôme Heissler & François Senis
 */
public class CaseFilter implements Filter {

  
  @Override
  public String filter(final String sign) {
	// On retourne la cha�ne de caract�re en minuscule  
    return sign.toLowerCase();
  }

}

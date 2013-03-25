package fr.univtours.polytech.indexing_engine_mapreduce.signextractors;

import java.util.List;

/**
 * Classe permettant d'extraite des mots � partir d'une cha�ne de caract�res.
 * @author S�bastien Aupetit
 */
public class WordExtractor implements SignExtractor {

  private String[] items;
  private int cursor;
	
  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.signextractors.SignExtractor#nextToken()
   */
  @Override
  public String nextToken() {
	// Si le curseur est arriv� � la fin de la cha�ne de caract�res
	if(cursor == items.length)
	  return null;
	// Sinon on retourne le prochain mot
	else {
	  cursor++;
	  return items[cursor-1];
	}
  }

  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.signextractors.SignExtractor#setContent(java.lang.String)
   */
  @Override
  public void setContent(final String content) {
	// On coupe la phrase quand on rencontre un espace ou un caract�re sp�cial
    items = content.split("[^a-zàâéèêîôûùA-Z0-9]+");
    
    // On initialise le curseur � 0
    cursor = 0;
  }
}

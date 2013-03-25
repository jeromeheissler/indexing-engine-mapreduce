package fr.univtours.polytech.indexing_engine_mapreduce.signextractors;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant d'extraite des N-grams � partir d'une cha�ne de caract�res.
 * @author S�bastien Aupetit
 */
public class NGramExtractor implements SignExtractor {

  private int n;
  private int cursor;
  private List<String> items;
	
  /**
   * Le constructeur.
   * @param size la taille des N-grams
   */
  public NGramExtractor(final int size) {
   n = size;
  }

  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.signextractors.SignExtractor#nextToken()
   */
  @Override
  public String nextToken() {
	  // Si on est � la fin du mot
	  if(cursor == items.size())
		  return null;
	  // Sinon on retourne le prochain 'bloc'
	  else {
		  cursor++;
		  return items.get(cursor-1);
		}
  }

  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.signextractors.SignExtractor#setContent(java.lang.String)
   */
  @Override
  public void setContent(final String content) {
    items = new ArrayList<String>();
    int i = 0;
    
    // Tant que l'on est pas � la fin du mot
    while((i+n)<=content.length())	{
    	// On ajoute � la liste le prochain 'bloc'
    	items.add(content.substring(i, i+n));
    	i++;
    }
    // On initialise le curseur � 0
	cursor = 0;
  }
}

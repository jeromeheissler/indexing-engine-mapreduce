package fr.univtours.polytech.indexing_engine_mapreduce.signextractors;

/**
 * Interface definissant comment extraire des signes a partir d'une chaine de
 * caracteres.
 * @author Jérôme Heissler & Francois Senist
 */
public interface SignExtractor {
  /**
   * Permet d'obtenir le signe suivant.
   * @return le signe ou null s'il n'y a plus de signes a extraire
   */
  public String nextToken();

  /**
   * Permet de definir le chaine de caracteres a partir de laquelle les signes
   * seront extraits.
   * @param content la chaine de caractere
   */
  public void setContent(String content);
}

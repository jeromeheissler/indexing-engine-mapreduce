package fr.univtours.polytech.indexing_engine_mapreduce.signextractors;

/**
 * Interface d�finissant comment extraire des signes � partir d'une cha�ne de
 * caract�res.
 * @author S�bastien Aupetit
 */
public interface SignExtractor {
  /**
   * Permet d'obtenir le signe suivant.
   * @return le signe ou null s'il n'y a plus de signes � extraire
   */
  public String nextToken();

  /**
   * Permet de d�finir le cha�ne de caract�res � partir de laquelle les signes
   * seront extraits.
   * @param content la cha�ne de caract�re
   */
  public void setContent(String content);
}

package fr.univtours.polytech.indexing_engine_mapreduce.filter;

/**
 * Interface representant un filtre pour les signes (suites de caract�res).
 * @author Jérôme Heissler & François Senis
 */
public interface Filter {
  /**
   * Filtre le parametre.
   * @param sign la suite de caracteres a filtrer
   * @return la chaine de caracteres filtree ou null si le signe doit etre
   *         elimine.
   */
  String filter(String sign);
}

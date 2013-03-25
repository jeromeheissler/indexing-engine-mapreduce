package fr.univtours.polytech.indexing_engine_mapreduce.filter;

/**
 * Interface repr�sentant un filtre pour les signes (suites de caract�res).
 * @author S�bastien Aupetit
 */
public interface Filter {
  /**
   * Filtre le param�tre.
   * @param sign la suite de caract�res � filtrer
   * @return la cha�ne de caract�res filtr�e ou null si le signe doit �tre
   *         �limin�.
   */
  String filter(String sign);
}

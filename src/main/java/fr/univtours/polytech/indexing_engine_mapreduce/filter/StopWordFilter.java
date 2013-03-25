package fr.univtours.polytech.indexing_engine_mapreduce.filter;


/**
 * Classe de filtrage impl�mentant un filtre de mots vides.
 * @author S�bastien Aupetit
 */
public class StopWordFilter implements Filter {

	// Cr�ation d'une liste stop words
	public static String[] stopWordFr = {
		    "a","alors","au","aucuns","aussi","autre","avant","avec","avoir","�","aux",
		    "bon",
		    "c","car","ce","cela","ces","ceux","chaque","ci","co","comme","comment","�a","c1",//"cest",
		    "d","dans","de","des","du","dedans","dehors","depuis","deux","devrait","doit","donc","dos","droite","d�but",
		    "elle","elles","en","encore","essai","est","et","eu","�taient","�tat","�tions","�t�","�tre",
		    "fait","faites","fois","font","force",
		    "haut","hors","http",
		    "ici","il","ils",
		    "je", "juste",
		    "l","la","le","les","leur","l�",
		    "ma","maintenant","mais","mes","mine","moins","mon","mot","m�me",
		    "ni","nomm�s","notre","nous","nouveaux","ne",
		    "ou","o�",
		    "par","parce","parole","pas","personnes","peut","peu","pi�ce","plupart","pour","pourquoi",
		    "quand","que","quel","quelle","quelles","quels","qui",
		    "rt",
		    "s", "sa","sans","se","ses","seulement","si","sien","son","sont","sous","soyez", "sujet","sur",
		    "t","ta","tandis","tellement","tels","tes","ton","tous","tout","trop","tr�s","tu",
		    "un","une",
		    "valeur","voie","voient","vont","votre","vous","vu",
		    "www" };
	
	private boolean caseFilterApplied;
	private boolean accentFilterApplied;
	
  /**
   * Le constructeur.
   * @param caseFilterApplied indique si les signes ont �t� filtr�s en minuscule
   * @param accentFilterApplied indique si les signes ont �t� filtr�s sans
   *          accent et sans caract�res sp�ciaux
   */
  public StopWordFilter(final boolean caseFilterApplied,
      final boolean accentFilterApplied) {
    this.caseFilterApplied = caseFilterApplied;
    this.accentFilterApplied = accentFilterApplied;
  }

  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.filters.Filter#filter(java.lang.String)
   */
  @Override
  public String filter(final String sign) {
	String s = sign;
	// On parcourt la liste des stop words
	for(String fr : stopWordFr)	{
		// Si le mot est pr�sent, on le remplace par une cha�ne vide
		/* [^a-z����������A-Z] = aucun des caract�res entre crochet */
		s = s.replaceFirst("^"+fr+"$", "");
		s = s.replaceFirst("^"+fr+"[^a-z����������A-Z0-9]", "");
	    s = s.replaceFirst("[^a-z����������A-Z0-9]"+fr+"$", "");
	    s = s.replaceAll("[^a-z����������A-Z0-9]"+fr+"[^a-z����������A-Z0-9]", " ");
	}
	return s;
  }

}

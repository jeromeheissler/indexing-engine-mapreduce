package fr.univtours.polytech.indexing_engine_mapreduce.filter;


/**
 * Classe de filtrage implementant un filtre de mots vides.
 * @author Jérôme Heissler & François Senis
 */
public class StopWordFilter implements Filter {

	// Creation d'une liste stop words
	public static String[] stopWordFr = {
		    "a","alors","au","aucuns","aussi","autre","avant","avec","avoir","à","aux",
		    "bon",
		    "c","car","ce","cela","ces","ceux","chaque","ci","co","comme","comment","ça","c1",//"cest",
		    "d","dans","de","des","du","dedans","dehors","depuis","deux","devrait","doit","donc","dos","droite","début",
		    "elle","elles","en","encore","essai","est","et","eu","étaient","état","étions","été","être",
		    "fait","faites","fois","font","force",
		    "haut","hors","http",
		    "ici","il","ils",
		    "je", "juste",
		    "l","la","le","les","leur","là",
		    "ma","maintenant","mais","mes","mine","moins","mon","mot","même",
		    "ni","nommés","notre","nous","nouveaux","ne",
		    "ou","où",
		    "par","parce","parole","pas","personnes","peut","peu","pièce","plupart","pour","pourquoi",
		    "quand","que","quel","quelle","quelles","quels","qui",
		    "rt",
		    "s", "sa","sans","se","ses","seulement","si","sien","son","sont","sous","soyez", "sujet","sur",
		    "t","ta","tandis","tellement","tels","tes","ton","tous","tout","trop","trés","tu",
		    "un","une",
		    "valeur","voie","voient","vont","votre","vous","vu",
		    "www" };
  
  @Override
  public String filter(final String sign) {
	String s = sign.toLowerCase();
	// On parcourt la liste des stop words
	for(String fr : stopWordFr)	{
		// Si le mot est present, on le remplace par une chaine vide
		/* [^a-éèàêëîïùôöçüûA-Z] = aucun des caracteres entre crochet */
		s = s.replaceFirst("^"+fr+"$", "");
		s = s.replaceFirst("^"+fr+"[^a-zéèàêëîïùôöçüûA-Z0-9]", "");
	    s = s.replaceFirst("[^a-zéèàêëîïùôöçüûA-Z0-9]"+fr+"$", "");
	    s = s.replaceAll("[^a-zéèàêëîïùôöçüûA-Z0-9]"+fr+"[^a-zéèàêëîïùôöçüûA-Z0-9]", " ");
	}
	return s;
  }

}

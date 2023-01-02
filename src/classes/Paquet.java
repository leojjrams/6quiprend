package classes;

import java.util.*;

public class Paquet {
	
	private static int nbcartes;
	private static List<Carte> paquet; // ensemble des cartes du paquet
	
	/*
	 * M�thode d'initialisation du paquet
	 */
	public Paquet() {
		nbcartes = Carte.getMAX(); // Le nombre de cartes correspond � la valeur maximale d'une carte
		paquet = new ArrayList<Carte>();
		// On ajoute toutes les cartes au paquet
		for(int i=0; i<nbcartes; ++i) {
			Carte c = new Carte();
			paquet.add(c);
		}
	}
	
	/*
	 * M�lange le paquet de cartes pour que les cartes soient dans le d�sordre
	 */
	public static void melanger() {
		Collections.shuffle(paquet);
	}
	
	/*
	 * Permet de piocher une carte en prenant la derni�re du paquet
	 * 
	 * @return La carte pioch�e
	 */
	public static Carte piocher() {
		--nbcartes;
		return paquet.get(nbcartes);
	}
	
	/*
	 * R�cup�re le nombre de carte restantes dans le paquet
	 * 
	 * @return Le nombre de cartes
	 */
	public static int getNbCartes() {
		return nbcartes;
	}
	
}
package classes;

import java.util.*;

public class Paquet {
	
	private static int nbcartes;
	private static List<Carte> paquet; // ensemble des cartes du paquet
	
	/*
	 * Méthode d'initialisation du paquet
	 */
	public Paquet() {
		nbcartes = Carte.getMAX(); // Le nombre de cartes correspond à la valeur maximale d'une carte
		paquet = new ArrayList<Carte>();
		// On ajoute toutes les cartes au paquet
		for(int i=0; i<nbcartes; ++i) {
			Carte c = new Carte();
			paquet.add(c);
		}
	}
	
	/*
	 * Mélange le paquet de cartes pour que les cartes soient dans le désordre
	 */
	public static void melanger() {
		Collections.shuffle(paquet);
	}
	
	/*
	 * Permet de piocher une carte en prenant la dernière du paquet
	 * 
	 * @return La carte piochée
	 */
	public static Carte piocher() {
		--nbcartes;
		return paquet.get(nbcartes);
	}
	
	/*
	 * Récupère le nombre de carte restantes dans le paquet
	 * 
	 * @return Le nombre de cartes
	 */
	public static int getNbCartes() {
		return nbcartes;
	}
	
}
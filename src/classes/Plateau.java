package classes;

import java.util.ArrayList;
import java.util.List;

public class Plateau {

	private static List<Serie> plateau; // ensemble des séries présentes sur le plateau
	private static final int nbseries = 4;

	public Plateau() {
		plateau = new ArrayList<Serie>();
		// On initialise toutes les séries du plateau
		for(int i=0; i<nbseries; ++i) {
			Serie s = new Serie();
			plateau.add(s);
		}
	}

	/*
	 * Permet de savoir si une carte donnée peut être ajoutée à une des séries du plateau
	 * 
	 * @param c
	 * 			La carte que l'on veut ajouter
	 * 
	 * @return true si la carte peut être ajoutée à une série, false sinon
	 */
	public static boolean ajoutPossible(Carte c) {
		for(int i=0; i<nbseries; ++i) {
			if(plateau.get(i).ajoutPossible(c))
				return true;
		}
		return false;
	}

	/*
	 * Permet de savoir si la valeur d'une carte donnée est trop petite
	 * pour pouvoir ajouter la carte à une des séries du plateau
	 * 
	 * @param c
	 * 			La carte que l'on veut ajouter
	 * 
	 * @return false si la valeur de la carte n'est pas trop petite, false sinon
	 */
	public static boolean valeurTropPetite(Carte c) {
		for(int i=0; i<nbseries; ++i) {
			// Si la valeur de la carte c est plus grande que la dernière carte
			// d'une des séries alors cette valeur n'est pas trop petite
			if(c.getValeur()>plateau.get(i).getLast().getValeur())
				return false;
		}
		return true;
	}

	/*
	 * Récupère l'indice de la série ou doit être posée une carte donnée
	 * 
	 * @param c
	 * 			La carte que l'on veut ajouter
	 * 
	 * @return l'indice de la série où poser la carte
	 */
	public static int getSerieOuPoser(Carte c) {
		int valMIN = Carte.getMAX(), difference, numChoix = 0;
		// Si la carte peut être ajoutée à une série alors
		// il faut trouver une série où la carte peut effectivement être posée
		if(ajoutPossible(c)) {
			for(int i=0; i<nbseries; ++i) {
				difference = c.getValeur()-plateau.get(i).getLast().getValeur();
				// On cherche la série où la différence de valeur entre la carte c
				// et la dernière carte de la série sera la plus faible
				if(valMIN>difference && difference>0 && plateau.get(i).ajoutPossible(c)) {
					valMIN = difference;
					numChoix = i;
				}
			}
		}
		// Si la carte ne peut pas être posée on trouve la série où elle aurait dû être ajoutée
		else {
			for(int i=0; i<nbseries; ++i) {
				difference = c.getValeur()-plateau.get(i).getLast().getValeur();
				if(valMIN>difference && difference>0) {
					valMIN = difference;
					numChoix = i;
				}
			}
		}
		return numChoix;
	}

	/*
	 * Ajoute une carte donnée au plateau
	 * 
	 * @param c
	 * 			La carte que l'on veut poser
	 */
	public static void ajouterCarte(Carte c) {
		// On ajoute la carte à une série déterminée automatiquement
		plateau.get(getSerieOuPoser(c)).ajouterCarte(c);
	}

	/*
	 * Remplace les cartes d'une série d'un indice donné par une carte donnée également
	 * 
	 * @param indice
	 * 			Indice de la série dont on veut remplacer les cartes
	 * 
	 * @param c
	 * 			Carte que l'on veut mettre à la place des cartes de la série
	 */
	public static void remplacerSerie(int indice, Carte c) {
		plateau.get(indice).remplacer(c);
	}

	/*
	 * Récupère le nombre de séries présentes sur le plateau
	 * 
	 * @return Le nombre de séries du plateau
	 */
	public static int getNbSeries() {
		return nbseries;
	}

	/*
	 * Récupère sur le plateau une série d'un index donné
	 * 
	 * @param indice
	 * 			Indice de la série qu'on veut récupérer
	 * 
	 * @return La série correspondant à l'indice
	 */
	public static Serie getSerie(int indice) {
		return plateau.get(indice);
	}

	/*
	 * Méthode d'affichage du plateau
	 * Affiche toutes les séries du plateau
	 */
	public static void afficherPlateau() {
		for(int i=0; i<nbseries; ++i) {
			System.out.println(plateau.get(i));
		}
	}

}
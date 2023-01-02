package classes;

import java.util.ArrayList;
import java.util.List;

public class Plateau {

	private static List<Serie> plateau; // ensemble des s�ries pr�sentes sur le plateau
	private static final int nbseries = 4;

	public Plateau() {
		plateau = new ArrayList<Serie>();
		// On initialise toutes les s�ries du plateau
		for(int i=0; i<nbseries; ++i) {
			Serie s = new Serie();
			plateau.add(s);
		}
	}

	/*
	 * Permet de savoir si une carte donn�e peut �tre ajout�e � une des s�ries du plateau
	 * 
	 * @param c
	 * 			La carte que l'on veut ajouter
	 * 
	 * @return true si la carte peut �tre ajout�e � une s�rie, false sinon
	 */
	public static boolean ajoutPossible(Carte c) {
		for(int i=0; i<nbseries; ++i) {
			if(plateau.get(i).ajoutPossible(c))
				return true;
		}
		return false;
	}

	/*
	 * Permet de savoir si la valeur d'une carte donn�e est trop petite
	 * pour pouvoir ajouter la carte � une des s�ries du plateau
	 * 
	 * @param c
	 * 			La carte que l'on veut ajouter
	 * 
	 * @return false si la valeur de la carte n'est pas trop petite, false sinon
	 */
	public static boolean valeurTropPetite(Carte c) {
		for(int i=0; i<nbseries; ++i) {
			// Si la valeur de la carte c est plus grande que la derni�re carte
			// d'une des s�ries alors cette valeur n'est pas trop petite
			if(c.getValeur()>plateau.get(i).getLast().getValeur())
				return false;
		}
		return true;
	}

	/*
	 * R�cup�re l'indice de la s�rie ou doit �tre pos�e une carte donn�e
	 * 
	 * @param c
	 * 			La carte que l'on veut ajouter
	 * 
	 * @return l'indice de la s�rie o� poser la carte
	 */
	public static int getSerieOuPoser(Carte c) {
		int valMIN = Carte.getMAX(), difference, numChoix = 0;
		// Si la carte peut �tre ajout�e � une s�rie alors
		// il faut trouver une s�rie o� la carte peut effectivement �tre pos�e
		if(ajoutPossible(c)) {
			for(int i=0; i<nbseries; ++i) {
				difference = c.getValeur()-plateau.get(i).getLast().getValeur();
				// On cherche la s�rie o� la diff�rence de valeur entre la carte c
				// et la derni�re carte de la s�rie sera la plus faible
				if(valMIN>difference && difference>0 && plateau.get(i).ajoutPossible(c)) {
					valMIN = difference;
					numChoix = i;
				}
			}
		}
		// Si la carte ne peut pas �tre pos�e on trouve la s�rie o� elle aurait d� �tre ajout�e
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
	 * Ajoute une carte donn�e au plateau
	 * 
	 * @param c
	 * 			La carte que l'on veut poser
	 */
	public static void ajouterCarte(Carte c) {
		// On ajoute la carte � une s�rie d�termin�e automatiquement
		plateau.get(getSerieOuPoser(c)).ajouterCarte(c);
	}

	/*
	 * Remplace les cartes d'une s�rie d'un indice donn� par une carte donn�e �galement
	 * 
	 * @param indice
	 * 			Indice de la s�rie dont on veut remplacer les cartes
	 * 
	 * @param c
	 * 			Carte que l'on veut mettre � la place des cartes de la s�rie
	 */
	public static void remplacerSerie(int indice, Carte c) {
		plateau.get(indice).remplacer(c);
	}

	/*
	 * R�cup�re le nombre de s�ries pr�sentes sur le plateau
	 * 
	 * @return Le nombre de s�ries du plateau
	 */
	public static int getNbSeries() {
		return nbseries;
	}

	/*
	 * R�cup�re sur le plateau une s�rie d'un index donn�
	 * 
	 * @param indice
	 * 			Indice de la s�rie qu'on veut r�cup�rer
	 * 
	 * @return La s�rie correspondant � l'indice
	 */
	public static Serie getSerie(int indice) {
		return plateau.get(indice);
	}

	/*
	 * M�thode d'affichage du plateau
	 * Affiche toutes les s�ries du plateau
	 */
	public static void afficherPlateau() {
		for(int i=0; i<nbseries; ++i) {
			System.out.println(plateau.get(i));
		}
	}

}
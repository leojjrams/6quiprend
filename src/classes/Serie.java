package classes;

import java.util.ArrayList;
import java.util.List;

public class Serie {

	private int nbcartes;
	private List<Carte> serie; // ensemble des cartes de la série
	private int tetes; // nombre de têtes cumulées de la série
	private int numero;
	private static int cptnumero = 1; // compteur permettant d'initialiser les séries et leurs numéros
	// Nombre de cartes maximum et minimum dans une série
	private static final int MIN = 1;
	private static final int MAX = 5;

	/*
	 * Méthode d'initialisation d'une série
	 */
	public Serie() {
		this.nbcartes = MIN; 
		this.numero = cptnumero++; // On initialise son numéro à partir du compteur
		this.serie = new ArrayList<Carte>();
		this.serie.add(Paquet.piocher()); // On pioche une carte pour la mettre dans la série
		this.tetes += serie.get(0).getTetes();
	}

	/*
	 * Retire toutes les cartes d'une série pour y mettre une nouvelle carte
	 * 
	 * @param c
	 * 			La carte à placer
	 */
	public void remplacer(Carte c) {
		this.serie.set(0, c); // remplace la première carte par la carte c
		this.tetes = serie.get(0).getTetes();
		// Retire toutes les autres cartes
		for(int i=this.nbcartes-1; i>0; --i) {
			this.serie.remove(i);
		}
		this.nbcartes = 1;
	}

	/*
	 * Permet de savoir si une carte donnée peut être ajoutée ou non à la série
	 * 
	 * @param c
	 * 			La carte que l'on veut ajouter
	 * 
	 * @return true si la carte peut être ajoutée, false sinon
	 */
	public boolean ajoutPossible(Carte c) {
		// Si la valeur de la carte est supérieure à celle de la dernière carte de la série
		// et si la série a un nombre de cartes suffisamment faible alors la carte peut être ajoutée
		if(this.getLast().getValeur()<c.getValeur() && this.nbcartes<MAX)
			return true;
		return false;
	}

	/*
	 * Ajoute une carte donnée à la série
	 * 
	 * @param c
	 * 			La carte à ajouter
	 */
	public void ajouterCarte(Carte c) {
		this.serie.add(c);
		++this.nbcartes;
		this.tetes += c.getTetes();
	}

	/*
	 * Récupère le nombre de cartes d'une série
	 * 
	 * @return Le nombre de cartes de la série
	 */
	public int getNbCartes() {
		return this.nbcartes;
	}

	/*
	 * Récupère le nombre de têtes cumulées d'une série
	 * 
	 * @return le nombre de têtes de la série
	 */
	public int getTetes() {
		return this.tetes;
	}

	/*
	 * Récupère la dernière carte d'une série
	 * 
	 * @return La dernière carte de la série
	 */
	public Carte getLast() {
		return this.serie.get(nbcartes-1);
	}
	
	/*
	 * Récupère le nombre de cartes que peut contenir une série au maximum
	 * 
	 * @return Le nombre maximum de cartes d'une série
	 */
	public static int getMAX() {
		return MAX;
	}

	/*
	 * Récupère le numéro d'une série sur la plateau
	 * 
	 * @return le numéro de la série
	 */
	public int getNumero() {
		return this.numero;
	}

	/*
	 * Méthode d'affichage de la série
	 * Affiche le numéro de la série suivi de toutes ses cartes
	 */
	public String toString() {
		String s = "- série n° " + this.numero + " : ";
		for(int i=0; i<this.nbcartes; ++i) {
			if(i>0)
				s += ", ";
			s += this.serie.get(i);
		}
		return s;
	}

}
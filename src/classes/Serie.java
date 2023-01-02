package classes;

import java.util.ArrayList;
import java.util.List;

public class Serie {

	private int nbcartes;
	private List<Carte> serie; // ensemble des cartes de la s�rie
	private int tetes; // nombre de t�tes cumul�es de la s�rie
	private int numero;
	private static int cptnumero = 1; // compteur permettant d'initialiser les s�ries et leurs num�ros
	// Nombre de cartes maximum et minimum dans une s�rie
	private static final int MIN = 1;
	private static final int MAX = 5;

	/*
	 * M�thode d'initialisation d'une s�rie
	 */
	public Serie() {
		this.nbcartes = MIN; 
		this.numero = cptnumero++; // On initialise son num�ro � partir du compteur
		this.serie = new ArrayList<Carte>();
		this.serie.add(Paquet.piocher()); // On pioche une carte pour la mettre dans la s�rie
		this.tetes += serie.get(0).getTetes();
	}

	/*
	 * Retire toutes les cartes d'une s�rie pour y mettre une nouvelle carte
	 * 
	 * @param c
	 * 			La carte � placer
	 */
	public void remplacer(Carte c) {
		this.serie.set(0, c); // remplace la premi�re carte par la carte c
		this.tetes = serie.get(0).getTetes();
		// Retire toutes les autres cartes
		for(int i=this.nbcartes-1; i>0; --i) {
			this.serie.remove(i);
		}
		this.nbcartes = 1;
	}

	/*
	 * Permet de savoir si une carte donn�e peut �tre ajout�e ou non � la s�rie
	 * 
	 * @param c
	 * 			La carte que l'on veut ajouter
	 * 
	 * @return true si la carte peut �tre ajout�e, false sinon
	 */
	public boolean ajoutPossible(Carte c) {
		// Si la valeur de la carte est sup�rieure � celle de la derni�re carte de la s�rie
		// et si la s�rie a un nombre de cartes suffisamment faible alors la carte peut �tre ajout�e
		if(this.getLast().getValeur()<c.getValeur() && this.nbcartes<MAX)
			return true;
		return false;
	}

	/*
	 * Ajoute une carte donn�e � la s�rie
	 * 
	 * @param c
	 * 			La carte � ajouter
	 */
	public void ajouterCarte(Carte c) {
		this.serie.add(c);
		++this.nbcartes;
		this.tetes += c.getTetes();
	}

	/*
	 * R�cup�re le nombre de cartes d'une s�rie
	 * 
	 * @return Le nombre de cartes de la s�rie
	 */
	public int getNbCartes() {
		return this.nbcartes;
	}

	/*
	 * R�cup�re le nombre de t�tes cumul�es d'une s�rie
	 * 
	 * @return le nombre de t�tes de la s�rie
	 */
	public int getTetes() {
		return this.tetes;
	}

	/*
	 * R�cup�re la derni�re carte d'une s�rie
	 * 
	 * @return La derni�re carte de la s�rie
	 */
	public Carte getLast() {
		return this.serie.get(nbcartes-1);
	}
	
	/*
	 * R�cup�re le nombre de cartes que peut contenir une s�rie au maximum
	 * 
	 * @return Le nombre maximum de cartes d'une s�rie
	 */
	public static int getMAX() {
		return MAX;
	}

	/*
	 * R�cup�re le num�ro d'une s�rie sur la plateau
	 * 
	 * @return le num�ro de la s�rie
	 */
	public int getNumero() {
		return this.numero;
	}

	/*
	 * M�thode d'affichage de la s�rie
	 * Affiche le num�ro de la s�rie suivi de toutes ses cartes
	 */
	public String toString() {
		String s = "- s�rie n� " + this.numero + " : ";
		for(int i=0; i<this.nbcartes; ++i) {
			if(i>0)
				s += ", ";
			s += this.serie.get(i);
		}
		return s;
	}

}
package classes;

public class Carte implements Comparable<Carte>{
	
	private int valeur;
	private int tetes;
	private static final int MIN = 1;
	private static final int MAX = 104;
	private static int cptcartes = MIN; // compteur permettant d'initialiser un paquet de cartes
	
	/*
	 * Trouve le nombre de t�tes de boeufs que doit avoir une carte en fonction de sa valeur
	 *
	 * @return Le nombre de d�placements de disques effectu�s
	 */
	private int setTetes() {
		int nbtetes = 0;
		String chiffres = String.valueOf(this.valeur); // Transforme la valeur de la carte en String pour pouvoir lire ses chiffres
		// Si tous les chiffres sont identiques alors on augmente les t�tes de 5
		if(chiffres.length()==2 && chiffres.charAt(0)==chiffres.charAt(1)) { 
			nbtetes += 5;
		}
		// Si le dernier chiffre est un 5 alors on augmente les t�tes de 2
		if(chiffres.charAt(chiffres.length()-1)=='5') {
			nbtetes += 2;
		}
		// Si le dernier chiffre est un 0 alors on augmente les t�tes de 3
		else if(chiffres.charAt(chiffres.length()-1)=='0') {
			nbtetes += 3;
		}
		// Si aucune condition n'est remplie alors le nombre de t�tes est de 1 
		if(nbtetes==0) {
			++nbtetes;
		}
		return nbtetes;
	}
	
	/*
	 * M�thode d'initialisation d'une carte
	 */
	public Carte() {
		this.valeur = cptcartes++; // on initialise sa valeur gr�ce au compteur
		this.tetes = this.setTetes();
	}
	
	/*
	 * R�cup�re la valeur d'une carte
	 * 
	 * @return La valeur de la carte
	 */
	public int getValeur() {
		return this.valeur;
	}
	
	/*
	 * R�cup�re le nombre de t�tes d'une carte
	 * 
	 * @return Le nombre de t�tes de la carte
	 */
	public int getTetes() {
		return this.tetes;
	}
	
	/*
	 * R�cup�re la valeur minimale que peut prendre une carte
	 * 
	 * @return La valeur minimale d'une carte
	 */
	public static int getMIN() {
		return MIN;
	}
	
	/*
	 * R�cup�re la valeur maximale que peut prendre une carte
	 * 
	 * @return La valeur maximale d'une carte
	 */
	public static int getMAX() {
		return MAX;
	}
	
	/*
	 * M�thode d'affichage d'une carte qui affiche sa valeur avec son nombre de t�tes entre parenth�ses
	 */
	public String toString() {
		return (this.getValeur() + " (" + this.getTetes() + ")");
	}
	
	/*
	 * M�thode de comparaison de deux cartes � partir de leurs valeurs respectives
	 */
	public int compareTo(Carte c) {
		return (this.getValeur() - c.getValeur());
	}

	
}
package classes;

public class Carte implements Comparable<Carte>{
	
	private int valeur;
	private int tetes;
	private static final int MIN = 1;
	private static final int MAX = 104;
	private static int cptcartes = MIN; // compteur permettant d'initialiser un paquet de cartes
	
	/*
	 * Trouve le nombre de têtes de boeufs que doit avoir une carte en fonction de sa valeur
	 *
	 * @return Le nombre de déplacements de disques effectués
	 */
	private int setTetes() {
		int nbtetes = 0;
		String chiffres = String.valueOf(this.valeur); // Transforme la valeur de la carte en String pour pouvoir lire ses chiffres
		// Si tous les chiffres sont identiques alors on augmente les têtes de 5
		if(chiffres.length()==2 && chiffres.charAt(0)==chiffres.charAt(1)) { 
			nbtetes += 5;
		}
		// Si le dernier chiffre est un 5 alors on augmente les têtes de 2
		if(chiffres.charAt(chiffres.length()-1)=='5') {
			nbtetes += 2;
		}
		// Si le dernier chiffre est un 0 alors on augmente les têtes de 3
		else if(chiffres.charAt(chiffres.length()-1)=='0') {
			nbtetes += 3;
		}
		// Si aucune condition n'est remplie alors le nombre de têtes est de 1 
		if(nbtetes==0) {
			++nbtetes;
		}
		return nbtetes;
	}
	
	/*
	 * Méthode d'initialisation d'une carte
	 */
	public Carte() {
		this.valeur = cptcartes++; // on initialise sa valeur grâce au compteur
		this.tetes = this.setTetes();
	}
	
	/*
	 * Récupère la valeur d'une carte
	 * 
	 * @return La valeur de la carte
	 */
	public int getValeur() {
		return this.valeur;
	}
	
	/*
	 * Récupère le nombre de têtes d'une carte
	 * 
	 * @return Le nombre de têtes de la carte
	 */
	public int getTetes() {
		return this.tetes;
	}
	
	/*
	 * Récupère la valeur minimale que peut prendre une carte
	 * 
	 * @return La valeur minimale d'une carte
	 */
	public static int getMIN() {
		return MIN;
	}
	
	/*
	 * Récupère la valeur maximale que peut prendre une carte
	 * 
	 * @return La valeur maximale d'une carte
	 */
	public static int getMAX() {
		return MAX;
	}
	
	/*
	 * Méthode d'affichage d'une carte qui affiche sa valeur avec son nombre de têtes entre parenthèses
	 */
	public String toString() {
		return (this.getValeur() + " (" + this.getTetes() + ")");
	}
	
	/*
	 * Méthode de comparaison de deux cartes à partir de leurs valeurs respectives
	 */
	public int compareTo(Carte c) {
		return (this.getValeur() - c.getValeur());
	}

	
}
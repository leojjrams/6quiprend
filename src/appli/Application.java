package appli;

import classes.*;

public class Application {
	
	// nom du fichier permettant de r�cup�rer les noms des joueurs
	public static String nomFichier = "config.txt";
	
	/*
	 * R�cup�re le nom du fichier permettant de r�cup�rer les noms des joueurs
	 * 
	 * @return Le nom du fichier avec les noms des joueurs
	 */
	public static String getNomFichier() {
		return nomFichier;
	}
	
	/*
	 * Fonction main
	 * Initialise et commence une partie
	 */
	public static void main(String args[]) {
		new Jeu(nomFichier);
		Jeu.Partie();
	}
	
}

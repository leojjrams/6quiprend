package classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import util.Console;

public class Jeu {

	private static List<Joueur> joueurs; // ensemble des joueurs participant � la partie
	private static int nbjoueurs;
	private static final int JMIN = 2; // nombre minimum de joueurs dans une partie
	private static final int JMAX = 10; // nombre maximum de joueurs dans une partie

	/*
	 * M�thode d'initialisation du jeu
	 * 
	 * @param nom
	 * 			Nom du fichier permettant de r�cup�rer les noms des joueurs
	 */
	public Jeu(String nom) {
		new Paquet();
		Paquet.melanger();
		new Plateau();
		joueurs = new ArrayList<Joueur>();
		// On r�cup�re les joueurs � partir du fichier dont le nom est donn�
		try {
			Scanner in = new Scanner(new File(nom));
			while(in.hasNext()) {
				++nbjoueurs;
				// Si le nombre de joueurs est trop grand on affiche un message
				// d'erreur et on arr�te le programme
				if(nbjoueurs>JMAX) {
					System.out.println("Il y a trop de joueurs");
					System.exit(0);
				}
				Joueur j = new Joueur(in.next());
				joueurs.add(j);
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Impossible d'ouvrir le fichier");
		}
		// Si le nombre de joueurs est trop petit on affiche un message
		// d'erreur et on arr�te le programme
		if(nbjoueurs<JMIN) {
			System.out.println("Il n'y a pas assez de joueurs");
			System.exit(0);
		}
		// Message d'introduction du jeu
		else {
			System.out.print("Les " + nbjoueurs + " joueurs sont ");
			for(int i=0; i<nbjoueurs;++i) {
				if(i==nbjoueurs-1)
					System.out.print(" et ");
				System.out.print(joueurs.get(i));
				if(i<(nbjoueurs-2))
					System.out.print(", ");
			}
			System.out.println(". Merci de jouer � 6 qui prend !");
		}
	}
	
	/*
	 * R�cup�re le nombre de joueurs participant � la partie
	 * 
	 * @return Le nombre de joueurs dans la partie
	 */
	public static int getNbJoueurs() {
		return nbjoueurs;
	}

	/*
	 * Permet de jouer un tour complet
	 */
	public static void jouerTour() {
		for(int i=0; i<nbjoueurs; ++i) {
			if(i>0)
				// A chaque fois qu'un joueur doit jouer, on nettoie l'�cran
				Console.clearScreen();
			System.out.println("A " + joueurs.get(i) + " de jouer.");
			// Quand un joueur doit jouer, on attend qu'il se manifeste en appuyant sur une touche
			Console.pause();
			Plateau.afficherPlateau();
			joueurs.get(i).afficherMain();
			joueurs.get(i).carteTour();
		}
		classerJoueurs();
		Console.clearScreen();
		// Si certains joueurs ont choisi une carte dont la valeur est trop petite
		// alors on pr�vient que les cartes vont �tre pos�es car le joueur en question
		// devra choisir une carte � r�cup�rer
		if(existeValeursTropPetites()) {
			System.out.print("Les cartes ");
			for(int i=0; i<nbjoueurs; ++i) {
				System.out.print(joueurs.get(i).getCarteJouee().getValeur() + " (" + joueurs.get(i) + ")");
				if(i<nbjoueurs-2)
					System.out.print(", ");
				if(i==nbjoueurs-2)
					System.out.print(" et ");
			}
			System.out.println(" vont �tre pos�es.");
		}
		// On joue le tour de chaque joueur
		for(int i=0; i<nbjoueurs; ++i) {
			joueurs.get(i).jouerTour();
		}
		finTour();
	}

	/*
	 * Termine un tour
	 */
	private static void finTour() {
		System.out.print("Les cartes ");
		for(int i=0; i<nbjoueurs; ++i) {
			System.out.print(joueurs.get(i).getCarteJouee().getValeur() + " (" + joueurs.get(i) + ")");
			if(i<nbjoueurs-2)
				System.out.print(", ");
			if(i==nbjoueurs-2)
				System.out.print(" et ");
		}
		System.out.println(" ont �t� pos�es.");
		Plateau.afficherPlateau();
		// On affiche les t�tes de boeufs ramass�es par les joueurs
		boolean TetesRamassees = false;
		for(int i=0; i<nbjoueurs; ++i) {
			if(joueurs.get(i).getTetesRamassees()>0) {
				TetesRamassees = true;
				System.out.println(joueurs.get(i) + " a ramass� " + joueurs.get(i).getTetesRamassees() + " t�tes de boeufs");
			}
		}
		if(!TetesRamassees)
			System.out.println("Aucun joueur ne ramasse de t�te de boeufs.");
		// On termine le tour de chaque joueur
		for(int i=0; i<nbjoueurs; ++i) {
			joueurs.get(i).finTour();
		}
	}
	
	/*
	 * Classe les joueurs en fonction de la carte qu'ils ont choisi de jouer pendant le tour
	 */
	private static void classerJoueurs() {
		// On parcourt la liste des joueurs en �changeant les positions
		// de deux joueurs s'ils ne sont pas � leur place
		for(int i=0; i<nbjoueurs; ++i) {
			for(int k=0; k<nbjoueurs-1; ++k) {
				// Si un joueur est cens� jouer avant celui qui se trouve avant lui
				// dans la liste, alors on �change leurs positions
				if(!(joueurs.get(k).joueAvant(joueurs.get(k+1))))
					Collections.swap(joueurs, k, k+1);
			}
		}
	}

	/*
	 * Permet de savoir si certains joueurs ont choisi de poser une carte
	 * dont la valeur est trop petite pour �tre pos�e sur le plateau
	 * 
	 * @return true si un joueur a choisi une carte dont la valeur est trop petite, false sinon
	 */
	public static boolean existeValeursTropPetites() {
		for(int i=0; i<nbjoueurs; ++i) {
			if(Plateau.valeurTropPetite(joueurs.get(i).getCarteJouee()))
				return true;
		}
		return false;
	}

	/*
	 * Permet de jouer une partie compl�te
	 */
	public static void Partie() {
		// Joue des tours jusqu'� ce que les joueurs n'aient plus de cartes en main
		for(int i=0; i<Joueur.getTailleMain(); ++i) {
			jouerTour();
		}
		finPartie();
	}
	
	/*
	 * Termine la partie
	 */
	public static void finPartie() {
		// Affiche les scores de tous les joueurs dans l'ordre croissant
		// En cas d'�galit�, les joueurs sont class�s par ordre alphab�tique
		System.out.println("** Score final");
		Collections.sort(joueurs);
		for(int i=0; i<nbjoueurs; ++i)
			System.out.println(joueurs.get(i) + " a ramass� " + joueurs.get(i).getScore() + " t�tes de boeufs");
	}

}
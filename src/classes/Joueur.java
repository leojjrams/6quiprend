package classes;

import java.util.*;

public class Joueur implements Comparable<Joueur>{

	private List<Carte> main; // ensemble des cartes dans la main d'un joueur
	private int cartesMain; // nombre de cartes dans la main d'un joueur
	private int score; // nombre de t�tes ramass�es par le joueur au total
	private String nom;
	private Carte carteJouee; // carte que le joueur a d�cid� de jouer pour le tour en cours
	private int tetesRamassees; // nombre de t�tes ramass�es par le joueur durant le tour en cours
	private static final int tailleMain = 10; // nombre de cartes au maximum dans la main d'un joueur

	/*
	 * M�thode d'initialisation d'un joueur
	 * 
	 * @param Nom
	 * 			le nom du joueur
	 */
	public Joueur(String Nom) {
		this.score = 0;
		this.nom = Nom;
		this.tetesRamassees = 0;
		this.cartesMain = tailleMain;
		main = new ArrayList<Carte>();
		this.piocher(); // pioche jusqu'� remplir sa main
	}
	
	/*
	 * Permet � un joueur de piocher jusqu'� ce que sa main soit remplie
	 */
	public void piocher() {
		for(int i=0; i<tailleMain; ++i) {
			this.main.add(Paquet.piocher());
		}
		Collections.sort(this.main); // les cartes sont rang�es dans l'ordre croissant de leurs valeurs
	}

	/*
	 * M�thode d'affichage de la main d'un joueur
	 * Affiche toutes les cartes de la main du joueur
	 */
	public void afficherMain() {
		System.out.print("- Vos cartes : ");
		for (int i=0; i<this.cartesMain; ++i) {
			if(i>0)
				System.out.print(", ");
			System.out.print(this.main.get(i));
		}
		System.out.print("\n");
	}

	/*
	 * Ajoute des points � un joueur
	 * 
	 * @param points
	 * 			Les points � ajouter
	 */
	public void ajouterPoints(int points) {
		this.score += points;
	}

	/*
	 * Permet � un joueur de r�cup�rer les t�tes d'une s�rie dont l'indice est donn�
	 * 
	 * @param indice
	 * 			Indice de la s�rie dont on veut r�cup�rer les t�tes
	 * 
	 * @param c
	 * 			Carte que le joueur ajoute pour r�cup�rer les t�tes
	 */
	private void recupTetes(int indice, Carte c) {
		this.tetesRamassees = Plateau.getSerie(indice).getTetes();
		this.score += this.tetesRamassees;
		Plateau.remplacerSerie(indice, c); // on remplace les cartes de la s�rie par la carte ajout�e par le joueur
	}

	/*
	 * Permet � un joueur de r�cup�rer une s�rie
	 * 
	 * @param c
	 * 			La carte que le joueur ajoute pour r�cup�rer une s�rie
	 */
	private void recupSerie(Carte c) {
		// Si la valeur de la carte est trop petite alors le joueur doit choisir une s�rie � r�cup�rer
		if(Plateau.valeurTropPetite(c)) {
			System.out.println("Pour poser la carte " + this.carteJouee.getValeur() + ", " + this.nom + " doit choisir la s�rie qu'il va ramasser.");
			Plateau.afficherPlateau();
			System.out.print("Saisissez votre choix : ");
			Scanner sc = new Scanner(System.in);
			int numChoix = 1;
			boolean erreur = false;
			// Tant qu'autre chose qu'un entier est saisi ou que le num�ro saisi est invalide,
			// le joueur doit saisir un nouveau num�ro de s�rie � r�cup�rer
			try {
				numChoix = sc.nextInt();
				if(numChoix>Plateau.getNbSeries() || numChoix<1)
					erreur = true;
			} catch(Exception e) {
				sc.nextLine();
				erreur = true;
			}
			while(erreur) {
				System.out.print("Ce n'est pas une s�rie valide, saisissez votre choix : ");
				try {
					numChoix = sc.nextInt();
					if(numChoix<=Plateau.getNbSeries() && numChoix>=1)
						erreur = false;
				} catch(Exception e) {
					sc.nextLine();
					continue;
				}
			}
			sc.close();
			// Une fois un num�ro valide saisi, le joueur r�cup�re les t�tes de la s�rie choisie
			this.recupTetes(numChoix-1, c);
		}
		// Si la valeur de la carte n'est pas trop petite alors la s�rie � r�cup�rer est choisie automatiquement
		else {
			int indice = Plateau.getSerieOuPoser(c);
			Plateau.getSerie(indice).ajouterCarte(c);
			// les cartes de la s�rie sont remplac�es par la prochaine carte du paquet
			this.recupTetes(indice, Paquet.piocher()); 
		}
	}

	/*
	 * Permet � un joueur d'ajouter une carte au plateau
	 * 
	 * @param c
	 * 			La carte que le joueur veut ajouter
	 */
	public void ajouterCarte(Carte c) {
		// Si la carte peut �tre ajout�e alors on l'ajoute
		if(Plateau.ajoutPossible(c))
			Plateau.ajouterCarte(c);
		// Si la carte ne peut pas �tre ajout�e alors le joueur r�cup�re une s�rie
		else
			this.recupSerie(c);
		this.main.remove(this.getIndiceCarte(c.getValeur()));
		--this.cartesMain;
	}

	/*
	 * R�cup�re le score total d'un joueur
	 * 
	 * @return Le score du joueur
	 */
	public int getScore() {
		return this.score;
	}

	/*
	 * R�cup�re l'indice d'une carte dans la main d'un joueur
	 * 
	 * @param numero
	 * 			Valeur de la carte dont on veut conna�tre l'indice
	 * 
	 * @return L'indice de la carte si elle est dans la main du joueur, -1 sinon
	 */
	public int getIndiceCarte(int numero) {
		for(int i=0; i<this.cartesMain; ++i) {
			if(numero==this.main.get(i).getValeur())
				return i;
		}
		return -1; // retourne -1 si le joueur n'a pas la carte donn�e en main
	}
	
	/*
	 * R�cup�re une carte d'un indice donn� dans la main du joueur
	 * 
	 * @param indice
	 * 			Indice de la carte que l'on veut r�cup�rer
	 * 
	 * @return La carte dont on a donn� l'indice
	 */
	public Carte getCarte(int indice) {
		return this.main.get(indice);
	}

	/*
	 * R�cup�re le nombre de cartes que peut avoir un joueur dans sa main au maximum
	 * 
	 * @return Le nombre max de carte dans une main
	 */
	public static int getTailleMain() {
		return tailleMain;
	}
	
	/*
	 * R�cup�re le nombre de cartes au total dans la main d'un joueur
	 * 
	 * @return Le nombre de cartes dans la main du joueur
	 */
	public int getNbCartes() {
		return this.cartesMain;
	}

	/*
	 * Permet � un joueur de choisir la carte qu'il va jouer pendant un tour
	 * 
	 * @return La carte que le joueur a choisi
	 */
	private Carte choisirCarte() {
		System.out.print("Saisissez votre choix : ");
		Scanner sc = new Scanner(System.in);
		int numero = 0;
		boolean erreur = false;
		// Tant que le joueur saisit autre chose qu'un entier
		// ou qu'il n'a pas la carte saisie en main, il doit ressaisir une carte
		try {
			numero = sc.nextInt();
			if(this.getIndiceCarte(numero)==-1)
				erreur = true;
		} catch(Exception e) {
			sc.nextLine();
			erreur = true;
		}
		while(erreur) {
			System.out.print("Vous n'avez pas cette carte, saisissez votre choix : ");
			try {
				numero = sc.nextInt();
				if(this.getIndiceCarte(numero)!=-1)
					erreur = false;
			} catch(Exception e) {
				sc.nextLine();
				continue;
			}
		}
		sc.close();
		return this.main.get(this.getIndiceCarte(numero));
	}

	/*
	 * Attribue � un joueur une carte jou�e pendant le tour en cours
	 */
	public void carteTour() {
		this.carteJouee = this.choisirCarte();
	}
	
	/*
	 * R�cup�re la carte jou�e par un joueur pendant le tour en cours
	 * 
	 * @return La carte jou�e par le joueur
	 */
	public Carte getCarteJouee() {
		return this.carteJouee;
	}

	/*
	 * Permet � un joueur de jouer son tour en ajoutant une carte
	 */
	public void jouerTour() {
		// ajoute sur le plateau la carte que le joueur a choisi de jouer pendant ce tour
		this.ajouterCarte(this.main.get(this.getIndiceCarte(this.carteJouee.getValeur())));
	}
	
	/*
	 * Termine le tour d'un joueur
	 */
	public void finTour() {
		this.carteJouee = null;
		this.tetesRamassees = 0;
	}
	
	/*
	 * R�cup�re le nombre de t�tes ramass�es par un joueur pendant le tour en cours
	 * 
	 * @return Le nombre de t�tes ramass�es par le joueur
	 */
	public int getTetesRamassees() {
		return this.tetesRamassees;
	}

	/*
	 * M�thode d'affichage d'un joueur
	 * Affiche le nom du joueur
	 */
	public String toString() {
		return this.nom;
	}
	
	/*
	 * Permet de savoir si la main d'un joueur est tri�e
	 * 
	 * @return false si la main n'est pas tri�e, true sinon
	 */
	public boolean mainTriee() {
		for(int i=0; i<tailleMain-1; ++i) {
			// Si une valeur donn�e est suivie d'une valeur inf�rieure � celle-ci, alors la main n'est pas tri�e
			if(this.main.get(i+1).getValeur()<this.main.get(i).getValeur())
				return false;
		}
		return true;
	}

	/*
	 * M�thode de comparaison des joueurs
	 * Compare les joueurs en fonction de leur score et classe les joueurs par ordre alphab�tique en cas d'�galit�
	 * 
	 * @param j
	 * 			Joueur qu'on compare au joueur donn�
	 */
	public int compareTo(Joueur j) {
		if((this.score - j.score)==0 && !(this.nom.equals(j.nom))) {
			int i=0;
			while(this.nom.charAt(i)==j.nom.charAt(i))
				++i;
			return(this.nom.charAt(i)-j.nom.charAt(i));
		}
		return(this.score - j.score);
	}
	
	/*
	 * Permet de savoir si un joueur joue avant un autre joueur
	 * 
	 * @param j
	 * 			Joueur dont on veut savoir s'il joue apr�s le joueur donn�
	 * 
	 * @return true si le premier joueur joue avant l'autre, false sinon
	 */
	public boolean joueAvant(Joueur j) {
		// Si la valeur de la carte jou�e par le premier joueur est inf�rieure �
		// celle jou�e par le deuxi�me joueur, alors le premier joueur joue avant
		return this.carteJouee.getValeur()<j.carteJouee.getValeur();
	}

}
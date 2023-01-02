package classes;

import java.util.*;

public class Joueur implements Comparable<Joueur>{

	private List<Carte> main; // ensemble des cartes dans la main d'un joueur
	private int cartesMain; // nombre de cartes dans la main d'un joueur
	private int score; // nombre de têtes ramassées par le joueur au total
	private String nom;
	private Carte carteJouee; // carte que le joueur a décidé de jouer pour le tour en cours
	private int tetesRamassees; // nombre de têtes ramassées par le joueur durant le tour en cours
	private static final int tailleMain = 10; // nombre de cartes au maximum dans la main d'un joueur

	/*
	 * Méthode d'initialisation d'un joueur
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
		this.piocher(); // pioche jusqu'à remplir sa main
	}
	
	/*
	 * Permet à un joueur de piocher jusqu'à ce que sa main soit remplie
	 */
	public void piocher() {
		for(int i=0; i<tailleMain; ++i) {
			this.main.add(Paquet.piocher());
		}
		Collections.sort(this.main); // les cartes sont rangées dans l'ordre croissant de leurs valeurs
	}

	/*
	 * Méthode d'affichage de la main d'un joueur
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
	 * Ajoute des points à un joueur
	 * 
	 * @param points
	 * 			Les points à ajouter
	 */
	public void ajouterPoints(int points) {
		this.score += points;
	}

	/*
	 * Permet à un joueur de récupérer les têtes d'une série dont l'indice est donné
	 * 
	 * @param indice
	 * 			Indice de la série dont on veut récupérer les têtes
	 * 
	 * @param c
	 * 			Carte que le joueur ajoute pour récupérer les têtes
	 */
	private void recupTetes(int indice, Carte c) {
		this.tetesRamassees = Plateau.getSerie(indice).getTetes();
		this.score += this.tetesRamassees;
		Plateau.remplacerSerie(indice, c); // on remplace les cartes de la série par la carte ajoutée par le joueur
	}

	/*
	 * Permet à un joueur de récupérer une série
	 * 
	 * @param c
	 * 			La carte que le joueur ajoute pour récupérer une série
	 */
	private void recupSerie(Carte c) {
		// Si la valeur de la carte est trop petite alors le joueur doit choisir une série à récupérer
		if(Plateau.valeurTropPetite(c)) {
			System.out.println("Pour poser la carte " + this.carteJouee.getValeur() + ", " + this.nom + " doit choisir la série qu'il va ramasser.");
			Plateau.afficherPlateau();
			System.out.print("Saisissez votre choix : ");
			Scanner sc = new Scanner(System.in);
			int numChoix = 1;
			boolean erreur = false;
			// Tant qu'autre chose qu'un entier est saisi ou que le numéro saisi est invalide,
			// le joueur doit saisir un nouveau numéro de série à récupérer
			try {
				numChoix = sc.nextInt();
				if(numChoix>Plateau.getNbSeries() || numChoix<1)
					erreur = true;
			} catch(Exception e) {
				sc.nextLine();
				erreur = true;
			}
			while(erreur) {
				System.out.print("Ce n'est pas une série valide, saisissez votre choix : ");
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
			// Une fois un numéro valide saisi, le joueur récupère les têtes de la série choisie
			this.recupTetes(numChoix-1, c);
		}
		// Si la valeur de la carte n'est pas trop petite alors la série à récupérer est choisie automatiquement
		else {
			int indice = Plateau.getSerieOuPoser(c);
			Plateau.getSerie(indice).ajouterCarte(c);
			// les cartes de la série sont remplacées par la prochaine carte du paquet
			this.recupTetes(indice, Paquet.piocher()); 
		}
	}

	/*
	 * Permet à un joueur d'ajouter une carte au plateau
	 * 
	 * @param c
	 * 			La carte que le joueur veut ajouter
	 */
	public void ajouterCarte(Carte c) {
		// Si la carte peut être ajoutée alors on l'ajoute
		if(Plateau.ajoutPossible(c))
			Plateau.ajouterCarte(c);
		// Si la carte ne peut pas être ajoutée alors le joueur récupère une série
		else
			this.recupSerie(c);
		this.main.remove(this.getIndiceCarte(c.getValeur()));
		--this.cartesMain;
	}

	/*
	 * Récupère le score total d'un joueur
	 * 
	 * @return Le score du joueur
	 */
	public int getScore() {
		return this.score;
	}

	/*
	 * Récupère l'indice d'une carte dans la main d'un joueur
	 * 
	 * @param numero
	 * 			Valeur de la carte dont on veut connaître l'indice
	 * 
	 * @return L'indice de la carte si elle est dans la main du joueur, -1 sinon
	 */
	public int getIndiceCarte(int numero) {
		for(int i=0; i<this.cartesMain; ++i) {
			if(numero==this.main.get(i).getValeur())
				return i;
		}
		return -1; // retourne -1 si le joueur n'a pas la carte donnée en main
	}
	
	/*
	 * Récupère une carte d'un indice donné dans la main du joueur
	 * 
	 * @param indice
	 * 			Indice de la carte que l'on veut récupérer
	 * 
	 * @return La carte dont on a donné l'indice
	 */
	public Carte getCarte(int indice) {
		return this.main.get(indice);
	}

	/*
	 * Récupère le nombre de cartes que peut avoir un joueur dans sa main au maximum
	 * 
	 * @return Le nombre max de carte dans une main
	 */
	public static int getTailleMain() {
		return tailleMain;
	}
	
	/*
	 * Récupère le nombre de cartes au total dans la main d'un joueur
	 * 
	 * @return Le nombre de cartes dans la main du joueur
	 */
	public int getNbCartes() {
		return this.cartesMain;
	}

	/*
	 * Permet à un joueur de choisir la carte qu'il va jouer pendant un tour
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
	 * Attribue à un joueur une carte jouée pendant le tour en cours
	 */
	public void carteTour() {
		this.carteJouee = this.choisirCarte();
	}
	
	/*
	 * Récupère la carte jouée par un joueur pendant le tour en cours
	 * 
	 * @return La carte jouée par le joueur
	 */
	public Carte getCarteJouee() {
		return this.carteJouee;
	}

	/*
	 * Permet à un joueur de jouer son tour en ajoutant une carte
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
	 * Récupère le nombre de têtes ramassées par un joueur pendant le tour en cours
	 * 
	 * @return Le nombre de têtes ramassées par le joueur
	 */
	public int getTetesRamassees() {
		return this.tetesRamassees;
	}

	/*
	 * Méthode d'affichage d'un joueur
	 * Affiche le nom du joueur
	 */
	public String toString() {
		return this.nom;
	}
	
	/*
	 * Permet de savoir si la main d'un joueur est triée
	 * 
	 * @return false si la main n'est pas triée, true sinon
	 */
	public boolean mainTriee() {
		for(int i=0; i<tailleMain-1; ++i) {
			// Si une valeur donnée est suivie d'une valeur inférieure à celle-ci, alors la main n'est pas triée
			if(this.main.get(i+1).getValeur()<this.main.get(i).getValeur())
				return false;
		}
		return true;
	}

	/*
	 * Méthode de comparaison des joueurs
	 * Compare les joueurs en fonction de leur score et classe les joueurs par ordre alphabétique en cas d'égalité
	 * 
	 * @param j
	 * 			Joueur qu'on compare au joueur donné
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
	 * 			Joueur dont on veut savoir s'il joue après le joueur donné
	 * 
	 * @return true si le premier joueur joue avant l'autre, false sinon
	 */
	public boolean joueAvant(Joueur j) {
		// Si la valeur de la carte jouée par le premier joueur est inférieure à
		// celle jouée par le deuxième joueur, alors le premier joueur joue avant
		return this.carteJouee.getValeur()<j.carteJouee.getValeur();
	}

}
package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import classes.Joueur;
import classes.Paquet;
import classes.Plateau;

public class JoueurTest {

	@Test
	public void test() {
		new Paquet();
		Joueur j = new Joueur("J1");
		new Plateau();
		// Vérifie que la main du joueur a été correctement triée
		assertTrue(j.mainTriee());
		j.ajouterCarte(j.getCarte(0));
		// Vérifie que le nombre de cartes du joueur a bien diminué et que son score est de 0
		assertEquals(j.getNbCartes(),Joueur.getTailleMain()-1);
		assertEquals(j.getScore(),0);
		// Vérifie que l'ajout de points fontionne correctement
		j.ajouterPoints(10);
		assertEquals(j.getScore(),10);
	}

}

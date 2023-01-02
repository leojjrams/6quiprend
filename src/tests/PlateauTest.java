package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import classes.Plateau;
import classes.Paquet;
import classes.Carte;

public class PlateauTest {

	@Test
	public void test() {
		new Paquet();
		Carte c1 = Paquet.piocher();
		new Plateau();
		// Vérifie que la dernière carte du paquet (104) peut être ajoutée au plateau
		// Comme elle a la plus grande valeur possible, elle doit pouvoir être ajoutée
		assertTrue(Plateau.ajoutPossible(c1));
		// Vérifie que la valeur de la carte n'est pas trop petite
		assertFalse(Plateau.valeurTropPetite(c1));
		Carte c2 = Paquet.piocher();
		// Vérifie que la série où poser la carte est la première
		assertEquals(Plateau.getSerieOuPoser(c2),0);
	}

}

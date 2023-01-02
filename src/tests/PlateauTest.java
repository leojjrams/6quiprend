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
		// V�rifie que la derni�re carte du paquet (104) peut �tre ajout�e au plateau
		// Comme elle a la plus grande valeur possible, elle doit pouvoir �tre ajout�e
		assertTrue(Plateau.ajoutPossible(c1));
		// V�rifie que la valeur de la carte n'est pas trop petite
		assertFalse(Plateau.valeurTropPetite(c1));
		Carte c2 = Paquet.piocher();
		// V�rifie que la s�rie o� poser la carte est la premi�re
		assertEquals(Plateau.getSerieOuPoser(c2),0);
	}

}

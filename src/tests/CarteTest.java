package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import classes.Carte;

public class CarteTest {

	@Test
	public void testCarte() {
		// On initialise deux cartes
		Carte c1 = new Carte();
		Carte c2 = new Carte();
		// On vérifie que la valeur de la première carte est cohérente par rapport à la deuxième
		assertTrue(c1.getValeur()>=Carte.getMIN());
		assertTrue(c1.getValeur()<c2.getValeur());
		// On vérifie que le nombre de têtes de la carte de valeur 1 est bien de 1
		assertEquals(c1.getTetes(),1);
	}

}

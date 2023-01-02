package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import classes.Serie;
import classes.Paquet;
import classes.Carte;

public class SerieTest {

	@Test
	public void testSerie() {
		new Paquet();
		Serie s1 = new Serie();
		Serie s2 = new Serie();
		// V�rifie que les num�ros des s�ries sont correctement initialis�s
		assertTrue(s1.getNumero()<s2.getNumero());
		Carte c = new Carte();
		// V�rifie que le remplacement de s�ries fonctionne bien
		s2.remplacer(c);
		assertEquals(s2.getLast(),c);
		// V�rifie que la carte de valeur 1 ne peut pas �tre ajout�e � la s�rie
		assertFalse(s2.ajoutPossible(c));
	}

}

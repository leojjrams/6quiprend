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
		// Vérifie que les numéros des séries sont correctement initialisés
		assertTrue(s1.getNumero()<s2.getNumero());
		Carte c = new Carte();
		// Vérifie que le remplacement de séries fonctionne bien
		s2.remplacer(c);
		assertEquals(s2.getLast(),c);
		// Vérifie que la carte de valeur 1 ne peut pas être ajoutée à la série
		assertFalse(s2.ajoutPossible(c));
	}

}

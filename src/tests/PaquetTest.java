package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import classes.Paquet;

public class PaquetTest {

	@Test
	public void testPaquet() {
		new Paquet();
		// Vérifie que la pioche fonctionne et que les valeurs des cartes du paquet sont correctes
		for(int i=Paquet.getNbCartes(); i>0; --i) {
			assertEquals(Paquet.piocher().getValeur(),i);
		}
	}

}

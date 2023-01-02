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
		// On v�rifie que la valeur de la premi�re carte est coh�rente par rapport � la deuxi�me
		assertTrue(c1.getValeur()>=Carte.getMIN());
		assertTrue(c1.getValeur()<c2.getValeur());
		// On v�rifie que le nombre de t�tes de la carte de valeur 1 est bien de 1
		assertEquals(c1.getTetes(),1);
	}

}

package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import appli.Application;
import classes.*;

public class JeuTest {

	@Test
	public void testJeu() {
		// Initialise une partie et v�rifie que le nombre de joueurs est correct
		new Jeu(Application.getNomFichier());
		assertTrue(Jeu.getNbJoueurs()>=2);
		assertTrue(Jeu.getNbJoueurs()<=10);
	}

}

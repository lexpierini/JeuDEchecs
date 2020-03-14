package application;

import echecs.MatchEchecs;

public class Program {

	public static void main(String[] args) {
		MatchEchecs matchEchecs = new MatchEchecs();
		IU.imprimerPlateau(matchEchecs.getPieces());
	}

}

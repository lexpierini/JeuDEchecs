package application;

import java.util.Scanner;

import echecs.MatchEchecs;
import echecs.PieceEchecs;
import echecs.PositionEchecs;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		MatchEchecs matchEchecs = new MatchEchecs();
		
		while (true) {
			IU.imprimerPlateau(matchEchecs.getPieces());
			System.out.println();
			System.out.print("Source: ");
			PositionEchecs source = IU.lireLaPositionDesEchecs(sc);
			
			System.out.println();
			System.out.print("Cible: ");
			PositionEchecs cible = IU.lireLaPositionDesEchecs(sc);
			
			PieceEchecs pieceCapturee = matchEchecs.effectuerUnMouvementDEchecs(source, cible);
		}
	}

}

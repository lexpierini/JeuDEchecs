package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import echecs.EchecsException;
import echecs.MatchEchecs;
import echecs.PieceEchecs;
import echecs.PositionEchecs;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		MatchEchecs matchEchecs = new MatchEchecs();
		List<PieceEchecs> capturee = new ArrayList<>();
		
		while (!matchEchecs.getEchecEtMat()) {
			try {
				IU.clearScreen();
				IU.imprimerLeMatch(matchEchecs, capturee);
				System.out.println();
				System.out.print("Source: ");
				PositionEchecs source = IU.lireLaPositionDesEchecs(sc);
				
				boolean[][] mouvementsPossibles = matchEchecs.mouvementsPossibles(source);
				IU.clearScreen();
				IU.imprimerPlateau(matchEchecs.getPieces(), mouvementsPossibles);
				System.out.println();
				System.out.print("Cible: ");
				PositionEchecs cible = IU.lireLaPositionDesEchecs(sc);
				
				PieceEchecs pieceCapturee = matchEchecs.effectuerUnMouvementDEchecs(source, cible);
				
				if (pieceCapturee != null) {
					capturee.add(pieceCapturee);
				}
				
			} catch(EchecsException erreur) {
				System.out.println(erreur.getMessage());
				sc.nextLine();
			} catch(InputMismatchException erreur) {
				System.out.println(erreur.getMessage());
				sc.nextLine();
			}
		}
		IU.clearScreen();
		IU.imprimerLeMatch(matchEchecs, capturee);
	}
}

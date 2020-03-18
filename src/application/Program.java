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
		List<PieceEchecs> piecesCapturees = new ArrayList<>();
		
		while (!matchEchecs.getEchecEtMat()) {
			try {
				IU.clearScreen();
				IU.imprimerLeMatch(matchEchecs, piecesCapturees);
				System.out.println();
				System.out.print("Origine: ");
				PositionEchecs origine = IU.lireLaPositionDesEchecs(sc);
				
				boolean[][] deplacementsPossibles = matchEchecs.deplacementsPossibles(origine);
				IU.clearScreen();
				IU.imprimerEchiquier(matchEchecs.getPieces(), deplacementsPossibles);
				System.out.println();
				System.out.print("Destination: ");
				PositionEchecs destination = IU.lireLaPositionDesEchecs(sc);
				
				PieceEchecs pieceCapturee = matchEchecs.effectuerDeplacementEchec(origine, destination);
				
				if (pieceCapturee != null) {
					piecesCapturees.add(pieceCapturee);
				}
				
				if (matchEchecs.getPromouvoirPion() != null) {
					System.out.print("Choisissez la pièce pour la promotion (F/C/T/D): ");
					String type = sc.nextLine();
					matchEchecs.remplacerPiecePromue(type);
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
		IU.imprimerLeMatch(matchEchecs, piecesCapturees);
	}
}

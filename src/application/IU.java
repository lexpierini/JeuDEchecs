package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import echecs.Couleur;
import echecs.MatchEchecs;
import echecs.PieceEchecs;
import echecs.PositionEchecs;

public class IU {
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	public static PositionEchecs lireLaPositionDesEchecs(Scanner sc) {
		try {
			String s = sc.nextLine();
			char colonne = s.charAt(0);
			int ligne = Integer.parseInt(s.substring(1));
			return new PositionEchecs(colonne, ligne);
		} catch (RuntimeException erreur) {
			throw new InputMismatchException("Erreur de lecture de PositionEchecs. Les valeurs valides vont de a1 à h8.");
		}
	}
	
	public static void imprimerLeMatch(MatchEchecs matchEchecs, List<PieceEchecs> capturee) {
		imprimerPlateau(matchEchecs.getPieces());
		System.out.println();
		imprimerLesPiecesCapturees(capturee);
		System.out.println();
		System.out.println("Tour: " + matchEchecs.getTour());
		System.out.println("Joueur en attente: " + matchEchecs.getJoueurActuel());
		if (matchEchecs.getEchec()) {
			System.out.println("ÉCHEC!");
		}
	}
	
	public static void imprimerPlateau(PieceEchecs[][] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				imprimerPiece(pieces[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	public static void imprimerPlateau(PieceEchecs[][] pieces, boolean[][] mouvementsPossibles) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				imprimerPiece(pieces[i][j], mouvementsPossibles[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	private static void imprimerPiece(PieceEchecs piece, boolean arrierePlan) {
    	if (arrierePlan) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		
		if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (piece.getCouleur() == Couleur.BLANC) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}
	
	private static void imprimerLesPiecesCapturees(List<PieceEchecs> capturee) {
		List<PieceEchecs> blanc = capturee.stream().filter(x -> x.getCouleur() == Couleur.BLANC).collect(Collectors.toList());
		List<PieceEchecs> noir = capturee.stream().filter(x -> x.getCouleur() == Couleur.NOIR).collect(Collectors.toList());
		System.out.println("Pièces capturées: ");
		System.out.print("Blanc: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(blanc.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Noir: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(noir.toArray()));
		System.out.print(ANSI_RESET);
	}
}

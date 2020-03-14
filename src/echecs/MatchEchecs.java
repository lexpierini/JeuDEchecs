package echecs;

import echecs.pieces.Roi;
import echecs.pieces.Tour;
import jeuDePlateau.Plateau;
import jeuDePlateau.Position;

public class MatchEchecs {
	private Plateau plateau;
	
	public MatchEchecs() {
		plateau = new Plateau(8, 8);
		configurationInitiale();
	}
	
	public PieceEchecs[][] getPieces() {
		PieceEchecs[][] matrice = new PieceEchecs[plateau.getLignes()][plateau.getColonnes()];
		for (int i = 0; i < plateau.getLignes(); i++) {
			for (int j = 0; j < plateau.getColonnes(); j++) {
				matrice[i][j] = (PieceEchecs) plateau.piece(i, j);
			}
		}
		return matrice;
	}
	
	private void configurationInitiale() {
		plateau.placerPiece(new Tour(plateau, Couleur.BLANC), new Position(2, 1));
		plateau.placerPiece(new Roi(plateau, Couleur.NOIR), new Position(0, 4));
		plateau.placerPiece(new Roi(plateau, Couleur.BLANC), new Position(7, 4));
	}
}

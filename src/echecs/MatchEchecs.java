package echecs;

import echecs.pieces.Roi;
import echecs.pieces.Tour;
import jeuDePlateau.Plateau;

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
	
	private void placerUneNouvellePiece(char colonne, int ligne, PieceEchecs piece) {
		plateau.placerPiece(piece, new PositionEchecs(colonne, ligne).versPosition());
	}
	
	private void configurationInitiale() {
		placerUneNouvellePiece('c', 1, new Tour(plateau, Couleur.BLANC));
		placerUneNouvellePiece('c', 2, new Tour(plateau, Couleur.BLANC));
		placerUneNouvellePiece('d', 2, new Tour(plateau, Couleur.BLANC));
		placerUneNouvellePiece('e', 2, new Tour(plateau, Couleur.BLANC));
		placerUneNouvellePiece('e', 1, new Tour(plateau, Couleur.BLANC));
		placerUneNouvellePiece('d', 1, new Roi(plateau, Couleur.BLANC));

		placerUneNouvellePiece('c', 7, new Tour(plateau, Couleur.NOIR));
		placerUneNouvellePiece('c', 8, new Tour(plateau, Couleur.NOIR));
		placerUneNouvellePiece('d', 7, new Tour(plateau, Couleur.NOIR));
		placerUneNouvellePiece('e', 7, new Tour(plateau, Couleur.NOIR));
		placerUneNouvellePiece('e', 8, new Tour(plateau, Couleur.NOIR));
		placerUneNouvellePiece('d', 8, new Roi(plateau, Couleur.NOIR));
	}
}

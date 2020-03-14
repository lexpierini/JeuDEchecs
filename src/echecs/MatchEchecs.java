package echecs;

import jeuDePlateau.Plateau;

public class MatchEchecs {
	private Plateau plateau;
	
	public MatchEchecs() {
		plateau = new Plateau(8, 8);
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
	
}

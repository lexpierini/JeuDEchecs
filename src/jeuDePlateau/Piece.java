package jeuDePlateau;

public abstract class Piece {
	protected Position position;
	private Plateau plateau;
	
	public Piece(Plateau plateau) {
		this.plateau = plateau;
		this.position = null;
	}

	protected Plateau getPlateau() {
		return plateau;
	}
	
	public abstract boolean[][] mouvementsPossibles();
	
	public boolean mouvementPossible(Position position) {
		return mouvementsPossibles()[position.getLigne()][position.getColonne()];
	}
	
	public boolean ilYAUnPossibleMovement() {
		boolean[][] matrice = mouvementsPossibles();
		for (int i = 0; i < matrice.length; i++) {
			for (int j = 0; j < matrice.length; j++) {
				if (matrice[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
}

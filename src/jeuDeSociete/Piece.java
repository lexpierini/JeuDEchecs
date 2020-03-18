package jeuDeSociete;

public abstract class Piece {
	protected Position position;
	private Echiquier echiquier;
	
	public Piece(Echiquier echiquier) {
		this.echiquier = echiquier;
		this.position = null;
	}

	protected Echiquier getEchiquier() {
		return echiquier;
	}
	
	public abstract boolean[][] deplacementsPossibles();
	
	public boolean deplacementPossible(Position position) {
		return deplacementsPossibles()[position.getLigne()][position.getColonne()];
	}
	
	public boolean aMovementPossible() {
		boolean[][] matrice = deplacementsPossibles();
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

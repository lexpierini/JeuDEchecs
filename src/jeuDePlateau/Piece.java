package jeuDePlateau;

public class Piece {
	protected Position position;
	private Plateau plateau;
	
	public Piece(Plateau plateau) {
		this.plateau = plateau;
		this.position = null;
	}

	protected Plateau getPlateau() {
		return plateau;
	}

}

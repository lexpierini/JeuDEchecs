package jeuDePlateau;

public class Plateau {
	private int lignes;
	private int colonnes;
	private Piece[][] pieces;
	
	public Plateau(int lignes, int colonnes) {
		this.lignes = lignes;
		this.colonnes = colonnes;
		this.pieces = new Piece[lignes][colonnes];
	}

	public int getLignes() {
		return lignes;
	}

	public void setLignes(int lignes) {
		this.lignes = lignes;
	}

	public int getColonnes() {
		return colonnes;
	}

	public void setColonnes(int colonnes) {
		this.colonnes = colonnes;
	}
	
	public Piece piece(int ligne, int colonne) {
		return pieces[ligne][colonne];
	}
	
	public Piece piece(Position position) {
		return pieces[position.getLigne()][position.getColonne()];
	}
	
	public void placerPiece(Piece piece, Position position) {
		this.pieces[position.getLigne()][position.getColonne()] = piece;
		piece.position = position;
	}
}

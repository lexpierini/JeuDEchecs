package jeuDePlateau;

public class Plateau {
	private int lignes;
	private int colonnes;
	private Piece[][] pieces;
	
	public Plateau(int lignes, int colonnes) {
		if(lignes < 1 || colonnes < 1) {
			throw new PlateauException("Erreur lors de la création du plateau: il doit y avoir au moins 1 ligne et 1 colonne.");
		}
		
		this.lignes = lignes;
		this.colonnes = colonnes;
		this.pieces = new Piece[lignes][colonnes];
	}

	public int getLignes() {
		return lignes;
	}

	public int getColonnes() {
		return colonnes;
	}

	public Piece piece(int ligne, int colonne) {
		if (!laPositionExiste(ligne, colonne)) {
			throw new PlateauException("Position inexistante sur le plateau.");
		}
		
		return pieces[ligne][colonne];
	}
	
	public Piece piece(Position position) {
		if (!laPositionExiste(position)) {
			throw new PlateauException("Position inexistante sur le plateau.");
		}
		
		return pieces[position.getLigne()][position.getColonne()];
	}
	
	public void placerPiece(Piece piece, Position position) {
		if (ilYAUnPiece(position)) {
			throw new PlateauException("Il y a déjà une pièce sur la position " + position);
		}
		this.pieces[position.getLigne()][position.getColonne()] = piece;
		piece.position = position;
	}
	
	public boolean laPositionExiste(int ligne, int colonne) {
		return ligne >= 0 && ligne < this.lignes && colonne >= 0 && colonne < this.colonnes;
	}
	
	public boolean laPositionExiste(Position position) {
		return laPositionExiste(position.getLigne(), position.getColonne());
	}
	
	public boolean ilYAUnPiece(Position position) {
		if (!laPositionExiste(position)) {
			throw new PlateauException("Position inexistante sur le plateau.");
		}
		
		return piece(position) != null;
	}
}

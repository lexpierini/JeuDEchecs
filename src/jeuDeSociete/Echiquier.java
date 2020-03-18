package jeuDeSociete;

public class Echiquier {
	private int lignes;
	private int colonnes;
	private Piece[][] pieces;
	
	public Echiquier(int lignes, int colonnes) {
		if(lignes < 1 || colonnes < 1) {
			throw new EchiquierException("Erreur lors de la création d'échiquier: il doit y avoir au moins 1 ligne et 1 colonne.");
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
			throw new EchiquierException("Position inexistante sur l'échiquier.");
		}
		
		return pieces[ligne][colonne];
	}
	
	public Piece piece(Position position) {
		if (!positionExiste(position)) {
			throw new EchiquierException("Position inexistante sur l'échiquier.");
		}
		
		return pieces[position.getLigne()][position.getColonne()];
	}
	
	public void placerPiece(Piece piece, Position position) {
		if (aPiece(position)) {
			throw new EchiquierException("Il y a déjà une pièce sur la position " + position);
		}
		this.pieces[position.getLigne()][position.getColonne()] = piece;
		piece.position = position;
	}
	
	public Piece supprimerPiece(Position position) {
		if (!positionExiste(position)) {
			throw new EchiquierException("Position inexistante sur l'échiquier.");
		}
		
		if (piece(position) == null) {
			return null;
		}
		
		Piece auxiliaire = piece(position);
		auxiliaire.position = null;
		this.pieces[position.getLigne()][position.getColonne()] = null;
		
		return auxiliaire;
	}
	
	public boolean laPositionExiste(int ligne, int colonne) {
		return ligne >= 0 && ligne < this.lignes && colonne >= 0 && colonne < this.colonnes;
	}
	
	public boolean positionExiste(Position position) {
		return laPositionExiste(position.getLigne(), position.getColonne());
	}
	
	public boolean aPiece(Position position) {
		if (!positionExiste(position)) {
			throw new EchiquierException("Position inexistante sur l'échiquier.");
		}
		
		return piece(position) != null;
	}
}

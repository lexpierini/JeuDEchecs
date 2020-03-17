package echecs;

import jeuDePlateau.Piece;
import jeuDePlateau.Plateau;
import jeuDePlateau.Position;

public abstract class PieceEchecs extends Piece{
	private Couleur couleur;

	public PieceEchecs(Plateau plateau, Couleur couleur) {
		super(plateau);
		this.couleur = couleur;
	}

	public Couleur getCouleur() {
		return couleur;
	}
	
	public PositionEchecs getPositionEchecs() {
		return PositionEchecs.desPosition(position);
	}
	
	protected boolean ilYAUnePieceAdverse(Position position) {
		PieceEchecs piece = (PieceEchecs)getPlateau().piece(position);
		return piece != null && piece.getCouleur() != couleur;
	}
}

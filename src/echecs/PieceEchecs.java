package echecs;

import jeuDePlateau.Piece;
import jeuDePlateau.Plateau;

public class PieceEchecs extends Piece{
	private Couleur couleur;

	public PieceEchecs(Plateau plateau, Couleur couleur) {
		super(plateau);
		this.couleur = couleur;
	}

	public Couleur getCouleur() {
		return couleur;
	}
}

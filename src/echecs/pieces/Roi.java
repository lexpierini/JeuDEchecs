package echecs.pieces;

import echecs.Couleur;
import echecs.PieceEchecs;
import jeuDePlateau.Plateau;

public class Roi extends PieceEchecs {
	public Roi(Plateau plateau, Couleur couleur) {
		super(plateau, couleur);
	}
	
	@Override
	public String toString() {
		return "R";
	}
}

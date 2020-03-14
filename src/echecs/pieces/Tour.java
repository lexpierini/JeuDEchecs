package echecs.pieces;

import echecs.Couleur;
import echecs.PieceEchecs;
import jeuDePlateau.Plateau;

public class Tour extends PieceEchecs {

	public Tour(Plateau plateau, Couleur couleur) {
		super(plateau, couleur);
	}
	
	@Override
	public String toString() {
		return "T";
	}
}

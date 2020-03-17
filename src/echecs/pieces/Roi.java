package echecs.pieces;

import echecs.Couleur;
import echecs.PieceEchecs;
import jeuDePlateau.Plateau;
import jeuDePlateau.Position;

public class Roi extends PieceEchecs {
	public Roi(Plateau plateau, Couleur couleur) {
		super(plateau, couleur);
	}
	
	private boolean peutSeDeplacer(Position position) {
		PieceEchecs p = (PieceEchecs)getPlateau().piece(position);
		return p == null || p.getCouleur() != getCouleur();
	}

	@Override
	public boolean[][] mouvementsPossibles() {
		boolean[][] matrice = new boolean[getPlateau().getLignes()][getPlateau().getColonnes()];
		
		Position p = new Position(0, 0);
		
		// dessus
		p.setValeurs(position.getLigne() - 1 , position.getColonne());
		if (getPlateau().laPositionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// dessous
		p.setValeurs(position.getLigne() + 1 , position.getColonne());
		if (getPlateau().laPositionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// gauche
		p.setValeurs(position.getLigne(), position.getColonne() - 1);
		if (getPlateau().laPositionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// droite
		p.setValeurs(position.getLigne(), position.getColonne() + 1);
		if (getPlateau().laPositionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// nord-ouest
		p.setValeurs(position.getLigne() - 1, position.getColonne() - 1);
		if (getPlateau().laPositionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// nord-est
		p.setValeurs(position.getLigne() - 1, position.getColonne() + 1);
		if (getPlateau().laPositionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// sud-ouest
		p.setValeurs(position.getLigne() + 1, position.getColonne() - 1);
		if (getPlateau().laPositionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// sud-est
		p.setValeurs(position.getLigne() + 1, position.getColonne() + 1);
		if (getPlateau().laPositionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}

		return matrice;
	}
	
	@Override
	public String toString() {
		return "R";
	}
}

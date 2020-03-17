package echecs.pieces;

import echecs.Couleur;
import echecs.PieceEchecs;
import jeuDePlateau.Plateau;
import jeuDePlateau.Position;

public class Cavalier extends PieceEchecs {
	public Cavalier(Plateau plateau, Couleur couleur) {
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
		
		p.setValeurs(position.getLigne() - 1 , position.getColonne() - 2);
		if (getPlateau().laPositionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		p.setValeurs(position.getLigne() - 2 , position.getColonne() - 1);
		if (getPlateau().laPositionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		p.setValeurs(position.getLigne() - 2, position.getColonne() + 1);
		if (getPlateau().laPositionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		p.setValeurs(position.getLigne() - 1, position.getColonne() + 2);
		if (getPlateau().laPositionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		p.setValeurs(position.getLigne() + 1, position.getColonne() + 2);
		if (getPlateau().laPositionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		p.setValeurs(position.getLigne() + 2, position.getColonne() + 1);
		if (getPlateau().laPositionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		p.setValeurs(position.getLigne() + 2, position.getColonne() - 1);
		if (getPlateau().laPositionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		p.setValeurs(position.getLigne() + 1, position.getColonne() - 2);
		if (getPlateau().laPositionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}

		return matrice;
	}
	
	@Override
	public String toString() {
		return "C";
	}
}

package echecs.pieces;

import echecs.Couleur;
import echecs.PieceEchecs;
import jeuDeSociete.Echiquier;
import jeuDeSociete.Position;

public class Cavalier extends PieceEchecs {
	public Cavalier(Echiquier echiquier, Couleur couleur) {
		super(echiquier, couleur);
	}
	
	private boolean peutDeplacer(Position position) {
		PieceEchecs p = (PieceEchecs)getEchiquier().piece(position);
		return p == null || p.getCouleur() != getCouleur();
	}

	@Override
	public boolean[][] deplacementsPossibles() {
		boolean[][] matrice = new boolean[getEchiquier().getLignes()][getEchiquier().getColonnes()];
		
		Position p = new Position(0, 0);
		
		p.setValeurs(position.getLigne() - 1 , position.getColonne() - 2);
		if (getEchiquier().positionExiste(p) && peutDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		p.setValeurs(position.getLigne() - 2 , position.getColonne() - 1);
		if (getEchiquier().positionExiste(p) && peutDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		p.setValeurs(position.getLigne() - 2, position.getColonne() + 1);
		if (getEchiquier().positionExiste(p) && peutDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		p.setValeurs(position.getLigne() - 1, position.getColonne() + 2);
		if (getEchiquier().positionExiste(p) && peutDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		p.setValeurs(position.getLigne() + 1, position.getColonne() + 2);
		if (getEchiquier().positionExiste(p) && peutDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		p.setValeurs(position.getLigne() + 2, position.getColonne() + 1);
		if (getEchiquier().positionExiste(p) && peutDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		p.setValeurs(position.getLigne() + 2, position.getColonne() - 1);
		if (getEchiquier().positionExiste(p) && peutDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		p.setValeurs(position.getLigne() + 1, position.getColonne() - 2);
		if (getEchiquier().positionExiste(p) && peutDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}

		return matrice;
	}
	
	@Override
	public String toString() {
		return "C";
	}
}

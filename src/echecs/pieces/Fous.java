package echecs.pieces;

import echecs.Couleur;
import echecs.PieceEchecs;
import jeuDeSociete.Echiquier;
import jeuDeSociete.Position;

public class Fous extends PieceEchecs {

	public Fous(Echiquier echiquier, Couleur couleur) {
		super(echiquier, couleur);
	}
	
	@Override
	public boolean[][] deplacementsPossibles() {
		boolean[][] matrice = new boolean[getEchiquier().getLignes()][getEchiquier().getColonnes()];
		
		Position p = new Position(0, 0);
		
		// Nord-ouest
		p.setValeurs(position.getLigne() - 1, position.getColonne() - 1);
		while (getEchiquier().positionExiste(p) && !getEchiquier().aPiece(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
			p.setValeurs(p.getLigne() - 1, p.getColonne() - 1);
		}
		
		if (getEchiquier().positionExiste(p) && aPieceAdverse(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// Sud-ouest
		p.setValeurs(position.getLigne() + 1, position.getColonne() - 1);
		while (getEchiquier().positionExiste(p) && !getEchiquier().aPiece(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
			p.setValeurs(p.getLigne() + 1, p.getColonne() - 1);
		}
		
		if (getEchiquier().positionExiste(p) && aPieceAdverse(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// Nord-est
		p.setValeurs(position.getLigne() - 1, position.getColonne() + 1);
		while (getEchiquier().positionExiste(p) && !getEchiquier().aPiece(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
			p.setValeurs(p.getLigne() - 1, p.getColonne() + 1);
		}
		
		if (getEchiquier().positionExiste(p) && aPieceAdverse(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
			
		// Sud-est
		p.setValeurs(position.getLigne() + 1, position.getColonne() + 1);
		while (getEchiquier().positionExiste(p) && !getEchiquier().aPiece(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
			p.setValeurs(p.getLigne() + 1, p.getColonne() + 1);
		}
		
		if (getEchiquier().positionExiste(p) && aPieceAdverse(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		return matrice;
	}
	
	@Override
	public String toString() {
		return "F";
	}
}

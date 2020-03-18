package echecs.pieces;

import echecs.Couleur;
import echecs.PieceEchecs;
import jeuDeSociete.Echiquier;
import jeuDeSociete.Position;

public class Tour extends PieceEchecs {

	public Tour(Echiquier echiquier, Couleur couleur) {
		super(echiquier, couleur);
	}
	
	@Override
	public boolean[][] deplacementsPossibles() {
		boolean[][] matrice = new boolean[getEchiquier().getLignes()][getEchiquier().getColonnes()];
		
		Position p = new Position(0, 0);
		
		// Dessus
		p.setValeurs(position.getLigne() - 1, position.getColonne());
		while (getEchiquier().positionExiste(p) && !getEchiquier().aPiece(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
			p.setLigne(p.getLigne() - 1);
		}
		
		if (getEchiquier().positionExiste(p) && aPieceAdverse(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// Dessous
		p.setValeurs(position.getLigne() + 1, position.getColonne());
		while (getEchiquier().positionExiste(p) && !getEchiquier().aPiece(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
			p.setLigne(p.getLigne() + 1);
		}
		
		if (getEchiquier().positionExiste(p) && aPieceAdverse(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// Gauche
		p.setValeurs(position.getLigne(), position.getColonne() - 1);
		while (getEchiquier().positionExiste(p) && !getEchiquier().aPiece(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
			p.setColonne(p.getColonne() - 1);
		}
		
		if (getEchiquier().positionExiste(p) && aPieceAdverse(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
			
		// Droite
		p.setValeurs(position.getLigne(), position.getColonne() + 1);
		while (getEchiquier().positionExiste(p) && !getEchiquier().aPiece(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
			p.setColonne(p.getColonne() + 1);
		}
		
		if (getEchiquier().positionExiste(p) && aPieceAdverse(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		return matrice;
	}
	
	@Override
	public String toString() {
		return "T";
	}
}

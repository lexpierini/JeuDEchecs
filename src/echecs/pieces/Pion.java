package echecs.pieces;

import echecs.Couleur;
import echecs.MatchEchecs;
import echecs.PieceEchecs;
import jeuDeSociete.Echiquier;
import jeuDeSociete.Position;

public class Pion extends PieceEchecs {
	private MatchEchecs matchEchecs;
	
	public Pion(Echiquier echiquier, Couleur couleur, MatchEchecs matchEchecs) {
		super(echiquier, couleur);
		this.matchEchecs = matchEchecs;
	}

	@Override
	public boolean[][] deplacementsPossibles() {
		boolean[][] matrice = new boolean[getEchiquier().getLignes()][getEchiquier().getColonnes()];
		
		Position p = new Position(0, 0);
		
		if (getCouleur() == Couleur.BLANC) {
			p.setValeurs(position.getLigne() - 1, position.getColonne());
			if (getEchiquier().positionExiste(p) && !getEchiquier().aPiece(p)) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
			
			p.setValeurs(position.getLigne() - 2, position.getColonne());
			Position p2 = new Position(position.getLigne() - 1, position.getColonne());
			if (getEchiquier().positionExiste(p) && !getEchiquier().aPiece(p) && getEchiquier().positionExiste(p2) && !getEchiquier().aPiece(p2) && getCompteurMouvement() == 0) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
			
			p.setValeurs(position.getLigne() - 1, position.getColonne() - 1);
			if (getEchiquier().positionExiste(p) && aPieceAdverse(p)) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
			
			p.setValeurs(position.getLigne() - 1, position.getColonne() + 1);
			if (getEchiquier().positionExiste(p) && aPieceAdverse(p)) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
			
			// Mouvement spécial: La prise en passant blanc
			if (position.getLigne() == 3) {
				Position gauche = new Position(position.getLigne(), position.getColonne() - 1);
				if (getEchiquier().positionExiste(gauche) && aPieceAdverse(gauche) && getEchiquier().piece(gauche) == matchEchecs.getPriseEnPassant()) {
					matrice[gauche.getLigne() - 1][gauche.getColonne()] = true;
				}
				
				Position droite = new Position(position.getLigne(), position.getColonne() + 1);
				if (getEchiquier().positionExiste(droite) && aPieceAdverse(droite) && getEchiquier().piece(droite) == matchEchecs.getPriseEnPassant()) {
					matrice[droite.getLigne() - 1][droite.getColonne()] = true;
				}
			}
		} else {
			p.setValeurs(position.getLigne() + 1, position.getColonne());
			if (getEchiquier().positionExiste(p) && !getEchiquier().aPiece(p)) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
			
			p.setValeurs(position.getLigne() + 2, position.getColonne());
			Position p2 = new Position(position.getLigne() + 1, position.getColonne());
			if (getEchiquier().positionExiste(p) && !getEchiquier().aPiece(p) && getEchiquier().positionExiste(p2) && !getEchiquier().aPiece(p2) && getCompteurMouvement() == 0) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
			
			p.setValeurs(position.getLigne() + 1, position.getColonne() - 1);
			if (getEchiquier().positionExiste(p) && aPieceAdverse(p)) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
			
			p.setValeurs(position.getLigne() + 1, position.getColonne() + 1);
			if (getEchiquier().positionExiste(p) && aPieceAdverse(p)) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
			// Mouvement spécial: La prise en passant noir
			if (position.getLigne() == 4) {
				Position gauche = new Position(position.getLigne(), position.getColonne() - 1);
				if (getEchiquier().positionExiste(gauche) && aPieceAdverse(gauche) && getEchiquier().piece(gauche) == matchEchecs.getPriseEnPassant()) {
					matrice[gauche.getLigne() + 1][gauche.getColonne()] = true;
				}
				
				Position droite = new Position(position.getLigne(), position.getColonne() + 1);
				if (getEchiquier().positionExiste(droite) && aPieceAdverse(droite) && getEchiquier().piece(droite) == matchEchecs.getPriseEnPassant()) {
					matrice[droite.getLigne() + 1][droite.getColonne()] = true;
				}
			}
		}
		
		return matrice;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}

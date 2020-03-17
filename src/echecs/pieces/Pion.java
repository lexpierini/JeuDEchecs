package echecs.pieces;

import echecs.Couleur;
import echecs.PieceEchecs;
import jeuDePlateau.Plateau;
import jeuDePlateau.Position;

public class Pion extends PieceEchecs {

	public Pion(Plateau plateau, Couleur couleur) {
		super(plateau, couleur);
	}

	@Override
	public boolean[][] mouvementsPossibles() {
		boolean[][] matrice = new boolean[getPlateau().getLignes()][getPlateau().getColonnes()];
		
		Position p = new Position(0, 0);
		
		if (getCouleur() == Couleur.BLANC) {
			p.setValeurs(position.getLigne() - 1, position.getColonne());
			if (getPlateau().laPositionExiste(p) && !getPlateau().ilYAUnPiece(p)) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
			
			p.setValeurs(position.getLigne() - 2, position.getColonne());
			Position p2 = new Position(position.getLigne() - 1, position.getColonne());
			if (getPlateau().laPositionExiste(p) && !getPlateau().ilYAUnPiece(p) && getPlateau().laPositionExiste(p2) && !getPlateau().ilYAUnPiece(p2) && getCompteurDeMopuvement() == 0) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
			
			p.setValeurs(position.getLigne() - 1, position.getColonne() - 1);
			if (getPlateau().laPositionExiste(p) && ilYAUnePieceAdverse(p)) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
			
			p.setValeurs(position.getLigne() - 1, position.getColonne() + 1);
			if (getPlateau().laPositionExiste(p) && ilYAUnePieceAdverse(p)) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
		} else {
			p.setValeurs(position.getLigne() + 1, position.getColonne());
			if (getPlateau().laPositionExiste(p) && !getPlateau().ilYAUnPiece(p)) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
			
			p.setValeurs(position.getLigne() + 2, position.getColonne());
			Position p2 = new Position(position.getLigne() - 1, position.getColonne());
			if (getPlateau().laPositionExiste(p) && !getPlateau().ilYAUnPiece(p) && getPlateau().laPositionExiste(p2) && !getPlateau().ilYAUnPiece(p2) && getCompteurDeMopuvement() == 0) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
			
			p.setValeurs(position.getLigne() + 1, position.getColonne() - 1);
			if (getPlateau().laPositionExiste(p) && ilYAUnePieceAdverse(p)) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
			
			p.setValeurs(position.getLigne() + 1, position.getColonne() + 1);
			if (getPlateau().laPositionExiste(p) && ilYAUnePieceAdverse(p)) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
		}
		
		return matrice;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}

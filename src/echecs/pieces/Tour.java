package echecs.pieces;

import echecs.Couleur;
import echecs.PieceEchecs;
import jeuDePlateau.Plateau;
import jeuDePlateau.Position;

public class Tour extends PieceEchecs {

	public Tour(Plateau plateau, Couleur couleur) {
		super(plateau, couleur);
	}
	
	@Override
	public String toString() {
		return "T";
	}
	
	@Override
	public boolean[][] mouvementsPossibles() {
		boolean[][] matrice = new boolean[getPlateau().getLignes()][getPlateau().getColonnes()];
		
		Position p = new Position(0, 0);
		
		// au dessus
		p.setValeurs(position.getLigne() - 1, position.getColonne());
		while (getPlateau().laPositionExiste(p) && !getPlateau().ilYAUnPiece(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
			p.setLigne(p.getLigne() - 1);
		}
		
		if (getPlateau().laPositionExiste(p) && ilYAUnePieceAdverse(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// au dessous
		p.setValeurs(position.getLigne() + 1, position.getColonne());
		while (getPlateau().laPositionExiste(p) && !getPlateau().ilYAUnPiece(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
			p.setLigne(p.getLigne() + 1);
		}
		
		if (getPlateau().laPositionExiste(p) && ilYAUnePieceAdverse(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// gauche
		p.setValeurs(position.getLigne(), position.getColonne() - 1);
		while (getPlateau().laPositionExiste(p) && !getPlateau().ilYAUnPiece(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
			p.setColonne(p.getColonne() - 1);
		}
		
		if (getPlateau().laPositionExiste(p) && ilYAUnePieceAdverse(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
			
		// droite
		p.setValeurs(position.getLigne(), position.getColonne() + 1);
		while (getPlateau().laPositionExiste(p) && !getPlateau().ilYAUnPiece(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
			p.setColonne(p.getColonne() + 1);
		}
		
		if (getPlateau().laPositionExiste(p) && ilYAUnePieceAdverse(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		return matrice;
	}
}

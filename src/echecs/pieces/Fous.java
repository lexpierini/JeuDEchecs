package echecs.pieces;

import echecs.Couleur;
import echecs.PieceEchecs;
import jeuDePlateau.Plateau;
import jeuDePlateau.Position;

public class Fous extends PieceEchecs {

	public Fous(Plateau plateau, Couleur couleur) {
		super(plateau, couleur);
	}
	
	@Override
	public boolean[][] mouvementsPossibles() {
		boolean[][] matrice = new boolean[getPlateau().getLignes()][getPlateau().getColonnes()];
		
		Position p = new Position(0, 0);
		
		// Nord-ouest
		p.setValeurs(position.getLigne() - 1, position.getColonne() - 1);
		while (getPlateau().laPositionExiste(p) && !getPlateau().ilYAUnPiece(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
			p.setValeurs(p.getLigne() - 1, p.getColonne() - 1);
		}
		
		if (getPlateau().laPositionExiste(p) && ilYAUnePieceAdverse(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// Sud-ouest
		p.setValeurs(position.getLigne() + 1, position.getColonne() - 1);
		while (getPlateau().laPositionExiste(p) && !getPlateau().ilYAUnPiece(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
			p.setValeurs(p.getLigne() + 1, p.getColonne() - 1);
		}
		
		if (getPlateau().laPositionExiste(p) && ilYAUnePieceAdverse(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// Nord-est
		p.setValeurs(position.getLigne() - 1, position.getColonne() + 1);
		while (getPlateau().laPositionExiste(p) && !getPlateau().ilYAUnPiece(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
			p.setValeurs(p.getLigne() - 1, p.getColonne() + 1);
		}
		
		if (getPlateau().laPositionExiste(p) && ilYAUnePieceAdverse(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
			
		// Sud-est
		p.setValeurs(position.getLigne() + 1, position.getColonne() + 1);
		while (getPlateau().laPositionExiste(p) && !getPlateau().ilYAUnPiece(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
			p.setValeurs(p.getLigne() + 1, p.getColonne() + 1);
		}
		
		if (getPlateau().laPositionExiste(p) && ilYAUnePieceAdverse(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		return matrice;
	}
	
	@Override
	public String toString() {
		return "F";
	}
}

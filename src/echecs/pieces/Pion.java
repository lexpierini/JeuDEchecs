package echecs.pieces;

import echecs.Couleur;
import echecs.MatchEchecs;
import echecs.PieceEchecs;
import jeuDePlateau.Plateau;
import jeuDePlateau.Position;

public class Pion extends PieceEchecs {
	private MatchEchecs matchEchecs;
	
	public Pion(Plateau plateau, Couleur couleur, MatchEchecs matchEchecs) {
		super(plateau, couleur);
		this.matchEchecs = matchEchecs;
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
			
			// mouvement spécial: La prise en passant blanc
			if (position.getLigne() == 3) {
				Position gauche = new Position(position.getLigne(), position.getColonne() - 1);
				if (getPlateau().laPositionExiste(gauche) && ilYAUnePieceAdverse(gauche) && getPlateau().piece(gauche) == matchEchecs.getEnPassantVulnerable()) {
					matrice[gauche.getLigne() - 1][gauche.getColonne()] = true;
				}
				
				Position droite = new Position(position.getLigne(), position.getColonne() + 1);
				if (getPlateau().laPositionExiste(droite) && ilYAUnePieceAdverse(droite) && getPlateau().piece(droite) == matchEchecs.getEnPassantVulnerable()) {
					matrice[droite.getLigne() - 1][droite.getColonne()] = true;
				}
			}
		} else {
			p.setValeurs(position.getLigne() + 1, position.getColonne());
			if (getPlateau().laPositionExiste(p) && !getPlateau().ilYAUnPiece(p)) {
				matrice[p.getLigne()][p.getColonne()] = true;
			}
			
			p.setValeurs(position.getLigne() + 2, position.getColonne());
			Position p2 = new Position(position.getLigne() + 1, position.getColonne());
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
			// mouvement spécial: La prise en passant noir
			if (position.getLigne() == 4) {
				Position gauche = new Position(position.getLigne(), position.getColonne() - 1);
				if (getPlateau().laPositionExiste(gauche) && ilYAUnePieceAdverse(gauche) && getPlateau().piece(gauche) == matchEchecs.getEnPassantVulnerable()) {
					matrice[gauche.getLigne() + 1][gauche.getColonne()] = true;
				}
				
				Position droite = new Position(position.getLigne(), position.getColonne() + 1);
				if (getPlateau().laPositionExiste(droite) && ilYAUnePieceAdverse(droite) && getPlateau().piece(droite) == matchEchecs.getEnPassantVulnerable()) {
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

package echecs.pieces;

import echecs.Couleur;
import echecs.MatchEchecs;
import echecs.PieceEchecs;
import jeuDePlateau.Plateau;
import jeuDePlateau.Position;

public class Roi extends PieceEchecs {
	private MatchEchecs matchEchecs;
	
	public Roi(Plateau plateau, Couleur couleur, MatchEchecs matchEchecs) {
		super(plateau, couleur);
		this.matchEchecs = matchEchecs;
	}
	
	private boolean peutSeDeplacer(Position position) {
		PieceEchecs p = (PieceEchecs)getPlateau().piece(position);
		return p == null || p.getCouleur() != getCouleur();
	}
	
	private boolean testerTourRoque(Position position) {
		PieceEchecs p = (PieceEchecs)getPlateau().piece(position);
		return p != null && p instanceof Tour && p.getCouleur() == getCouleur() && p.getCompteurDeMopuvement() == 0;
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
		
		// mouvement spécial roque
		if (getCompteurDeMopuvement() == 0 && !matchEchecs.getEchec()) {
			// mouvement spécial roque Roi vers Tour à droite (Kingside)
			Position positionTour1 = new Position(position.getLigne(), position.getColonne() + 3);
			if (testerTourRoque(positionTour1)) {
				Position p1 = new Position(position.getLigne(), position.getColonne() + 1);
				Position p2 = new Position(position.getLigne(), position.getColonne() + 2);
				if (getPlateau().piece(p1) == null && getPlateau().piece(p2) == null) {
					matrice[position.getLigne()][position.getColonne() + 2] = true;
				}
			}
			// mouvement spécial roque Roi vers Tour à gauche (Queenside)
			Position positionTour2 = new Position(position.getLigne(), position.getColonne() - 4);
			if (testerTourRoque(positionTour2)) {
				Position p1 = new Position(position.getLigne(), position.getColonne() - 1);
				Position p2 = new Position(position.getLigne(), position.getColonne() - 2);
				Position p3 = new Position(position.getLigne(), position.getColonne() - 3);
				if (getPlateau().piece(p1) == null && getPlateau().piece(p2) == null && getPlateau().piece(p3) == null) {
					matrice[position.getLigne()][position.getColonne() - 2] = true;
				}
			}
		}

		return matrice;
	}
	
	@Override
	public String toString() {
		return "R";
	}
}

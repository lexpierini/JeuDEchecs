package echecs.pieces;

import echecs.Couleur;
import echecs.MatchEchecs;
import echecs.PieceEchecs;
import jeuDeSociete.Echiquier;
import jeuDeSociete.Position;

public class Roi extends PieceEchecs {
	private MatchEchecs matchEchecs;
	
	public Roi(Echiquier echiquier, Couleur couleur, MatchEchecs matchEchecs) {
		super(echiquier, couleur);
		this.matchEchecs = matchEchecs;
	}
	
	private boolean peutSeDeplacer(Position position) {
		PieceEchecs p = (PieceEchecs)getEchiquier().piece(position);
		return p == null || p.getCouleur() != getCouleur();
	}
	
	private boolean testerTourRoque(Position position) {
		PieceEchecs p = (PieceEchecs)getEchiquier().piece(position);
		return p != null && p instanceof Tour && p.getCouleur() == getCouleur() && p.getCompteurMouvement() == 0;
	}

	@Override
	public boolean[][] deplacementsPossibles() {
		boolean[][] matrice = new boolean[getEchiquier().getLignes()][getEchiquier().getColonnes()];
		
		Position p = new Position(0, 0);
		
		// Dessus
		p.setValeurs(position.getLigne() - 1 , position.getColonne());
		if (getEchiquier().positionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// Dessous
		p.setValeurs(position.getLigne() + 1 , position.getColonne());
		if (getEchiquier().positionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// Gauche
		p.setValeurs(position.getLigne(), position.getColonne() - 1);
		if (getEchiquier().positionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// Droite
		p.setValeurs(position.getLigne(), position.getColonne() + 1);
		if (getEchiquier().positionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// Nord-ouest
		p.setValeurs(position.getLigne() - 1, position.getColonne() - 1);
		if (getEchiquier().positionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// Nord-est
		p.setValeurs(position.getLigne() - 1, position.getColonne() + 1);
		if (getEchiquier().positionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// Sud-ouest
		p.setValeurs(position.getLigne() + 1, position.getColonne() - 1);
		if (getEchiquier().positionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// Sud-est
		p.setValeurs(position.getLigne() + 1, position.getColonne() + 1);
		if (getEchiquier().positionExiste(p) && peutSeDeplacer(p)) {
			matrice[p.getLigne()][p.getColonne()] = true;
		}
		
		// Mouvement spécial: Roque
		if (getCompteurMouvement() == 0 && !matchEchecs.getEchec()) {
			// Mouvement spécial: Roque, Roi vers Tour à droite (Kingside)
			Position positionTour1 = new Position(position.getLigne(), position.getColonne() + 3);
			if (testerTourRoque(positionTour1)) {
				Position p1 = new Position(position.getLigne(), position.getColonne() + 1);
				Position p2 = new Position(position.getLigne(), position.getColonne() + 2);
				if (getEchiquier().piece(p1) == null && getEchiquier().piece(p2) == null) {
					matrice[position.getLigne()][position.getColonne() + 2] = true;
				}
			}
			// Mouvement spécial: Roque, Roi vers Tour à gauche (Queenside)
			Position positionTour2 = new Position(position.getLigne(), position.getColonne() - 4);
			if (testerTourRoque(positionTour2)) {
				Position p1 = new Position(position.getLigne(), position.getColonne() - 1);
				Position p2 = new Position(position.getLigne(), position.getColonne() - 2);
				Position p3 = new Position(position.getLigne(), position.getColonne() - 3);
				if (getEchiquier().piece(p1) == null && getEchiquier().piece(p2) == null && getEchiquier().piece(p3) == null) {
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

package echecs;

import jeuDeSociete.Piece;
import jeuDeSociete.Echiquier;
import jeuDeSociete.Position;

public abstract class PieceEchecs extends Piece{
	private Couleur couleur;
	private int compteurMouvement;

	public PieceEchecs(Echiquier echiquier, Couleur couleur) {
		super(echiquier);
		this.couleur = couleur;
	}

	public Couleur getCouleur() {
		return couleur;
	}
	
	public int getCompteurMouvement() {
		return compteurMouvement;
	}
	
	public void augmenterCompteurMouvements() {
		compteurMouvement++;
	}
	
	public void reduireCompteurMouvements() {
		compteurMouvement--;
	}
	
	public PositionEchecs getPositionEchecs() {
		return PositionEchecs.desPosition(position);
	}
	
	protected boolean aPieceAdverse(Position position) {
		PieceEchecs piece = (PieceEchecs)getEchiquier().piece(position);
		return piece != null && piece.getCouleur() != couleur;
	}
}

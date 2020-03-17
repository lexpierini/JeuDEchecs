package echecs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import echecs.pieces.Roi;
import echecs.pieces.Tour;
import jeuDePlateau.Piece;
import jeuDePlateau.Plateau;
import jeuDePlateau.Position;

public class MatchEchecs {
	private int tour;
	private Couleur joueurActuel;
	private Plateau plateau;
	private boolean echec;
	
	private List<Piece> piecesSurLePlateau = new ArrayList<>();
	private List<Piece> piecesCapturees = new ArrayList<>();
	
	public MatchEchecs() {
		plateau = new Plateau(8, 8);
		tour = 1;
		joueurActuel = Couleur.BLANC;
		echec = false;
		configurationInitiale();
	}
	
	public int getTour() {
		return tour;
	}
	
	public Couleur getJoueurActuel() {
		return joueurActuel;
	}
	
	public boolean getEchec() {
		return echec;
	}
	
	public PieceEchecs[][] getPieces() {
		PieceEchecs[][] matrice = new PieceEchecs[plateau.getLignes()][plateau.getColonnes()];
		for (int i = 0; i < plateau.getLignes(); i++) {
			for (int j = 0; j < plateau.getColonnes(); j++) {
				matrice[i][j] = (PieceEchecs) plateau.piece(i, j);
			}
		}
		return matrice;
	}
	
	public boolean[][] mouvementsPossibles(PositionEchecs positionSource){
		Position position = positionSource.versPosition();
		validerLaPositionSource(position);
		return plateau.piece(position).mouvementsPossibles();
	}
	
	public PieceEchecs effectuerUnMouvementDEchecs(PositionEchecs positionSource, PositionEchecs positionCible) {
		Position source = positionSource.versPosition();
		Position cible = positionCible.versPosition();
		validerLaPositionSource(source);
		validerLaPositionCible(source, cible);
		Piece pieceCapturee = faireUnMouvement(source, cible);
		
		if (testerEchec(joueurActuel)) {
			annulerLeDeplacement(source, cible, pieceCapturee);
			throw new  EchecsException("Vous ne pouvez pas vous mettre en échec.");
		}
		
		echec = (testerEchec(adversaire(joueurActuel))) ? true : false;
		
		prochainTour();
		return (PieceEchecs)pieceCapturee;
	}
	
	private Piece faireUnMouvement(Position source, Position cible) {
		Piece piece = plateau.supprimerPiece(source);
		Piece pieceCapturee = plateau.supprimerPiece(cible);
		plateau.placerPiece(piece, cible);
		
		if (pieceCapturee !=null) {
			piecesSurLePlateau.remove(pieceCapturee);
			piecesCapturees.add(pieceCapturee);
		}
		
		return pieceCapturee;
	}
	
	private void annulerLeDeplacement(Position source, Position cible, Piece pieceCapturee) {
		Piece p = plateau.supprimerPiece(cible);
		plateau.placerPiece(p, source);
		
		if (pieceCapturee != null) {
			plateau.placerPiece(pieceCapturee, cible);
			piecesCapturees.remove(pieceCapturee);
			piecesSurLePlateau.add(pieceCapturee);
		}
	}
	
	private void validerLaPositionSource(Position position) {
		if (!plateau.ilYAUnPiece(position)) {
			throw new EchecsException("Il n'y a aucune pièce sur la position de la source.");
		} if (joueurActuel != ((PieceEchecs)plateau.piece(position)).getCouleur()) {
			throw new EchecsException("La pièce choisie n'est pas la vôtre.");
		} if(!plateau.piece(position).ilYAUnPossibleMovement()) {
			throw new EchecsException("Il n'y a aucun mouvement possible pour la pièce choisie.");
		}
	}
	
	public void validerLaPositionCible(Position source, Position cible) {
		if (!plateau.piece(source).mouvementPossible(cible)) {
			throw new EchecsException("La pièce choisie ne peut pas se déplacer vers la position choisi.");
		}
	}
	
	private void prochainTour() {
		tour++;
		joueurActuel = (joueurActuel == Couleur.BLANC) ? Couleur.NOIR : Couleur.BLANC;
	}
	
	private Couleur adversaire(Couleur couleur) {
		return (couleur == Couleur.BLANC) ? Couleur.NOIR : Couleur.BLANC;
	}
	
	private PieceEchecs roi(Couleur couleur) {
		List<Piece> list = piecesSurLePlateau.stream().filter(x -> ((PieceEchecs)x).getCouleur() == couleur).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof Roi) {
				return (PieceEchecs)p;
			}
		}
		throw new IllegalStateException("Il n'y a pas de roi " + couleur + " sur le plateau.");
	}
	
	private boolean testerEchec(Couleur couleur) {
		Position positionDuRoi = roi(couleur).getPositionEchecs().versPosition();
		List<Piece> piecesDeLAdversaire = piecesSurLePlateau.stream().filter(x -> ((PieceEchecs)x).getCouleur() == adversaire(couleur)).collect(Collectors.toList());
		for (Piece p : piecesDeLAdversaire) {
			boolean[][] matrice = p.mouvementsPossibles();
			if (matrice[positionDuRoi.getLigne()][positionDuRoi.getColonne()]) {
				return true;
			}
		}
		return false;
	}
		
	private void placerUneNouvellePiece(char colonne, int ligne, PieceEchecs piece) {
		plateau.placerPiece(piece, new PositionEchecs(colonne, ligne).versPosition());
		piecesSurLePlateau.add(piece);
	}
	
	private void configurationInitiale() {
		placerUneNouvellePiece('c', 1, new Tour(plateau, Couleur.BLANC));
		placerUneNouvellePiece('c', 2, new Tour(plateau, Couleur.BLANC));
		placerUneNouvellePiece('d', 2, new Tour(plateau, Couleur.BLANC));
		placerUneNouvellePiece('e', 2, new Tour(plateau, Couleur.BLANC));
		placerUneNouvellePiece('e', 1, new Tour(plateau, Couleur.BLANC));
		placerUneNouvellePiece('d', 1, new Roi(plateau, Couleur.BLANC));

		placerUneNouvellePiece('c', 7, new Tour(plateau, Couleur.NOIR));
		placerUneNouvellePiece('c', 8, new Tour(plateau, Couleur.NOIR));
		placerUneNouvellePiece('d', 7, new Tour(plateau, Couleur.NOIR));
		placerUneNouvellePiece('e', 7, new Tour(plateau, Couleur.NOIR));
		placerUneNouvellePiece('e', 8, new Tour(plateau, Couleur.NOIR));
		placerUneNouvellePiece('d', 8, new Roi(plateau, Couleur.NOIR));
	}
}

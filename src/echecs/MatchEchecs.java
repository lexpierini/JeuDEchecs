package echecs;

import echecs.pieces.Roi;
import echecs.pieces.Tour;
import jeuDePlateau.Piece;
import jeuDePlateau.Plateau;
import jeuDePlateau.PlateauException;
import jeuDePlateau.Position;

public class MatchEchecs {
	private Plateau plateau;
	
	public MatchEchecs() {
		plateau = new Plateau(8, 8);
		configurationInitiale();
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
	
	public PieceEchecs effectuerUnMouvementDEchecs(PositionEchecs positionSource, PositionEchecs positionCible) {
		Position source = positionSource.versPosition();
		Position cible = positionCible.versPosition();
		validerLaPositionSource(source);
		Piece pieceCapturee = faireUnMouvement(source, cible);
		return (PieceEchecs)pieceCapturee;
	}
	
	private Piece faireUnMouvement(Position source, Position cible) {
		Piece piece = plateau.supprimerPiece(source);
		Piece pieceCapturee = plateau.supprimerPiece(cible);
		plateau.placerPiece(piece, cible);
		return pieceCapturee;
	}
	
	private void validerLaPositionSource(Position position) {
		if (!plateau.ilYAUnPiece(position)) {
			throw new EchecsException("Il n'y a aucune pièce sur la position de la source.");
		} if(!plateau.piece(position).ilYAUnPossibleMovement()) {
			throw new EchecsException("Il n'y a aucun mouvement possible pour la pièce choisie.");
		}
	}
		
	private void placerUneNouvellePiece(char colonne, int ligne, PieceEchecs piece) {
		plateau.placerPiece(piece, new PositionEchecs(colonne, ligne).versPosition());
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

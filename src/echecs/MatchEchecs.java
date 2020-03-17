package echecs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import echecs.pieces.Cavalier;
import echecs.pieces.Dame;
import echecs.pieces.Fous;
import echecs.pieces.Pion;
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
	private boolean  echecEtMat; 
	
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
	
	public boolean getEchecEtMat() {
		return echecEtMat;
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
		
		if (testerEchecEtMat(adversaire(joueurActuel))) {
			echecEtMat = true;
		} else {
			prochainTour();			
		}
		
		return (PieceEchecs)pieceCapturee;
	}
	
	private Piece faireUnMouvement(Position source, Position cible) {
		PieceEchecs piece = (PieceEchecs)plateau.supprimerPiece(source);
		piece.augmenterCompteurDeMouvements();
		Piece pieceCapturee = plateau.supprimerPiece(cible);
		plateau.placerPiece(piece, cible);
		
		if (pieceCapturee !=null) {
			piecesSurLePlateau.remove(pieceCapturee);
			piecesCapturees.add(pieceCapturee);
		}
		// mouvement spécial roque Roi vers Tour à droite (Kingside)
		if (piece instanceof Roi && cible.getColonne() == source.getColonne() + 2) {
			Position sourceTour = new Position(source.getLigne(), source.getColonne() + 3);
			Position cibleTour = new Position(source.getLigne(), source.getColonne() + 1);
			PieceEchecs tour = (PieceEchecs)plateau.supprimerPiece(sourceTour);
			plateau.placerPiece(tour, cibleTour);
			tour.augmenterCompteurDeMouvements();
		}
		// mouvement spécial roque Roi vers Tour à gauche (Queenside)
		if (piece instanceof Roi && cible.getColonne() == source.getColonne() - 2) {
			Position sourceTour = new Position(source.getLigne(), source.getColonne() - 4);
			Position cibleTour = new Position(source.getLigne(), source.getColonne() - 1);
			PieceEchecs tour = (PieceEchecs)plateau.supprimerPiece(sourceTour);
			plateau.placerPiece(tour, cibleTour);
			tour.augmenterCompteurDeMouvements();
		}
		
		return pieceCapturee;
	}
	
	private void annulerLeDeplacement(Position source, Position cible, Piece pieceCapturee) {
		PieceEchecs piece = (PieceEchecs)plateau.supprimerPiece(cible);
		piece.reduireCompteurDeMouvements();
		plateau.placerPiece(piece, source);
		
		if (pieceCapturee != null) {
			plateau.placerPiece(pieceCapturee, cible);
			piecesCapturees.remove(pieceCapturee);
			piecesSurLePlateau.add(pieceCapturee);
		}
		// mouvement spécial roque Roi vers Tour à droite (Kingside)
		if (piece instanceof Roi && cible.getColonne() == source.getColonne() + 2) {
			Position sourceTour = new Position(source.getLigne(), source.getColonne() + 3);
			Position cibleTour = new Position(source.getLigne(), source.getColonne() + 1);
			PieceEchecs tour = (PieceEchecs)plateau.supprimerPiece(cibleTour);
			plateau.placerPiece(tour, sourceTour);
			tour.reduireCompteurDeMouvements();
		}
		// mouvement spécial roque Roi vers Tour à gauche (Queenside)
		if (piece instanceof Roi && cible.getColonne() == source.getColonne() - 2) {
			Position sourceTour = new Position(source.getLigne(), source.getColonne() - 4);
			Position cibleTour = new Position(source.getLigne(), source.getColonne() - 1);
			PieceEchecs tour = (PieceEchecs)plateau.supprimerPiece(cibleTour);
			plateau.placerPiece(tour, sourceTour);
			tour.reduireCompteurDeMouvements();
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
	
	private boolean testerEchecEtMat(Couleur couleur) {
		if (!testerEchec(couleur)) {
			return false;
		}
		List<Piece> list = piecesSurLePlateau.stream().filter(x -> ((PieceEchecs)x).getCouleur() == couleur).collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] matrice = p.mouvementsPossibles();
			for (int i = 0; i < plateau.getLignes(); i++) {
				for (int j = 0; j < plateau.getColonnes(); j++) {
					if (matrice[i][j]) {
						Position source = ((PieceEchecs)p).getPositionEchecs().versPosition();
						Position cible = new Position(i, j);
						Piece pieceCapturee = faireUnMouvement(source, cible);
						boolean testerEchec = testerEchec(couleur);
						annulerLeDeplacement(source, cible, pieceCapturee);
						if (!testerEchec) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
		
	private void placerUneNouvellePiece(char colonne, int ligne, PieceEchecs piece) {
		plateau.placerPiece(piece, new PositionEchecs(colonne, ligne).versPosition());
		piecesSurLePlateau.add(piece);
	}
	
	private void configurationInitiale() {
		placerUneNouvellePiece('a', 1, new Tour(plateau, Couleur.BLANC));
		placerUneNouvellePiece('b', 1, new Cavalier(plateau, Couleur.BLANC));
		placerUneNouvellePiece('c', 1, new Fous(plateau, Couleur.BLANC));
		placerUneNouvellePiece('d', 1, new Dame(plateau, Couleur.BLANC));
		placerUneNouvellePiece('e', 1, new Roi(plateau, Couleur.BLANC, this));
		placerUneNouvellePiece('f', 1, new Fous(plateau, Couleur.BLANC));
		placerUneNouvellePiece('g', 1, new Cavalier(plateau, Couleur.BLANC));
		placerUneNouvellePiece('h', 1, new Tour(plateau, Couleur.BLANC));
		placerUneNouvellePiece('a', 2, new Pion(plateau, Couleur.BLANC));
		placerUneNouvellePiece('b', 2, new Pion(plateau, Couleur.BLANC));
		placerUneNouvellePiece('c', 2, new Pion(plateau, Couleur.BLANC));
		placerUneNouvellePiece('d', 2, new Pion(plateau, Couleur.BLANC));
		placerUneNouvellePiece('e', 2, new Pion(plateau, Couleur.BLANC));
		placerUneNouvellePiece('f', 2, new Pion(plateau, Couleur.BLANC));
		placerUneNouvellePiece('g', 2, new Pion(plateau, Couleur.BLANC));
		placerUneNouvellePiece('h', 2, new Pion(plateau, Couleur.BLANC));
		
		placerUneNouvellePiece('a', 8, new Tour(plateau, Couleur.NOIR));
		placerUneNouvellePiece('b', 8, new Cavalier(plateau, Couleur.NOIR));
		placerUneNouvellePiece('c', 8, new Fous(plateau, Couleur.NOIR));
		placerUneNouvellePiece('d', 8, new Dame(plateau, Couleur.NOIR));
		placerUneNouvellePiece('e', 8, new Roi(plateau, Couleur.NOIR, this));
		placerUneNouvellePiece('f', 8, new Fous(plateau, Couleur.NOIR));
		placerUneNouvellePiece('g', 8, new Cavalier(plateau, Couleur.NOIR));
		placerUneNouvellePiece('h', 8, new Tour(plateau, Couleur.NOIR));
		placerUneNouvellePiece('a', 7, new Pion(plateau, Couleur.NOIR));
		placerUneNouvellePiece('b', 7, new Pion(plateau, Couleur.NOIR));
		placerUneNouvellePiece('c', 7, new Pion(plateau, Couleur.NOIR));
		placerUneNouvellePiece('d', 7, new Pion(plateau, Couleur.NOIR));
		placerUneNouvellePiece('e', 7, new Pion(plateau, Couleur.NOIR));
		placerUneNouvellePiece('f', 7, new Pion(plateau, Couleur.NOIR));
		placerUneNouvellePiece('g', 7, new Pion(plateau, Couleur.NOIR));
		placerUneNouvellePiece('h', 7, new Pion(plateau, Couleur.NOIR));
	}
}

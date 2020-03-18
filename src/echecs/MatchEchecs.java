package echecs;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import echecs.pieces.Cavalier;
import echecs.pieces.Dame;
import echecs.pieces.Fous;
import echecs.pieces.Pion;
import echecs.pieces.Roi;
import echecs.pieces.Tour;
import jeuDeSociete.Piece;
import jeuDeSociete.Echiquier;
import jeuDeSociete.Position;

public class MatchEchecs {
	private int tour;
	private Couleur joueurActuel;
	private Echiquier echiquier;
	private boolean echec;
	private boolean  echecMat;
	private PieceEchecs priseEnPassant;
	private PieceEchecs promouvoirPion;
	
	private List<Piece> piecesSurEchiquier = new ArrayList<>();
	private List<Piece> piecesCapturees = new ArrayList<>();
	
	public MatchEchecs() {
		echiquier = new Echiquier(8, 8);
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
		return echecMat;
	}
	
	public PieceEchecs getPriseEnPassant() {
		return priseEnPassant;
	}
	
	public PieceEchecs getPromouvoirPion() {
		return promouvoirPion;
	}
	
	public PieceEchecs[][] getPieces() {
		PieceEchecs[][] matrice = new PieceEchecs[echiquier.getLignes()][echiquier.getColonnes()];
		for (int i = 0; i < echiquier.getLignes(); i++) {
			for (int j = 0; j < echiquier.getColonnes(); j++) {
				matrice[i][j] = (PieceEchecs) echiquier.piece(i, j);
			}
		}
		return matrice;
	}
	
	public boolean[][] deplacementsPossibles(PositionEchecs positionOrigine){
		Position position = positionOrigine.versPosition();
		validerPositionOrigine(position);
		return echiquier.piece(position).deplacementsPossibles();
	}
	
	public PieceEchecs effectuerDeplacementEchec(PositionEchecs positionOrigine, PositionEchecs positionDestination) {
		Position origine = positionOrigine.versPosition();
		Position destination = positionDestination.versPosition();
		validerPositionOrigine(origine);
		validerPositionDestination(origine, destination);
		Piece pieceCapturee = faireMouvement(origine, destination);
		
		if (testerEchec(joueurActuel)) {
			annulerDeplacement(origine, destination, pieceCapturee);
			throw new  EchecsException("Vous ne pouvez pas vous mettre en échec.");
		}
		
		PieceEchecs pieceDeplacee = (PieceEchecs)echiquier.piece(destination);
		
		// Mouvement spécial: Promotion
		promouvoirPion = null;
		if (pieceDeplacee instanceof Pion) {
			if ((pieceDeplacee.getCouleur() == Couleur.BLANC && destination.getLigne() == 0) || (pieceDeplacee.getCouleur() == Couleur.NOIR && destination.getLigne() == 7)) {
				promouvoirPion = (PieceEchecs)echiquier.piece(destination);
				promouvoirPion = remplacerPiecePromue("D");
			}
		}
		
		echec = (testerEchec(adversaire(joueurActuel))) ? true : false;
		
		if (testerEchecEtMat(adversaire(joueurActuel))) {
			echecMat = true;
		} else {
			prochainTour();			
		}
		
		// Mouvement spécial: La prise en passant
		if (pieceDeplacee instanceof Pion && (destination.getLigne() == origine.getLigne() - 2 || destination.getLigne() == origine.getLigne() + 2)) {
			priseEnPassant = pieceDeplacee;
		} else {
			priseEnPassant = null;
		}
		
		return (PieceEchecs)pieceCapturee;
	}
	
	public PieceEchecs remplacerPiecePromue(String type) {
		if (promouvoirPion == null) {
			throw new IllegalStateException("Il n'y a aucune pièce à promouvoir.");
		}
		if (!type.equals("F") && !type.equals("C") && !type.equals("T") && !type.equals("D")) {
			throw new InvalidParameterException("Type non valide pour la promotion.");
		}
		
		Position pos = promouvoirPion.getPositionEchecs().versPosition();
		Piece p = echiquier.supprimerPiece(pos);
		piecesSurEchiquier.remove(p);
		
		PieceEchecs nouvellePiece = nouvellePiece(type, promouvoirPion.getCouleur());
		echiquier.placerPiece(nouvellePiece, pos);
		piecesSurEchiquier.add(nouvellePiece);
		
		return nouvellePiece;
	}
	
	private PieceEchecs nouvellePiece(String type, Couleur couleur) {
		if (type.equals("F")) return new Fous(echiquier, couleur);
		if (type.equals("C")) return new Cavalier(echiquier, couleur);
		if (type.equals("D")) return new Dame(echiquier, couleur);
		return new Tour(echiquier, couleur);
	}
	
	private Piece faireMouvement(Position origine, Position destination) {
		PieceEchecs piece = (PieceEchecs)echiquier.supprimerPiece(origine);
		piece.augmenterCompteurMouvements();
		Piece pieceCapturee = echiquier.supprimerPiece(destination);
		echiquier.placerPiece(piece, destination);
		
		if (pieceCapturee !=null) {
			piecesSurEchiquier.remove(pieceCapturee);
			piecesCapturees.add(pieceCapturee);
		}
		// Mouvement spécial: Roque, Roi vers Tour à droite (Kingside)
		if (piece instanceof Roi && destination.getColonne() == origine.getColonne() + 2) {
			Position origineTour = new Position(origine.getLigne(), origine.getColonne() + 3);
			Position destinationTour = new Position(origine.getLigne(), origine.getColonne() + 1);
			PieceEchecs tour = (PieceEchecs)echiquier.supprimerPiece(origineTour);
			echiquier.placerPiece(tour, destinationTour);
			tour.augmenterCompteurMouvements();
		}
		// Mouvement spécial: Roque, Roi vers Tour à gauche (Queenside)
		if (piece instanceof Roi && destination.getColonne() == origine.getColonne() - 2) {
			Position origineTour = new Position(origine.getLigne(), origine.getColonne() - 4);
			Position destinationTour = new Position(origine.getLigne(), origine.getColonne() - 1);
			PieceEchecs tour = (PieceEchecs)echiquier.supprimerPiece(origineTour);
			echiquier.placerPiece(tour, destinationTour);
			tour.augmenterCompteurMouvements();
		}
		// Mouvement spécial: La prise en passant
		if (piece instanceof Pion) {
			if (origine.getColonne() != destination.getColonne() && pieceCapturee == null) {
				Position positionPion;
				if (piece.getCouleur() == Couleur.BLANC) {
					positionPion = new Position(destination.getLigne() + 1, destination.getColonne());
				} else {
					positionPion = new Position(destination.getLigne() - 1, destination.getColonne());
				}
				pieceCapturee = echiquier.supprimerPiece(positionPion);
				piecesCapturees.add(pieceCapturee);
				piecesSurEchiquier.remove(pieceCapturee);
			}
		}
		
		return pieceCapturee;
	}
	
	private void annulerDeplacement(Position origine, Position destination, Piece pieceCapturee) {
		PieceEchecs piece = (PieceEchecs)echiquier.supprimerPiece(destination);
		piece.reduireCompteurMouvements();
		echiquier.placerPiece(piece, origine);
		
		if (pieceCapturee != null) {
			echiquier.placerPiece(pieceCapturee, destination);
			piecesCapturees.remove(pieceCapturee);
			piecesSurEchiquier.add(pieceCapturee);
		}
		// Mouvement spécial: Roque, Roi vers Tour à droite (Kingside)
		if (piece instanceof Roi && destination.getColonne() == origine.getColonne() + 2) {
			Position origineTour = new Position(origine.getLigne(), origine.getColonne() + 3);
			Position destinationTour = new Position(origine.getLigne(), origine.getColonne() + 1);
			PieceEchecs tour = (PieceEchecs)echiquier.supprimerPiece(destinationTour);
			echiquier.placerPiece(tour, origineTour);
			tour.reduireCompteurMouvements();
		}
		// Mouvement spécial: Roque, Roi vers Tour à gauche (Queenside)
		if (piece instanceof Roi && destination.getColonne() == origine.getColonne() - 2) {
			Position origineTour = new Position(origine.getLigne(), origine.getColonne() - 4);
			Position destinationTour = new Position(origine.getLigne(), origine.getColonne() - 1);
			PieceEchecs tour = (PieceEchecs)echiquier.supprimerPiece(destinationTour);
			echiquier.placerPiece(tour, origineTour);
			tour.reduireCompteurMouvements();
		}
		
		// Mouvement spécial: La prise en passant
		if (piece instanceof Pion) {
			if (origine.getColonne() != destination.getColonne() && pieceCapturee == priseEnPassant) {
				PieceEchecs pion = (PieceEchecs)echiquier.supprimerPiece(destination);
				Position positionPion;
				if (piece.getCouleur() == Couleur.BLANC) {
					positionPion = new Position(3, destination.getColonne());
				} else {
					positionPion = new Position(4, destination.getColonne());
				}
				echiquier.placerPiece(pion, positionPion);
			}
		}
	}
	
	private void validerPositionOrigine(Position position) {
		if (!echiquier.aPiece(position)) {
			throw new EchecsException("Il n'y a aucune pièce sur la position d'origine.");
		} if (joueurActuel != ((PieceEchecs)echiquier.piece(position)).getCouleur()) {
			throw new EchecsException("La pièce choisie n'est pas la vôtre.");
		} if(!echiquier.piece(position).aMovementPossible()) {
			throw new EchecsException("Il n'y a aucun mouvement possible pour la pièce choisie.");
		}
	}
	
	public void validerPositionDestination(Position origine, Position destination) {
		if (!echiquier.piece(origine).deplacementPossible(destination)) {
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
		List<Piece> list = piecesSurEchiquier.stream().filter(x -> ((PieceEchecs)x).getCouleur() == couleur).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof Roi) {
				return (PieceEchecs)p;
			}
		}
		throw new IllegalStateException("Il n'y a pas de roi " + couleur + " sur le échiquier.");
	}
	
	private boolean testerEchec(Couleur couleur) {
		Position positionRoi = roi(couleur).getPositionEchecs().versPosition();
		List<Piece> piecesAdversaire = piecesSurEchiquier.stream().filter(x -> ((PieceEchecs)x).getCouleur() == adversaire(couleur)).collect(Collectors.toList());
		for (Piece p : piecesAdversaire) {
			boolean[][] matrice = p.deplacementsPossibles();
			if (matrice[positionRoi.getLigne()][positionRoi.getColonne()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testerEchecEtMat(Couleur couleur) {
		if (!testerEchec(couleur)) {
			return false;
		}
		List<Piece> list = piecesSurEchiquier.stream().filter(x -> ((PieceEchecs)x).getCouleur() == couleur).collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] matrice = p.deplacementsPossibles();
			for (int i = 0; i < echiquier.getLignes(); i++) {
				for (int j = 0; j < echiquier.getColonnes(); j++) {
					if (matrice[i][j]) {
						Position origine = ((PieceEchecs)p).getPositionEchecs().versPosition();
						Position destination = new Position(i, j);
						Piece pieceCapturee = faireMouvement(origine, destination);
						boolean testerEchec = testerEchec(couleur);
						annulerDeplacement(origine, destination, pieceCapturee);
						if (!testerEchec) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
		
	private void placerNouvellePiece(char colonne, int ligne, PieceEchecs piece) {
		echiquier.placerPiece(piece, new PositionEchecs(colonne, ligne).versPosition());
		piecesSurEchiquier.add(piece);
	}
	
	private void configurationInitiale() {
		placerNouvellePiece('a', 1, new Tour(echiquier, Couleur.BLANC));
		placerNouvellePiece('b', 1, new Cavalier(echiquier, Couleur.BLANC));
		placerNouvellePiece('c', 1, new Fous(echiquier, Couleur.BLANC));
		placerNouvellePiece('d', 1, new Dame(echiquier, Couleur.BLANC));
		placerNouvellePiece('e', 1, new Roi(echiquier, Couleur.BLANC, this));
		placerNouvellePiece('f', 1, new Fous(echiquier, Couleur.BLANC));
		placerNouvellePiece('g', 1, new Cavalier(echiquier, Couleur.BLANC));
		placerNouvellePiece('h', 1, new Tour(echiquier, Couleur.BLANC));
		placerNouvellePiece('a', 2, new Pion(echiquier, Couleur.BLANC, this));
		placerNouvellePiece('b', 2, new Pion(echiquier, Couleur.BLANC, this));
		placerNouvellePiece('c', 2, new Pion(echiquier, Couleur.BLANC, this));
		placerNouvellePiece('d', 2, new Pion(echiquier, Couleur.BLANC, this));
		placerNouvellePiece('e', 2, new Pion(echiquier, Couleur.BLANC, this));
		placerNouvellePiece('f', 2, new Pion(echiquier, Couleur.BLANC, this));
		placerNouvellePiece('g', 2, new Pion(echiquier, Couleur.BLANC, this));
		placerNouvellePiece('h', 2, new Pion(echiquier, Couleur.BLANC, this));
		
		placerNouvellePiece('a', 8, new Tour(echiquier, Couleur.NOIR));
		placerNouvellePiece('b', 8, new Cavalier(echiquier, Couleur.NOIR));
		placerNouvellePiece('c', 8, new Fous(echiquier, Couleur.NOIR));
		placerNouvellePiece('d', 8, new Dame(echiquier, Couleur.NOIR));
		placerNouvellePiece('e', 8, new Roi(echiquier, Couleur.NOIR, this));
		placerNouvellePiece('f', 8, new Fous(echiquier, Couleur.NOIR));
		placerNouvellePiece('g', 8, new Cavalier(echiquier, Couleur.NOIR));
		placerNouvellePiece('h', 8, new Tour(echiquier, Couleur.NOIR));
		placerNouvellePiece('a', 7, new Pion(echiquier, Couleur.NOIR, this));
		placerNouvellePiece('b', 7, new Pion(echiquier, Couleur.NOIR, this));
		placerNouvellePiece('c', 7, new Pion(echiquier, Couleur.NOIR, this));
		placerNouvellePiece('d', 7, new Pion(echiquier, Couleur.NOIR, this));
		placerNouvellePiece('e', 7, new Pion(echiquier, Couleur.NOIR, this));
		placerNouvellePiece('f', 7, new Pion(echiquier, Couleur.NOIR, this));
		placerNouvellePiece('g', 7, new Pion(echiquier, Couleur.NOIR, this));
		placerNouvellePiece('h', 7, new Pion(echiquier, Couleur.NOIR, this));
	}
}

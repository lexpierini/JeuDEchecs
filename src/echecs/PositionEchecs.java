package echecs;

import jeuDeSociete.Position;

public class PositionEchecs {
	private char colonne;
	private int ligne;
	
	public PositionEchecs(char colonne, int ligne) {
		if (colonne < 'a' || colonne > 'h' || ligne < 1 || ligne > 8) {
			throw new EchecsException("Erreur lors de l'instanciation de PositionEchecs. Les valeurs valides vont de a1 à h8.");
		}
		
		this.colonne = colonne;
		this.ligne = ligne;
	}

	public char getColonne() {
		return colonne;
	}

	public int getLigne() {
		return ligne;
	}
	
	protected Position versPosition() {
		return new Position(8 - ligne, colonne - 'a');
	}
	
	protected static PositionEchecs desPosition(Position position) {
		return new PositionEchecs((char)('a' + position.getColonne()), 8 - position.getLigne());
	}
	
	@Override
	public String toString() {
		return "" + colonne + ligne;
	}
}

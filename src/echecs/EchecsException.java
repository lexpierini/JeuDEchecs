package echecs;

import jeuDePlateau.PlateauException;

public class EchecsException extends PlateauException {
	private static final long serialVersionUID = 1L;
	
	public EchecsException(String msg) {
		super(msg);
	}
}

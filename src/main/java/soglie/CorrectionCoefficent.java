package soglie;


import java.math.BigDecimal;

/**
 * Un coefficente di correzione ha il compito di modificare un valore in input secondo il proprio fattore correttivo interno.
 * 
 * 
 * @author s.stranieri
 *
 */
public interface CorrectionCoefficent {

	public static final String VALORE = "VALORE";
	public static final String VALORE_BOOLEANO = "VALORE_BOOLEANO";
	public static final String VALORE_MIN = "VALORE_MIN";
	public static final String VALORE_MAX = "VALORE_MAX";
	public static final String SEGNO = "SEGNO";
	public static final String COEFFICENTE = "COEFFICENTE";
	public static final String DESCRIZIONE = "DESCRIZIONE";
	
	boolean isApplicable(Operazione operazione, String field);
	
	BigDecimal apply(BigDecimal amount);
	
	SogliaCalcolata apply(SogliaCalcolata newThreshold);
	
	/**
	 * Metodo che prende in input la classe Visitor a cui delegare
	 * la gestione delle varie tipologie di CorrectionCoefficient
	 * 
	 * @param visitable
	 * @param operazione
	 * @param field
	 * @return
	 */
	boolean verifica(Visitable visitable, Operazione operazione, String field);
	
}


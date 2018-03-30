package soglie;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

/**
 * Un possibile valore che può essere assunto da una {@link SogliaCorrettiva}
 * 
 * @author s.stranieri
 *
 */
public class ValoreSogliaCorrettiva {
	
	public static final String VALORE = "VALORE";
	public static final String DESCRIZIONE = "DESCRIZIONE";
	public static final String IMPORTO_BASE = "IMPORTO_BASE";
	public static final String COEFFICIENTI = "COEFFICIENTI";

	private String valore;
	private String descrizione;
	private BigDecimal importo;
	
	private List<Coefficente> coefficenti = new ArrayList<Coefficente>();
	
	public ValoreSogliaCorrettiva(DBObject dominio) {
		valore = (String) dominio.get(VALORE);
		descrizione = (String) dominio.get(DESCRIZIONE);
		importo = BigDecimal.valueOf( ((Number) dominio.get(IMPORTO_BASE)).doubleValue() );
		
		BasicDBList domini = (BasicDBList) dominio.get(COEFFICIENTI);
		for (Object o : domini) {
			DBObject dbo = (DBObject) o;
	
			coefficenti.add(new Coefficente(dbo));
		}
	}

	public String getValore() {
		return valore;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public BigDecimal getImporto() {
		return importo;
	}

	public List<Coefficente> getCoefficenti() {
		return coefficenti;
	}
	
	/**
	 * Calcola la nuova soglia per l'operazione passata in input.
	 * La Classe Helper gestisce le diverse tipologie di Coefficienti
	 * o fornisce i domini Gpr per i Coefficienti che li necessitano 
	 * 
	 * @param operazione
	 * @param helper
	 * @return
	 */
	public SogliaCalcolata calculateThreshold(Operazione operazione, CoefficientCorrectionHelper helper){
		
		SogliaCalcolata sogliaCalcolata = new SogliaCalcolata();
		sogliaCalcolata.setAmount(importo);		
		sogliaCalcolata.setDescrizione(descrizione);		
		sogliaCalcolata.setSegmento(valore);
		
		
		List<Coefficente> coefficienti = getCoefficenti();
		for (Coefficente coefficiente : coefficienti) {
			/**
			 * Recupero il nome del CAMPO coefficiente correttivo: 
			 * Questo è il nome del campo che cercherò in Operazione
			 */
			String nomeCampo = coefficiente.getCampo();
			List<CorrectionCoefficent> coefficientiCorrettivi = coefficiente.getCoefficentiCorrettivi();
			
			for (CorrectionCoefficent correctionCoefficent : coefficientiCorrettivi) {
				/**
				 * Confronto il valore di Operazione con quello del Coefficiente:
				 * se rientra nei valori del coefficiente , modifico importo
				 */
//				if(correctionCoefficent.isApplicable(operazione, nomeCampo))
//					importo = correctionCoefficent.apply(importo);				
				if(correctionCoefficent.verifica(helper, operazione, nomeCampo))
					sogliaCalcolata = correctionCoefficent.apply(sogliaCalcolata);	
			}
		}
		return sogliaCalcolata;
	}
	
}

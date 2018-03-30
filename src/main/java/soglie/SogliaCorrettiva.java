package soglie;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

/**
 * Una Soglia correttiva contiene le logiche di calibrazione di un determinato importo sulla base dei contenuti dei campi di un'anagrafe
 * 
 * SOGLIA_CORRETTIVA
 * 
 * @author s.stranieri
 *
 */
public class SogliaCorrettiva {
	
	public static final String CAMPO = "CAMPO";
	public static final String DESCRIZIONE = "DESCRIZIONE";
	public static final String DOMINI_SOGLIA = "DOMINI_SOGLIA";
	
	private String campoRiferimento;
	private String descrizione;
	
	private  Map<String, ValoreSogliaCorrettiva> dominiSoglia = new HashMap<String, ValoreSogliaCorrettiva>();

	public SogliaCorrettiva(DBObject document) {
		campoRiferimento = (String) document.get(CAMPO);
		descrizione = (String) document.get(DESCRIZIONE);
		
		BasicDBList domini = (BasicDBList) document.get(DOMINI_SOGLIA);
		//TODO: la lista domini potrebbe essere null.
		for (Object o : domini) {
			DBObject dominio = (DBObject) o;
			String valore = (String) dominio.get(ValoreSogliaCorrettiva.VALORE);
			dominiSoglia.put(valore, new ValoreSogliaCorrettiva(dominio));
		}
	}
	
	public String getCampoRiferimento() {
		return campoRiferimento;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public Map<String, ValoreSogliaCorrettiva> getDominiSoglia() {
		return dominiSoglia;
	}

	public void setDominiSoglia(Map<String, ValoreSogliaCorrettiva> dominiSoglia) {
		this.dominiSoglia = dominiSoglia;
	}
	
	
	public SogliaCalcolata calculateThreshold(Operazione operazione, CoefficientCorrectionHelper helper) throws ThresholdException{
				
		Map<String, ValoreSogliaCorrettiva> dominiSoglia = this.getDominiSoglia();
				
		/**
		 * Per individuare il segmento, recupero:
		 * - TIPOLOGIA_ATTIVITA (se valorizzato mi indica una PF)
		 *   Se null oppure 00 recupero 
		 *   - NATURA_GIURIDICA (se valorizzato mi indica PNF)
		 * 		Se è null o 00 recupero 
		 *      - TIPO_RAPPORTO
		 * 			IF == 0,1 -> PF (settare segmento a 'Dipendente') ELSE PF (settare segmento a 'Persona Giuridica Non S.p.a')
		 * 		    in entrambi i casi bisogna settare: segmento = "01"
		 */
		
		String segmento = (operazione.field("TIPOLOGIA_ATTIVITA") != null  && !operazione.field("TIPOLOGIA_ATTIVITA").equals("00") ) ? operazione.field("TIPOLOGIA_ATTIVITA") : operazione.field("NATURA_GIURIDICA"); 
		
		if (segmento ==null || segmento.equals("00")){
//			String indiceTipoRapporto = (operazione.field("TIPO_RAPPORTO"));
			segmento = "01";
		}		
		
		ValoreSogliaCorrettiva valoreSogliaCorrettiva = dominiSoglia.get(segmento);
		return  valoreSogliaCorrettiva.calculateThreshold( operazione,  helper);
	}
	
}

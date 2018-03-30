package soglie;

import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;

/**
 * CorrectionCoefficient che dipende da Domini Gpr
 * Diversamente da tutti gli altri, ha bisogno dei domini GPR per valutare
 * se è applicabile o meno.
 * 
 * @author Admin
 *
 */
public class GprCorrectionCoefficient extends NumericCorrectionCoefficent {
	
	
	public GprCorrectionCoefficient(DBObject document, String name) {
		super(document, name);
	}
	
	@Override
	public boolean verifica(Visitable visitable, Operazione operazione, String field){
		return visitable.verifica(this, operazione,  field);
	}

	
	protected boolean doCheckApplicability(Map<String, List<RangeGpr>> domainsGpr, Operazione operazione, String field) {
		
		/**
		 * recupero il campo (Sae o Ateco) da Operazione corrente
		 */
		String saeOperazione = operazione.field(field);
		
		/**
		 * domainsGpr contiene i domini GPR relativi ad Ateco e Sae
		 */
		List<RangeGpr> listaRange = null;
		if (domainsGpr==null) return false;
		
		listaRange = domainsGpr.get(field);
		if (listaRange==null) return false;
			
		for (RangeGpr range : listaRange) {
			
			String value = range.getValore();			
			if (value.equals(saeOperazione)){				
				int punteggio = range.getPunteggio();
				return doCheckApplicability(punteggio);
			}
			
		}

		return false;
	}
}


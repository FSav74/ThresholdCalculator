package soglie;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

/**
 * Un Valore soglia rappresenta uno dei possibili valori del campo referenziato da una {@link SogliaCorrettiva}
 * 
 * 
 * @author s.stranieri
 *
 */
public class CorrectiveCoefficentOperator {
	
	public static final String FIELD = "FIELD";
	public static final String TYPE = "TYPE";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String DOMINI_COEFFICENTE = "DOMINI_COEFFICENTE";
	
	private String field;
	private String type;
	private String description;
	
	private List<CorrectionCoefficent> coefficents = new ArrayList<CorrectionCoefficent>();
	
	public CorrectiveCoefficentOperator(DBObject document) {
		this.field = (String) document.get(FIELD);
		this.type = (String) document.get(TYPE);
		this.description = (String) document.get(DESCRIPTION);
		
		BasicDBList domains = (BasicDBList) document.get(DOMINI_COEFFICENTE);
		if(domains != null) {
			for(Object domain : domains) {
				coefficents.add( AgeCorrectionCoefficent.create((DBObject) domain, field, type) );
			}
		}
	}
	
//	public BigDecimal correctIfApplicable(Operazione operazione, BigDecimal amount) {
//		for (CorrectionCoefficent correctionCoefficent : coefficents) {
//			if(correctionCoefficent.isApplicable(operazione, field)) {
//				amount = correctionCoefficent.apply(amount);
//				break;
//			}
//		}
//		return amount;
//	}
	
	public void setCoefficents(List<CorrectionCoefficent> coefficents) {
		this.coefficents = coefficents;
	}
	
	public String getDescription() {
		return description;
	}

}

package soglie;



import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public abstract class AbstractCorrectionCoefficent implements CorrectionCoefficent {

	private static final Logger LOG = LoggerFactory.getLogger(CorrectionCoefficent.class);

	private String description;
	private BigDecimal sign;
	private BigDecimal coefficent;
	private DBObject dbObject;
	private String nameField;
	
	public AbstractCorrectionCoefficent(DBObject document, String name) {
		nameField = name;
		dbObject = (BasicDBObject) ((BasicDBObject)document).copy();	
		this.description = (String) document.get( DESCRIZIONE );
		this.sign = BigDecimal.valueOf( ((Number) document.get( SEGNO )).doubleValue() );
		this.coefficent = BigDecimal.valueOf( ((Number) document.get( COEFFICENTE )).doubleValue() );
	}
	
	public static CorrectionCoefficent create(DBObject content, String field, String type) {
		CorrectionCoefficent result = null;
		
		if("NUMERIC".equals(type)) {
			result = new NumericCorrectionCoefficent(content, field);
		} else
		
		if("BOOLEAN".equals(type)) {
			result = new BooleanCorrectionCoefficent(content, field);
		} else
		
		if("FUNC".equals(type)) {
			
			/**
			 * Check field name
			 */
			if("ETA".equals(field) || ("ANNI_COSTITUZIONE".equals(field))) {
				result = new AgeCorrectionCoefficent(content, field);
			}

			/**
			 * Coefficienti che dipendono da GPR
			 */
			if (  ("SAE".equals(field)) || ("ATECO".equals(field))   ){
				result = new GprCorrectionCoefficient(content, field);
			}
			
			
		} else
			
		result = new AlphanumericCorrectionCoefficent(content,field);
		
		return result;
		
	}
	
	//@Override
	public boolean isApplicable(Operazione operazione, String field) {
		boolean result = false;
		try {
			result = this.doCheckApplicability(operazione, field);
		} catch(Exception e) {
			if(LOG.isWarnEnabled()) {
				LOG.warn("isApplicable({}, {})", operazione.getId().toString(), field, e);
			}
		}
		return result;
	}
	
	//@Override
	public boolean verifica(Visitable visitable, Operazione operazione, String field){
		return visitable.verifica(this, operazione,  field);
	}
	
	protected abstract boolean doCheckApplicability(Operazione operazione, String field);
	
	//@Override
	public BigDecimal apply(BigDecimal amount) {
		return amount.add( amount.multiply( sign ).multiply( coefficent ) );
	}
	
	//@Override
	public SogliaCalcolata apply(SogliaCalcolata newThreshold) {
		BigDecimal amount = newThreshold.getAmount();
		amount = amount.add( amount.multiply( sign ).multiply( coefficent ) );
		newThreshold.setAmount(amount);
		
		newThreshold.getCoefficienti().put(nameField,dbObject);
		
		return newThreshold;
	}
	
	public String getDescription() {
		return description;
	}

}

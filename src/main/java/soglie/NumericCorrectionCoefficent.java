package soglie;


import com.mongodb.DBObject;

public class NumericCorrectionCoefficent extends AbstractCorrectionCoefficent {

	private Integer min;
	private Integer max;
	
	public NumericCorrectionCoefficent(DBObject document, String name) {
		super(document, name);
		this.min = (Integer) document.get(VALORE_MIN);
		this.max = (Integer) document.get(VALORE_MAX);
	}

	@Override
	protected boolean doCheckApplicability(Operazione operazione, String field) {
		//Number value = operazione.get(field, operazione, DocumentWrapper.DEFAULT_DOUBLE);
		Number value = operazione.get(field);
		return doCheckApplicability(value);
	}

	protected boolean doCheckApplicability(Number value) {
		if(max == null || max.doubleValue() < 0) {
			return value.doubleValue() >= min.doubleValue();
		} else {
			return value.doubleValue() >= min.doubleValue() && value.doubleValue() <= max.doubleValue();
		}
	}

}

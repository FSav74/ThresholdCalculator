package soglie;


import com.mongodb.DBObject;

public class AlphanumericCorrectionCoefficent extends AbstractCorrectionCoefficent {
	
	private String targetValue;
	
	public AlphanumericCorrectionCoefficent(DBObject document, String name) {
		super(document, name);
		this.targetValue = (String) document.get(VALORE);
	}

	@Override
	protected boolean doCheckApplicability(Operazione operazione, String field) {
		String value = operazione.field(field);
		if (value==null) return false;
		return value.equals(targetValue);
	}

}

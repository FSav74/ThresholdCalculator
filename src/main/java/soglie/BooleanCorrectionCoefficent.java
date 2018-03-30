package soglie;


import com.mongodb.DBObject;

public class BooleanCorrectionCoefficent extends AbstractCorrectionCoefficent {
	
	private Boolean targetValue;

	public BooleanCorrectionCoefficent(DBObject document, String name) {
		super(document, name);
		this.targetValue = (Boolean) document.get(VALORE_BOOLEANO);
	}

	@Override
	protected boolean doCheckApplicability(Operazione operazione, String field) {
		Boolean bool = operazione.bool(field);
		if (bool==null) return false;
		return bool.equals(targetValue);
	}

}

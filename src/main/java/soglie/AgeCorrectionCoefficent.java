package soglie;


import com.mongodb.DBObject;

public class AgeCorrectionCoefficent extends NumericCorrectionCoefficent {

	public AgeCorrectionCoefficent(DBObject document,String name) {
		super(document, name);
	}

	@Override
	protected boolean doCheckApplicability(Operazione operazione, String field) {
		int eta = operazione.eta();
		return super.doCheckApplicability( Integer.valueOf(eta) );
	}

}

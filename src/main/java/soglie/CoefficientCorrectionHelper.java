package soglie;

import java.util.List;
import java.util.Map;



/**
 * Classe Helper (Visitor) per gestire le diverse tipologie di Correction Coefficient.
 * in particolare quelli dipendenti da domini Gpr (che necessitano in input
 * dei domini GPR
 * 
 * 
 * @author Admin
 *
 */
public class CoefficientCorrectionHelper implements Visitable{
	
	private Map<String, List<RangeGpr>> domainsGpr = null;
	
	public CoefficientCorrectionHelper(Map<String, List<RangeGpr>> param){
		domainsGpr = param;
	}

	//@Override
	public boolean verifica(CorrectionCoefficent cc, Operazione o, String campo) {
		return cc.isApplicable(o, campo);
	}

	//@Override
	public boolean verifica(GprCorrectionCoefficient cc, Operazione o, String campo) {
		return cc.doCheckApplicability(domainsGpr,o,campo);
	}

}

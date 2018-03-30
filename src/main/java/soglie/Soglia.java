package soglie;



import java.util.HashMap;
import java.util.Map;

public class Soglia {
	
	private String nomeSoglia;

	private Map<String, SogliaCorrettiva> soglieCorrettive = new HashMap<String, SogliaCorrettiva>();
	
	public Soglia(String nomeSoglia) {
		this.nomeSoglia = nomeSoglia;
	}
	
	public void addSogliaCorrettiva(SogliaCorrettiva sogliaCorrettiva) {
		soglieCorrettive.put(sogliaCorrettiva.getCampoRiferimento(), sogliaCorrettiva);
	}
	public Map<String, SogliaCorrettiva> getSoglieCorrettive(){
		return soglieCorrettive;
	}
	
	public String getNomeSoglia() {
		return nomeSoglia;
	}
	
	
	public SogliaCalcolata calculateThreshold(Operazione operazione, CoefficientCorrectionHelper helper) throws ThresholdException{

		Map<String, SogliaCorrettiva> soglie = this.getSoglieCorrettive();
		
		SogliaCorrettiva sogliaCorrettiva = (soglie.get("SEGMENTO_CLIENTELA") != null) ? soglie.get("SEGMENTO_CLIENTELA") : soglie.get("NATURA_GIURIDICA"); 
		
		if (sogliaCorrettiva == null){
			throw new ThresholdException("Error retrieve thresholds for field SEGMENTO_CLIENTELA or NATURA_GIURIDICA");
		}
		
		return sogliaCorrettiva.calculateThreshold(operazione,helper);
	}
	
}

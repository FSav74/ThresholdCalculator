package soglie;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

/**
 * Un possibile coefficente da applicare 
 * 
 * @author s.stranieri
 *
 */
public class Coefficente {
	
	private String campo;
	private String tipo;
	private String descrizione;
	
	
	public static final String CAMPO = "CAMPO";
	public static final String TIPO = "TIPO";
	public static final String DESCRIZIONE = "DESCRIZIONE";
	public static final String DOMINI_COEFFICENTE = "DOMINI_COEFFICENTE";
	
	private List<CorrectionCoefficent> coefficentiCorrettivi = new ArrayList<CorrectionCoefficent>();
	
	public Coefficente(DBObject coefficente) {
		
		campo = (String) coefficente.get(CAMPO);
		tipo  = (String) coefficente.get(TIPO);
		descrizione = (String) coefficente.get(DESCRIZIONE);
		
		BasicDBList dominiCoefficienti = (BasicDBList) coefficente.get(DOMINI_COEFFICENTE);
		for (Object o : dominiCoefficienti) {
			DBObject dbo = (DBObject) o;
			
			CorrectionCoefficent correctionCoefficent = AbstractCorrectionCoefficent.create(dbo, campo, tipo);
			coefficentiCorrettivi.add(correctionCoefficent);
			
		}
		
	}

	public String getCampo() {
		return campo;
	}

	public String getTipo() {
		return tipo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public List<CorrectionCoefficent> getCoefficentiCorrettivi() {
		return coefficentiCorrettivi;
	}
	
	

}

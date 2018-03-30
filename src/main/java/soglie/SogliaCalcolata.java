package soglie;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Classe per memorizzare 
 * la soglia calcolata e i coefficienti applicati
 * 
 * @author F.Saverio Letterese
 *
 */
public class SogliaCalcolata {
	private BigDecimal amount;
	private Map<String,DBObject> coefficienti = new HashMap<String,DBObject>();
	
	private String segmento;
	private String descrizione;
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Map<String, DBObject> getCoefficienti() {
		return coefficienti;
	}
	public void setCoefficienti(Map<String,DBObject> coefficienti) {
		this.coefficienti = coefficienti;
	}
	
	public String getSegmento() {
		return segmento;
	}
	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public DBObject build(){
		
		DBObject document = new BasicDBObject();
		document.put("SEGMENTO", segmento);
		document.put("DESCRIZIONE", descrizione);
		
		Iterator<Entry<String, DBObject>> it = coefficienti.entrySet().iterator();
		List<BasicDBObject> listaCoefficienti = new ArrayList<BasicDBObject>();
		
	    while (it.hasNext()) {
	        Map.Entry<String, DBObject> pair = (Map.Entry<String, DBObject>)it.next();
	       
	        BasicDBObject coeff = new BasicDBObject();
	        coeff.put("NOME", pair.getKey());
	        coeff.put("COEFFICIENTE", pair.getValue());
	        
	        listaCoefficienti.add(coeff);	        
	        
	    }
	    document.put("COEFFICIENTI",listaCoefficienti);
			
		DBObject documentRoot = new BasicDBObject();
		documentRoot.put("CAMPO", "tot_imp");
		documentRoot.put("VALORE", amount.doubleValue());
		//document.put("COEFFICIENTI", sogliaCalcolata.getCoefficienti());
		documentRoot.put("COEFFICIENTI", document);
		

		
		return documentRoot;
	}
	
		
}

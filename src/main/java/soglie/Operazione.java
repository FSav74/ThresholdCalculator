package soglie;

import java.util.HashMap;
import java.util.Map;

public class Operazione {

	private String id;
	private Map<String, String> hash = new HashMap<String, String>();
	
	private Map<String, Number> hashNumber = new HashMap<String, Number>();
	
	private Map<String, Boolean> hashBoolean = new HashMap<String, Boolean>();
	public Operazione() {
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String field(String input){
		return hash.get(input);
		
	}
	
	public Number get(String input){
		return hashNumber.get(input);
		
	}
	
	public Boolean bool(String input){
		return hashBoolean.get(input);	
	}
	public int eta(){
		return (Integer) hashNumber.get("ETA");	
	}
}

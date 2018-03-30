import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import soglie.Soglia;
import soglie.SogliaCorrettiva;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;


public class ThresholdCalculator {

	public ThresholdCalculator() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "C:\\software\\progetti-KEPLERx86_64\\ThresholdCalculator\\src\\main\\resources\\REGOLA_01.json";
		//Charset charSet = Charset.forName("UTF-8");
		String jsonString="EMPTY";
		try {
			jsonString = readFile(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("json:\n"+jsonString);
		
		// convert JSON to DBObject directly
		DBObject dbObject =  null;
		try {
			dbObject = (DBObject) JSON.parse(jsonString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
	
		DBObject dbObjectRoot = (DBObject)dbObject.get("_id");
		String nomeRegola = (String) dbObjectRoot.get("ID_REGOLA");

		
		DBObject dbObject2 = (DBObject)dbObject.get("INPUT");
		List<Object> listaSoglie = (List<Object>) dbObject2.get("SOGLIE_CORRETTIVE_REGOLA");

			
			Soglia sogliaRegola = new Soglia(nomeRegola);
			if (listaSoglie != null){
				for (Object elem : listaSoglie) {
					
					SogliaCorrettiva sogliaCorrettiva = new SogliaCorrettiva((DBObject)elem);
					sogliaRegola.addSogliaCorrettiva( sogliaCorrettiva );
					DBObject ooo = (DBObject)elem;
					
					System.out.println("ELEM:\n"+ooo.toString());
				}
			}
			//doc.put("SOGLIE", soglie);
			
			//thresholds.put( nomeRegola, sogliaRegola );

	}
	
	static String readFile(String path, Charset encoding) throws IOException 
	{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
	}

}

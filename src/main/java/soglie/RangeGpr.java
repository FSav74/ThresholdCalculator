package soglie;


/**
 * Pojo per i domini Gpr necessari per le soglie 
 * 
 * @author Admin
 *
 */
public class RangeGpr {
	
	private String idParametro;
	private String valore;
	private int punteggio=0;
	
	public RangeGpr(String iIdParametro, String ivalore, int ipunteggio){
		idParametro = iIdParametro;
		valore = ivalore;
		punteggio=ipunteggio;
	}

	public String getIdParametro() {
		return idParametro;
	}

	public void setIdParametro(String idParametro) {
		this.idParametro = idParametro;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public int getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}
	
}


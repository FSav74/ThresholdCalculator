package soglie;

/**
 * 
 * @author F.Saverio Letterese
 *
 */
public class ThresholdException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -845369275293941380L;
	
	public ThresholdException(String message){
		super(message);
	}
	public ThresholdException(String message, Throwable throwable){
		super(message, throwable);
	}

}

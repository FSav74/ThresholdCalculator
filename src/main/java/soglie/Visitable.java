package soglie;



public interface Visitable {
	
	public boolean verifica(CorrectionCoefficent cc, Operazione o, String campo);
	public boolean verifica(GprCorrectionCoefficient cc, Operazione o, String campo);

}

package poo.pn;

public class ArcoOut extends Arco{
	Posto postoOut;
	//Transizione t;
	
	public ArcoOut( Posto postoOut, int peso ) {
		super(peso);
		this.postoOut = postoOut;
	}//ArcoOut
	
	public ArcoOut( Posto postoOut ) {
		super(1);
		this.postoOut = postoOut;
	}//ArcoOut
	
	public String toString() {
		return "Arco verso " + postoOut + " " + super.toString();
	}//toString
		
}//ArcoOut

package poo.pn;

public class ArcoIn extends Arco{
	Posto postoIn;
	//Transizione t;
	
	public ArcoIn( Posto postoIn, int peso ){
		super(peso);
		this.postoIn = postoIn; // aliasing di un oggetto mutabile
	}
	public ArcoIn( Posto postoIn ){
		super(1);
		this.postoIn = postoIn; // aliasing di un oggetto mutabile
	}
	
	public String toString() {
		return "Arco da " + postoIn + " " + super.toString();
	}
}//ArcoIn

package poo.pn;

public abstract class Arco {
	// identità del posto?
	private int peso;
	public Arco( int peso ) {
		this.peso = peso;
	}
	
	public int getPeso() { return peso; }
	
	public String toString() {
		return "peso: " + peso;
	}//toString
}//Arco

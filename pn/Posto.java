package poo.pn;

public class Posto extends Entita {
	private int marcatura;
	public Posto( String nome ) {
		super(nome);
		marcatura = 0; // ridondante
	}
	public Posto( String nome, int marcatura ) {
		super(nome);
		this.marcatura = marcatura;
	}
	public int getMarcatura() { return marcatura; }
	public void setMarcatura( int m ) { marcatura = m; }
	
	public String toString() {
		return super.toString() + "#" + marcatura;
	}
}//Posto

package poo.pn;

public abstract class Entita {
	private String nome;
	
	public Entita( String nome ) {
		this.nome = nome;
	}
	
	public String getNome() { return nome; }
	
	public boolean equals( Object o ) {
		if ( !(o instanceof Entita) ) return false;
		if ( o == this ) return true;
		Entita e = (Entita) o;
		return e.nome==this.nome;
	}//equals
	
	public int hashCode() {
		return nome.hashCode();
	}//hashCode
	
	public String toString() {
		return nome;
	}//toString
}//Entita

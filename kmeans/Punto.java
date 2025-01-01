package poo.kmeans;

public class Punto {
	private final double X;
	private final double Y;
	
	public Punto( final double X, final double Y ) {
		this.X = X;
		this.Y = Y;
	}
	
	public double getX() { return X; }
	public double getY() { return Y; }
	
	public boolean equals( Object o ) {
		if ( !(o instanceof Punto) ) return false;
		if ( o==this ) return true;
		Punto p = (Punto) o;
		return X==p.X && Y==p.Y;
	}//equals
	
	public int hashCode() {
		final int M = 83;
		return Double.hashCode(X*M+Y);
	}//hashCode
	
	public String toString() {
		return "<"+X+","+Y+">";
	}//toString
	
	public double distanza( Punto p ) {
		return Math.sqrt( (X-p.X)*(X-p.X) + (Y-p.Y)*(Y-p.Y) );
	}//distanza
	
	public Punto puntoMedio( Punto p ) {
		return new Punto( (X+p.X)/2, (Y+p.Y)/2 );
	}//puntoMedio
	
}//Punto

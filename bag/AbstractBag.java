package poo.bag;

import java.util.Iterator;

public abstract class AbstractBag<T> implements Bag<T>{
	public String toString() {
		StringBuilder sb = new StringBuilder(150);
		sb.append('[');
		for ( T elem : this ) {
			sb.append(elem);
			sb.append("("+this.multiplicity(elem)+"), ");
		}
		if ( sb.length() > 1 ) sb.setLength( sb.length()-2 );
		sb.append("]");
		return sb.toString();
	}//toString
	
	public boolean equals( Object o ) {
		if ( !(o instanceof Bag) ) return false;
		if ( o == this ) return true;
		Bag<T> b = (Bag<T>) o;
		if ( b.cardinality() != this.cardinality() ) return false;
		Iterator<T> it1 = b.iterator();
		Iterator<T> it2 = this.iterator();
		while ( it1.hasNext() ) {
			T xRif = it1.next();
			while ( it2.hasNext() ) {
				T x = it2.next();
				if ( x.equals(xRif) )
					if ( this.multiplicity(x) != b.multiplicity(x) ) return false;
			}
		}
		return true;
	}//equals
	
	public int hashCode() {
		final int M = 83, P = 89;
		int h = 0;
		for ( T x : this ) {
			h += x.hashCode()*M + multiplicity(x)*P;
		}
		return h;
	}//hashCode
}//AbstractBag

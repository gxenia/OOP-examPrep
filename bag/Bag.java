package poo.bag;

import java.util.Comparator;
import java.util.Iterator;

public interface Bag<T> extends Iterable<T>{
	default int size() {
		int c = 0;
		for ( T elem : this ) c+=this.multiplicity(elem); // l'iteratore itera sugli elementi DISTINTI del bag
		return c;
	}//size
	
	default int cardinality() {
		int c = 0;
		for ( T elem : this ) c+=1; // l'iteratore itera sugli elementi DISTINTI del bag
		return c;
	}//cardinality
	
	default void clear() {
		Iterator<T> it = this.iterator();
		while ( it.hasNext() ) {
			it.next();
			it.remove();
		}
	}//clear
	
	int multiplicity( T x ); //ritorna il numero di volte in cui x è presente in this, di seguito abbreviata m(x)
	
	void add( T x ); //aggiunge una singola occorrenza di x <- la aggiunge a this e quindi non posso usare factory()
	void add( T x, int multiplicity ); // usa la tua fantasia
	void addAll( Bag<T> b ); //usa la tua fantasia
	
	boolean remove( T x ); //rimuove una singola occorrenza di x e restituisce true se this è modificato
	
	default boolean removeAll( T x ){
		//rimuove tutte le occorrenze di x in this
		Iterator<T> it = this.iterator();
		while ( it.hasNext() ) {
			T elem = it.next();
			if ( elem.equals(x) ) {
				it.remove();
				return true;
			}
		}
		return false;
	}//removeAll
	
	Bag<T> factory(); //usa la tua fantasia
	Bag<T> factory( Comparator<T> c ); //crea un factor da mantenere ordinato secondo c
	
	default boolean included( Bag<T> b ) {
		//ritorna true se this è contenuto in b, ossia: ∀ x in this: this.m(x)≤b.m(x)
		for ( T x : this )
			if ( this.multiplicity(x) > b.multiplicity(x) )
				return false;
		return true;
	}//included
	default Bag<T> union( Bag<T> b ){
		//ritorna il bag contenente gli x di this e di b, e con: max(this.m(x),b.m(x))
		Bag<T> nuovoBag = factory();
		for ( T x : this ) {
			int maxMult = Math.max(this.multiplicity(x), b.multiplicity(x));
			nuovoBag.add(x, maxMult);
		}
		return nuovoBag;
	}//union
	default Bag<T> difference( Bag<T> b ){
		//ritorna un nuovo bag this -(meno) b: ∀x in this: max(this.m(x)-b.m(x),0)
		Bag<T> nuovoBag = factory();
		for ( T x : this ) {
			int maxMult = Math.max(this.multiplicity(x) - b.multiplicity(x), 0);
			nuovoBag.add(x, maxMult);
		}
		return nuovoBag;
	}//difference
	default Bag<T> intersection( Bag<T> b ){
		//ritorna un bag con gli elem. comuni x, e con min(this.m(x),b.m(x))
		Bag<T> nuovoBag = factory();
		for ( T x : this ) {
			int maxMult = Math.min(this.multiplicity(x), b.multiplicity(x));
			nuovoBag.add(x, maxMult);
		}
		return nuovoBag;
	}//intersection
	
	default T piuFrequente() {
		int m = 0; T maxElem = null;
		for ( T x : this ) {
			if ( multiplicity(x) > m ) {
				m = multiplicity(x);
				maxElem = x;
			}
		}
		return maxElem;
	}

}//Bag
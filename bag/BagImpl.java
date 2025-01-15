package poo.bag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class BagImpl<T> extends AbstractBag<T> {
	Map<T,Integer> bag;
	
	public BagImpl() {
		bag = new HashMap<>();
	}
	public BagImpl( T[] array ) {
		this();
		for ( T elem : array )
			add(elem);
	}
	
	public BagImpl( Comparator<T> c ) {
		bag = new TreeMap<>(c);
	}
	
	public int multiplicity( T x ) {
		return bag.get(x);
	}//multiplicity
	
	public void add( T x ) {
		add(x, 1);
	}//add
	
	public void add( T x, int multiplicity ) {
		if ( bag.containsKey(x) ) {
			int m = bag.get(x) + multiplicity;
			bag.put(x, m);
			return;
		}
		bag.put(x, multiplicity);
	}//add
	
	public void addAll( Bag<T> b ) {
		for ( T x : b )
			add(x, b.multiplicity(x));
	}//addAll
	
	public boolean remove( T x ) {
		if ( bag.containsKey(x) ) {
			bag.put(x, bag.get(x)-1);
			return true;
		}
		return false;
	}//remove
	
	public Bag<T> factory() {
		return new BagImpl();
	}//factory
	
	public Bag<T> factory( Comparator<T> c ){
		return new BagImpl(c);
	}//factory
	
	@Override
	public Iterator<T> iterator() {
		return bag.keySet().iterator();
	}
	
	public T piuFrequente() {
		int f = Collections.max(bag.values());
		for ( T x : bag.keySet() )
			if ( this.multiplicity(x) == f )
				return x;
		return null;
	}
	
	
	public static void main( String...args ) {
		Scanner sc = new Scanner( System.in );
		String nf;
		do {
			System.out.print("Inserire nome file: ");
			nf = sc.next(); sc.nextLine();
		} while ( !nf.matches(".*\\.txt") );
		File f = new File(nf);
		if ( !f.exists() )
			throw new IllegalArgumentException("Il file " + f + " è inesistente");
		
		Bag<String> b = new BagImpl<>( new Comparator<String>() {
			public int compare( String s1, String s2 ) {
				if ( s1.length() < s2.length() ) return -1;
				if ( s1.length() > s2.length() ) return 1;
				return s1.compareTo(s2);
			}//compare
			});
		try ( BufferedReader br = new BufferedReader( new FileReader(f) ) ){			
			for ( ;; ) {
				String line = br.readLine();
				if ( line==null ) break;
				StringTokenizer st = new StringTokenizer( line, " ,.;:'?!<>");
				while ( st.hasMoreTokens() ) {
					String nuovaParola = st.nextToken();
					System.out.println(nuovaParola);
					b.add(nuovaParola.toUpperCase());
				}
			}
		}
		catch( FileNotFoundException e1 ) { System.out.println( e1 );}
		catch( IOException e2 ) { System.out.println( e2 ); }
		

		System.out.println(b);
		System.out.println(b.piuFrequente());
		
		sc.close();
	}//main
	
}//BagImpl

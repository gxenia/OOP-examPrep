package poo.matrice_sparsa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class MatriceSparsaImpl extends MatriceSparsaAstratta {
	private Map<Integer, Map<Integer, Double>> ms = new TreeMap<Integer, Map<Integer, Double>>();
	private final int nR, nC;
	
	public MatriceSparsaImpl( int nR, int nC, String nomeFile ) {
		this.nR = nR; this.nC = nC;
		File f = new File(nomeFile);
		if ( !f.exists() )
			throw new IllegalArgumentException( "Il file è inesistente" );
		try ( BufferedReader br = new BufferedReader( new FileReader(f) ) ) {
			for ( ;; ) {
				String line = br.readLine();
				if ( line == null ) break;
				if ( !(line.matches("i=\\d+\\s+j=\\d+\\s+v=\\-?\\d*\\.\\d+")) )
					throw new IllegalArgumentException("Presente una o più linee malformate. "
							+ "Formato: i=int spazi j=int spazi v=double");
				StringTokenizer st = new StringTokenizer( line, " " );
				int i = Integer.parseInt(st.nextToken().split("=")[1]);
				int j = Integer.parseInt(st.nextToken().split("=")[1]);
				if ( i<0 || i>=nR || j<0 || j>=nC )
					throw new RuntimeException("Numero di riga o di colonna non appartenente alla matrice: " + i + " " + j);
				double v = Double.parseDouble(st.nextToken().split("=")[1]);
				
				if ( !ms.containsKey(i) ) {
					Map<Integer, Double> m2liv = new TreeMap<Integer, Double>();
					ms.put(i, m2liv);
				}
				ms.get(i).put(j, v);
			}
		} 
		catch (FileNotFoundException e) { System.out.println(e);} 
		catch (IOException e) { System.out.println(e); }
		
	}//MatriceSparsaImpl
	
	public MatriceSparsaImpl( int nR, int nC ) {
		this.nR = nR; this.nC = nC;
	}//MatriceSparsaImpl
	
	public int nr_righe() { return nR; }
	public int nr_colonne() { return nC; }
	
	public double get( int i, int j ) {
		//restituisce il valore dell’elemento <i,j>
		if ( ms.containsKey(i) && ms.get(i).containsKey(j) )
			return ms.get(i).get(j);
		return 0;
	}//get
	
	public void set( int i, int j, double v ) {
		//assegna v all’elemento <i,j>. Attne se v=0.
		if ( v != 0 ) {
			if ( !ms.containsKey(i) ) {
				Map<Integer, Double> m2liv = new TreeMap<Integer, Double>();
				ms.put(i, m2liv);
			}
			ms.get(i).put(j, v);
			return;
		}
		if ( ms.containsKey(i) && ms.get(i).containsKey(j) ) {
			ms.get(i).remove(j);
		}
	}//set
	
	public MatriceSparsa factory( int nr_righe, int nr_colonne ) {
		return new MatriceSparsaImpl( nr_righe, nr_colonne );
	}//factory
	
	
	public static void main( String...args ) {
		String nomeFile = "C:\\poo-file\\matriceSparsaFile.txt";
		MatriceSparsa ms = new MatriceSparsaImpl( 3, 4, nomeFile );
		System.out.println(ms);
		
		ms.set(0, 0, 0);
		ms.set(0, 3, 5.4);
		System.out.println(ms);
		
		ms.set(2, 0, 0);
		System.out.println(ms);		
		System.out.println( ms.get(1, 2) );
		System.out.println( ms.mul(2) );
		
		// System.out.println( ms.determinanteG() );
		
		MatriceSparsa ms1 = new MatriceSparsaImpl(2,2);
		ms1.set(0, 0, 1);
		ms1.set(0, 1, 4);
		ms1.set(1, 0, 5);
		ms1.set(1, 1, 3);
		System.out.println(ms1);
		System.out.println("Determinante: " + ms1.determinanteG());
		
		MatriceSparsa ms2 = new MatriceSparsaImpl(2,2);
		ms2.set(0, 0, 2);
		ms2.set(0, 1, 4);
		ms2.set(1, 0, 5);
		ms2.set(1, 1, 3);
		System.out.println(ms2);
		System.out.println("Determinante: " + MatriceSparsa.determinanteL(ms2));
		
		System.out.println(ms1.add(ms2));
	}//main
	
}//MatriceSparsaImpl

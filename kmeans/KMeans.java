package poo.kmeans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class KMeans {
	final int K; // numero di centroidi
	private Map<Punto,Integer> dataSet = new HashMap<>();
	private Punto[] centroidi;
	
	public KMeans( File f, final int K ) {
		this.K = K;
		
		// Inserire punti nel dataSet
		if( !f.exists() ) 
			throw new IllegalArgumentException( "File " + f + " does not exist!");
		
		try ( BufferedReader br = new BufferedReader( new FileReader(f) ) ){
			for ( ;; ) {
				String line = br.readLine();
				if ( line==null ) break;
				StringTokenizer st = new StringTokenizer( line, " " );
				Double X = Double.parseDouble( st.nextToken().replace(',', '.') );
				Double Y = Double.parseDouble( st.nextToken().replace(',', '.') );
				Punto p = new Punto(X, Y);
				dataSet.put(p, -1);
			}
		}
		catch( FileNotFoundException e1 ) { System.out.println( e1 );}
		catch( IOException e2 ) { System.out.println( e2 ); }
		
		// Inizializzare array di centroidi
		centroidi = new Punto[K];
		ArrayList<Punto> punti = new ArrayList<>( dataSet.keySet() );
		int MAX = dataSet.size(), MIN=0;
		double n;
		for ( int i = 0; i < K; ++i ) {
			do { n = Math.random()*(MAX-MIN)+MIN; }
			while ( giaPresente(centroidi, punti.get( (int) n )) );
			centroidi[i] = punti.get( (int) n );
		}
	}//KMeans
	
	private boolean giaPresente( Punto[] listaPunti, Punto pRif ) {
		for ( Punto p : listaPunti )
			if ( pRif.equals(p) ) return true;
		return false;
	}//giaPresente
	
	
	public void run( int maxIte ) {
		for ( int i = 0; i < maxIte; ++i ) {
			assegnaCluster();
			var old = ridefCentroidi();
			if ( convergenzaRaggiunta(1e-6, old) ) break;
		}
	}//run
	
	private void assegnaCluster() {
		Set<Punto> punti = dataSet.keySet();
		for ( Punto p : punti ) {
			double distanzaMin = Double.MAX_VALUE;
			int centroidePV = -1;
			for ( int i=0; i<K; ++i ) {
				double d = p.distanza(centroidi[i]);
				if ( d < distanzaMin ) {
					distanzaMin = d;
					centroidePV = i;
				}
			}
			dataSet.put( p, centroidePV );
		}
	}//assegnaCluster
	
	private Punto[] ridefCentroidi() {
		Punto[] old = Arrays.copyOf(centroidi, K);
		for ( int i = 0; i < K; ++i ) { // ciclo sui cluster
			if ( !clusterVuoto(i) )
				centroidi[i] = baricentro(i);
			else {
				ArrayList<Punto> punti = new ArrayList<>( dataSet.keySet() );
				double n = 0;
				do { n = Math.random()*dataSet.size(); }
				while ( giaPresente(centroidi, punti.get( (int) n )) );
				centroidi[i] = punti.get( (int) n );
			}
		}
		return old;
	}//ridefCentroidi
	
	private boolean convergenzaRaggiunta( double soglia, Punto[] old ) {
		for ( int i = 0; i<K; ++i )
			if ( old[i].distanza(centroidi[i]) > soglia ) return false;
		return true;
	}//conergenzaRaggiunta
	
	private boolean clusterVuoto( int c ) {
		for ( Punto p : dataSet.keySet() )
			if ( dataSet.get(p) == c )
				return false;
		return true;
	}//clusterVuoto
	
	private Punto baricentro( int cluster ) {
		double sumX = 0, sumY = 0;
		for ( Punto p : dataSet.keySet() )
			if ( dataSet.get(p) == cluster ) {
				sumX += p.getX();
				sumY += p.getY();
			}
		return new Punto( sumX/K, sumY/K );
	}//baricentro
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder(3000);
		sb.append( "Centroidi:\n" );
		
		for ( int i = 0; i<K; ++i ) {
			sb.append( centroidi[i] );
			sb.append( "\n" );
		}
		
		sb.append( String.format("%-50s %s%n", "Punto", "Cluster") );
		Set<Punto> punti = dataSet.keySet();
		for ( Punto p : punti ) {
			int cluster = dataSet.get(p);
			sb.append( String.format("%-50s %d%n", p, cluster) );
		}
		
		return sb.toString();
	}//toString
	
	
	public static void main( String[] args ) {
		
		String nf_dataset = "c:\\poo-file\\dataset.txt";
		File f_dataset = new File(nf_dataset);
		
		final int N=100, MIN=500, MAX=1500, K=10;
		DataSet.crea( f_dataset, N, MIN, MAX );
		
		KMeans km = new KMeans( f_dataset, K );
		System.out.println(km);
		km.run(50);
		System.out.println(km);
	}//main
	
}//KMeans

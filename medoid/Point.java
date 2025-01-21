package poo.medoid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Point {
	private final static int D = 3;
	private double[] coordinates;
	
	public Point( double[] coordinates ) {
		if ( coordinates.length != D ) throw new RuntimeException();
		this.coordinates = Arrays.copyOf(coordinates, D);
	}//Point
	
	public Point( Point p ) {
		if ( coordinates.length != D ) throw new RuntimeException();
		this.coordinates = Arrays.copyOf(p.coordinates, D);
	}//Point
	
	public double[] getCoord() {
		return Arrays.copyOf(coordinates, D);
	}//getCoord
	
	public double distance( Point p ) {
		int dist = 0;
		for ( int i = 0; i < D; ++i )
			dist += Math.pow( (this.coordinates[i] - p.coordinates[i]), 2 );
		return Math.sqrt(dist);
	}//distance
	
	public boolean equals( Object o ) {
		if ( !(o instanceof Point) ) return false;
		if ( o == this ) return true;
		Point p = (Point) o;
		for ( int i = 0; i < D; ++i )
			if ( coordinates[i] != p.coordinates[i] ) return false;
		return true;
	}//equals
	
	public String toString() {
		StringBuilder sb = new StringBuilder(D*4);
		sb.append("Point[");
		for ( int i = 0; i < D; ++i ) {
			sb.append(coordinates[i]);
			sb.append(", ");
		}
		if ( sb.length() > 1 ) sb.setLength( sb.length()-3 );
		sb.append("]");
		return sb.toString();
	}//toString
	
	public static int getD() {
		return D;
	}//getD
	
	public static Point medoid( Set<Point> group ) {
		//Si chiama medoide quel punto di un gruppo di punti che ha minima somma delle
		//distanze da tutti gli altri punti del gruppo.
		
		double dMin = Double.MAX_VALUE;
		Point pMin = null;
		for ( Point p : group ) {
			double sumP = 0;
			for ( Point q : group )
				if ( !(q.equals(p)) ) sumP += p.distance(q); // controllo inutile ma logico
			if (sumP < dMin) {
				dMin = sumP;
				pMin = p;
			}
		}
		return pMin;		
	}//medoid
	
	public static Set<Point>[] split( Point[] ds, Point[] md ){
		final int K = md.length;
		Set<Point>[] partizione = (Set<Point>[])new HashSet<?>[K];
		for ( int i = 0; i < K; ++i )
			partizione[i] = new HashSet<Point>();
		
		for ( Point p : ds ) {
			int h = -1;
			double dMin = Integer.MAX_VALUE;
			for ( int i = 0; i < K; ++i ) {
				double distance = p.distance(md[i]);
				if ( distance < dMin ) {
					dMin = distance;
					h = i;
				}
			}
			partizione[h].add(p);
		}
		return partizione;
	}//split
	
	public static Point[] update( Set<Point>[] partition ) {
		Point[] md = new Point[partition.length];
		int i = 0;
		for ( Set<Point> s : partition ) {
			md[i] = medoid(s);
			++i;
		}
		return md;
	}//update
	
	public static void main( String...args ) {
		Point[] dataset = new Point[100];
		for ( int i = 0; i < 100; ++i ) {
			double x = Math.random()*(2000)-1000;
			double y = Math.random()*(2000)-1000;
			double z = Math.random()*(2000)-1000;
			double[] p = {x, y, z};
			dataset[i] = new Point(p);
		}
		//System.out.println(Arrays.toString(dataset));
		
		Point[] medoidi = new Point[4]; //K = 4
		for ( int i = 0; i < 4; ++i ) {
			double r = Math.random()*100;
			int rI = (int) r;
			medoidi[i] = dataset[rI];
		}
		//System.out.println(Arrays.toString(medoidi));
		
		Set<Point>[] partizione = split(dataset, medoidi);
		//System.out.println(Arrays.toString(partizione));
		
		Point[] md = update(partizione);
		System.out.println(Arrays.toString(md));
	}//main
	
}//Point

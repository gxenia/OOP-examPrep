package poo.kmeans;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public final class DataSet {
	private DataSet() {}
	
	protected static void crea( File f, final int N, final int MIN, final int MAX ) {
		try ( PrintWriter pw = new PrintWriter( new FileWriter(f) ) ){
			for ( int i=0; i<N; ++i ) {
				double aX = Math.random()*(MAX-MIN)+MIN;
				double aY = Math.random()*(MAX-MIN)+MIN;
				String X = String.format("%27.17f", aX);
				String Y = String.format("%27.17f", aY);
				pw.println( X+" "+Y );
			}
		}
		catch ( IOException e ) { System.out.println(e); }
	}//crea
	
}//DataSet

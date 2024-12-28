package poo.backtracking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import poo.util.Array;

public class Sudoku {
	private int[][] sudokuTable = new int[9][9];
	final private boolean[][] sudokuCheckTable = new boolean[9][9];
	final private int lastZero;
	
	LinkedList<String> solutions = new LinkedList<>();
	
	public Sudoku( String[] lines ) {
		assert lines.length == 9 : "Expected 9 lines to form a Sudoku table." ;
		for ( int i = 0; i < 9; ++i ) {
			String line = lines[i];
			assert line.length() == 9 : "Line should have length 9.";
			for ( int j = 0; j < 9; ++j ) {
				int n = line.charAt(j) - 48;
				if ( !assignable( i, j, n ) )
					throw new IllegalArgumentException( "Input does not satisfy constraints." );
				// everything is ok - assign
				sudokuTable[i][j] = n;
				if ( n == 0 ) sudokuCheckTable[i][j] = false;
				else sudokuCheckTable[i][j] = true;
			}
		}
		int lnc = 0;
		for ( int i = 8; i >= 0; --i ) {
			for ( int j = 8; j>=0; --j ) { if  ( !sudokuCheckTable[i][j] ) { lnc = i*9+j; break; } }
			if ( lnc != 0 ) break;
		}
		lastZero = lnc;
	}
	
	private boolean assignable( int posX, int posY, int n ) {
		if ( n == 0 ) { // if the cell is empty - no need to check anything
			sudokuTable[posX][posY] = 0;
			return true;
		}
		if ( sudokuCheckTable[posX][posY] ) return false;
		// check line
		for ( int j = 0; j < 9 ; ++j )
			if ( sudokuTable[posX][j] == n ) return false;
		// check column
		for ( int i = 0; i < 9; ++i )
			if ( sudokuTable[i][posY] == n ) return false;
		// check sub-block
		int iniX = (posX/3)*3, iniY = (posY/3)*3;
		for ( int i = 0; i < 3; ++i )
			for ( int j = 0; j < 3; ++j )
				if ( sudokuTable[iniX+i][iniY+j] == n) return false;
		return true;		
	}//assignable
	
	
	private void fillNumber( int nc ) { // nc e' punto di scelta
		if ( nc<80 && !isZero(nc) ) fillNumber(nc+1);
		//System.out.println(this);
		int posX = nc/9, posY = nc%9;
		for ( int n=1; n<=9; ++n ) { //ciclo su tutte le possibile scelte
			if ( assignable( posX, posY, n ) ) {
				assign(posX, posY, n);
				if ( nc >= lastZero  ) { writeSolution();}
				else fillNumber(nc+1);
				unassign(posX, posY ,n);
			}
		}
	}//fillNumber
	
	private boolean noZeros() {
		for ( int nc = 0; nc<80; ++nc ) {
			int posX = nc/9, posY = nc%9;
			if ( isZero(nc) ) return false;
		}
		return true;
	}//noZeros
	
	private void assign( int posX, int posY, int n ) {
		sudokuTable[posX][posY] = n;
	}//assign
	
	private void unassign( int posX, int posY, int n ) {
		sudokuTable[posX][posY] = 0;
	}//unassign

	private void writeSolution() {
		System.out.println("Solution: ");
		System.out.println( this.toString() );
		solutions.add( this.toString() );
	}//writeSolution
	
	public void writeSolutions( String fileName ) {
		File f=new File( fileName );
		if( !f.exists() ) {
			System.out.println("File "+fileName+" does not exist!");
			return;
		}
		try ( PrintWriter pw = new PrintWriter( new FileWriter(f) ) ){
			if ( solutions.isEmpty() ) pw.println("There are no solutions.");
			for ( String sol : solutions )
				pw.println(sol);
		}
		catch ( IOException e ) { System.out.println(); }
	}//writeSolutions
	
	public void risolvi() {
		fillNumber( 0 );
	}//risolvi
	
	private boolean isZero( int nc ) {
		if ( nc >= 80 ) throw new IllegalArgumentException( "nc greater than 80" );
		return sudokuTable[nc/9][nc%9] == 0;
	}//isZero
	
	public String toString() {
		StringBuilder sb = new StringBuilder(200);
		for ( int i = 0; i < 9; ++i ) {
			for ( int j = 0; j < 9; ++j )
				sb.append( String.format("%2d", sudokuTable[i][j]) );
			sb.append(String.format("%n"));
		}
		return sb.toString();
	}//toString
	
	public static void main( String...args ) {
		System.out.println( "Sudoku solver" );
		Scanner sc = new Scanner( System.in );
		System.out.print( "Do you want to provide your sudoku via file or via prompt? (file: 0, prompt: 1) " );
		int via = sc.nextInt(); sc.nextLine();
		String[] inputLines = new String[9];
		Sudoku s = null;
		do {
			switch( via ) {
			case 0: {
				String fileName=null;
				File f=null;
				do{
					System.out.print("Provide text-file name: ");
					fileName=sc.nextLine();
					f=new File( fileName );
					if( !f.exists() ) 
						System.out.println("File "+fileName+" does not exist! Try again.");
				}while( !f.exists() );
				try ( BufferedReader br = new BufferedReader( new FileReader(f) ) ){
					for ( int i = 0; i < 9; ++i ) {
						String line = br.readLine();
						if ( !line.matches("\\d{9}") )
							throw new IllegalArgumentException( "Malformed line." );
						inputLines[i] = line;
					}
				}
				catch( FileNotFoundException e1 ) { System.out.println( e1 );}
				catch( IOException e2 ) { System.out.println( e2 ); }
				break;
			}
			case 1: {
				for ( int i = 0; i < 9; ++i ) {
					System.out.print( "Insert line: " );
					String line = sc.nextLine();
					if ( !line.matches("\\d{9}") )
						throw new IllegalArgumentException( "Malformed input string." );
					inputLines[i] = line;
				}
				break;
			}	
			}
		} while ( via != 0 && via != 1 );
		
		s = new Sudoku( inputLines ); 
		System.out.println("Sudoku table given: \n" + s);
		s.risolvi();
		System.out.println( "All solutions were found." );
		
		System.out.print("Would you like to save your solutions into a file? (y/n)");
		enum Save{YES, NO}
		String res = sc.next();
		Save save = res.matches("(y|Y)(es|ES|eS|Es)?") ? Save.YES : Save.NO;
		switch(save) {
		case YES: {
			System.out.print("Destination file name: ");
			String dest = sc.next();
			s.writeSolutions(dest);
		}
		case NO: System.out.println("Bye");
		}
		sc.close();
	}
}//Sudoku

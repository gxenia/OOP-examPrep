package poo.spelling_checker;

public final class Similarita {
	private Similarita() {}
	
	public static int lev( String a, String b ) {
		if ( a.length() == 0 ) return b.length();
		if ( b.length() == 0 ) return a.length();
		String tailA = a.substring(1), tailB = b.substring(1);
		if ( a.charAt(0) == b.charAt(0) ) return lev( tailA, tailB );
		return 1+ min( lev(tailA, b), lev(a, tailB), lev(tailA, tailB) );
	}//lev
	
	private static int min( Integer...integers ) {
		int min = Integer.MAX_VALUE;
		for ( Integer i : integers )
			if ( i < min ) min = i;
		return min;
	}//min
	
}//Similarita

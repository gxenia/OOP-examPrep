package poo.spelling_checker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SpellingChecker {
	private Set<String> dizionario;
	private List<String> documento;
	
	public SpellingChecker( Set<String> dizionario, List<String> documento ) {
		this.dizionario = new TreeSet<>(dizionario);
		this.documento = new LinkedList<>(documento);
	}
	
	public void spelling() {
		File f = new File("c:\\poo-file\\proposte_editing.txt");
		try ( PrintWriter pw = new PrintWriter( new FileWriter(f) ) ){
			// spelling
			for ( String parola : documento ) {
				String pc = parolaDistMin(parola);
				if ( !(pc.equals(parola)) ) pw.write( "<" + parola + ", " + pc + ">\n" );
			}
		} catch (IOException e) { System.out.println(e); }
	}//spelling
	
	private String parolaDistMin( String p ) {
		String pMin = null;
		int min = Integer.MAX_VALUE;
		for ( String s : dizionario ) {
			int l = Similarita.lev(p.toUpperCase(), s.toUpperCase());
			if ( l < min ) {
				pMin = s;
				min = l;
			}
		}
		return pMin;
	}//parolaDistMin
	
	
	
	public static void main( String...args ) {
		Set<String> dizionario = new TreeSet<>();
		LinkedList<String> documento = new LinkedList<>();
		
		// Caricamento del dizionario
		File fDizionario = new File("c:\\poo-file\\dizionario.txt");
		if ( !fDizionario.exists() )
			throw new IllegalArgumentException("Nome file-dizionario errato. Il file è inesistente.");
		try ( BufferedReader br = new BufferedReader( new FileReader(fDizionario) ) ) {
			for (;;) {
				String line = br.readLine();
				if ( line == null ) break;
				StringTokenizer st = new StringTokenizer(line, "\\W");
				String parola = st.nextToken(); // Il dizionario ha una sola parola per riga
				dizionario.add(parola);
			}
		} 
		catch (FileNotFoundException e) { System.out.println(e); } 
		catch (IOException e) { e.printStackTrace(); }
		
		// Caricamento del documento
		File fDocumento = new File("c:\\poo-file\\documento.txt");
		if ( !fDocumento.exists() )
			throw new IllegalArgumentException("Nome file-documento errato. Il file è inesistente.");
		try ( BufferedReader br = new BufferedReader( new FileReader(fDocumento) ) ) {
			for (;;) {
				String line = br.readLine();
				if ( line == null ) break;
				StringTokenizer st = new StringTokenizer(line, " ,:;.?!");
				while ( st.hasMoreTokens() ) {
					String parola = st.nextToken();
					documento.add(parola);
				}
			}
		} 
		catch (FileNotFoundException e) { System.out.println(e); } 
		catch (IOException e) { e.printStackTrace(); }
		
		SpellingChecker sc = new SpellingChecker(dizionario, documento);
		sc.spelling();
		System.out.println("Finito! Vedi il file proposte_editing.txt!");
	}//main
}//SpellingChecker
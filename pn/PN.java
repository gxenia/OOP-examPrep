package poo.pn;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class PN {
	Map<String, Posto> M;
	LinkedList<Transizione> T;
	LinkedList<Transizione> abilitate = new LinkedList<>();
	LinkedList<Transizione> disabilitate = new LinkedList<>();
	
	public PN( Map<String, Posto> M, LinkedList<Transizione> T) {
		this.M = new HashMap<String, Posto>(M);
		this.T = new LinkedList<Transizione>(T);
		aggiornaAbilitate();
	}//PN
	
	public void singleStep() {
		if ( abilitate.isEmpty() ) {
			System.out.println("Deadlock!");
			return;
		}
		Collections.shuffle(abilitate);
		System.out.print("Spara " + abilitate.get(0).getNome());
		abilitate.get(0).sparo();
		aggiornaAbilitate();
		System.out.println( " " + marcatura());
	}//singleStep
	
	public void multipleSteps( int n ) {
		int i = 0;
		while ( i < n ) {
			singleStep();
			++i;
		}
	}//multipleSteps
	
	public String marcatura() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for ( String s : M.keySet() ) {
			sb.append(M.get(s).toString());
			sb.append(", ");
		}
		if ( sb.length() > 1 ) sb.setLength( sb.length()-2 );
		sb.append("]");
		return sb.toString();
	}//mostraMarcatura
	
	private void aggiornaAbilitate() {
		abilitate.clear();
		disabilitate.clear();
		for ( Transizione t : T ) {
			if ( t.abilitata() ) abilitate.add(t);
			else disabilitate.add(t);
		}
	}//aggiornaAbilitate
}//PN

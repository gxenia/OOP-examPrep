package poo.pn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
	public static void main( String...args ) {
		Posto p1 = new Posto("p1", 2);
		Posto p2 = new Posto("p2");
		Posto p3 = new Posto("p3");
		Posto p4 = new Posto("p4");
		
		Map<String, Posto> M = new HashMap<>();
		M.put("p1", p1);
		M.put("p2", p2);
		M.put("p3", p3);
		M.put("p4", p4);
		
		//System.out.println(M);
		
		LinkedList<Transizione> T = new LinkedList<>();
		Transizione t1 = new Transizione("t1", java.util.Arrays.asList( new ArcoIn(p1) ), java.util.Arrays.asList( new ArcoOut(p2) ));
		Transizione t2 = new Transizione("t2", java.util.Arrays.asList( new ArcoIn(p2) ), java.util.Arrays.asList( new ArcoOut(p3) ));
		Transizione t3 = new Transizione("t3", java.util.Arrays.asList( new ArcoIn(p2) ), java.util.Arrays.asList( new ArcoOut(p4) ));
		Transizione t4 = new Transizione("t4", java.util.Arrays.asList( new ArcoIn(p3), new ArcoIn(p4) ), java.util.Arrays.asList( new ArcoOut(p1) ));
		Transizione t5 = new Transizione("t5", java.util.Arrays.asList( new ArcoIn(p4) ), java.util.Arrays.asList( new ArcoOut(p1) ));
		T.add(t1); T.add(t2); T.add(t3); T.add(t4); T.add(t5);
		
		//System.out.println(T);
		
		PN pn = new PN(M, T);
		System.out.println("Situazione iniziale " + pn.marcatura());
		pn.multipleSteps(5);
	}
}//Main

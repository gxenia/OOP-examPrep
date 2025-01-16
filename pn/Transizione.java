package poo.pn;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Transizione extends Entita {
	private List<ArcoIn> preset; // se vuoto: consuma token ma non ne genera
	private List<ArcoOut> postset; // se vuoto: transizione sempre abilitata
	
	public Transizione( String nome, List<ArcoIn> preset, List<ArcoOut> postset ) {
		super(nome);
		this.preset = new LinkedList<>(preset);
		this.postset = new LinkedList<>(postset);
	}
	
	public boolean abilitata() {
		for ( ArcoIn in : preset ) {
			if ( in.postoIn.getMarcatura() < in.getPeso() ) return false;
		}
		return true;
	}//abilitata
	
	public void sparo() {
		if ( !abilitata() )
			throw new IllegalArgumentException("Impossibile sparo. La transizione non è abilitata.");
		for ( ArcoIn in : preset ) {
			int originalMarc = in.postoIn.getMarcatura();
			in.postoIn.setMarcatura(originalMarc - in.getPeso());
		}
		for ( ArcoOut out : postset ) {
			int originalMarc = out.postoOut.getMarcatura();
			out.postoOut.setMarcatura(originalMarc + out.getPeso());
		}
	}//sparo
	
	public String toString() {
		return super.toString() + this.preset + this.postset;
	}//toString
	
}//Transizione

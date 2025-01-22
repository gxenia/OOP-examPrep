package poo.matrice_sparsa;

public abstract class MatriceSparsaAstratta implements MatriceSparsa{
	public String toString() {
		int nR = this.nr_righe(), nC = this.nr_colonne();
		StringBuilder sb = new StringBuilder( nR*nC );
		for ( int i = 0; i < nR; ++i ) {
			for ( int j = 0; j < nC; ++j )
				sb.append( String.format("%10.4f ", this.get(i, j) ) );
			sb.append("\n");
		}
		return sb.toString();
	}//toString
	
}//MatriceSparsaAstratta

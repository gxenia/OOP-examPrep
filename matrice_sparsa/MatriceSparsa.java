package poo.matrice_sparsa;

import poo.util.Mat;

public interface MatriceSparsa{
	int nr_righe();
	int nr_colonne();
	double get( int i, int j ); //restituisce il valore dell’elemento <i,j>
	void set( int i, int j, double v ); //assegna v all’elemento <i,j>. Attne se v=0.
	
	default MatriceSparsa add( MatriceSparsa ms ) {
		//crea e ritorna una nuova matrice this+ms
		if ( this.nr_righe() != ms.nr_righe() || this.nr_colonne() != ms.nr_colonne() )
			throw new IllegalArgumentException();
		int nR = this.nr_righe(), nC = this.nr_colonne();
		MatriceSparsa ret = factory(nR, nC);
		for ( int i = 0; i < nR; ++i ) {
			for ( int j = 0; j < nC; ++j ) {
				double sum = this.get(i, j) + ms.get(i, j);
				ret.set(i, j, sum);
			}
		}
		return ret;
	}//add
	
	default MatriceSparsa mul( MatriceSparsa ms ) {
		//crea e ritorna una nuova matrice this*ms
		if ( this.nr_colonne() != ms.nr_righe() )
			throw new IllegalArgumentException();
		int nR = this.nr_righe(), nC = ms.nr_colonne(), nP = this.nr_colonne();
		MatriceSparsa ret = factory(nR, nC);
		for ( int i = 0; i < nR; ++i ) {
			for ( int j = 0; j < nC; ++j ) {
				double mul = 0;
				for ( int k = 0; k < nP; ++k )
					mul += this.get(i, k) * ms.get(k, j);
				ret.set(i, j, mul);
			}
		}
		return ret;
	}//mul
	
	default MatriceSparsa mul( double scalare ) {
		//crea e ritorna una nuova matrice this*scalare
		int nR = this.nr_righe(), nC = this.nr_colonne();
		MatriceSparsa ret = factory(nR, nC);
		for ( int i = 0; i < nR; ++i ) {
			for ( int j = 0; j < nC; ++j ) {
				double mul = this.get(i, j)*scalare;
				ret.set(i, j, mul);
			}
		}
		return ret;
	}//mul
	
	MatriceSparsa factory( int nr_righe, int nr_colonne );
	
	default MatriceSparsa copia() {
		//crea e ritorna una copia della matrice sparsa this
		int nR = this.nr_righe(), nC = this.nr_colonne();
		MatriceSparsa ret = factory(nR, nC);
		for ( int i = 0; i < nR; ++i ) {
			for ( int j = 0; j < nC; ++j ) {
				ret.set(i, j, this.get(i, j));
			}
		}
		return ret;
	}//copia
	
	default double determinanteG() {
		if ( this.nr_righe() != this.nr_colonne() )
			throw new RuntimeException("La matrice non è quadrata.");
		MatriceSparsa ms = copia();
		int ordine = ms.nr_righe(), scambi = 0;
		
		for ( int p = 0; p < ordine; ++p ) {
			if ( ms.get(p, p) == 0 ) {
				int j = p++;
				for ( ; j<ordine; ++j )
					if ( ms.get(j, p) != 0 ) break;
				if ( j == ordine ) throw new RuntimeException();
				
				for ( int i = 0; i < ordine; ++i ) { // scambia riga j e riga p
					double temp = ms.get(j, i);
					ms.set(j, i, ms.get(p, i));
					ms.set(p, i, temp);
				}
				scambi++;
			}	
			
			// azzeriamo i coefficienti sotto <p,p>
			for ( int j = p+1; j < ordine; ++j )
				if ( ms.get(j, p) != 0 ) {
					double coeff = ms.get(j, p) / ms.get(p, p);
					for ( int i = p; i < ordine; ++i ) {
						double v = ms.get(j, i) - coeff * ms.get(p, i);
						ms.set(j, i, v);
					}
				}
		}
			
		// fai produttora della diagonale principale di a triangolare e dia d
		double d=1;
		for ( int p = 0; p < ordine; ++p ) d = d*ms.get(p, p);
		// se il numero di scambi è dispari, allora fai d=d*(-1)
		if ( scambi%2 != 0 ) d = d*(-1);
		
		return d;
	}//determinanteG
	
	static MatriceSparsa minore( MatriceSparsa a, int i, int j ) {
		int nR = a.nr_righe(), nC = a.nr_colonne();
		if ( nR != nC ) throw new IllegalArgumentException("La matrice non è quadratra");
		int n=nR, k=0;
		
		MatriceSparsa ms = a.factory(nR-1, nC-1);
		for ( int r = 0; r < nR; ++r ) {
			for ( int c = 0; c < nC; ++c ) {
				if ( r != i && c != j ) {
					ms.set(k/(n-1), k%(n-1), a.get(r, c) );
					k++;
				}
			}
		}		
		return ms;
	}//minore
	
	static double determinanteL( MatriceSparsa a ) {
		if ( a.nr_righe() != a.nr_colonne() )
			throw new IllegalArgumentException("La matrice non è quadratra");
		double det = 0;
		int ordine = a.nr_righe();
		if ( ordine==1 ) return a.get(0, 0);
		for ( int i = 0; i < ordine; ++i ) { // considero la prima colonna
			det += Math.pow(-1, i) * a.get(i, 0) * determinanteL( minore(a, i, 0) ); // -1^(i+j)
		}
		return det;
	}//determinanteL
	

}//MatriceSparsa
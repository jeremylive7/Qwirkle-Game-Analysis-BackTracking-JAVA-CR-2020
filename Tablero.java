import java.util.ArrayList;

class Tablero
{
	//Variables Globales
	static List<Fichas> fichas;
	static Margen margen;

	//Constructor
	Tablero(int x, int y) 
	{		
		this.fichas = new ArrayList<Fichas>();		
		this.margen = new Margen(x, y);
	}

	//Metodos de get y set
	private void setOneFilaFichas(Fichas pFicha)
	{
		this.fichas.add(pFicha);
	}

}
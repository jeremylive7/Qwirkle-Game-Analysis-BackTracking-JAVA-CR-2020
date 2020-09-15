import java.util.ArrayList;

class Tablero
{
	//Variables Globales
	static ArrayList<Fichas> fichas;
	static Margen margen;

	//Constructor
	Tablero(int x, int y) 
	{		
		this.fichas = new ArrayList<Fichas>();		
		this.margen = new Margen(x, y);
	}

	//Metodos de get y set
	public ArrayList<Fichas> getTablero()
	{
		return this.fichas;
	}

	public void setOneFilaFichas(Fichas pFicha)
	{
		this.fichas.add(pFicha);
	}

}
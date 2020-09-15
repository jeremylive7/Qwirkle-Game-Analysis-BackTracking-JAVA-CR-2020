import java.util.ArrayList;

public class Fichas
{
	//Variables Globales
	static List<Ficha> fichas_fila;
	static int cantidad;

	//Constructor
	Fichas() 
	{		
		this.fichas_fila = new ArrayList<Ficha>();
		fullFichasEmpty();
	}

	//Metodos get y set
	private int getFichasFila()
	{
		return this.fichas_fila;
	}

	private int setFichasFila(Ficha pFichasFila)
	{
		this.fichas_fila.add(pFichasFila);
	}

}
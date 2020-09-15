import java.util.ArrayList;

public class Fichas
{
	//Variables Globales
	static ArrayList<Ficha> fichas_fila;
	static int cantidad;

	//Constructor
	Fichas() 
	{		
		this.fichas_fila = new ArrayList<Ficha>();
	}

	//Metodos get y set
	public ArrayList<Ficha> getFichasFila()
	{
		return this.fichas_fila;
	}

	public void setFichasFila(Ficha pFichasFila)
	{
		this.fichas_fila.add(pFichasFila);
	}

}
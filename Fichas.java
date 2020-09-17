import java.util.ArrayList;
import java.util.List;


public class Fichas
{
	//Variables Globales
	private List<Ficha> fichas_fila;
	private int cantidad;

	//Constructor
	public Fichas() 
	{		
		this.fichas_fila = new ArrayList<Ficha>();
	}

	//Metodos get y set
	public List<Ficha> getFichasFila()
	{
		return this.fichas_fila;
	}

	public void setFichasFila(Ficha pFichasFila)
	{
		this.fichas_fila.add(pFichasFila);
	}

}
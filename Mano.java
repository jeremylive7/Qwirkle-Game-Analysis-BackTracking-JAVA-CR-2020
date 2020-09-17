import java.util.*;

class Mano
{
	//Variables Globales
	ArrayList<Ficha> fichas;
	String nombre_jugador;

	//Constructor
	Mano(String pNombre) 
	{		
		this.fichas = new ArrayList<Ficha>(7);
		this.nombre_jugador = pNombre;
	}

	//Metodos de get y set
	public ArrayList<Ficha> getFichas()
	{
		return this.fichas;
	}
}
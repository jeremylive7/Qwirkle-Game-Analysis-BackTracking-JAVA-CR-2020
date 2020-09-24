import java.util.ArrayList;

class Mano
{
	private ArrayList<Ficha> fichas;
	private String nombre_jugador;

	public Mano(String pNombre) 
	{		
		this.fichas = new ArrayList<Ficha>(7);
		this.nombre_jugador = pNombre;
	}

	public ArrayList<Ficha> getFichas()
	{
		return this.fichas;
	}
}
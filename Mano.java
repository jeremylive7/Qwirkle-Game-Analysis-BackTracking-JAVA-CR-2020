import java.util.*;

class Mano
{
	private ArrayList<Ficha> fichas;
	private String nombre_jugador;

	public Mano(String pNombre) 
	{		
		this.fichas = new ArrayList<Ficha>(6);
		this.nombre_jugador = pNombre;
	}

	public ArrayList<Ficha> getFichas()
	{
		return this.fichas;
	}

	public void setFicha(Ficha pFicha)
	{
		this.fichas.add(pFicha);
	}

	public Ficha getFichaXIndex(int pIndex)
	{
		return this.fichas.get(pIndex);
	}
}
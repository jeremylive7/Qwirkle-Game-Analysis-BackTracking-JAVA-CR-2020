import java.util.*;

class BolsaFichas
{
	//Variables Globales
	private List<Ficha> fichas;

	//Constructor
	public BolsaFichas() 
	{		
		this.fichas = new ArrayList<Ficha>(109);
	}

	//Metodos de get y set
	public List<Ficha> getFichas()
	{
		return this.fichas;
	}

	public int getLengthBolsaFichas()
	{
		return this.fichas.size();
	}

	//Elimino ficha

}
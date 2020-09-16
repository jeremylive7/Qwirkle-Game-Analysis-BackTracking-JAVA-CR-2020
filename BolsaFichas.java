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

	public void setFicha(Ficha pFicha)
	{
		this.fichas.add(pFicha);
	}

	//Elimino ficha
	public void deletedFicha(Ficha pFicha)
	{
		//Encuentro en la bolsa si existe la ficha

		//La borro de la bolsa de ficha
		
	}
}
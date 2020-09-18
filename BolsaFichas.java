import java.util.*;

class BolsaFichas
{
	//Variables Globale
	private ArrayList<Ficha> fichas;

	//Constructor
	public BolsaFichas() 
	{		
		this.fichas = new ArrayList<Ficha>(108);	
	}

	//Metodos de get y set
	public ArrayList<Ficha> getFichas()
	{
		return this.fichas;
	}

	public int getLengthBolsaFichas()
	{
		return this.fichas.size();
	}

	public void clearFichaXIndex(int pIndex)
	{
		Ficha pFicha = new Ficha();
		this.fichas.add(pIndex, pFicha);
	}

	public Ficha getFichaXIndex(int pIndex)
	{
		return this.fichas.get(pIndex);
	}

	public void setFicha(Ficha pFicha, int pIndex)
	{
		this.fichas.add(pIndex, pFicha);
	}
}
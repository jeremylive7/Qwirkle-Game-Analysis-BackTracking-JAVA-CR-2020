import java.util.*;

class BolsaFichas
{
	private ArrayList<Ficha> fichas;

	public BolsaFichas() 
	{		
		this.fichas = new ArrayList<Ficha>(109);
	}

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

	public void addFicha(Ficha f)
	{
		this.fichas.add(f);
	}

}
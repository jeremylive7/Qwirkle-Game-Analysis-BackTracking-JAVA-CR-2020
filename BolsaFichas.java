import java.util.*;

class BolsaFichas
{
	//Variables Globales
	private static final int MATRIX_SIDE=109;
	private Ficha[] fichas;

	//Constructor
	public BolsaFichas() 
	{		
		this.fichas = new Ficha[MATRIX_SIDE];	
	}

	//Metodos de get y set
	public Ficha[] getFichas()
	{
		return this.fichas;
	}

	public int getLengthBolsaFichas()
	{
		return this.fichas.length;
	}

	public void setFicha(Ficha pFicha, int pIndex)
	{
		this.fichas[pIndex] = pFicha;
	}

	//Elimino ficha
	public void deletedFicha(Ficha pFicha, int pIndex)
	{
		//Encuentro en la bolsa si existe la ficha

		//La borro de la bolsa de ficha
		
	}
}
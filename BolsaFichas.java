import java.util.ArrayList;

class BolsaFichas
{
	//Variables Globales
	static ArrayList<Ficha> fichas;

	//Constructor
	BolsaFichas() 
	{		
		this.fichas = new ArrayList<Ficha>(109);
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

	//Elimino ficha

}
import java.util.ArrayList;

class Mano
{
	//Variables Globales
	static ArrayList<Ficha> fichas;

	//Constructor
	Mano() 
	{		
		this.fichas = new ArrayList<Ficha>(7);
	}

	//Metodos de get y set
	public ArrayList<Ficha> getFichas()
	{
		return this.fichas;
	}
}
import java.util.*;
class BolsaFichas
{
	//Variables Globales
	public static final int CANT_FICHAS=108;
	private List<Ficha> fichas;

	//Constructor
	public BolsaFichas() 
	{		
		this.fichas = new ArrayList<>();
	}

	public void addFicha(Ficha f){
		fichas.add(f);
	}

	
}
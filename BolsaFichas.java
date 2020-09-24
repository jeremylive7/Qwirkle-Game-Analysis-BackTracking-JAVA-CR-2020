import java.util.*;

class BolsaFichas
{
	private ArrayList<Ficha> fichas;

	public BolsaFichas() 
	{		
		this.fichas = new ArrayList<Ficha>(109);
	}

	public void addFicha(Ficha f){
		this.fichas.add(f);
	}
}
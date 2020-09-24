import java.util.*;

class BolsaFichas
{
	private static final int CANT_FICHAS=108;
	private ArrayList<Ficha> fichas;

	public BolsaFichas() 
	{		
		this.fichas = new ArrayList<Ficha>();
	}

	public void addFicha(Ficha f){
		this.fichas.add(f);
	}
}
import java.util.ArrayList;

class Mazo
{
	//Variables Globales
	static List<Ficha> fichas;

	//Constructor
	Mazo() 
	{		
		this.fichas = new ArrayList<Ficha>();
	}

	//Metodos de get y set
	private ArrayList<Fichas> getFichas()
	{
		return this.fichas;
	}

	//Obtengo largo de Mazo
	private int getLengthMazo()
	{
		return this.fichas.size();
	}

	//Imprimo mazo
	private void printMazo()
	{
		pFichas = getFichas();
		largo_mazo = getLengthMazo();

		for (int i=0; i<largo_mazo; i++) {
			System.out.println( "Figura -> " 
				+ pFichas[i].getFigura().getTipo() 
				+ "/n" 
				+ "Color -> "
				+ pFichas[i].getColor().getTipo());
		}
	}

}
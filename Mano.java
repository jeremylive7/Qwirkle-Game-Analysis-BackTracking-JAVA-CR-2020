class Mano
{
	//Variables Globales
	static fichas[] Ficha;

	//Constructor
	Mano() 
	{		
		this.fichas = new Ficha[108];
	}

	//Metodos de get y set
	private Fichas[] getFichas()
	{
		return this.fichas;
	}

	//Obtengo largo de Mazo
	private int getLengthMazo()
	{
		return this.fichas.length;
	}

	//Imprimo mazo
	private void printMano()
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
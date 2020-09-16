class Tablero
{
	//Variables
	private Grafito fichas;

	//Constructor
	public Tablero() 
	{		
		this.fichas = new Grafito();	
	}

	//Metodos de get y set
	public Grafito getTablero()
	{
		return this.fichas;
	}

}
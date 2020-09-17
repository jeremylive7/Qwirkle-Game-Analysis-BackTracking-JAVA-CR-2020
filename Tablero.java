class Tablero
{
	//Variables
	private List<Fichas> fichas;
	private Margen margen;

	//Constructor
	public Tablero() 
	{			
		this.fichas = new ArrayList<Fichas>();		
		this.margen = new Margen(x, y);
	}

	//Metodos de get y set
	public List<Fichas> getTablero()
	{
		return this.fichas;
	}

	public void setOneFilaFichas(Fichas pFicha)
	{
		this.fichas.add(pFicha);
	}


}
class Jugador
{
	//Variables Globales
	Turno turno;
	Score score;
	Mano mano;
	String nombre;

	//Constructor
	Jugador(String pNombre) 
	{		
		this.turno = new Turno(pNombre);
		this.score = new Score(pNombre);
		this.mano = new Mano(pNombre);
		this.nombre = pNombre;
	}

	//Metodos get y set
	public Turno getTurno()
	{
		return this.turno;
	}

	private Score getScore()
	{
		return this.score;
	}

	public Mano getMano()
	{
		return this.mano;
	}

	public String getNombre()
	{
		return this.nombre;
	}

	private void setTurno(Turno pTurno)
	{
		this.turno = pTurno;
	}

	private void setScore(Score pScore)
	{
		this.score = pScore;
	}

	private void setMano(Mano pMano)
	{
		this.mano = pMano;
	}

	private void setNombre(String pNombre)
	{
		this.nombre = pNombre;
	}
}
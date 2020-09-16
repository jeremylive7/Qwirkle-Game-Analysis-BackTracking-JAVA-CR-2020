class Jugador
{
	//Variables Globales
	private Turno turno;
	private Score score;
	private Mano mano;
	private String nombre;

	//Constructor
	public Jugador(String pNombre) 
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

	public Score getScore()
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

	public void setTurno(Turno pTurno)
	{
		this.turno = pTurno;
	}

	public void setScore(Score pScore)
	{
		this.score = pScore;
	}

	public void setMano(Mano pMano)
	{
		this.mano = pMano;
	}

	public void setNombre(String pNombre)
	{
		this.nombre = pNombre;
	}
}
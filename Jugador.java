class Jugador
{
	//Variables Globales
	static Turno turno;
	static Score score;
	static Mano mano;
	static String nombre;

	//Constructor
	Jugador(String pNombre) 
	{		
		this.turno = new Turno();
		this.score = new Score();
		this.mano = new Mano();
		this.nombre = pNombre;
	}

	//Metodos get y set
	private Turno getTurno()
	{
		return this.turno;
	}

	private Score getScore()
	{
		return this.score;
	}

	private Mano getMano()
	{
		return this.mano;
	}

	private String getNombre()
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
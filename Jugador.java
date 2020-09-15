class Jugador
{
	//Variables Globales
	static Turno turno;
	static Score score;

	//Constructor
	Jugador() 
	{		
		this.turno = new Turno();
		this.score = new Score();
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

	private void setTurno(Turno pTurno)
	{
		this.turno = pTurno;
	}

	private void setScore(Score pScore)
	{
		this.score = pScore;
	}
}
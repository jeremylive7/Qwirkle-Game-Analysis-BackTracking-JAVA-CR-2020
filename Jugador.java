import java.util.ArrayList;
import java.util.List;

class Jugador
{
	private Turno turno;
	private Score score;
	private ArrayList<Ficha> mano;
	private String nombre;

	public Jugador(String pNombre,ArrayList<Ficha> pMano) 
	{		
		this.turno = new Turno(pNombre);
		this.score = new Score(pNombre);
		this.mano = pMano;
		this.nombre = pNombre;
	}

	public Turno getTurno()
	{
		return this.turno;
	}

	public Score getScore()
	{
		return this.score;
	}

	public ArrayList<Ficha> getMano()
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

	public void setMano(ArrayList<Ficha> pMano)
	{
		this.mano = pMano;
	}

	public void setFicha(Ficha pFicha)
	{
		this.mano.add(pFicha);
	}

	public void setNombre(String pNombre)
	{
		this.nombre = pNombre;
	}
}
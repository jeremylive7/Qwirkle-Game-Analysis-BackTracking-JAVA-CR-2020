import java.util.ArrayList;
import java.util.List;

class Jugador
{
	private int score;
	private ArrayList<Ficha> mano;
	private String nombre;

	//Constructor
	public Jugador(String pNombre,List<Ficha>manoInicial) 
	{		
		this.score = 0;
		this.mano = new ArrayList<>(manoInicial);
		this.nombre = pNombre;
	}

	public void procesarJugada(Ficha ficha, int cantPuntos){
		score+=cantPuntos;
		mano.remove(ficha);
	}

	public int getScore()
	{
		return this.score;
	}

	public ArrayList<Ficha> getMano()
	{
		return this.mano;
	}

	public void setFichaMano(Ficha pFicha)
	{
		this.mano.add(pFicha);
	}

	public String getNombre()
	{
		return this.nombre;
	}

	public void setNombre(String pNombre)
	{
		this.nombre = pNombre;
	}
}
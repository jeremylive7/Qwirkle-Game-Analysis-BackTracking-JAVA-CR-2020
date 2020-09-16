public class Score
{
	//Variables Globales
	String nombre_jugador;
	int pts_totales;

	//Constructor
	Score(String pNombre) 
	{		
		this.nombre_jugador = pNombre;
		this.pts_totales = 0;
	}

	//Metodos get y set
	public String getNombreJugador()
	{
		return this.nombre_jugador;
	}

	public int getPtsTotales()
	{
		return this.pts_totales;
	}

}
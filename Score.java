public class Score
{
	private String nombre_jugador;
	private int pts_totales;

	public Score(String pNombre) 
	{		
		this.nombre_jugador = pNombre;
		this.pts_totales = 0;
	}

	public String getNombreJugador()
	{
		return this.nombre_jugador;
	}

	public int getPtsTotales()
	{
		return this.pts_totales;
	}

}
public class Turno
{
	private Boolean su_turno;
	private String nombre_jugador;
	
	public Turno(String pNombre) 
	{		
		this.su_turno = false;
		this.nombre_jugador = pNombre;
	}

	public String getNombreJugador()
	{
		return this.nombre_jugador;
	}

	public Boolean getSuTurno()
	{
		return this.su_turno;
	}

	public void setSuTurno(Boolean pSuTurno)
	{
		this.su_turno = pSuTurno;
	}
}
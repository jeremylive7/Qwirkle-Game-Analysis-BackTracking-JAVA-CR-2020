public class Turno
{
	//Variables Globales
	Boolean su_turno;
	String nombre_jugador;
	
	//Constructor
	Turno(String pNombre) 
	{		
		this.su_turno = false;
		this.nombre_jugador = pNombre;
	}

	//Metodos get y set
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
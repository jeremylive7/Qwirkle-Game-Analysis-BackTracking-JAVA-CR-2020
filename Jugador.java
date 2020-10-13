import java.util.ArrayList;
import java.util.List;

class Jugador
{
	int score;
	private ArrayList<Ficha> mano;
	String nombre;
	long tiempo;

	//Constructor
	public Jugador(String pNombre,List<Ficha>manoInicial) 
	{		
		this.score = 0;
		this.mano = new ArrayList<>(manoInicial);
		this.nombre = pNombre;
	}

	public void procesarJugada(Jugada jugada, int cantPuntos,long t){
		score+=cantPuntos;
		tiempo+=t;
		for(Jugadita par:jugada.jugaditas)
			mano.remove(par.ficha);
		if(mano.isEmpty())score+=6;
	}
	public String toString(){
		String out="Nombre: "+nombre+"\nScore: "+score+"\nTiempo total: "+tiempo+"\nMano: [";
		for(Ficha f:mano){
			out+=f+", ";
		}
		return out.substring(0,out.length()-2) + "]";
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

	public void updateManoPlayer(ArrayList<Ficha> pLista)
	{
		for (Ficha pFicha : pLista) 
		{
			this.mano.add(pFicha);	
		}
	}

	public long getTiempo() {
		return tiempo;
	}

	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}
}
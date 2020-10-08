import java.util.*;
import javax.swing.*;

class Qwirkle 
{
	private Jugador jugador1, jugador2, jugador3;
	private Jugador jugadorActual;
	private Tablero tablero;
	private List<Ficha> bolsa_fichas;
	private int opcion;
	public static final Figura[] FIGURAS = { Figura.CIRCULO, Figura.CUADRADO, Figura.SOL, Figura.TREBOL, Figura.X,
			Figura.ROMBO };
	public static final Color[] COLORES = { Color.AMARILLO, Color.AZUL, Color.NARANJA, Color.MORADO, Color.ROJO,
			Color.VERDE };
	public static final int CANT_CARTAS_EN_LA_MANO = 6;

	public Qwirkle() 
	{
		this.bolsa_fichas = new ArrayList<>();
		this.fullFichasToBolsa();

		this.jugador1 = null;//new Jugador("Jeremy", getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO));
		this.jugador2 = new Jugador("Edgerik", getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO));
		jugador3 = new Jugador("Roberto", getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO));
		
		this.tablero = new Tablero();
		this.tablero.llenarTableroConEjemplo();
		
	}

	public void iniciarJuego(){
		imprimirTablero();
		while(jugador1==null){
			System.out.println("Estado del jugador básico: \n"+jugador2);
			System.out.println("\nEstado del jugador mejorado: \n"+jugador3);
			if(jugadorHumanoHizoSuJugada())
				break;
			try{
				Thread.sleep(1500);
			}catch(Exception e){}
		}	
	}

	public static void main(String[] args){
		Qwirkle q=new Qwirkle();
		q.iniciarJuego();
	}
	
	private boolean procesarJugada(Jugador jugador, Jugada jugada,long tiempo) 
	{
		int cantPuntos = 0;
		if (jugada.complete){
			cantPuntos = tablero.getPuntos(jugada);
			cantPuntos += 6;
		}else{
			cantPuntos = tablero.getPuntos(jugada);
		}
		
		jugador.procesarJugada(jugada,cantPuntos,tiempo);
		tablero.procesarJugada(jugada);
		if(jugador.getMano().isEmpty()&&bolsa_fichas.isEmpty()){
			seTerminoElJuego();
			return true;
		}
		jugador.getMano().addAll(getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO - jugador.getMano().size()));
		System.out.println("Jugada escogida por el algoritmo: "+jugada);
		System.out.println("Con un tiempo de: "+tiempo+" milisegundos.");
		System.out.println("Y el jugador ganó un total de "+cantPuntos+" puntos.");

		FileOperations.createdFileXRound(jugador.getNombre(), jugador.getScore() + "", tiempo + "");
		return false;
	}

	private void seTerminoElJuego() {
	}

	private boolean turno(Jugador jugador) {
		BackTraking algoritmo = new BackTraking(tablero,this.getHandWithOutRepet(jugador),jugador.getNombre().equals(jugador3.getNombre()));
		long tiempo = System.currentTimeMillis();
		Jugada jugada=algoritmo.getRespuesta();
		tiempo=System.currentTimeMillis()-tiempo;
		return procesarJugada(jugador, jugada,tiempo);
	}

	public boolean jugadorHumanoHizoSuJugada() {

		// juega algoritmo básico
		if(turno(jugador2))
			return true;
		// juega algoritmo mejorado
		System.out.println("Esperando 3 segundos...");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		} // Para que haya un tiempo entre las jugadas de cada uno
		imprimirTablero();
		if(turno(jugador3))
			return true;
		imprimirTablero();
		return false;
		
	}
	public Tablero getTablero() {
		return tablero;
	}
	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

	public ArrayList<Ficha> getHandWithOutRepet(Jugador pJugador)
	{
		
		return new ArrayList<>(new HashSet<>(pJugador.getMano()));
	}

	public void showPossiblePlaysHand(Map<Ficha, ArrayList<ArrayList<Ficha>>> pGrupo)
	{
		for(Map.Entry<Ficha, ArrayList<ArrayList<Ficha>>> entry:pGrupo.entrySet())
		{    
    	Ficha key = entry.getKey();  
    	ArrayList<ArrayList<Ficha>> value = entry.getValue(); 
    	System.out.println("\nLa ficha: " + fichaToSimbol(key) 
    		+ ", tiene las siguientes jugadas: ");
    	
    	for (ArrayList<Ficha> playList : value) 
			{
				for (Ficha ficha : playList) 
				{
					System.out.println(fichaToSimbol(ficha));		
				}
				System.out.println("-");
			}
		}
	}

	public void imprimirMano(ArrayList<Ficha> pMano)
	{
		String out="\n[ ";
		for (Ficha ficha : pMano)
		{
			out+= fichaToSimbol(ficha)+", ";
		}
		System.out.println(out+"]");
	}
/*
	public void imprimirJugadaTotales(ArrayList<ArrayList<Ficha>> pJugadasTotales)
	{
		for (ArrayList<Ficha> pList : pJugadasTotales)
		{
			String out="\n[ ";
			for (Ficha ficha : pList) 
			{
				out+= fichaToSimbol(pList)+", ";	
			}
			System.out.println(out+"]");
		}
	}*/

	public void fullFichasToBolsa()
	{	
		for (Figura figura:FIGURAS) 
		{
			for (int index=0; index<3; index++) 
			{
				for(Color color:COLORES)
				{
					bolsa_fichas.add(new Ficha(figura,color));
				}
			}
		}
	}
	private String getSimboloColor(Color c)
	{
		if(c==Color.AMARILLO)
			return "Am";
		else if(c==Color.AZUL)
			return "Az";
		else if(c==Color.NARANJA)
			return "Na";
		else if(c==Color.MORADO)
			return "Mo";
		else if(c==Color.ROJO)
			return "Ro";
		else if(c==Color.VERDE)
			return "Ve";
		else return "";
		
	}
	private String getSimboloFigura(Figura f)
	{
		switch(f){
			case CIRCULO:
				return "O";
			case CUADRADO:
				return "C";
			case ROMBO:
				return "R";
			case SOL:
				return "S";
			case TREBOL:
				return "T";
			case X:
				return "X";
			case N:return "N";
		}
		return "";
	}
	private String fichaToSimbol(Ficha ficha)
	{
		if(ficha==null)return "---";
		return getSimboloFigura(ficha.getFigura())+getSimboloColor(ficha.getColor());
	}
	public void showBolsaFichas()
	{
		System.out.println(bolsa_fichas.size());		
		for (int i=0; i<bolsa_fichas.size(); i++) {
			System.out.print( fichaToSimbol(bolsa_fichas.get(i))+", ");
		}
	}
	public void imprimirTablero(){
		String out="";
		for(int i=0;i<Tablero.MATRIX_SIDE;i++){
			for(int j=0;j<Tablero.MATRIX_SIDE;j++)
				out+="# "+fichaToSimbol(getTablero().getFichas()[i][j]) + " #";
			out+="\n";
		}
		System.out.println("\n"+out);
	}

	public void showMano(Jugador pJugador)
	{
		String out="\n[ ";
		for (Ficha ficha:pJugador.getMano())
		{
			out+= fichaToSimbol(ficha)+", ";
		}
		System.out.println(out+"]");
	}
	public ArrayList<Ficha>getFichasDeLaBolsa(int cantFichas){
		ArrayList<Ficha>out=new ArrayList<>();
		while(cantFichas-->0)
			out.add(popRandomFicha());
		return out;
	}
	public Ficha popRandomFicha(){
		return bolsa_fichas.remove((int)(Math.random()*(bolsa_fichas.size()-1)));
	}
/*
	public void showPtsJugador(Jugador pJugador)
	{
		System.out.println(pJugador.getScore().getPtsTotales());
	}
	public void setJugadaTablero(ArrayList<Ficha> pPlay)
	{
		
	}
	
	public List<Ficha> seleccionoJugada(Tablero pTable, Jugador pPlayer)
	{
		ArrayList<Ficha> player_hand = pPlayer.getMano();
		
		List<Jugada> jugadasCompletas = pTable.getJugadas(pTable.getPossiblePlaysHand(player_hand));
		pTable.setPointsAllPlays(jugadasCompletas);
		jugadasCompletas.sort((o1, o2) -> Integer.compare(o2.puntos, o1.puntos));

		return jugadasCompletas.get(0);
	}*/

	/*
			EJEMPLO #1
	*/
	public void printTablero(){
		System.out.println(getTablero().toString());
	}
	private boolean juegoPuedeSeguir(){
		//algun jugador tiene 0 cartas y en la bolsa hay 0 cartas.
		return 
		!(
			(
				(
					jugador1!=null &&
					jugador1.getMano().isEmpty()
				) ||
				jugador2.getMano().isEmpty() ||
				jugador3.getMano().isEmpty() 
			) &&
			!bolsa_fichas.isEmpty()
		);
	}

}






/*	
	//Lleno matriz en blanco
	public void fullMatrizEmpty()
	{
		//Matriz 6x6
		for (int i=0; i<this.dimencion_inicial; i++) {
			this.fichas_totales = new Fichas();
			for (int j=0; j<this.dimencion_inicial; j++) {
				this.ficha = new Ficha();
				this.fichas_totales.setFichasFila(ficha);
			}
			this.tablero.setOneFilaFichas(fichas_totales);		
		}

	}
*/
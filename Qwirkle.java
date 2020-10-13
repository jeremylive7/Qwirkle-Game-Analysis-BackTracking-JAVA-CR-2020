import java.util.*;
import javax.swing.*;

class Qwirkle 
{
	private Jugador jugador1, jugador2, jugador3;
	private Jugador jugadorActual;
	private Tablero tablero;
	private List<Ficha> bolsa_fichas;
	private Map<Ficha, Integer> repet_fichas = new HashMap<Ficha, Integer>();
	private int opcion;
	public static final Figura[] FIGURAS = { Figura.CIRCULO, Figura.CUADRADO, Figura.SOL, Figura.TREBOL, Figura.X,
			Figura.ROMBO };
	public static final Color[] COLORES = { Color.AMARILLO, Color.AZUL, Color.NARANJA, Color.MORADO, Color.ROJO,
			Color.VERDE };
	public static final int CANT_CARTAS_EN_LA_MANO = 6;

	private List<Ficha> getQwirkleEjemplo(){
		List<Ficha>fichas=new ArrayList<>();
		for(Figura fig:FIGURAS)
			fichas.add(new Ficha(fig,Color.ROJO));
		return fichas;
	}

	public Qwirkle() 
	{
		this.bolsa_fichas = new ArrayList<>();
		this.fullFichasToBolsa();

		this.jugador1 = null;//new Jugador("Jeremy", getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO));
		this.jugador2 = new Jugador("Edgerik", getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO));
		jugador3 = new Jugador("Roberto", getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO));
		
		this.tablero = new Tablero();
		this.tablero.ponerPrimeraFicha();
		
	}

	public static void main(String[] args)
	{
		Qwirkle q=new Qwirkle();
		q.iniciarJuego();
	}
	
	public void iniciarJuego()
	{
		imprimirTablero();
		while(jugador1==null){
			System.out.println("\n-------------------------------------------");
			System.out.println("\nTotal de fichas en la BOLSA DE FICHAS -> "+bolsa_fichas.size());
			System.out.println("\n-------------------------------------------");
			System.out.println("\nEstado del jugador basico: \n"+jugador2);
			System.out.println("\n-------------------------------------------");
			System.out.println("\nEstado del jugador mejorado: \n"+jugador3);
			if(jugadorHumanoHizoSuJugada()){
				//seTerminoElJuego();
				break;
			}
		}	
	}

	private boolean turno(Jugador jugador) 
	{
		long tiempo = System.currentTimeMillis();
		BackTraking algoritmo = new BackTraking(tablero,jugador.getMano(), jugador.getNombre().equals(jugador3.getNombre()));
		Jugada jugada = algoritmo.getRespuesta();
		
		tiempo = System.currentTimeMillis() - tiempo;

		return procesarJugada(jugador, jugada, tiempo);
	}

	public boolean jugadorHumanoHizoSuJugada() {

		// juega algoritmo básico
		if(turno(jugador2))
			return true;
		//imprimirTablero();
		if(turno(jugador3))
			return true;
		//imprimirTablero();
		return false;
		
	}
	/*private void seTerminoElJuego() {
		FileOperations.finDeTurno(jugador2.getNombre(),jugador2.tiempo,jugador2.score);
		FileOperations.finDeTurno(jugador3.getNombre(),jugador3.tiempo,jugador3.score);
		imprimirTablero();
	}*/
	private boolean procesarJugada(Jugador jugador, Jugada jugada,long tiempo)
	{
		System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("\nTurno del jugador "+jugador.getNombre());
		System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		tablero.procesarJugada(jugada);
		int cantPuntos = tablero.getPuntos(jugada);
		jugador.procesarJugada(jugada,cantPuntos,tiempo);
		
		
		System.out.println("\n-------------------------------------------");
		System.out.println("JUGADA escogida por el algoritmo: "+jugada);
		System.out.println("\n-------------------------------------------");
		System.out.println("Con un TIEMPO de: "+tiempo+" milisegundos.");
		System.out.println("\n-------------------------------------------");
		System.out.println("Y el jugador "+jugador.getNombre()+" gano un total de "+cantPuntos+" PUNTOS.");

		FileOperations.createdFileXRound(jugador.getNombre(), jugador.getScore() + "", tiempo + "");

		if(jugador.getMano().isEmpty()&&bolsa_fichas.isEmpty())
		{
			System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("El jugador "+jugador.getNombre()+" gano la partida");
			System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			return true;
		}

		jugador.getMano().addAll(getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO - jugador.getMano().size()));

		return false;
	}

	public Tablero getTablero() {
		return tablero;
	}
	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
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
		while(cantFichas-->0&&!bolsa_fichas.isEmpty())
			out.add(popRandomFicha());
		return out;
	}
	public Ficha popRandomFicha(){
		return bolsa_fichas.remove((int)(Math.random()*(bolsa_fichas.size()-1)));
	}

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
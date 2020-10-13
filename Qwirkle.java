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
		//imprimirTablero();
		while(jugador1==null){
			System.out.println("Quedan "+bolsa_fichas.size()+" fichas en la bolsa.");
			System.out.println("Estado del jugador básico: \n"+jugador2);
			System.out.println("\nEstado del jugador mejorado: \n"+jugador3);
			if(jugadorHumanoHizoSuJugada()){
				seTerminoElJuego();
				break;
			}
		}	
	}

	public static void main(String[] args){
		Qwirkle q=new Qwirkle();
		q.iniciarJuego();
	}
	
	private boolean procesarJugada(Jugador jugador, Jugada jugada,long tiempo) {
		int cantPuntos = tablero.getPuntos(jugada);
		jugador.procesarJugada(jugada,cantPuntos,tiempo);
		tablero.procesarJugada(jugada);
		//dao.procesarJugada(jugador,jugada,cantPuntos,tiempo);
		if(jugador.getMano().isEmpty()&&bolsa_fichas.isEmpty()){
			return true;
		}
		jugador.getMano().addAll(getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO - jugador.getMano().size()));
		System.out.println("Jugada escogida por "+jugador.nombre+": "+jugada);
		System.out.println("Con un tiempo de: "+tiempo+" milisegundos.");
		System.out.println("Y el jugador ganó un total de "+cantPuntos+" puntos.");

		FileOperations.createdFileXRound(jugador.getNombre(), jugador.getScore(), jugador.tiempo);
		return false;
	}

	private void seTerminoElJuego() {
		FileOperations.finDeTurno(jugador2.getNombre(),jugador2.tiempo,jugador2.score);
		FileOperations.finDeTurno(jugador3.getNombre(),jugador3.tiempo,jugador3.score);
		imprimirTablero();
	}
	

	private boolean turno(Jugador jugador) {
		long tiempo = System.currentTimeMillis();
		BackTraking algoritmo = new BackTraking(tablero,jugador.getMano(),jugador.getNombre().equals(jugador3.getNombre()));
		Jugada jugada=algoritmo.getRespuesta();
		tiempo=System.currentTimeMillis()-tiempo;
		return procesarJugada(jugador, jugada,tiempo);
	}

	public boolean jugadorHumanoHizoSuJugada() {

		// juega algoritmo básico
		if(turno(jugador2))
			return true;
		// juega algoritmo mejorado
		//imprimirTablero();
		if(turno(jugador3))
			return true;
		//imprimirTablero();
		return false;
		
	}
	public Tablero getTablero() {
		return tablero;
	}
	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

	public Map<Ficha, ArrayList<ArrayList<Ficha>>> getPossiblePlaysHand(ArrayList<Ficha> pFichas)
	{
		int cant_man = pFichas.size()-1;
		Map<Ficha, ArrayList<ArrayList<Ficha>>> grupos = new HashMap<Ficha, ArrayList<ArrayList<Ficha>>>();

		for(int pI=0; pI<cant_man; pI++)
		{
			ArrayList<ArrayList<Ficha>> lista_fichas_slices = new ArrayList<ArrayList<Ficha>>();
			ArrayList<Ficha> combination_list_1 = new ArrayList<Ficha>();
			ArrayList<Ficha> combination_list_2 = new ArrayList<Ficha>();

			for(int pJ=0; pJ<cant_man; pJ++)
			{			
				if(!pFichas.get(pI).noCombina(pFichas.get(pJ)))
				{
					if(pFichas.get(pI).getFigura()!=pFichas.get(pJ).getFigura()
						&&pFichas.get(pI).getColor()==pFichas.get(pJ).getColor())
					{
						combination_list_1.add(pFichas.get(pJ));	
					}
					else if(pFichas.get(pI).getFigura()==pFichas.get(pJ).getFigura()
						&&pFichas.get(pI).getColor()!=pFichas.get(pJ).getColor())
					{
						combination_list_2.add(pFichas.get(pJ));
					}
				}
			}
				
			lista_fichas_slices.add(combination_list_1);
			lista_fichas_slices.add(combination_list_2);
			grupos.put(pFichas.get(pI), lista_fichas_slices);

			if(combination_list_1.size() == 2)
			{
				ArrayList<Ficha> combination_list_1_1 = getCombinationList1(combination_list_1);

				lista_fichas_slices.add(combination_list_1_1);
				grupos.put(pFichas.get(pI), lista_fichas_slices);
			}

			if(combination_list_2.size() == 2)
			{
				ArrayList<Ficha> combination_list_1_2 = getCombinationList1(combination_list_2);

				lista_fichas_slices.add(combination_list_1_2);
				grupos.put(pFichas.get(pI), lista_fichas_slices);
			}
		}
		return grupos;
	}

	public ArrayList<Ficha> getCombinationList1(ArrayList<Ficha> pList)
	{
		int contador = 0;
		ArrayList<Ficha> combination = new ArrayList<Ficha>();

		for (int index=1; index >= 0; index--) 
		{
				combination.add(contador, pList.get(index));
				contador = 1;
		}
		return combination;
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
				return "■";
			case ROMBO:
				return "÷";
			case SOL:
				return "§";
			case TREBOL:
				return "¤";
			case X:
				return "×";
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
		while(cantFichas-->0&&bolsa_fichas.size()>0)
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
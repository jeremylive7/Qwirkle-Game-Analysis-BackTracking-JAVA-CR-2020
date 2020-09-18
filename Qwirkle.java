import java.util.*; 
import javax.swing.*;

class Qwirkle
{
	private Jugador jugador_humano_1;
	private Jugador jugador_humano_2;
	private Jugador jugadorActual;
	private Tablero tablero;
	private BolsaFichas bolsa_fichas;
	private Turno turno;
	private Ficha ficha;
	private int opcion;
	private JFrame frame;
	private int dimencion_inicial;
	private String[] lista_figura = {"circulo", "cuadrado", "estrella", "trebol", "corazon", "ovalo"};
	private int contador_bolsa;

	public Qwirkle() 
	{		
		this.frame = new JFrame("Qwirkle");
		this.jugador_humano_1 = new Jugador("Jeremy");
		this.jugador_humano_2 = new Jugador("Edgerik");
		this.bolsa_fichas = new BolsaFichas();
		this.ficha = new Ficha();
		this.opcion = 0;
		this.dimencion_inicial = 6;
		this.tablero = new Tablero();
		this.contador_bolsa = 0;

		this.fullFichasToBolsa(this.lista_figura);
		
		this.showBolsaFichas();
		
		this.controlMenu();

		//Ejemplo#1
		//tablero.llenarTableroConEjemplo();
		//qwirkle.printTablero();
	}

	public void controlMenu()
	{
		while(this.opcion < 2)
		{
			this.menu(this.jugador_humano_1);
			this.menu(this.jugador_humano_2);
		}
	}

	public void menu(Jugador pJugador)
	{
		//this.showMano(pJugador);
		pJugador.getTurno().setSuTurno(true);

		do{
			JOptionPane.showMessageDialog(this.frame, "Es el turno del jugador " 
				+ pJugador.getNombre());

			this.opcion = Integer.parseInt(JOptionPane.showInputDialog("1. Seleccionar jugada"
				+ "\n"
				+ "2. Salir del Juego"));

			if(opcion==1)
			{
				pJugador.getTurno().setSuTurno(false);
			}else 
				break;

		}while(pJugador.getTurno().getSuTurno() != false);
	
	}

	public void fullFichasToBolsa(String[] pLista)
	{	
		for (int index=0; index<pLista.length; index++) 
		{
			System.out.println(pLista[index]);
			this.llenoBolsaFichas(pLista[index]);	
		}
	}

	public void llenoBolsaFichas(String pFigura)
	{
		//Creo las 108 fichas
		for (int index=0; index<3; index++) 
		{
			this.asignoFicha(pFigura, "verde");
			this.asignoFicha(pFigura, "anaranjado");
			this.asignoFicha(pFigura, "amarillo");
			this.asignoFicha(pFigura, "celeste");
			this.asignoFicha(pFigura, "morado");
			this.asignoFicha(pFigura, "rojo");
		}
	}

	public void asignoFicha(String pFigura, String pColor)
	{
		this.ficha.setFigura(pFigura);
		this.ficha.setColor(pColor);
		this.bolsa_fichas.setFicha(this.ficha, this.contador_bolsa);
		System.out.println(this.contador_bolsa);
		this.contador_bolsa++;
	}

	public void showBolsaFichas()
	{
		System.out.println(this.bolsa_fichas.getLengthBolsaFichas());		
		for (int i=0; i<this.bolsa_fichas.getLengthBolsaFichas(); i++) {
			System.out.println(i);
			System.out.println( "Figura -> " 
				+ this.bolsa_fichas.getFichas()[i].getFigura() 
				+ "\n" 
				+ "Color -> "
				+ this.bolsa_fichas.getFichas()[i].getColor());
		}
	}

	public void showMano(Jugador pJugador)
	{
		for (int i=0; i<this.dimencion_inicial; i++)
		{
			System.out.println( "Figura -> " 
				+ pJugador.getMano().getFichas().get(i).getFigura()
				+ "\n" 
				+ "Color -> "
				+ pJugador.getMano().getFichas().get(i).getColor());
		}
	}

	public void showPtsJugador(Jugador pJugador)
	{
		System.out.println(pJugador.getScore().getPtsTotales());
	}

	public void getPtsJugada(){

	}

	public void setJugadaTablero()
	{

	}

	public void seleccionoJugada()
	{

	}

	/*
			EJEMPLO #1
	*/
	public void printTablero(){
		System.out.println(tablero.toString());
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
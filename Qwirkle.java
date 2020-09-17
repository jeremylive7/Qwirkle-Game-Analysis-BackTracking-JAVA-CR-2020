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

		//this.llenoBolsaFichas();
		//tablero.llenarTableroConEjemplo();

		this.controlMenu();

		//Ejemplo#1
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

	public void llenoBolsaFichas()
	{
		//Creo las 108 fichas
		for (int index=0; index<3; index++) 
		{
			this.asignoFicha("circulo", "verde");
			this.asignoFicha("circulo", "anaranjado");
			this.asignoFicha("circulo", "amarillo");
			this.asignoFicha("circulo", "celeste");
			this.asignoFicha("circulo", "morado");
			this.asignoFicha("circulo", "rojo");

			this.asignoFicha("cuadrado", "verde");
			this.asignoFicha("cuadrado", "anaranjado");
			this.asignoFicha("cuadrado", "amarillo");
			this.asignoFicha("cuadrado", "celeste");
			this.asignoFicha("cuadrado", "morado");
			this.asignoFicha("cuadrado", "rojo");

			this.asignoFicha("estrella", "verde");
			this.asignoFicha("estrella", "anaranjado");
			this.asignoFicha("estrella", "amarillo");
			this.asignoFicha("estrella", "celeste");
			this.asignoFicha("estrella", "morado");
			this.asignoFicha("estrella", "rojo");

			this.asignoFicha("trebol", "verde");
			this.asignoFicha("trebol", "anaranjado");
			this.asignoFicha("trebol", "amarillo");
			this.asignoFicha("trebol", "celeste");
			this.asignoFicha("trebol", "morado");
			this.asignoFicha("trebol", "rojo");

			this.asignoFicha("corazon", "verde");
			this.asignoFicha("corazon", "anaranjado");
			this.asignoFicha("corazon", "amarillo");
			this.asignoFicha("corazon", "celeste");
			this.asignoFicha("corazon", "morado");
			this.asignoFicha("corazon", "rojo");

			this.asignoFicha("ovalo", "verde");
			this.asignoFicha("ovalo", "anaranjado");
			this.asignoFicha("ovalo", "amarillo");
			this.asignoFicha("ovalo", "celeste");
			this.asignoFicha("ovalo", "morado");
			this.asignoFicha("ovalo", "rojo");

		}
	}

	public void asignoFicha(String pFigura, String pColor)
	{
		this.ficha.setFigura(pFigura);
		this.ficha.setColor(pColor);
		this.bolsa_fichas.setFicha(this.ficha);
	}

	public void showBolsaFichas()
	{
		for (int i=0; i<this.bolsa_fichas.getLengthBolsaFichas(); i++) {
			System.out.println( "Figura -> " 
				+ this.bolsa_fichas.getFichas().get(i).getFigura() 
				+ "/n" 
				+ "Color -> "
				+ this.bolsa_fichas.getFichas().get(i).getColor());
		}
	}

	public void showMano(Jugador pJugador)
	{
		for (int i=0; i<this.dimencion_inicial; i++)
		{
			System.out.println( "Figura -> " 
				+ pJugador.getMano().getFichas().get(i).getFigura()
				+ "/n" 
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
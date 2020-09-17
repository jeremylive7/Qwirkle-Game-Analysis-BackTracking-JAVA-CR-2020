import javax.swing.*;

class Qwirkle
{
	//Variables Globales
	private Jugador jugador_humano_1;
	private Jugador jugador_humano_2;
	private Tablero tablero;
	private BolsaFichas bolsa_fichas;
	private int opcion;
	private JFrame frame;
	private Ficha ficha;
	private int dimencion_inicial;


	//Constructor
	public Qwirkle() 
	{		
		//Creo ficha para trabajar con ella durante el programa
		this.ficha = new Ficha();

		//
		this.dimencion_inicial = 35;

		//Creo jframe
   		this.frame = new JFrame("Qwirkle");

		//Creo jugadores
		this.jugador_humano_1 = new Jugador("Jeremy");
		this.jugador_humano_2 = new Jugador("Edgerik");

		//Creo tablero
		this.tablero = new Tablero();

		//Creo y lleno BolsaFichas
		this.bolsa_fichas = new BolsaFichas();
		this.llenoBolsaFichas();
    
		//Ronda del Juego, se encicla hasta que se diga salir del juego
		this.opcion = 0;
		while(this.opcion < 4){
			this.menu(this.jugador_humano_1);
			//BUG: No sale del juegoo de imediato debe de dar la opcion de salir el jugador 2.
			this.menu(this.jugador_humano_2);
		}
	}

	//Creo Menu
	public void menu(Jugador pJugador_humano)
	{
		pJugador_humano.getTurno().setSuTurno(true);
		JOptionPane.showMessageDialog(this.frame, "Es el turno del jugador " + pJugador_humano.getNombre());

		do{
			//Muestro mano del jugador
			//this.showMano(jugador_humano_1);
			
			//Obtengo el # de la opcion
			this.opcion = Integer.parseInt(JOptionPane.showInputDialog("1. Reseteo de fichas"
				+ "\n"
				+ "2. Seleccionar mi jugada"
				+ "\n"
				+ "3. No tengo fichas a jugar"
				+ "\n"
				+ "4. Salir del Juego"));

			//Muestro menu
			switch(this.opcion){
				//Escoge fichas a resetear.
				case 1:
					System.out.println("Elegiste reseteo de fichas");
					pJugador_humano.getTurno().setSuTurno(false);
					break;
				//Elige jugada a colocar
				case 2:
					System.out.println("Elegiste seleccionar mi jugada");
					pJugador_humano.getTurno().setSuTurno(false);

					//Empieza turno, selecciono mi jugada
					seleccionoJugada();

					//Coloco jugada en el tablero
					setJugadaTablero();

					//Obtencion de pts por las fichas seteadas
					getPtsJugada();

					//Imprimo pts
					showPtsJugador(pJugador_humano);
					break;
				//No tiene fichas
				case 3:
					System.out.println("Elegiste no tengo fichas a jugara");
					pJugador_humano.getTurno().setSuTurno(false);
					break;
				//Salir del juego
				default:
					System.out.println("Elegiste salir del juego");
					pJugador_humano.getTurno().setSuTurno(false);
			}

		}while(pJugador_humano.getTurno().getSuTurno() != false);
	}

	//Lleno la bolsa de fichas con fichas adecuadas a las 108 en totales
	//Seteo las fichas en la bolsa de ficha
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

	//Seteo la informacion de la ficha para setearla en la bolsa de fichas
	public void asignoFicha(String pFigura, String pColor)
	{
		this.ficha.getFigura().setTipo(pFigura);
		this.ficha.getColor().setTipo(pColor);
		this.bolsa_fichas.setFicha(this.ficha);
	}


	//Muestro pts totales del jugador
	public void showPtsJugador(Jugador pJugador)
	{
		System.out.println(pJugador.getScore().getPtsTotales());
	}

	//Obtengo pts de la jugada reciente para sumarselos al jugador
	public void getPtsJugada()
	{

	}

	//Ya escogida la jugada a setear en el tablero
	//Se agaarra como parametro, se coloca en el tablero
	//Preuntando primero donde quiero poner cada ficha 
	//Siguiendo las reglas del juego.
	public void setJugadaTablero()
	{

	}

	//Ya mostroadas las opciones que tengo disponibles en la mano
	//Selecciono la jugada de alguna forma
	public void seleccionoJugada()
	{

	}

	//Imprimo mano jugador humano
	public void showMano(Jugador pJugador)
	{
		for (int i=0; i<this.dimencion_inicial; i++) {
			System.out.println( "Figura -> " 
				+ pJugador.getMano().getFichas().get(i).getFigura().getTipo() 
				+ "/n" 
				+ "Color -> "
				+ pJugador.getMano().getFichas().get(i).getColor().getTipo());
		}
	}

	//Imprimo BlosaDeFichas
	public void showBolsaFichas()
	{
		for (int i=0; i<this.bolsa_fichas.getLengthBolsaFichas(); i++) {
			System.out.println( "Figura -> " 
				+ this.bolsa_fichas.getFichas().get(i).getFigura().getTipo() 
				+ "/n" 
				+ "Color -> "
				+ this.bolsa_fichas.getFichas().get(i).getColor().getTipo());
		}
	}

	//Imprimo Tablero grafito


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
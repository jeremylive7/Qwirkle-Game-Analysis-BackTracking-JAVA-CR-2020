import javax.swing.*;

class Qwirkle
{
	//Variables Globales
	private Jugador jugador_humano_1;
	private Jugador jugador_humano_2;
	private Tablero tablero;
	private BolsaFichas bolsa_fichas;
	private int opcion = 0;
	private JFrame frame;

	//Constructor
	public Qwirkle() 
	{		

		//Creo jframe
   		this.frame = new JFrame("Qwirkle");

		//Creo jugadores
		this.jugador_humano_1 = new Jugador("Jeremy");
		this.jugador_humano_2 = new Jugador("Edgerik");

		//Creo tablero
		this.tablero = new Tablero();

		//Creo BolsaFichas
		this.bolsa_fichas = new BolsaFichas();
    
		//Ronda del Juego, se encicla hasta que se diga salir del juego
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
			this.showMano();
			
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
					showPtsJugador();
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
				+ this.pJugador.getMano().getFichas().get(i).getFigura().getTipo() 
				+ "/n" 
				+ "Color -> "
				+ this.pJugador.getMano().getFichas().get(i).getColor().getTipo());
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
	public void showTableroGrafito(Grafito pGrafito)
	{
		
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
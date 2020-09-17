import javax.swing.*;

class Qwirkle
{
	static final private int dimencion_inicial=6;
	private Jugador jugador_humano_1;
	private Jugador jugador_humano_2;
	private Jugador jugadorActual;
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
		jugadorActual=jugador_humano_1;

		//Creo tablero
		this.tablero = new Tablero();

		//Creo BolsaFichas
		this.bolsa_fichas = new BolsaFichas();
		
		menu();
	}

	//Creo Menu
	public void menu()
	{
		while(this.opcion < 4){
			JOptionPane.showMessageDialog(this.frame, "Es el turno del jugador " + jugadorActual.getNombre());

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

				if(opcion==1)
				{//Resetear fichas del jugadorActual
					System.out.println("Elegiste reseteo de fichas");
					//jugadorActual.resetearFichas(sacarFichasDeLaBolsa(interfaz.cualesFichas(jugadorActual.getMazo())))
				}	
				if(opcion==2)
				{//Elige jugada a colocar
					System.out.println("Elegiste seleccionar mi jugada");
					seleccionoJugada();//Empieza turno, selecciono mi jugada
					setJugadaTablero();//Coloco jugada en el tablero
					getPtsJugada();//Obtencion de pts por las fichas seteadas
					showPtsJugador();//Imprimo pts
				}
				if(opcion==3)//No tiene fichas
					System.out.println("Elegiste no tengo fichas a jugara");
				else	//Salir del juego
					break;
						
				jugadorActual=(jugadorActual==jugador_humano_1?jugador_humano_2:jugador_humano_1);

			}while(true);
		}
	}

	//Muestro pts totales del jugador
	public void showPtsJugador()
	{
		System.out.println(jugadorActual.getScore().getPtsTotales());
	}

	//Obtengo pts de la jugada reciente para sumarselos al jugador
	public void getPtsJugada()
	{
		/*jajsakdjflasf
		*/

	}

	
	public void setJugadaTablero()
	{
		/*
		Ya escogida la jugada a setear en el tablero
		Se agaarra como parametro, se coloca en el tablero
		Preuntando primero donde quiero poner cada ficha 
		Siguiendo las reglas del juego.
		*/
	}

	//
	public void seleccionoJugada()
	{
		/*
		Ya mostroadas las opciones que tengo disponibles en la mano
		Selecciono la jugada de alguna forma
		*/
	}

	//Imprimo mano jugador humano
	public void showMano()
	{/*
		for (Ficha ficha:jugadorActual.getMano()){
			System.out.println(ficha.toString());
		}
		*/
		for (int i=0; i<this.dimencion_inicial; i++) {
			System.out.println( "Figura -> " 
				+ this.jugadorActual.getMano().getFichas().get(i).getFigura().getTipo() 
				+ "/n" 
				+ "Color -> "
				+ this.jugadorActual.getMano().getFichas().get(i).getColor().getTipo());
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
	public void showTableroGrafito(Grafito pGrafito)
	{
		/*
		Imprimo Tablero grafito
		*/
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
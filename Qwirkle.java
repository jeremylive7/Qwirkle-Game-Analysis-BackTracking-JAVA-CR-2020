import java.util.ArrayList;
import javax.swing.*;

class Qwirkle
{
	//Variables Globales
	Jugador jugador_humano_1;
	Jugador jugador_humano_2;
	Tablero tablero;
	BolsaFichas bolsa_fichas;
	Fichas fichas_totales;
	Ficha ficha;
	int dimencion_inicial = 6;
	int opcion = 0;
	JFrame frame;

	//Constructor
	Qwirkle() 
	{		
		//Creo jugadores
		this.jugador_humano_1 = new Jugador("Jeremy");
		this.jugador_humano_2 = new Jugador("Edgerik");

		//Creo tablero
		this.tablero = new Tablero(this.dimencion_inicial, this.dimencion_inicial);
		this.fullMatrizEmpty();

		//Creo BolsaFichas
		this.bolsa_fichas = new BolsaFichas();

		//Creo jframe
   		this.frame = new JFrame("Qwirkle");
    
		//Ronda del Juego, se encicla hasta que se diga salir del juego
		while(this.opcion != 4){
			this.menu(this.jugador_humano_1);
			this.menu(this.jugador_humano_2);
		}



	}

	//Creo Menu
	private void menu(Jugador pJugador_humano)
	{
		pJugador_humano.getTurno().setSuTurno(true);
		JOptionPane.showMessageDialog(this.frame, "Es el turno del jugador " + pJugador_humano.getNombre());

		do{
			this.opcion = Integer.parseInt(JOptionPane.showInputDialog("1. Reseteo de fichas"
				+ "\n"
				+ "2. Seleccionar mi jugada"
				+ "\n"
				+ "3. No tengo fichas a jugar"
				+ "\n"
				+ "4. Salir del Juego"));
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

	//Lleno matriz en blanco
	private void fullMatrizEmpty()
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

	//Imprimo mano jugador humano 1
	private void printMano()
	{
		for (int i=0; i<this.dimencion_inicial; i++) {
			System.out.println( "Figura -> " 
				+ this.jugador_humano_1.getMano().getFichas().get(i).getFigura().getTipo() 
				+ "/n" 
				+ "Color -> "
				+ this.jugador_humano_1.getMano().getFichas().get(i).getColor().getTipo());
		}
	}

	//Imprimo mazo
	private void printMazo()
	{
		for (int i=0; i<this.bolsa_fichas.getLengthBolsaFichas(); i++) {
			System.out.println( "Figura -> " 
				+ this.bolsa_fichas.getFichas().get(i).getFigura().getTipo() 
				+ "/n" 
				+ "Color -> "
				+ this.bolsa_fichas.getFichas().get(i).getColor().getTipo());
		}
	}

	//Imprimir tablero

}
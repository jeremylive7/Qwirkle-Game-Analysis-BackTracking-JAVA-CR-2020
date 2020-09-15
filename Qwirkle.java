import java.util.ArrayList;
import javax.swing.*;

class Qwirkle
{
	//Variables Globales
	static Jugador jugador_humano_1;
	static Jugador jugador_humano_2;
	static Tablero tablero;
	static BolsaFichas bolsa_fichas;
	static Fichas fichas_totales;
	static Ficha ficha;
	static int dimencion_inicial = 6;
	static int opcion = 0;

	//Constructor
	Qwirkle() 
	{		
		//Creo jugadores
		jugador_humano_1 = new Jugador("Jeremy");
		jugador_humano_2 = new Jugador("Edgerik");

		//Creo tablero
		tablero = new Tablero(dimencion_inicial, dimencion_inicial);
		this.fullMatrizEmpty();

		//Creo BolsaFichas
		bolsa_fichas = new BolsaFichas();

		//Menu
		this.menu();

	}

	//Creo Menu
	private void menu()
	{
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
					break;
				//Elige jugada a colocar
				case 2:
					System.out.println("Elegiste seleccionar mi jugada");
					break;
				//No tiene fichas
				case 3:
					System.out.println("Elegiste no tengo fichas a jugara");
					break;
				//Salir del juego
				default:
					System.out.println("Elegiste salir del jeugo");
			}

		}while(this.opcion!=4);
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
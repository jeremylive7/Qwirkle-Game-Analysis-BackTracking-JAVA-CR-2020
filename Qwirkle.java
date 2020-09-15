class Qwirkle
{
	//Variables Globales
	Jugador jugador_humano_1;
	Jugador jugador_humano_2;
	Tablero tablero;
	int dimencion_inicial = 6;

	//Constructor
	Qwirkle() 
	{		
		//Creo jugadores
		jugador_humano_1 = new Jugador("Jeremy");
		jugador_humano_2 = new Jugador("Edgerik");

		//Creo tablero
		tablero = new Tablero(dimencion_inicial, dimencion_inicial);
		tablero.fullMatrizEmpty();

		//Menu
		

	}

	//Lleno matriz en blanco
	private void fullMatrizEmpty()
	{
		//Matriz 6x6
		for (int i=0; i<dimencion_inicial; i++) {
			Fichas fichas_totales = new Fichas();
			for (int i=0; i<dimencion_inicial; i++) {
				Ficha ficha = new Ficha();
				fichas_totales.setFichasFila(ficha);
			}
			tablero.setOneFilaFichas(fichas_totales);		
		}

	}

}
class Main 
{
	public static void main(String[] args) 
	{		
		//Empieza el juego
		Qwirkle qwirkle;
		qwirkle = new Qwirkle();

		qwirkle.mostrarVentana();
		qwirkle.frame.mostrarTablero();

		qwirkle.tablero.startGame();

		qwirkle.menu();

		System.exit(0);
	}

}
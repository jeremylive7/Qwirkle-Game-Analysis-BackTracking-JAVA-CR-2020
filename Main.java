class Main 
{
	public static void main(String[] args) 
	{		
		//Empieza el juego
		Qwirkle qwirkle;
		qwirkle = new Qwirkle();
		qwirkle.getTablero().llenarTableroConEjemplo();
		qwirkle.imprimirTablero();

		System.exit(0);
	}

}
class Main 
{
	public static void main(String[] args) 
	{		
		Qwirkle qwirkle;
		qwirkle = new Qwirkle();

		qwirkle.getTablero().llenarTableroConEjemplo();
		qwirkle.imprimirTablero();
		
		qwirkle.menu();

		System.exit(0);
	}

}
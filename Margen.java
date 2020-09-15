class Margen
{
	//Variables Globales
	static int largo_fila;
	static int largo_columna;

	//Constructor
	Margen() 
	{		
		this.largo_fila = 0;
		this.largo_columna = 0;
	}

	//Metodos de get y set
	private int getLargoFila()
	{
		return this.largo_fila;
	}

	private int getLargoColuman()
	{
		return this.largo_columna;
	}

	private void setLargoFila(int pLargo_fila)
	{
		this.largo_fila = pLargo_fila;
	}

	private void setLargoColumna(int pLargo_columna)
	{
		this.largo_columna = pLargo_columna;
	}

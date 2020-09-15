class Margen
{
	//Variables Globales
	static int x;
	static int y;

	//Constructor
	Margen(int pX, int pY) 
	{		
		this.x = pX;
		this.y = pY;
	}

	//Metodos de get y set
	private int getX()
	{
		return this.x;
	}

	private int getY()
	{
		return this.y;
	}

	private void setX(int pX)
	{
		this.x = pX;
	}

	private void setY(int pY)
	{
		this.y = pY;
	}
}
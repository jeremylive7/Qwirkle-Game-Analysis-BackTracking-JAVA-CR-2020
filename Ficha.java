public class Ficha
{
	//Variables Globales
	int x;
	int y;
	Figura figura;
	Color color;

	//Constructor
	Ficha() 
	{		
		this.x = 0;
		this.y = 0;
		this.figura = new Figura();
		this.color = new Color();	
	}

	//Metodos get y set
	private int getX()
	{
		return this.x;
	}

	private int getY()
	{
		return this.y;
	}

	public Figura getFigura()
	{
		return this.figura;
	}

	public Color getColor()
	{
		return this.color;
	}

	private void setX(int pX)
	{
		this.x = pX;
	}

	private void setY(int pY)
	{
		this.y = pY;
	}

	private void setFigura(Figura pFigura)
	{
		this.figura = pFigura;
	}

	private void setColor(Color pColor)
	{
		this.color = pColor;
	}
}
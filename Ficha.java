public class Ficha
{
	//Variables Globales
	static int x;
	static int y;
	static Figura figura;
	static Color color;

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

	private Figura getFigura()
	{
		return this.figura;
	}

	private Color getColor()
	{
		return this.color;
	}

	private void setX(pX)
	{
		this.x = pX;
	}

	private void setY(pY)
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
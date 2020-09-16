public class Ficha
{
	//Variables Globales
	private Figura figura;
	private Color color;

	//Constructor
	public Ficha() 
	{		
		this.figura = new Figura();
		this.color = new Color();	
	}

	//Metodos get y set
	public Figura getFigura()
	{
		return this.figura;
	}

	public Color getColor()
	{
		return this.color;
	}

	public void setFigura(Figura pFigura)
	{
		this.figura = pFigura;
	}

	public void setColor(Color pColor)
	{
		this.color = pColor;
	}
}
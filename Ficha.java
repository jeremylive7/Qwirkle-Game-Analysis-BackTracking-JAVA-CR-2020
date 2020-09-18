public class Ficha
{
	private Figura figura;
	private Color color;

	//Constructor
	public Ficha(Figura figura,Color color) 
	{		
		this.figura = figura;
		this.color = color;
	}

	public String toString(){
		return "Figura: "+figura+"$Color: "+color;
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
public class Ficha
{
	private String figura;
	private String color;

	//Constructor
	public Ficha(String figura,String color) 
	{		
		this.figura = figura;
		this.color = color;
	}

	public String toString(){
		return "Figura: "+figura+"$Color: "+color;
	}

	public String getColor()
	{
		return this.color;
	}

	public void setFigura(String pFigura)
	{
		this.figura = pFigura;
	}

	public void setColor(String pColor)
	{
		this.color = pColor;
	}
}
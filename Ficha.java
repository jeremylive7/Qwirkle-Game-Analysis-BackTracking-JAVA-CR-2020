public class Ficha
{
	private String figura;
	private String color;

	public Ficha() 
	{		
		this.figura = "";
		this.color = "";
	}

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

	public String getFigura()
	{
		return this.figura;
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
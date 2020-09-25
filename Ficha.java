public class Ficha
{
	public Figura figura;
	public Color color;

	//Constructor
	public Ficha(Figura figura,Color color) 
	{		
		this.figura = figura;
		this.color = color;
	}

	public String toString(){
		return "Figura: "+figura+"$Color: "+color;
	}

	@Override
	public boolean equals(Object f){
		Ficha ficha;
		try{
			ficha=(Ficha) f;
			return ficha!=null&&figura==ficha.figura&&color==ficha.color;
		}catch (Exception e){
			return false;
		}
	}

	public Color getColor()
	{
		return this.color;
	}

	public Figura getFigura(){
		return figura;
	}

	public void setFigura(Figura pFigura)
  {
		this.figura = pFigura;
	}

	public void setColor(Color pColor)
	{
		this.color = pColor;
	}

	public boolean noCombina(Ficha ficha) {
		if (ficha==null) return false;
		return equals(ficha)||(figura!=ficha.figura&&color!=ficha.color);
	}
}
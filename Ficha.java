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
	private String getSimboloColor(Color c)
	{
		if(c==Color.AMARILLO)
			return "Am";
		else if(c==Color.AZUL)
			return "Az";
		else if(c==Color.NARANJA)
			return "Na";
		else if(c==Color.MORADO)
			return "Mo";
		else if(c==Color.ROJO)
			return "Ro";
		else if(c==Color.VERDE)
			return "Ve";
		else return "";
		
	}
	private String getSimboloFigura(Figura f)
	{
		switch(f){
			case CIRCULO:
				return "O";
			case CUADRADO:
				return "■";
			case ROMBO:
				return "÷";
			case SOL:
				return "§";
			case TREBOL:
				return "¤";
			case X:
				return "×";
		}
		return "";
	}
	public String toString(){
		return getSimboloFigura(figura)+getSimboloColor(color);
	}

	@Override
	public int hashCode(){
		return 0;
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
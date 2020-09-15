public class Figura
{
	//Variables Globales
	static String tipo;

	//Constructor
	Figura() 
	{		
		this.tipo = "";	
	}

	//Metodos get y set
	private String getTipo()
	{
		return this.tipo;
	}

	private void setTipo(String pTipo)
	{
		this.tipo = pTipo;
	}

}
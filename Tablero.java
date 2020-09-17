class Tablero
{
	private static final int MATRIX_SIDE=5;
	private Ficha fichas[][] ;

	//Constructor
	public Tablero() 
	{		
		fichas = new Ficha[MATRIX_SIDE][MATRIX_SIDE];	
	}
	public void llenarTableroConEjemplo(){
		int mitadDeLaMatriz=MATRIX_SIDE/2;
		fichas[mitadDeLaMatriz][mitadDeLaMatriz]=new Ficha("rombo","rojo");
		fichas[mitadDeLaMatriz+1][mitadDeLaMatriz]=new Ficha("circulo","rojo");
		fichas[mitadDeLaMatriz+2][mitadDeLaMatriz]=new Ficha("cuadrado","rojo");
		fichas[mitadDeLaMatriz][mitadDeLaMatriz+1]=new Ficha("rombo","azul");
	}
	@Override
	public String toString(){
		String out="";
		for(int i=0;i<MATRIX_SIDE;i++){
			for(int j=0;j<MATRIX_SIDE;j++)
				out+="# "+(fichas[i][j]!=null?fichas[i][j].toString():"\t\t\t")+" #";
			out+="\n";
		}
		return out;
	}
/*
	//Metodos de get y set
	public Grafito getTablero()
	{
		return this.fichas;
	}
*/
}
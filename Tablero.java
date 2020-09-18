import java.util.*;


class Tablero
{
	static final int MATRIX_SIDE=8;
	private Ficha fichas[][] ;

	//Constructor
	public Tablero() 
	{		
		fichas = new Ficha[MATRIX_SIDE][MATRIX_SIDE];	
	}
	public Ficha[][]getFichas(){
		return fichas;
	}
	public void llenarTableroConEjemplo(){
		int mitadDeLaMatriz=MATRIX_SIDE/2;
		meterFichaEnXY(new Ficha(Figura.ROMBO,Color.ROJO), mitadDeLaMatriz, mitadDeLaMatriz);
		meterFichaEnXY(new Ficha(Figura.CIRCULO,Color.ROJO), mitadDeLaMatriz+1, mitadDeLaMatriz);
		meterFichaEnXY(new Ficha(Figura.CUADRADO,Color.ROJO),mitadDeLaMatriz+2, mitadDeLaMatriz);
		meterFichaEnXY(new Ficha(Figura.ROMBO,Color.AZUL), mitadDeLaMatriz, mitadDeLaMatriz+1);
	}
	public boolean meterFichaEnXY(Ficha ficha,int x,int y){
		if(x<0||y<0||x>=MATRIX_SIDE||y>=MATRIX_SIDE)
			return false;
		fichas[x][y]=ficha;
		return true;
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
	public List<Fichas> getTablero()
	{
		return this.fichas;
	}
*/
}
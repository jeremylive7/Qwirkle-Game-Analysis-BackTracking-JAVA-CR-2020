import java.util.*;


class Tablero
{
	private Ficha fichas[][];

	public Tablero(int pSize) 
	{		
		this.fichas = new Ficha[pSize][pSize];	
	}

	public Ficha[][] getTablero()
	{
		return this.fichas;
	}

	public void setTablero(int x, int y, Ficha pFicha)
	{
		this.fichas[x][y] = pFicha;
	}

}
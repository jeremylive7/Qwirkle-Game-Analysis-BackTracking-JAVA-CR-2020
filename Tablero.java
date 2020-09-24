import java.util.*;


class Tablero
{
	static final int MATRIX_SIDE=20;
	private Ficha fichas[][];

	public Tablero() 
	{		
		fichas = new Ficha[MATRIX_SIDE][MATRIX_SIDE];	
	}

	public List<Fichas> getTablero()
	{
		return this.fichas;
	}

}






/* 	public Boolean canIDoPutFicha(ArrayList<Ficha> pFichas_pDisponibles)
	{

	}
	public ArrayList<Ficha> getFichasDisponiblesAJugar(ArrayList<Ficha> pJugadas_tablero)
	{
		ArrayList<Ficha> pFichas_disponibles = new ArrayList<Ficha>();
		String[] pFiguras_disponibles = {"TREBOL","SOL","ROMBO","CUADRADO","CIRCULO","X"};
		String[] pColores_disponibles = {"ROJO","VERDE","AMARILLO","AZUL","MORADO","NARANJA"};

		for (pFicha : pJugadas_tablero) 
		{
			for (int pIndex=0; pIndex<pFichas_disponibles.length; pIndex++) 
			{
				if(pFicha.getFigura()==pFiguras_disponibles[pIndex])
				{
					if(pFicha.getColor()==pColores_disponibles[pIndex])
					{

					}
				}
				pFiguras_disponibles[pIndex];
			}

			pFichas_disponibles.add(pFicha);
		}

		return pFichas_disponibles;
	} */
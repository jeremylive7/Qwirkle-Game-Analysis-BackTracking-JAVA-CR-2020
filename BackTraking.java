public class BackTraking
{
	private ArrayList<ArrayList<Ficha>> matrix_fichas;
	private ArrayList<Ficha> mano_fichas;

	public BackTraking(ArrayList<ArrayList<Ficha>> pMatriz, ArrayList<Ficha> pMano) 
	{		
		this.matrix_fichas = pMatriz;
		this.mano_fichas = pMano;
	}
	//1.Darme las jugadas que pueda jugar
	public void ArrayList<Ficha> getJugadasToSet()
	{
		boolean exist_ficha = false;
		ArrayList<ArrayList<Ficha>> matrix_jugadas_seters = new ArrayList<ArrayList<Ficha>>();
		ArrayList<Ficha> jugadas_seters = new ArrayList<Ficha>();

		for (pFicha : this.mano_fichas) 
		{
			for (pJugada : this.matrix_fichas) 
			{
				for (pFicha_jugada : this.pJugada) 
				{
					if(pFicha.getFicha() == pFicha_jugada.getFicha())
					{
						exist_ficha = true;
						break;
					}
					else
					{
						jugadas_seters.add(pFicha_jugada);
					}
				}

				if(exist_ficha == true && matrix_jugadas_seters.size() > 0)
				{
					matrix_jugadas_seters.add(jugadas_seters);
				}else
				{
					jugadas_seters = new ArrayList<ArrayList<Ficha>>();
				}	
			}	
		}

		return matrix_jugadas_seters;
	}
	//2.Darme las jugadas del largo mas alto
	public void ArrayList<ArrayList<Ficha>> getJugadasHigher(ArrayList<ArrayList<Ficha>> pJugadas_tablero)
	{
		ArrayList<ArrayList<Ficha>> jugadas_higher = new ArrayList<ArrayList<Ficha>>();
		int[] jugadas_sizes = new int[largo_jugadas_tablero];
		int[] jugadas_sizes_higher = new int[largo_jugadas_tablero];
		int largo_jugadas_tablero = pJugadas_tablero.size();
		int contador = 0;
		int most_higher = 0;

		for (pJugada : pJugadas_tablero)
		{
			for (pFicha : pJugada) 
			{
				largo_jugadas_tablero++;
			}
			jugadas_sizes[contador] = largo_jugadas_tablero;
			contador++;
		}

		for (int pIndex=0; pIndex<jugadas_sizes.length; pIndex++) 
		{
			if(jugadas_sizes[pIndex] > most_higher)
			{
				most_higher = jugadas_sizes[pIndex];
				jugadas_sizes_higher[pIndex] = most_higher;
			}
			else if(jugadas_sizes[pIndex] = most_higher)
			{
				jugadas_sizes_higher[pIndex] = most_higher;
			}
		}

		for (int pInt=0; pInt<largo_jugadas_tablero; pInt++)
		{
			if(jugadas_sizes_higher[pInt] != 0)
			{
				jugadas_higher.add(pJugadas_tablero.get(pInt));
			}
		}

		return jugadas_higher;
	}

}
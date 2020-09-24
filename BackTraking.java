import java.util.*;

public class BackTraking
{
	private ArrayList<HashMap<String, String>> possible_combinations;

	public BackTraking()
	{
		this.possible_combinations = new ArrayList<HashMap<String, String>>();
		this.fillCombinations();
	}

	public void fillCombinations()
	{
		String[] colores = {"ROJO", "VERDE", "AMARILLO", "AZUL", "MORADO", "NARANJA"};
		String[] figuras = {"TREBOL", "SOL", "ROMBO", "CUADRADO", "CIRCULO", "X"};
		HashMap<String, String> combinacion_1 = new HashMap<String, String>();
		HashMap<String, String> combinacion_2 = new HashMap<String, String>();

		for (color : colores) 
		{
			for (figura : figuras) 
			{
				combinacion_1.put(figura, color);
			}
			this.possible_combinations.add(combinacion_1);
			combinacion_1 = new HashMap<String, String>();
		}

		for (figura : figuras) 
		{
			for (color : colores) 
			{
				combinacion_2.put(figura, color);
			}
			this.possible_combinations.add(combinacion_2);
			combinacion_2 = new HashMap<String, String>();
		}
	}

	public void printPossiblePlays(ArrayList<ArrayList<Ficha>> pPlays)
	{
		for (play : pPlays) 
		{
			for (ficha : play) 
			{
				System.out.println(ficha.getFigura()+ficha.getColor()+"-");			
			}	
			System.out.println("\n");
		}
	}

	public void getFullPossibleCombinations()
	{
		int contador = 0;
		for (ficha_possible_combinations : this.possible_combinations) 
		{

			for (ficha_mano : pMano) 
			{
				
			}
				
		}
	}

	public void getPossiblePlays(ArrayList<ArrayList<Ficha>> pPlays, 
								ArrayList<ArrayList<Ficha>> pTablero, 
								ArrayList<Ficha> pMano)
	{	
		if(pMano.size() == 0)
		{
			printPossiblePlays(pPlays);
		}
		else if()
		{

		}
		else
		{
			getFullPossibleCombinations();
			//getPossiblePlays(pPlays, pMano, pTablero);

		}
	}


}



















/*	private ArrayList<ArrayList<Ficha>> matriz_fichas;
	private ArrayList<ArrayList<Ficha>> possible_plays;
	private ArrayList<Ficha> mano_fichas;
	
	public BackTraking() 
	{		
		this.matriz_fichas = new ArrayList<ArrayList<Ficha>>();
		this.possible_plays = new ArrayList<ArrayList<Ficha>>();
		this.mano_fichas = new ArrayList<Ficha>();
	}

	public ArrayList<ArrayList<Ficha>> getMatrizFichas()
	{
		return this.matriz_fichas;
	}

	public ArrayList<ArrayList<Ficha>> getPossiblePlays()
	{
		return this.possible_plays;
	}

	public ArrayList<Ficha> getManoFichas()
	{
		return this.mano_fichas;
	}

	public void setMatrixFichas(ArrayList<ArrayList<Ficha>> pMatriz)
	{
		this.matriz_fichas = pMatriz;
	}

	public void setPossiblePlays(ArrayList<ArrayList<Ficha>> pPlays)
	{
		this.possible_plays = pPlays;
	}

	public void setManoFichas(ArrayList<Ficha> pMano)
	{
		this.mano_fichas = pMano;
	}*/











/*
	//Darme las jugadas que pueda jugar
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
	//Darme las jugadas del largo mas alto
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
			largo_jugadas_tablero = 0;
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

		for (int pInt=0; pInt<contador; pInt++)
		{
			if(jugadas_sizes_higher[pInt] != 0)
			{
				jugadas_higher.add(pJugadas_tablero.get(pInt));
			}
		}

		return jugadas_higher;
	}*/
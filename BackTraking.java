import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class BackTraking
{
	/**
	 * Score Limit For Set Up Easy Qwirkle
	 */
	private static final int SLFSUEQ=12;
	private Tablero tablero;
	private ArrayList<Ficha> mano;
	private ArrayList<Jugada> jugadas;
	private Jugada jugada;
	public final Random r=new Random();

	private Map<Ficha, Integer> repet_fichas;

	//Constructor
	BackTraking(Tablero pTablero, ArrayList<Ficha> pMano, Map<Ficha, Integer> pRepet) 
	{		
		this.tablero = pTablero;
		this.repet_fichas = pRepet;
		this.mano = this.getHandWithOutRepet(pMano);
	}
	
	public Jugada getJugadaBasico()
	{
		this.jugadas = this.getJugadas(this.getPossiblePlaysHand(this.mano));
		this.jugadas.sort((o1,o2)->Integer.compare(o2.puntos, o1.puntos));

		return this.jugadas.get(0);
	}

	public Jugada getJugadaMejorado()
	{
		ArrayList<Ficha> repet_fichas_hand = this.getRepetFicha(this.mano);

		this.ejecutarMejorado();						

		this.jugadas = this.getJugadas(this.convertJugadaToMap(this.jugadas));		
		this.jugadas.sort((o1,o2)->Integer.compare(o2.puntos, o1.puntos));
	
		return this.jugadas.get(0);
	}
	
	private void ejecutarMejorado()
	{ 
		this.jugadas = this.getAllPossibilities(this.getPossiblePlaysHand(mano));
		this.jugadas.removeIf(jugada->cumpleAlgunCriterioDePoda(jugada));
	}

	private Map<Ficha,ArrayList<ArrayList<Ficha>>> convertJugadaToMap(ArrayList<Jugada> pJugadas)
	{
		Map<Ficha,ArrayList<ArrayList<Ficha>>> pTotal_jugadas = new HashMap<Ficha, ArrayList<ArrayList<Ficha>>>();

		return pTotal_jugadas;
	}

	private ArrayList<Jugada> getAllPossibilities(Map<Ficha,ArrayList<ArrayList<Ficha>>> pAllPlays)
	{
		ArrayList<Jugada> pJugadas = new ArrayList<Jugada>();

		return pJugadas;
	}

	public ArrayList<Ficha> getRepetFicha(Jugador pPlay)
	{
		ArrayList<Ficha> repetFichas = new ArrayList<Ficha>();
		ArrayList<Ficha> hand_player = pPlay.getMano();
		int largo_mano = hand_player.size()-1;
		
		for (int index=0; index<largo_mano; index++) 
		{
			for (int indey=index+1; indey<=largo_mano; indey++) 
			{	
				if(hand_player.get(index).getFigura()==hand_player.get(indey).getFigura()
					&&hand_player.get(index).getColor()==hand_player.get(indey).getColor())
				{
					repetFichas.add(hand_player.get(indey));
				}
			}
		}
		return repetFichas;
	}

	private boolean cumpleAlgunCriterioDePoda(Jugada pJugada)
	{
		Jugadita parInicial = pJugada.jugaditas.get(0);
		ArrayList<Jugadita> play_to_play = pJugada.jugaditas;
		ArrayList<Ficha> repet_fichas_tres = this.getFullRepetFichas(this.repet_fichas);
		ArrayList<Jugadita> jugada_semicompleta_line = new ArrayList<Jugadita>();
		ArrayList<Jugadita> jugada_semicompleta_colum = new ArrayList<Jugadita>();
		ArrayList<Jugadita> fichas_miss_put_line = new ArrayList<Jugadita>();
		ArrayList<Jugadita> fichas_miss_put_colum = new ArrayList<Jugadita>();
		Boolean esPorFila = pJugada.isLine;
		int derecha = parInicial.y;
		int izquierda = parInicial.y;
		int arriba = parInicial.x;
		int abajo = parInicial.x;

		//Poda #1
		if()
		{

		}
		/*if(pJugada.puntos < SLFSUEQ)
		{
			if(esPorFila == null || esPorFila)
				while(this.tablero.getFichas()[parInicial.x][derecha] != null && derecha < Tablero.MATRIX_SIDE - 1)
					derecha++;// Busca por fila a la derecha algún lugar nulo

				while(this.tablero.getFichas()[parInicial.x][izquierda] != null && izquierda>0)
					izquierda--;

				if(derecha - izquierda == 5) return true;

				jugada_semicompleta_line = this.getPlaySemiCompletaLine(izquierda, derecha, play_to_play);
				fichas_miss_put_line = this.getFichasMissPut(jugada_semicompleta_line);

				if(isItEnterMissPutLine(izquierda, derecha, fichas_miss_put_line))
				{
					//Caso si la jugada semiCompleta es de 3 o 4 fichas
					if(fichas_miss_put_line.size() >= 2 && fichas_miss_put_line.size() <= 3)
					{
						for (Ficha pFicha : repet_fichas_tres) 
						{
							//Escoger la jugada que pueda ponerse esta picha para hacer cuenta que las demas fichas que se puedan
							//poner serian las que debo buscar para hacer una jugada inteligente.	
							if(!isFichaHere(pFicha, fichas_miss_put_line))
							{
								return true;
							}
						}					
					}
				}

				if(repet_fichas_tres.size() != 0 && derecha - izquierda <= 4)
				{
					return true;
				}
			}

			if (esPorFila == null || !esPorFila)
			{
				while(this.tablero.getFichas()[abajo][parInicial.y] != null && abajo < Tablero.MATRIX_SIDE - 1)
					abajo++;

				while(this.tablero.getFichas()[arriba][parInicial.y] != null && arriba > 0)
					arriba--;

				if(abajo - arriba == 5) return true;

				jugada_semicompleta_colum = this.getPlaySemiCompletaColum(abajo, arriba, play_to_play);
				fichas_miss_put_colum = this.getFichasMissPut(jugada_semicompleta_colum);

				if(isItEnterMissPutColum(arriba, abajo, fichas_miss_put_colum))
				{
					if(fichas_miss_put_colum.size() >= 2 && fichas_miss_put_colum.size() <= 3)
					{
						for (Ficha pFicha : repet_fichas_tres) 
						{
							if(!isFichaHere(pFicha, fichas_miss_put_colum))
							{
								return true;
							}
						}					
					}
				}

				if(repet_fichas_tres.size() != 0 && derecha - izquierda <= 4)
				{
					return true;
				}
			}*/
		return false;
	}
	

	public boolean isItEnterMissPutColum(int pleft, int pRight, ArrayList<Jugadita> pMiss_fichas)
	{
		boolean flag = false;

		return flag;
	}

	public boolean isItEnterMissPutLine(int pleft, int pRight, ArrayList<Jugadita> pMiss_fichas)
	{
		boolean flag = false;

		return flag;
	}

	public boolean isFichaHere(Ficha pFicha, ArrayList<Jugadita> pMiss_putFichas)
	{
		boolean flag = false;

		return flag;
	}

	public ArrayList<Jugadita> getFichasMissPut(ArrayList<Jugadita> pPlay_semiCompleta)
	{
		ArrayList<Jugadita> pList = new ArrayList<Jugadita>();


		return pList;
	}

	public ArrayList<Jugadita> getPlaySemiCompletaLine(int pleft, int pRight, ArrayList<Jugadita> pJugada)
	{
		ArrayList<Jugadita> pList = new ArrayList<Jugadita>();


		return pList;
	}

	public ArrayList<Jugadita> getPlaySemiCompletaColum(int pDown, int pUp, ArrayList<Jugadita> pJugada)
	{
		ArrayList<Jugadita> pList = new ArrayList<Jugadita>();


		return pList;
	}

	public Jugada setJugadaWithRepetFicha(ArrayList<Ficha> pList, ArrayList<Jugada> pPlay)
	{
		Jugada pJugada = new Jugada();
		return pJugada;
	}

	public ArrayList<Ficha> getHandWithOutRepet(ArrayList<Ficha> pMano)
	{
		ArrayList<Ficha> mano_fichas = pMano;
		int largo_mano = mano_fichas.size()-1;

		for (int index=0; index<largo_mano; index++) 
		{
			for (int indey=index+1; indey<=largo_mano; indey++) 
			{	
				if(mano_fichas.get(index).getFigura()==mano_fichas.get(indey).getFigura()
					&&mano_fichas.get(index).getColor()==mano_fichas.get(indey).getColor())
				{
					mano_fichas.remove(indey);
				}
			}
		}
		return mano_fichas;
	}

	public ArrayList<Ficha> getFullRepetFichas(Map<Ficha, Integer> pRepet)
	{
		ArrayList<Ficha> pLista = new ArrayList<Ficha>();
		
		for(Entry<Ficha,Integer> lista : pRepet.entrySet()) 
		{
			int total_repet = lista.getValue();
			if(total_repet == 3)
			{
				pLista.add(lista.getKey());
			}
		}
		return pLista;
	}

	public ArrayList<Jugada> getJugadas(Map<Ficha,ArrayList<ArrayList<Ficha>>> grupitos)
	{
		ArrayList<Jugada> todasLasPosiblesJugadasCompletas = new ArrayList<>();

		for(Entry<Integer,Map<Integer,List<Ficha>>> entradaLugar : this.tablero.placesToPlay.entrySet()) 
		{
			for(Integer y : entradaLugar.getKey())
			{
				for(Entry<Ficha,ArrayList<ArrayList<Ficha>>> entradaGrupito : grupitos.entrySet())
				{
					if(this.tablero.getCualesSePuedePoner(entradaLugar.getKey(),y).contains(entradaGrupito.getKey()))
					{
						this.generarArbolDeJugadas(entradaGrupito, todasLasPosiblesJugadasCompletas, entradaLugar.getKey(), y);						
					}
				}
			}
		}
		return todasLasPosiblesJugadasCompletas;
	}

	private void generarArbolDeJugadas(Entry<Ficha,ArrayList<ArrayList<Ficha>>>jugadaDeLaMano,
		List<Jugada>jugadasCompletas,int x, int y)
	{
			this.generarArbolDeJugadas(jugadaDeLaMano.getValue().get(0), jugadaDeLaMano.getKey(), jugadasCompletas, new Jugada(), x, y, null);

			if(!jugadaDeLaMano.getValue().get(1).isEmpty())
				this.generarArbolDeJugadas(jugadaDeLaMano.getValue().get(1), jugadaDeLaMano.getKey(), jugadasCompletas, new Jugada(), x, y, null);
	}

	private void generarArbolDeJugadas(List<Ficha>fichasQueFaltanPorColocar,
					Ficha fichaInicial,List<Jugada>jugadasCompletas, 
					Jugada jugada,int x,int y,Boolean esPorFila)
	{
		//lo que ingresa es sí o sí una jugada válida
		fichasQueFaltanPorColocar.remove(fichaInicial);
		jugada.jugaditas.add(new Jugadita(x, y, fichaInicial));
		jugada.isLine = esPorFila;
		this.tablero.getFichas()[x][y] = fichaInicial;//hacer la jugada de forma hipotética (porque luego se deshace la jugada)
		
		if(fichasQueFaltanPorColocar.isEmpty())
		{ //Si no hacen falta fichas por colocar, este arbol de posible jugada estaría completo, por lo que termina la recursividad
			jugadasCompletas.add(jugada.copy(tablero.getPuntos(jugada)));
		}
		else{
			boolean flag=false;
			for (int indiceFichasPorColocar = 0; indiceFichasPorColocar < fichasQueFaltanPorColocar.size(); indiceFichasPorColocar++)
			{//Para cada ficha
				Ficha fichaPorColocar=fichasQueFaltanPorColocar.get(indiceFichasPorColocar);
				
				if(esPorFila == null || esPorFila)
				{
					int nextY = y;
					
					while(this.tablero.getFichas()[x][nextY] != null && nextY < Tablero.MATRIX_SIDE-1) nextY++;//Busca por fila a la derecha algún lugar nulo
					
					if(this.tablero.getCualesSePuedePoner(x,nextY).contains(fichaPorColocar))
					{
						this.generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugada, x,nextY,true);
						flag=true;
					}	
					nextY = y;

					while(this.tablero.getFichas()[x][nextY] != null && nextY > 0) nextY--;//
					
					if(this.tablero.getCualesSePuedePoner(x,nextY).contains(fichaPorColocar))
					{
						this.generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugada, x,nextY,true);
						flag = true;
					}	
				}
				if (esPorFila == null || !esPorFila)
				{
					int nextX = x;

					while(this.tablero.getFichas()[nextX][y] != null && nextX < Tablero.MATRIX_SIDE-1) nextX++;
					
					if(this.tablero.getCualesSePuedePoner(nextX, y).contains(fichaPorColocar))
					{
						this.generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugada, nextX, y,false);
						flag = true;
					}

					nextX = x;

					while(this.tablero.getFichas()[nextX][y] != null && nextX > 0) nextX--;
					
					if(this.tablero.getCualesSePuedePoner(nextX, y).contains(fichaPorColocar))
					{
						this.generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugada, nextX, y,false);
						flag = true;
					}
				}
			}//Si no encontró lugar para poner 
			if(!flag) jugadasCompletas.add(jugada.copy(tablero.getPuntos(jugada)));
		}
		this.tablero.getFichas()[x][y]=null;
		jugada.jugaditas.remove(jugada.jugaditas.size()-1);
		fichasQueFaltanPorColocar.add(fichaInicial);
	}

	public Map<Ficha, ArrayList<ArrayList<Ficha>>> getPossiblePlaysHand(ArrayList<Ficha> pFichas)
	{
		int cant_man = pFichas.size()-1;
		Map<Ficha, ArrayList<ArrayList<Ficha>>> grupos = new HashMap<Ficha, ArrayList<ArrayList<Ficha>>>();

		for(int pI=0; pI<cant_man; pI++)
		{
			ArrayList<ArrayList<Ficha>> lista_fichas_slices = new ArrayList<ArrayList<Ficha>>();
			ArrayList<Ficha> combination_list_1 = new ArrayList<Ficha>();
			ArrayList<Ficha> combination_list_2 = new ArrayList<Ficha>();

			for(int pJ=0; pJ<cant_man; pJ++)
			{			
				if(!pFichas.get(pI).noCombina(pFichas.get(pJ)))
				{
					if(pFichas.get(pI).getFigura()!=pFichas.get(pJ).getFigura()
						&&pFichas.get(pI).getColor()==pFichas.get(pJ).getColor())
					{
						combination_list_1.add(pFichas.get(pJ));	
					}
					else if(pFichas.get(pI).getFigura()==pFichas.get(pJ).getFigura()
						&&pFichas.get(pI).getColor()!=pFichas.get(pJ).getColor())
					{
						combination_list_2.add(pFichas.get(pJ));
					}
				}
			}
				
			lista_fichas_slices.add(combination_list_1);
			lista_fichas_slices.add(combination_list_2);
			grupos.put(pFichas.get(pI), lista_fichas_slices);

			if(combination_list_1.size() == 2)
			{
				ArrayList<Ficha> combination_list_1_1 = getCombinationList1(combination_list_1);

				lista_fichas_slices.add(combination_list_1_1);
				grupos.put(pFichas.get(pI), lista_fichas_slices);
			}

			if(combination_list_2.size() == 2)
			{
				ArrayList<Ficha> combination_list_1_2 = getCombinationList1(combination_list_2);

				lista_fichas_slices.add(combination_list_1_2);
				grupos.put(pFichas.get(pI), lista_fichas_slices);
			}
		}
		return grupos;
	}

	public ArrayList<Ficha> getCombinationList1(ArrayList<Ficha> pList)
	{
		int contador = 0;
		ArrayList<Ficha> combination = new ArrayList<Ficha>();

		for (int index=1; index >= 0; index--) 
		{
				combination.add(contador, pList.get(index));
				contador = 1;
		}
		return combination;
	}

}


//Si tengo 1, 2 o 3 fichas para hacer play en caso de que si la jugada de la mano que seteo completa una jugada de 4, 3 o 2
//mano: (Cna, Crojo, Tazul)
//tablero: (Cazul, Cmorado)  fichas a poner: (Cna, Crojo)  fichas que faltarian de poner: (Camarillo, Cverde)
//fichas que han salido tres veces de la bolsa: (Camarillo)

//{(Cazul, Cmorado, Cna, Crojo)}  (Camarillo, Cverde)
//repet_fichas_tress = (Camarillo);

//Casos:
//falta evaluar si por cada ficha, prepara un qwirkle fácil pero perpendicular a la jugada 
//también falta considerar si el espacio que falta para el qwirkle fácil está como en medio ?
//y diay, ya que estamos, también si prepara un qwirkle fácil perpendicular pero con el espacio vacío atravezado ?
//también algo que faltaría pero sería ya demasiado (tal vez), es que si la ficha que falta para ese qwirkle fácil
//ya no se puede jugar (porque ya hay en el tablero 3 de esa ficha)
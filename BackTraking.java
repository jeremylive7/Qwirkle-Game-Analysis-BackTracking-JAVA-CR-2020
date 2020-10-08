import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.awt.Point;

public class BackTraking
{
	/**
	 * Score Limit For Set Up Easy Qwirkle
	 */
	private static final int SLFSUEQ=12;
	private Tablero tablero;
	private Set<Ficha>mano;
	private List<Jugada>jugadas;
	private boolean esMejorado;
	public final Random r=new Random();
	private Map<Ficha, Integer> repet_fichas;
	public static final Figura[] FIGURAS = { Figura.CIRCULO, Figura.CUADRADO, Figura.SOL, Figura.TREBOL, Figura.X,
			Figura.ROMBO };
	public static final Color[] COLORES = { Color.AMARILLO, Color.AZUL, Color.NARANJA, Color.MORADO, Color.ROJO,
			Color.VERDE };
			
	BackTraking(Tablero tablero,ArrayList<Ficha>mano) 
	{		
		this.tablero=tablero;
		this.mano=new HashSet<>(mano);
	}
	
	BackTraking(Tablero tablero,ArrayList<Ficha>mano,boolean esMejorado) 
	{		
		this.tablero=tablero;
		this.mano=new HashSet<>(mano);
		this.esMejorado= esMejorado;
		this.repet_fichas = new HashMap<Ficha, Integer>();
		this.repet_fichas = this.startAllCeros();
		this.repet_fichas = this.updateRepetFichasWithHand(updateRepetFichas(this.repet_fichas, tablero.getFichas()), mano);
		initJugadasWithBackTracking();
		
	}
	private void initJugadasWithBackTracking(){
		jugadas=getJugadas(getPossiblePlaysHand(new ArrayList<>(mano)));
/* 		
	Pensando nueva estructura:
		ArrayList<ArrayList<Jugada>> jugadas
		Jugada{
			ArrayList<Jugadita> jugada
			Boolean inLine
			int puntos
		}
		Jugadita{
			int x
			int y
			Ficha f
			isSet estaEnEl_tablero
		}  */
	}
	public Jugada getRespuesta(){
		if(esMejorado)
			return getJugadaMejorado();
		else
			return getJugadaBasico();
	}
	private Jugada getJugadaBasico(){
		if(jugadas.isEmpty())
			System.out.println("Satanásxd");
		jugadas.sort((o1,o2)->Integer.compare(o2.puntos, o1.puntos));
		return jugadas.get(0);
	}
	private Jugada getJugadaMejorado(){
		getJugadaBasico();
		return jugadas.get(0);
	}

	public Map<Ficha, ArrayList<ArrayList<Ficha>>> getPossiblePlaysHand(ArrayList<Ficha> pFichas)
	{
		int cant_man = pFichas.size();
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
			
		}
		

		return grupos;
	}
	public List<Jugada>getJugadas(Map<Ficha,ArrayList<ArrayList<Ficha>>>grupitos){
		List<Jugada>todasLasPosiblesJugadasCompletas=new ArrayList<>();
		for(Point xy : tablero.demeLasPosicionesEnQuePueddoEmpezarJugada()) {
			for(Entry<Ficha,ArrayList<ArrayList<Ficha>>> entradaGrupito:grupitos.entrySet()){
				if(tablero.placesToPlay.get(xy.x).get(xy.y).contains(entradaGrupito.getKey())){
					generarArbolDeJugadas(entradaGrupito, todasLasPosiblesJugadasCompletas, xy.x, xy.y);						
				}
			}
			
		}
		return todasLasPosiblesJugadasCompletas;
	}

	private void generarArbolDeJugadas(Entry<Ficha,ArrayList<ArrayList<Ficha>>>jugadaDeLaMano,List<Jugada>jugadasCompletas,int x, int y){
			generarArbolDeJugadas(new ArrayList<>(jugadaDeLaMano.getValue().get(0)), jugadaDeLaMano.getKey(), jugadasCompletas, new Jugada(), x, y, null);						
			if(!jugadaDeLaMano.getValue().get(1).isEmpty())
				generarArbolDeJugadas(new ArrayList<>(jugadaDeLaMano.getValue().get(1)), jugadaDeLaMano.getKey(), jugadasCompletas, new Jugada(), x, y, null);
	}
	private void generarArbolDeJugadas(List<Ficha>fichasQueFaltanPorColocar,
					Ficha fichaInicial,List<Jugada>jugadasCompletas, 
					Jugada jugada,int x,int y,Boolean esPorFila)
	{
		fichasQueFaltanPorColocar.remove(fichaInicial);
		jugada.jugaditas.add(new Jugadita(x, y, fichaInicial));
		jugada.isLine = esPorFila;
		tablero.getFichas()[x][y]=fichaInicial;//hacer la jugada de forma hipotética (porque luego se deshace la jugada)
		if(fichasQueFaltanPorColocar.isEmpty()){ //Si no hacen falta fichas por colocar, este arbol de posible jugada estaría completo, por lo que termina la recursividad
			
			//Poda 1 se obtiene solo las jugadas que tengan alguna ficha repetida que correponda a una ficha repetida de la mano.
			//**No esta programado.

			//(Se hace al final de todo, en el sort)Poda 2 obtener el de mayor puntaje.

			//Poda algoritmo mejorado.
			//Poda 3 se obteiene las jugadas que tengan un contraste mayor a la hora del siguiewnte turno para que no me afecte de 
			//forma en que el jugador adversario gane mas puntos.
			//**No esta progrmado la manera de pensar que si localiza la ficha que ah salido dos veces, esta debe ser de las fichas que faltan por jugar.
/*			if (this.isItCheapInside(jugada.jugaditas, repet_fichas) && this.esMejorado) 
			{
				jugada.puntos += 7;
				jugadasCompletas.add(jugada.copy(tablero.getPuntos(jugada)));
				System.out.println("-------------------------------------------------------------Entra en la poda 1");
			}else{
				//Ver donde colocar esta línea.
				jugadasCompletas.add(jugada.copy(tablero.getPuntos(jugada)));
			}*/
			
			//Poda 4 Jugada semi completa 3 -> 2 si es de 4 -> 2

			//Poda 5 Cuando solo una jugada doble que le falte una ficha y que yo tenga esta ficha.
			
			
			Boolean esFila = jugada.isLine;
			Jugadita parInicial = jugada.jugaditas.get(0);

			// para el criterio de no ponerle un qwirkle fácil al adversario
			if ((jugada.puntos < SLFSUEQ) && tablero.getFichas()[parInicial.x][parInicial.y].inhabilitado == 0) {
				if (esFila == null || esFila) {
					int derecha = parInicial.y;
					while (tablero.getFichas()[parInicial.x][derecha] != null && derecha < Tablero.MATRIX_SIDE - 1)
						derecha++;// Busca por fila a la derecha algún lugar nulo
					int izquierda = parInicial.y;
					while (tablero.getFichas()[parInicial.x][izquierda] != null && izquierda > 0)
						izquierda--;
					int largo_jugada_tablero = derecha - izquierda;
					if (largo_jugada_tablero == 5)
						jugadasCompletas.add(jugada.copy(tablero.getPuntos(jugada)));
					if (jugada.jugaditas.size() + largo_jugada_tablero == 6){
						jugada.puntos += 6;
						jugada.complete=true;
						jugadasCompletas.add(jugada.copy(tablero.getPuntos(jugada)));
						
						if(jugada.jugaditas.get(0).y == derecha+1){
							tablero.setFichaInhabilitada(parInicial.x, derecha+2);
							tablero.setFichaInhabilitada(parInicial.x, izquierda-1);
						}else if(jugada.jugaditas.get(0).y == izquierda-1)
						{
							tablero.setFichaInhabilitada(parInicial.x, derecha+1);
							tablero.setFichaInhabilitada(parInicial.x, izquierda-2);
							
						}
					}

				}
				if (esFila == null || !esFila) {
					int arriba = parInicial.x;
					while (tablero.getFichas()[arriba][parInicial.y] != null && arriba < Tablero.MATRIX_SIDE - 1)
						arriba++;
					int abajo = parInicial.x;
					while (tablero.getFichas()[abajo][parInicial.y] != null && abajo > 0)
						abajo--;
					int largo_jugada_tablero_upDown = arriba - abajo;
					if (largo_jugada_tablero_upDown == 5)
						jugadasCompletas.add(jugada.copy(tablero.getPuntos(jugada)));
					if (jugada.jugaditas.size() + largo_jugada_tablero_upDown == 6){
						jugada.puntos += 6;
						jugadasCompletas.add(jugada.copy(tablero.getPuntos(jugada)));

						if(jugada.jugaditas.get(0).y == arriba+1){
							tablero.setFichaInhabilitada(parInicial.x, arriba+2);
							tablero.setFichaInhabilitada(parInicial.x, abajo-1);
						}else if(jugada.jugaditas.get(0).y == abajo-1)
						{
							tablero.setFichaInhabilitada(parInicial.x, arriba+1);
							tablero.setFichaInhabilitada(parInicial.x, abajo-2);
							
						}
					}
				}
			}

		}
		else{
			boolean flag=false;
			for (int indiceFichasPorColocar=0;indiceFichasPorColocar<fichasQueFaltanPorColocar.size();indiceFichasPorColocar++){//Para cada ficha
				Ficha fichaPorColocar=fichasQueFaltanPorColocar.get(indiceFichasPorColocar);
				if(esPorFila==null||esPorFila){
					int nextY=y;
					while(tablero.getFichas()[x][nextY]!=null&&nextY<Tablero.MATRIX_SIDE-1)nextY++;//Busca por fila a la derecha algún lugar nulo
					if(tablero.getCualesSePuedePoner(x,nextY).contains(fichaPorColocar)&&true){
						generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugada, x,nextY,true);
						flag=true;
					}	
					nextY=y;
					while(tablero.getFichas()[x][nextY]!=null&&nextY>0)nextY--;//
					if(tablero.getCualesSePuedePoner(x,nextY).contains(fichaPorColocar)&&true){
						generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugada, x,nextY,true);
						flag=true;
					}	
				}if (esPorFila==null||!esPorFila){
					int nextX=x;
					while(tablero.getFichas()[nextX][y]!=null&&nextX<Tablero.MATRIX_SIDE-1)nextX++;
					if(tablero.getCualesSePuedePoner(nextX, y).contains(fichaPorColocar)&&true){
						generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugada, nextX, y,false);
						flag=true;
					}
					nextX=x;
					while(tablero.getFichas()[nextX][y]!=null&&nextX>0)nextX--;
					if(tablero.getCualesSePuedePoner(nextX, y).contains(fichaPorColocar)&&true){
						generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugada, nextX, y,false);
						flag=true;
					}
				}
			}//Si no encontró lugar para poner 
			if(!flag) jugadasCompletas.add(jugada.copy(tablero.getPuntos(jugada)));
		}
		tablero.getFichas()[x][y]=null;
		jugada.jugaditas.remove(jugada.jugaditas.size()-1);
		fichasQueFaltanPorColocar.add(fichaInicial);
	}

	public boolean isItCheapInside(List<Jugadita> pJugada, Map<Ficha, Integer> pList_repets) {
		for (Map.Entry<Ficha, Integer> repets : pList_repets.entrySet()) {
			Ficha ficha = repets.getKey();
			Integer value = repets.getValue();
			if (value >= 2) {
				for (Jugadita pJugadita : pJugada) {
					Ficha pFicha = pJugadita.ficha;
					if (ficha.getFigura() == pFicha.getFigura() && ficha.getColor() == pFicha.getColor()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public ArrayList<Ficha> getFullRepetFichas(Map<Ficha, Integer> pRepet) {
		ArrayList<Ficha> pLista = new ArrayList<Ficha>();

		for (Entry<Ficha, Integer> lista : pRepet.entrySet()) {
			int total_repet = lista.getValue();
			if (total_repet == 3) {
				pLista.add(lista.getKey());
			}
		}
		return pLista;
	}

	public Map<Ficha, Integer> updateRepetFichas(Map<Ficha, Integer> pRepetFichas, Ficha[][] pFichasTablero) {
		Map<Ficha, Integer> pRepet_fichas = pRepetFichas;
		int pFichas_tablero = pFichasTablero[0].length;

		for (int indeX = 0; indeX < pFichas_tablero; indeX++) {
			for (int indeY = 0; indeY < pFichas_tablero; indeY++) {
				for (Map.Entry<Ficha, Integer> repetFichas : pRepet_fichas.entrySet()) {
					Ficha ficha_repet = repetFichas.getKey();
					if (pFichasTablero[indeX][indeY] == null) {
						break;
					} else if (pFichasTablero[indeX][indeY].getFigura() == ficha_repet.getFigura()
							&& pFichasTablero[indeX][indeY].getColor() == ficha_repet.getColor()) {
						Integer value = repetFichas.getValue();
						value++;
						pRepet_fichas.put(ficha_repet, value);
					}
				}
			}
		}
		return pRepetFichas;
	}

	public Map<Ficha, Integer> updateRepetFichasWithHand(Map<Ficha, Integer> pRepetFichas, ArrayList<Ficha> pFicha) {
		Map<Ficha, Integer> pRepet_fichas = pRepetFichas;

		for (Ficha ficha : pFicha) {
			for (Map.Entry<Ficha, Integer> repetFichas : pRepetFichas.entrySet()) {
				Ficha ficha_repet = repetFichas.getKey();
				Integer value = repetFichas.getValue();
				if (ficha == ficha_repet) {
					value += value + 1;
					pRepet_fichas.put(ficha_repet, value);
				}
			}
		}
		return pRepet_fichas;
	}

	public Map<Ficha, Integer> startAllCeros() {
		Map<Ficha, Integer> pList_repet = new HashMap<Ficha, Integer>();
		ArrayList<Ficha> pTotal_fichas = this.getAllCheaps();
		Integer initial_number = 0;

		for (Ficha pFicha : pTotal_fichas) {
			pList_repet.put(pFicha, initial_number);
		}

		return pList_repet;
	}

	public ArrayList<Ficha> getAllCheaps()
	{
		ArrayList<Ficha> lista = new ArrayList<Ficha>();
		for (Figura figura:Qwirkle.FIGURAS)
			for(Color color:Qwirkle.COLORES)
				lista.add(new Ficha(figura,color));
		

		return lista;
	}

}






/*


	public boolean isItEnterMissPutColum(int pleft, int pRight, ArrayList<Jugadita> pMiss_fichas) {
		boolean flag = false;

		return flag;
	}

	public boolean isItEnterMissPutLine(int pleft, int pRight, ArrayList<Jugadita> pMiss_fichas) {
		boolean flag = false;

		return flag;
	}

	public boolean isFichaHere(Ficha pFicha, ArrayList<Jugadita> pMiss_putFichas) {
		boolean flag = false;

		return flag;
	}

	public ArrayList<Jugadita> getFichasMissPut(ArrayList<Jugadita> pPlay_semiCompleta) {
		ArrayList<Jugadita> pList = new ArrayList<Jugadita>();

		return pList;
	}

	public ArrayList<Jugadita> getPlaySemiCompletaLine(int pleft, int pRight, ArrayList<Jugadita> pJugada) {
		ArrayList<Jugadita> pList = new ArrayList<Jugadita>();

		return pList;
	}

	public ArrayList<Jugadita> getPlaySemiCompletaColum(int pDown, int pUp, ArrayList<Jugadita> pJugada) {
		ArrayList<Jugadita> pList = new ArrayList<Jugadita>();

		return pList;
	}

	public Jugada setJugadaWithRepetFicha(ArrayList<Ficha> pList, ArrayList<Jugada> pPlay) {
		Jugada pJugada = new Jugada();
		return pJugada;
	}


*/






	/*


			Boolean esPorFila = jugada.isLine;
			Jugadita parInicial = jugada.jugaditas.get(0);

			// para el criterio de no ponerle un qwirkle fácil al adversario
			if (jugada.puntos < SLFSUEQ) {
				if (esPorFila == null || esPorFila) {
					int derecha = parInicial.y;
					while (tablero.getFichas()[parInicial.x][derecha] != null && derecha < Tablero.MATRIX_SIDE - 1)
						derecha++;// Busca por fila a la derecha algún lugar nulo
					int izquierda = parInicial.y;
					while (tablero.getFichas()[parInicial.x][izquierda] != null && izquierda > 0)
						izquierda--;
					// if(fichasPuestas[tablero.placesToPlay.get(x).get(y)]<3)return true;
					if (derecha - izquierda == 5)
						return;
				}
				if (esPorFila == null || !esPorFila) {
					int arriba = parInicial.x;
					while (tablero.getFichas()[arriba][parInicial.y] != null && arriba < Tablero.MATRIX_SIDE - 1)
						arriba++;
					int abajo = parInicial.x;
					while (tablero.getFichas()[abajo][parInicial.y] != null && abajo > 0)
						abajo--;
					if (arriba - abajo == 5)
						return;
				}
				// falta evaluar si por cada ficha, prepara un qwirkle fácil pero perpendicular
				// a la orientación de la jugada
				// también falta considerar si el espacio que falta para el qwirkle fácil está
				// como en medio
				// y diay, ya que estamos, también si prepara un qwirkle fácil perpendicular
				// pero con el espacio vacío atravezado
				// también algo que faltaría pero sería ya demasiado (tal vez), es que si la
				// ficha que falta para ese qwirkle fácil
				// ya no se puede jugar (porque ya hay en el tablero 3 de esa ficha)
				// si en la jugada perpendicular la ficha que falta la tengo en la mano, es una
				// jugada perfecta
			}
			jugadasCompletas.add(jugada.copy(tablero.getPuntos(jugada)));

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
			
			if(pJugada.puntos < SLFSUEQ)
			{
				if(esPorFila == null || esPorFila)
					while(this.tablero.getFichas()[parInicial.x][derecha] != null && derecha < Tablero.MATRIX_SIDE - 1)
						derecha++;// Busca por fila a la derecha algún lugar nulo

					while(this.tablero.getFichas()[parInicial.x][izquierda] != null && izquierda>0)
						izquierda--;
					// si la ficha que falta no está en "repete_fichas" y la puedo jugar
					//se descarta la jugada
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
				}	
			} 

	 */
																								
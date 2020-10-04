import java.util.*;
import javax.swing.*;

class Qwirkle 
{
	private Jugador jugador1, jugador2, jugador3;
	private Jugador jugadorActual;
	public Tablero tablero;
	private ArrayList<Ficha> bolsa_fichas;
	private int opcion;
	public InterfazDeUsuario frame;
	public static final Figura[] FIGURAS = { Figura.CIRCULO, Figura.CUADRADO, Figura.SOL, Figura.TREBOL, Figura.X,
			Figura.ROMBO };
	public static final Color[] COLORES = { Color.AMARILLO, Color.AZUL, Color.NARANJA, Color.MORADO, Color.ROJO,
			Color.VERDE };
	private static final int CANT_CARTAS_EN_LA_MANO = 6;
	
	public Qwirkle() 
	{
		this.frame = new InterfazDeUsuario(tablero);
		
		this.bolsa_fichas = new ArrayList<>();
		this.fullFichasToBolsa();

		this.jugador1 = new Jugador("Mosco", getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO));
		this.jugador2 = new Jugador("Darky", getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO));
		this.jugador3 = new Jugador("JJ", getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO));
		this.jugadorActual = this.jugador1;
		
		this.tablero = new Tablero();

		this.tablero.startGame();
		this.imprimirTablero();
	}

	public void menu()
	{
		Map<Ficha, Integer> repet_fichas = new HashMap<Ficha, Integer>();
		Map<Ficha, ArrayList<ArrayList<Ficha>>> all_plays = new HashMap<Ficha, ArrayList<ArrayList<Ficha>>>();
		Map<Ficha, Integer> repetFichas_withHand = new HashMap<Ficha, Integer>();
		ArrayList<Ficha> mano_sin_repetidas = new ArrayList<Ficha>(); 
		do{
			System.out.println("\nEs el turno del jugador: " + jugadorActual.getNombre()
				+ ". Su mano es: ");
			this.showMano(jugadorActual);

			repet_fichas = this.startAllCeros();
			repet_fichas = this.updateRepetFichas(repet_fichas, this.tablero.getFichas());
			repetFichas_withHand = this.updateRepetFichasWithHand(repet_fichas, jugadorActual.getMano());
			System.out.println("\nFichas repetidas contando la mano:");
			this.showAllRepetsFichas(repetFichas_withHand);

			mano_sin_repetidas = this.getHandWithOutRepet(jugadorActual.getMano());
			this.imprimirMano(mano_sin_repetidas);

			all_plays = this.getPossiblePlaysHand(mano_sin_repetidas);
			this.showPossiblePlaysHand(all_plays);
			
			turno(this.jugadorActual, repetFichas_withHand, all_plays);
			
			this.imprimirTablero();

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {}
			
			this.jugadorActual = (this.jugadorActual == this.jugador1 ? this.jugador2 : this.jugador1);
		}while(this.bolsa_fichas.size() != 0);
	}

	public Map<Ficha, ArrayList<ArrayList<Ficha>>> getPossiblePlaysHand(ArrayList<Ficha> pFichas) {
		int cant_man = pFichas.size() - 1;
		Map<Ficha, ArrayList<ArrayList<Ficha>>> grupos = new HashMap<Ficha, ArrayList<ArrayList<Ficha>>>();

		for (int pI = 0; pI < cant_man; pI++) {
			ArrayList<ArrayList<Ficha>> lista_fichas_slices = new ArrayList<ArrayList<Ficha>>();
			ArrayList<Ficha> combination_list_1 = new ArrayList<Ficha>();
			ArrayList<Ficha> combination_list_2 = new ArrayList<Ficha>();

			for (int pJ = 0; pJ < cant_man; pJ++) {
				if (!pFichas.get(pI).noCombina(pFichas.get(pJ))) {
					if (pFichas.get(pI).getFigura() != pFichas.get(pJ).getFigura()
							&& pFichas.get(pI).getColor() == pFichas.get(pJ).getColor()) {
						combination_list_1.add(pFichas.get(pJ));
					} else if (pFichas.get(pI).getFigura() == pFichas.get(pJ).getFigura()
							&& pFichas.get(pI).getColor() != pFichas.get(pJ).getColor()) {
						combination_list_2.add(pFichas.get(pJ));
					}
				}
			}

			lista_fichas_slices.add(combination_list_1);
			lista_fichas_slices.add(combination_list_2);
			grupos.put(pFichas.get(pI), lista_fichas_slices);

			if (combination_list_1.size() == 2) {
				ArrayList<Ficha> combination_list_1_1 = getCombinationList1(combination_list_1);

				lista_fichas_slices.add(combination_list_1_1);
				grupos.put(pFichas.get(pI), lista_fichas_slices);
			}

			if (combination_list_2.size() == 2) {
				ArrayList<Ficha> combination_list_1_2 = getCombinationList1(combination_list_2);

				lista_fichas_slices.add(combination_list_1_2);
				grupos.put(pFichas.get(pI), lista_fichas_slices);
			}
		}
		return grupos;
	}

	public ArrayList<Ficha> getCombinationList1(ArrayList<Ficha> pList) {
		int contador = 0;
		ArrayList<Ficha> combination = new ArrayList<Ficha>();

		for (int index = 1; index >= 0; index--) {
			combination.add(contador, pList.get(index));
			contador = 1;
		}
		return combination;
	}

	public Map<Ficha, Integer> startAllCeros()
	{
		Map<Ficha, Integer> pList_repet = new HashMap<Ficha, Integer>();
		ArrayList<Ficha> pTotal_fichas = this.getAllCheaps();
		Integer initial_number = 0;

		for (Ficha pFicha : pTotal_fichas) 
		{
			pList_repet.put(pFicha, initial_number);
		}

		return pList_repet;
	}

	public void showPossiblePlaysHand(Map<Ficha, ArrayList<ArrayList<Ficha>>> pGrupo) {
		for (Map.Entry<Ficha, ArrayList<ArrayList<Ficha>>> entry : pGrupo.entrySet()) {
			Ficha key = entry.getKey();
			ArrayList<ArrayList<Ficha>> value = entry.getValue();
			System.out.println("\nLa ficha: " + fichaToSimbol(key) + ", tiene las siguientes jugadas: ");

			for (ArrayList<Ficha> playList : value) {
				for (Ficha ficha : playList) {
					System.out.println(fichaToSimbol(ficha));
				}
				System.out.println("-");
			}
		}
	}

	public ArrayList<Ficha> getAllCheaps()
	{
		ArrayList<Ficha> total_fichas = new ArrayList<Ficha>();

		for (Figura figura:FIGURAS) 
		{
			for(Color color:COLORES)
			{
				total_fichas.add(new Ficha(figura,color));
			}
		}

		return total_fichas;
	}

	public void showAllRepetsFichas(Map<Ficha, Integer> pList_repets)
	{
		for(Map.Entry<Ficha, Integer> repets : pList_repets.entrySet())
		{    
			Ficha ficha = repets.getKey();  
			Integer value = repets.getValue(); 
			if(value > 0)
			{
				System.out.println(" La ficha: " + fichaToSimbol(ficha) + ", a salido: " + value + ".");
			}
			
		}
	}

	public void procesarJugada(Jugador jugador, Jugada jugada) 
	{
		int cantPuntos = this.tablero.getPuntos(jugada);
	
		this.frame.mostrarJugada(jugada);
		this.tablero.procesarJugada(jugada);
		jugador.procesarJugada(jugada, cantPuntos);
	}

	public void turno(Jugador jugador, Map<Ficha, Integer> pRepetFichas_withHand, Map<Ficha, ArrayList<ArrayList<Ficha>>> pAll_plays) 
	{
		BackTraking algoritmo = new BackTraking(this.tablero, pRepetFichas_withHand, pAll_plays);
 
		if(jugador.getNombre() == "Mosco")
		{
			procesarJugada(jugador, algoritmo.getJugadaBasico());
		}
		else if(jugador.getNombre() == "Darky")
		{
			procesarJugada(jugador, algoritmo.getJugadaBasico());
		}
		else if(jugador.getNombre() == "JJ")
		{
			procesarJugada(jugador, algoritmo.getJugadaMejorado());
		}

		jugador.getMano().addAll(getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO - jugador.getMano().size()));
	}

	public Map<Ficha, Integer> updateRepetFichas(Map<Ficha, Integer> pRepetFichas, Ficha[][] pFichasTablero)
	{
		Map<Ficha, Integer> pRepet_fichas = pRepetFichas;
		int  pFichas_tablero = pFichasTablero[0].length;

		for (int indeX = 0; indeX < pFichas_tablero; indeX++)
		{
			for (int indeY = 0; indeY < pFichas_tablero; indeY++)
			{
				for(Map.Entry<Ficha, Integer> repetFichas : pRepet_fichas.entrySet())
				{
					Ficha ficha_repet = repetFichas.getKey();
					if(pFichasTablero[indeX][indeY] == null)
					{
						break;
					}
					else if(pFichasTablero[indeX][indeY].getFigura()== ficha_repet.getFigura()
					&& pFichasTablero[indeX][indeY].getColor() == ficha_repet.getColor())
					{
						Integer value = repetFichas.getValue();
						value++;
						pRepet_fichas.put(ficha_repet, value);
					}
				} 	
			}
		}
		return pRepetFichas;
	}

	public Map<Ficha, Integer> updateRepetFichasWithHand(Map<Ficha, Integer> pRepetFichas, ArrayList<Ficha> pFicha)
	{
		Map<Ficha, Integer> pRepet_fichas = pRepetFichas;

		for (Ficha ficha : pFicha) 
		{
			for(Map.Entry<Ficha, Integer> repetFichas:pRepetFichas.entrySet())
			{
				Ficha ficha_repet = repetFichas.getKey();  
				Integer value = repetFichas.getValue();
				if(ficha == ficha_repet)
				{
					value += value + 1;
					pRepet_fichas.put(ficha_repet, value);
				}
			} 	
		}
		return pRepet_fichas;
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

	public void updateManoPlayer(Jugador pPlayer, int pLargoSet)
	{
		ArrayList<Ficha> fichas = getFichasDeLaBolsa(pLargoSet);

		for (Ficha pFicha : fichas) 
		{			
			pPlayer.setFichaMano(pFicha);	
		}
		
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

	public ArrayList<Ficha>getFichasDeLaBolsa(int cantFichas)
	{
		ArrayList<Ficha>out=new ArrayList<>();
	
		while(cantFichas-->0)
			out.add(popRandomFicha());
		return out;
	}
	
	public Ficha popRandomFicha()
	{
		return bolsa_fichas.remove((int)(Math.random()*(bolsa_fichas.size()-1)));
	}

	public String getSimboloColor(Color c)
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

	public String getSimboloFigura(Figura f)
	{
		switch(f){
			case CIRCULO:
				return "O";
			case CUADRADO:
				return "C";
			case ROMBO:
				return "R";
			case SOL:
				return "S";
			case TREBOL:
				return "T";
			case X:
				return "X";
		}
		return "";
	}

	public String fichaToSimbol(Ficha ficha)
	{
		if(ficha==null)return "---";
		return getSimboloFigura(ficha.getFigura())+getSimboloColor(ficha.getColor());
	}
	
	public void showBolsaFichas()
	{
		System.out.println(bolsa_fichas.size());		
		for (int i=0; i<bolsa_fichas.size(); i++) {
			System.out.print( fichaToSimbol(bolsa_fichas.get(i))+", ");
		}
	}

	public void showMano(Jugador pJugador)
	{
		String out="[ ";
		for (Ficha ficha:pJugador.getMano())
		{
			out+= fichaToSimbol(ficha)+", ";
		}
		System.out.println(out+"]");
	}

	public void showPtsJugador(Jugador pJugador)
	{
		System.out.println("Puntos del jugador " + pJugador.getNombre() + " : ");
		System.out.println(pJugador.getScore());
	}

	public void imprimirTablero()
	{
		String out="";
		for(int i=0;i<Tablero.MATRIX_SIDE;i++){
			for(int j=0;j<Tablero.MATRIX_SIDE;j++)
				out+="# "+fichaToSimbol(getTablero().getFichas()[i][j]) + " #";
			out+="\n";
		}
		System.out.println("\n"+out);
	}

	public void imprimirMano(ArrayList<Ficha> pMano)
	{
		String out="[ ";
		for (Ficha ficha : pMano)
		{
			out+= fichaToSimbol(ficha)+", ";
		}
		System.out.println(out+"]");
	}

	public void imprimirJugadaTotales(ArrayList<ArrayList<Ficha>> pJugadasTotales)
	{
		for (ArrayList<Ficha> pList : pJugadasTotales)
		{
			String out="\n[ ";
			for (Ficha ficha : pList) 
			{
				out+= fichaToSimbol(ficha)+", ";	
			}
			System.out.println(out+"]");
		}
	}

	public void fullFichasToBolsa()
	{	
		for (Figura figura:FIGURAS) 
		{
			for (int index=0; index<3; index++) 
			{
				for(Color color:COLORES)
				{
					this.bolsa_fichas.add(new Ficha(figura,color));
				}
			}
		}
	}
	
	public void mostrarVentana()
	{
		frame.setVisible(true);
	}
	
	public Tablero getTablero() 
	{
		return tablero;
	}
	
	public void setTablero(Tablero tablero) 
	{
		this.tablero = tablero;
	}

	/*
			EJEMPLO #1
	*/
	public void printTablero(){
		System.out.println(getTablero().toString());
	}
}






/*	
	//Lleno matriz en blanco
	public void fullMatrizEmpty()
	{
		//Matriz 6x6
		for (int i=0; i<this.dimencion_inicial; i++) {
			this.fichas_totales = new Fichas();
			for (int j=0; j<this.dimencion_inicial; j++) {
				this.ficha = new Ficha();
				this.fichas_totales.setFichasFila(ficha);
			}
			this.tablero.setOneFilaFichas(fichas_totales);		
		}

	}
*/
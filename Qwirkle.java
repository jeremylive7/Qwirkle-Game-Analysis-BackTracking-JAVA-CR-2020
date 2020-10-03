import java.util.*;
import javax.swing.*;

class Qwirkle 
{
	private Jugador jugador1, jugador2, jugador3;
	private Jugador jugadorActual;
	private Tablero tablero;
	private List<Ficha> bolsa_fichas;
	private int opcion;
	public InterfazDeUsuario frame;
	public static final Figura[] FIGURAS = { Figura.CIRCULO, Figura.CUADRADO, Figura.SOL, Figura.TREBOL, Figura.X,
			Figura.ROMBO };
	public static final Color[] COLORES = { Color.AMARILLO, Color.AZUL, Color.NARANJA, Color.MORADO, Color.ROJO,
			Color.VERDE };
	private static final int CANT_CARTAS_EN_LA_MANO = 6;

	private Map<Ficha, Integer> repet_fichas = new HashMap<Ficha, Integer>();
	
	public Qwirkle() 
	{
		this.frame = new InterfazDeUsuario(tablero);
		
		this.bolsa_fichas = new ArrayList<>();
		this.fullFichasToBolsa();

		this.jugador1 = new Jugador("Jeremy", getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO));
		this.jugador2 = new Jugador("Edgerik", getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO));
		this.jugador3 = new Jugador("Roberto", getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO));
		this.jugadorActual = this.jugador1;
		
		this.tablero = new Tablero();
		this.tablero.llenarTableroConEjemplo();
		this.imprimirTablero();
	}

	public void menu()
	{
		Ficha[][] fichas_tablero = this.tablero.getFichas();
		Map<Ficha, Integer> repetFichas_withHand = this.repet_fichas;

		Jugada playToSet = new Jugada();
		ArrayList<Ficha> fichas_repet_hand = new ArrayList<Ficha>();
		ArrayList<Ficha> hand_player = jugadorActual.getMano();

		int largo_nueva_mano = 0;
		boolean esRepetido = false;

		while(this.opcion < 4){

			do{
				JOptionPane.showMessageDialog(this.frame, "Es el turno del jugador " + jugadorActual.getNombre());
				//Muestro mano del jugador
				this.showMano(jugadorActual);
				
				//Obtengo el # de la opcion
				this.opcion = Integer.parseInt(JOptionPane.showInputDialog("1. Seleccionar mi jugada"
					+ "\n"
					+ "2. Solicitas salir del Juego al final de la ronda de turnos."));

				if(opcion==1)
				{
					System.out.println("Elegiste seleccionar mi jugada");

					System.out.println("Mano original: ");
					this.showMano(jugadorActual);

					fichas_repet_hand = this.getRepetFicha(jugadorActual);
					if(fichas_repet_hand.size() > 0)
					{
						esRepetido = true;
					}

					this.repet_fichas = this.updateRepetFichas(repetFichas_withHand, fichas_tablero);
					repetFichas_withHand = this.updateRepetFichasWithHand(repetFichas_withHand, hand_player, fichas_tablero);

					BackTraking algoritmo = new BackTraking(tablero, hand_player, repetFichas_withHand);
					//Inicio de tiempo
					playToSet = algoritmo.getJugadaMejorado();//Empieza turno, selecciono mi jugada
					//Fin de tiempo.

					this.procesarJugada(jugadorActual, playToSet);//Coloco jugada en el tablero
					this.showPtsJugador(jugadorActual);//Imprimo pts
				
					largo_nueva_mano = 6 - playToSet.jugaditas.size();
					if(esRepetido)
					{
						largo_nueva_mano+=fichas_repet_hand.size();
					}

					if(this.bolsa_fichas.size() >= largo_nueva_mano)
					{
						this.updateManoPlayer(jugadorActual, largo_nueva_mano);
					}
				}
				else	//Salir del juego
					break;
						
				jugadorActual=(jugadorActual==jugador1?jugador2:jugador1);

			}while(true);
		}
	}

	private boolean procesarJugada(Jugador jugador, Jugada jugada) 
	{
		int cantPuntos = this.tablero.getPuntos(jugada);
		
		if (cantPuntos == 0)
			return false;

		this.frame.mostrarJugada(jugada);
		this.tablero.procesarJugada(jugada);
		jugador.procesarJugada(jugada, cantPuntos);

		return true;
	}

/*	private void turno(Jugador jugador) 
	{
		BackTraking algoritmo = new BackTraking(tablero,jugador.getMano());
		while (true)
			if (!procesarJugada(jugador, algoritmo.getJugadaBasico()))
				// procesar jugada devuelve false si no se puede procesar la jugada
				// Y devuelve true si la procesa con éxito
				break;
		jugador.getMano().addAll(getFichasDeLaBolsa(CANT_CARTAS_EN_LA_MANO - jugador.getMano().size()));
	}

	public void jugadorHumanoHizoSuJugada() 
	{

		// juega algoritmo básico
		turno(jugador2);
		// juega algoritmo mejorado
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		} // Para que haya un tiempo entre las jugadas de cada uno
		turno(jugador3);
		
	}*/

	public Map<Ficha, Integer> updateRepetFichas(Map<Ficha, Integer> pRepetFichas, Ficha[][] pFichasTablero)
	{
		Map<Ficha, Integer> pRepet_fichas = pRepetFichas;
		int  pFichas_tablero = pFichasTablero[0].length;

		for (int indeX = 0; indeX < pFichas_tablero; indeX++)
		{
			for (int indeY = 0; indeY < pFichas_tablero; indeY++)
			{
				for(Map.Entry<Ficha, Integer> repetFichas:pRepetFichas.entrySet())
				{
					Ficha ficha_repet = repetFichas.getKey();  
		    		Integer value = repetFichas.getValue();
		    		if(pFichasTablero[indeX][indeY] == ficha_repet)
		    		{
		    			value++;
		    			pRepet_fichas.put(ficha_repet, value);
		    		}
				} 	
			}
		}
		return pRepetFichas;
	}

	public Map<Ficha, Integer> updateRepetFichasWithHand(Map<Ficha, Integer> pRepetFichas, ArrayList<Ficha> pFicha, Ficha[][] pFichasTablero)
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
	    			value++;
	    			pRepet_fichas.put(ficha_repet, value);
	    		}
			} 	
		}
		pRepetFichas = updateRepetFichas(pRepetFichas, pFichasTablero);
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

	private String getSimboloColor(Color c)
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

	private String getSimboloFigura(Figura f)
	{
		switch(f){
			case CIRCULO:
				return "O";
			case CUADRADO:
				return "■";
			case ROMBO:
				return "÷";
			case SOL:
				return "§";
			case TREBOL:
				return "¤";
			case X:
				return "×";
		}
		return "";
	}

	private String fichaToSimbol(Ficha ficha)
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
		String out="\n[ ";
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

	public void showPossiblePlaysHand(Map<Ficha, ArrayList<ArrayList<Ficha>>> pGrupo)
	{
		for(Map.Entry<Ficha, ArrayList<ArrayList<Ficha>>> entry:pGrupo.entrySet())
		{    
    	Ficha key = entry.getKey();  
    	ArrayList<ArrayList<Ficha>> value = entry.getValue(); 
    	System.out.println("\nLa ficha: " + fichaToSimbol(key) 
    		+ ", tiene las siguientes jugadas: ");
    	
    	for (ArrayList<Ficha> playList : value) 
			{
				for (Ficha ficha : playList) 
				{
					System.out.println(fichaToSimbol(ficha));		
				}
				System.out.println("-");
			}
		}
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
		String out="\n[ ";
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
					bolsa_fichas.add(new Ficha(figura,color));
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
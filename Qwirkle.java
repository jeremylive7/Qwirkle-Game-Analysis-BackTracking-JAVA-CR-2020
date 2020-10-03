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

	private Map<Ficha, Integer> repet_fichas = new HashMap<Ficha, Integer>();
	
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
		this.tablero.llenarTableroConEjemplo();
		this.imprimirTablero();
	}

	public void menu()
	{
		do{
			JOptionPane.showMessageDialog(this.frame, "Es el turno del jugador: " + jugadorActual.getNombre()
				+ "\nSu mano es: ");
			this.showMano(jugadorActual);

			this.repet_fichas = this.updateRepetFichas(this.repet_fichas, this.tablero.getFichas());
			
			Map<Ficha, Integer> repetFichas_withHand = this.updateRepetFichasWithHand(this.repet_fichas, jugadorActual.getMano(), this.tablero.getFichas());
			
			//imprimir repets..

			//turno(this.jugadorActual, repetFichas_withHand);
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {}
			
			this.jugadorActual = (this.jugadorActual == this.jugador1 ? this.jugador2 : this.jugador1);
		}while(this.bolsa_fichas.size() != 0);
	}

	public void showrepets(Map<Ficha, ArrayList<ArrayList<Ficha>>> pGrupo)
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

	public void procesarJugada(Jugador jugador, Jugada jugada) 
	{
		int cantPuntos = this.tablero.getPuntos(jugada);
	
		this.frame.mostrarJugada(jugada);
		this.tablero.procesarJugada(jugada);
		jugador.procesarJugada(jugada, cantPuntos);
	}

	public void turno(Jugador jugador, Map<Ficha, Integer> pRepetFichas_withHand) 
	{
		BackTraking algoritmo = new BackTraking(this.tablero, jugador.getMano(), pRepetFichas_withHand);
 
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
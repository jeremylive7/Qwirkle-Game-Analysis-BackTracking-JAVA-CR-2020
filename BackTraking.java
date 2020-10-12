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
	private ArrayList<Ficha> fichas_repetidasMano;

	BackTraking(Tablero tablero,ArrayList<Ficha>pMano,boolean esMejorado) 
	{		
		this.tablero=tablero;
		this.mano=new HashSet<>(pMano);
		this.esMejorado=esMejorado;

		this.fichas_repetidasMano = getRepetsHand(pMano);

		initJugadasWithBackTracking();
		
	}

	private void initJugadasWithBackTracking(){
		jugadas=getJugadas(getPossiblePlaysHand(new ArrayList<>(mano)));
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

		jugadas.sort((o1,o2)->Integer.compare(o2.puntos, o1.puntos));
		return jugadas.get(0);
	}

	public List<Jugada>getJugadas(Map<Ficha,ArrayList<ArrayList<Ficha>>>grupitos)
	{
		List<Jugada>todasLasPosiblesJugadasCompletas=new ArrayList<>();
		for(Point xy : tablero.demeLasPosicionesEnQuePueddoEmpezarJugada()) {
				for(Entry<Ficha,ArrayList<ArrayList<Ficha>>> entradaGrupito:grupitos.entrySet()){
					if(tablero.getCualesSePuedePoner(xy.x,xy.y).contains(entradaGrupito.getKey())){
						generarArbolDeJugadas(entradaGrupito, todasLasPosiblesJugadasCompletas, xy.x, xy.y);						
					}
				}
		}
		return todasLasPosiblesJugadasCompletas;
	}

	private void generarArbolDeJugadas(Entry<Ficha,ArrayList<ArrayList<Ficha>>>jugadaDeLaMano,
			List<Jugada>jugadasCompletas,int x, int y)
	{
		Ficha[][] t = tablero.getFichas();
		Ficha f = jugadaDeLaMano.getKey();

		if((t[x][y-1]!=null&&t[x][y+1]!=null&&
				t[x][y-1].noCombina(t[x][y]))||
				t[x-1][y]!=null&&t[x+1][y]!=null&&
				t[x+1][y].noCombina(t[x][y])){
			System.out.println("Satanás es muy grande, porque aquí puede que haga jugadas de más de 6 xdxdxd:c");
			return;
		}

		if(!jugadaDeLaMano.getValue().get(1).isEmpty()&&(
				(t[x][y-1]==null&&t[x][y+1]==null)||
				(t[x][y-1]!=null&&t[x][y-1].figura==f.figura)||
				(t[x][y+1]!=null&&t[x][y+1].figura==f.figura))){
			generarArbolDeJugadas(new ArrayList<>(jugadaDeLaMano.getValue().get(1)),f, jugadasCompletas, new Jugada(), x, y, true);
		} else if(!jugadaDeLaMano.getValue().get(0).isEmpty()&&(
				(t[x][y-1]==null&&t[x][y+1]==null)||
				(t[x][y-1]!=null&&t[x][y-1].color==f.color)||(t[x][y+1]!=null&&t[x][y+1].color==f.color))){
			generarArbolDeJugadas(new ArrayList<>(jugadaDeLaMano.getValue().get(0)), jugadaDeLaMano.getKey(), jugadasCompletas, new Jugada(), x, y, true);
		}
		
		if(!jugadaDeLaMano.getValue().get(1).isEmpty()&&(
				(t[x-1][y]==null&&t[x+1][y]==null)||
				(t[x-1][y]!=null&&t[x-1][y].figura==f.figura)||
				(t[x+1][y]!=null&&t[x+1][y].figura==f.figura))){
			generarArbolDeJugadas(new ArrayList<>(jugadaDeLaMano.getValue().get(1)), f, jugadasCompletas, new Jugada(), x, y, false);
		} else if(!jugadaDeLaMano.getValue().get(0).isEmpty()&&(
				(t[x-1][y]==null&&t[x+1][y]==null)||
				(t[x-1][y]!=null&&t[x-1][y].color==f.color)||
				(t[x+1][y]!=null&&t[x+1][y].color==f.color))){
			generarArbolDeJugadas(new ArrayList<>(jugadaDeLaMano.getValue().get(0)), f, jugadasCompletas, new Jugada(), x, y, false);
		}
		
		if(jugadaDeLaMano.getValue().get(0).isEmpty()&&jugadaDeLaMano.getValue().get(1).isEmpty()){
			generarArbolDeJugadas(new ArrayList<>(), f, jugadasCompletas, new Jugada(), x, y, null);
		}
		
	}

	public boolean isItChipInside(List<Ficha> pJugada, Map<Ficha, Integer> pList_repets) {
		for (Map.Entry<Ficha, Integer> repets : pList_repets.entrySet()) {
			Ficha ficha = repets.getKey();
			Integer value = repets.getValue();
			if (value >= 2) {
				for (Ficha pFicha : pJugada) {
					if (ficha.getFigura() == pFicha.getFigura() && ficha.getColor() == pFicha.getColor()) {
						return true;
					}
				}
			}
		}
		return false;
	}


	private void generarArbolDeJugadas(List<Ficha>fichasQueFaltanPorColocar,
					Ficha fichaInicial,List<Jugada>jugadasCompletas, 
					Jugada jugada,int x,int y,Boolean esPorFila)
	{
		fichasQueFaltanPorColocar.remove(fichaInicial);
		jugada.jugaditas.add(new Jugadita(x, y, fichaInicial));
		jugada.isLine = esPorFila;
		this.tablero.getFichas()[x][y]=fichaInicial;

		if(fichasQueFaltanPorColocar.isEmpty())
		{	
			/*
	
					Poda #1

						Esta poda trata de identificar las jugadas que tengan al menos una ficha igual a una ficha que este repetida en la mano del jugador actual.

			*/

			if (isItInJugadaRepetFicha(this.fichas_repetidasMano, jugada.jugaditas))
			{
				jugadasCompletas.add(jugada.copy(this.tablero.getPuntos(jugada) + 50));	
			}else if(this.isItChipInside(getMissingChips(y, x, esPorFila, jugada), ))
			{
			/*

					Poda #2

						De las fichas que faltan para que se logre un Qwirkle, ya haya salido dos veces, esa jugada es inteligente.

			*/



			}else 
			{
				jugadasCompletas.add(jugada.copy(this.tablero.getPuntos(jugada)));	

			}


			
			//List<Ficha> missing_chips = getMissingChips(y, x, esPorFila, jugada);
			//showArray(missing_chips);
			

















		}
		else{
			boolean flag=false;
			int derecha=y;
			int izquierda=y;
			int arriba=x;
			int abajo=x;

			if(esPorFila==null||esPorFila){
				while(this.tablero.getFichas()[x][derecha]!=null&&derecha<Tablero.MATRIX_SIDE-1){
					Ficha f=this.tablero.getFichas()[x][derecha];
					fichasQueFaltanPorColocar.removeIf(j->
						j.noCombina(f)
					);
					derecha++;//aquí falta algo--->eliminar de la jugada las que se están "saltando"
				}
				while(this.tablero.getFichas()[x][izquierda]!=null&&izquierda>0){
					Ficha f=this.tablero.getFichas()[x][izquierda];
					fichasQueFaltanPorColocar.removeIf(j->j.noCombina(f));
					izquierda--;
				}
			}if (esPorFila==null||!esPorFila){
				while(this.tablero.getFichas()[arriba][y]!=null&&arriba<Tablero.MATRIX_SIDE-1){
					Ficha f=this.tablero.getFichas()[arriba][y];
					fichasQueFaltanPorColocar.removeIf(j->j.noCombina(f));
					arriba++;
				}
				while(this.tablero.getFichas()[abajo][y]!=null&&abajo>0){
					Ficha f=this.tablero.getFichas()[abajo][y];
					fichasQueFaltanPorColocar.removeIf(j->j.noCombina(f));
					abajo--;
				}
			}
			for (int indiceFichasPorColocar=0;indiceFichasPorColocar<fichasQueFaltanPorColocar.size();indiceFichasPorColocar++)
			{
				Ficha fichaPorColocar = fichasQueFaltanPorColocar.get(indiceFichasPorColocar);

				if(esPorFila==null||esPorFila){
					if(this.tablero.getCualesSePuedePoner(x,derecha).contains(fichaPorColocar)){
						generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugada, x,derecha,true);
						flag=true;
					}	
					if(this.tablero.getCualesSePuedePoner(x,izquierda).contains(fichaPorColocar)){
						generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugada, x,izquierda,true);
						flag=true;
					}	
				}if (esPorFila==null||!esPorFila){
					if(this.tablero.getCualesSePuedePoner(arriba, y).contains(fichaPorColocar)){
						generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugada, arriba, y,false);
						flag=true;
					}
					if(this.tablero.getCualesSePuedePoner(abajo, y).contains(fichaPorColocar)){
						generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugada, abajo, y,false);
						flag=true;
					}
				}
			}//Si no encontró lugar para poner 
			if(!flag) jugadasCompletas.add(jugada.copy(this.tablero.getPuntos(jugada)));
		}
		tablero.getFichas()[x][y]=null;
		jugada.jugaditas.remove(jugada.jugaditas.size()-1);
		fichasQueFaltanPorColocar.add(fichaInicial);
	}

	public Boolean isItInJugadaRepetFicha(ArrayList<Ficha> pFichas_repets, List<Jugadita> pJugada)
	{
		for (Ficha pRepetFicha : pFichas_repets) 
		{
			if(pJugada.contains(pRepetFicha))
			{
				return true;
			}			
		}	
		return false;
	}

	public List<Ficha>getCualesFaltan(List<Jugadita>fichasDeLaJugada)
	{
		List<Ficha>losQueSePuedenPoner=new ArrayList<>(this.tablero.todasLasFichas);
	
		for(Jugadita f1: fichasDeLaJugada){
			for(int k=0;k<losQueSePuedenPoner.size();){
				if(f1.ficha.noCombina(losQueSePuedenPoner.get(k)))
					losQueSePuedenPoner.remove(k);
				else k++;
			}
		}
		return losQueSePuedenPoner;

	}

	public List<Ficha> getMissingChips(int y, int x, Boolean pEsPorFila, Jugada pJugada)
	{
		List<Jugadita> jugada_tablero = new ArrayList<Jugadita>();
		ArrayList<Ficha> fichas_faltantes = new ArrayList<Ficha>();
		List<Ficha> fichas_puedo_poner_verdad = new ArrayList<Ficha>();
		int derecha = y;
		int izquierda = y;
		int arriba = x;
		int abajo = x;

		if(pEsPorFila == null || pEsPorFila)
		{
			while(this.tablero.getFichas()[x][derecha] !=  null && derecha < Tablero.MATRIX_SIDE - 1)
				derecha++;

			while(this.tablero.getFichas()[x][izquierda] != null && izquierda>0)
				izquierda--;

			for(int i=izquierda+1; i < derecha; i++)
			{
				if(this.tablero.getFichas()[x][i] != null)
				{
					Jugadita pJugadita = new Jugadita(x, i, this.tablero.getFichas()[x][i]);
					jugada_tablero.add(pJugadita);
				}
			}
			

			for (Jugadita pPlay: pJugada.jugaditas) 
			{
				if(pPlay.x != x &&
					pPlay.y != y)
				{
					Jugadita pJugadita = new Jugadita(pPlay.x, pPlay.y, pPlay.ficha);
					jugada_tablero.add(pJugadita);
				}
			}

			jugada_tablero.sort((o1,o2)->Integer.compare(o1.y, o2.y));

			showArrayPlay(jugada_tablero);
			
			fichas_puedo_poner_verdad = getCualesFaltan(jugada_tablero);
		}

		if (pEsPorFila == null || !pEsPorFila)
		{
			while(this.tablero.getFichas()[abajo][y] != null && abajo < Tablero.MATRIX_SIDE - 1)
				abajo++;

			while(this.tablero.getFichas()[arriba][y] != null && arriba > 0)
				arriba--;

			for(int j=arriba+1; j < abajo; j++)
			{
				if(this.tablero.getFichas()[j][y] != null)
				{
					Jugadita pJugadita = new Jugadita(j, y, this.tablero.getFichas()[j][y]);
					jugada_tablero.add(pJugadita);
				}	
			}

			for (Jugadita pPlay: pJugada.jugaditas) 
			{
				if(x != pPlay.x && y != pPlay.y)
				{
					Jugadita pJugadita = new Jugadita(pPlay.x, pPlay.y, pPlay.ficha);
					jugada_tablero.add(pJugadita);
				}	
			}

			jugada_tablero.sort((o1,o2)->Integer.compare(o1.x, o2.x));

			//showArrayPlay(jugada_tablero);
			
			fichas_puedo_poner_verdad = getCualesFaltan(jugada_tablero);
		}


		

		return fichas_puedo_poner_verdad;
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
		else return "...";
		
	}
	private String getSimboloFigura(Figura f)
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
		return "....";
	}
	private String fichaToSimbol(Ficha ficha)
	{
		if(ficha==null)return "---";
		return getSimboloFigura(ficha.getFigura())+getSimboloColor(ficha.getColor());
	}
	
	public void imprimirList(List<Ficha> pMano)
	{
		String out="\nlista de fichas que puedo jugar - [ ";
		for (Ficha ficha : pMano)
		{
			out+= fichaToSimbol(ficha)+", ";
		}
		System.out.println(out+"]");
	}

	public void imprimirMano(Set<Ficha> pMano)
	{
		String out="\nMano sin repetidas [ ";
		for (Ficha ficha : pMano)
		{
			out+= fichaToSimbol(ficha)+", ";
		}
		System.out.println(out+"]");
	}
	

	public void showArray(List<Ficha> pMano)
	{
		String out="\nFichas que faltan por jugar -- [ ";
		for (Ficha ficha : pMano)
		{
			out+= fichaToSimbol(ficha)+", ";
		}
		System.out.println(out+"]");
	}

	public void showArrayPlay(List<Jugadita> pMano)
	{
		String out="\njugada del tablero con la que juego en ese turno suponiendo -- [ ";
		for (Jugadita jugadita : pMano)
		{
			System.out.println("x: "+ jugadita.x + " y: " + jugadita.y);
			out+= fichaToSimbol(jugadita.ficha)+", ";
		}
		System.out.println(out+"]");
	}

	public void showJugadas(List<Jugada> pJugadas)
	{
		for (Jugada pPlay : pJugadas) {
			showArrayPlay(pPlay.jugaditas);
		}
	}


	public ArrayList<Ficha> getRepetsHand(ArrayList<Ficha> pMano)
	{
		int largo_mano = pMano.size()-1;
		ArrayList<Ficha> mano_fichas = pMano;
		ArrayList<Ficha> fichas_repetidas_mano = new ArrayList<Ficha>();

		for (int index=0; index<largo_mano; index++) 
		{
			for (int indey=index+1; indey<=largo_mano; indey++) 
			{	
				if(mano_fichas.get(index).getFigura()==mano_fichas.get(indey).getFigura()
					&&mano_fichas.get(index).getColor()==mano_fichas.get(indey).getColor())
				{
					fichas_repetidas_mano.add(mano_fichas.get(indey));
				}
			}
		}
		return fichas_repetidas_mano;
	}

}
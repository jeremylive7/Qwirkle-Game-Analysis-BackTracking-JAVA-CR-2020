import java.util.*;
import java.util.Map.Entry;
import java.awt.Point;


class Tablero
{
	private class JugadaCompleta {
		public List<Jugada>jugadas=new ArrayList<>();
		public int puntos=0;
		public boolean isLine = false;
		public JugadaCompleta copy(){
			JugadaCompleta n=new JugadaCompleta();
			n.jugadas=new ArrayList<>(this.jugadas);
			n.puntos=this.puntos;
			n.isLine=isLine;
			return n;
		}
	}
	static final int MATRIX_SIDE=6;
	private final Ficha[][] fichas;
	private Map<Integer,Set<Integer>>placesToPlay;

	//Constructor
	public Tablero() 
	{		
		fichas = new Ficha[MATRIX_SIDE][MATRIX_SIDE];	
		placesToPlay= new HashMap<>();

	}
	public void llenarTableroConEjemplo(){
		final int mitadDeLaMatriz=MATRIX_SIDE/2;
		meterFichaEnXY(new Ficha(Figura.ROMBO,Color.AZUL), mitadDeLaMatriz-1, mitadDeLaMatriz);
		meterFichaEnXY(new Ficha(Figura.ROMBO,Color.ROJO), mitadDeLaMatriz, mitadDeLaMatriz);
		meterFichaEnXY(new Ficha(Figura.ROMBO,Color.VERDE), mitadDeLaMatriz, mitadDeLaMatriz-1);
	}
	public static void main(String[]args){
		Tablero tablero=new Tablero();
		tablero.llenarTableroConEjemplo();
		ArrayList<Ficha>mano=new ArrayList<>();
		mano.addAll(Arrays.asList(
			new Ficha(Figura.ROMBO,Color.ROJO),
			new Ficha(Figura.ROMBO,Color.VERDE),
			new Ficha(Figura.SOL,Color.VERDE)));
		List<JugadaCompleta>jugadasCompletas=tablero.getJugadas(tablero.getPossiblePlaysHand(mano));
		tablero.setPointsAllPlays(jugadasCompletas);
		jugadasCompletas.sort((o1,o2)->Integer.compare(o2.puntos, o1.puntos));
		System.out.println("XDXD");
	}
	//set de puntos en todas las jugadas.
	public void setPointsAllPlays(List<JugadaCompleta> pFullsPlays)
	{
		int listSize = pFullsPlays.size() - 1;

		for(int index = 0; index <= listSize; index++)
		{
			JugadaCompleta fullPlay = pFullsPlays.get(index);
			List<Jugada> plays = fullPlay.jugadas;
			int totalPoints = 0;

			for (Jugada jugada : plays) 
			{
				if (index < listSize) {
					totalPoints += getCantPuntosXColumn(jugada.x, jugada.y, jugada.ficha);
				} 
				else if (index == listSize) {
					totalPoints += getCantPuntos(jugada.x, jugada.y, jugada.ficha);
				}	
			}

			pFullsPlays.get(index).puntos = totalPoints;
		}
	}
	//get cantidad de puntos de columna.
	public int getCantPuntosXColumn(final int x, final int y, final Ficha ficha) {
		if (fichas[x][y] != null)
			return 0;
		// recorrer desde x,y para las cuatro direcciones contando la cantidad de fichas
		// sin repetirse, si se repite es 0 tod
		int puntos = 0;
		final ArrayList<Ficha> hileraHorizontal = new ArrayList<>(), hileraVertical = new ArrayList<>();
		int inicioHilera = x;
		int finHilera = x;
		while (inicioHilera > 0) {
			if (fichas[inicioHilera - 1][y] == null)
				break;
			else
				inicioHilera--;
		}
		while (finHilera < MATRIX_SIDE - 1) {
			if (fichas[finHilera + 1][y] == null)
				break;
			else
				finHilera++;
		}
		if (finHilera - inicioHilera >= 6)
			return 0;
		for (int i = inicioHilera; i <= finHilera; i++) {
			if (i == x)
				hileraVertical.add(ficha);
			else if (ficha.noCombina(fichas[i][y]))
				return 0;
			else
				hileraVertical.add(fichas[i][y]);
		}
		inicioHilera = finHilera = y;
		while (inicioHilera > 0) {
			if (fichas[x][inicioHilera - 1] == null)
				break;
			else
				inicioHilera--;
		}
		while (finHilera < MATRIX_SIDE - 1) {
			if (fichas[x][finHilera + 1] == null)
				break;
			else
				finHilera++;
		}
		if (finHilera - inicioHilera >= 6)
			return 0;
		for (int i = inicioHilera; i <= finHilera; i++) {
			if (i == y)
				hileraHorizontal.add(ficha);
			else if (ficha.noCombina(fichas[x][i]))
				return 0;
			else
				hileraHorizontal.add(fichas[x][i]);
		}
		// buscar repetidos
		Map<Figura, Map<Color, Boolean>> mapaParaEncontrarRepetidos = new HashMap<>();
		for (final Ficha f : hileraHorizontal) {
			if (mapaParaEncontrarRepetidos.containsKey(f.figura)
					&& mapaParaEncontrarRepetidos.get(f.figura).containsKey(f.color))
				return 0;
			mapaParaEncontrarRepetidos.putIfAbsent(f.figura, new HashMap<>());
			mapaParaEncontrarRepetidos.get(f.figura).put(f.color, true);
		}
		mapaParaEncontrarRepetidos = new HashMap<>();
		for (final Ficha f : hileraVertical) {
			if (mapaParaEncontrarRepetidos.containsKey(f.figura)
					&& mapaParaEncontrarRepetidos.get(f.figura).containsKey(f.color))
				return 0;
			mapaParaEncontrarRepetidos.putIfAbsent(f.figura, new HashMap<>());
			mapaParaEncontrarRepetidos.get(f.figura).put(f.color, true);
		}
		//JUST CONT THE COLUM
		puntos += hileraVertical.size() - 1;
		if (hileraVertical.size() == 6)
			puntos += 6;

		return puntos;
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
	public List<JugadaCompleta>getJugadas(Map<Ficha,ArrayList<ArrayList<Ficha>>>grupitos){
		List<JugadaCompleta>todasLasPosiblesJugadasCompletas=new ArrayList<>();
		for(Entry<Integer,Set<Integer>> entradaLugar:placesToPlay.entrySet()){
			for(Integer y:entradaLugar.getValue()){
				for(Entry<Ficha,ArrayList<ArrayList<Ficha>>> entradaGrupito:grupitos.entrySet()){
					if(getCualesPuedoPoner(entradaLugar.getKey(),y).contains(entradaGrupito.getKey())){
						generarArbolDeJugadas(entradaGrupito, todasLasPosiblesJugadasCompletas, entradaLugar.getKey(), y);						
					}
				}
			}
		}
		return todasLasPosiblesJugadasCompletas;
	}
	private void generarArbolDeJugadas(Entry<Ficha,ArrayList<ArrayList<Ficha>>>jugadaDeLaMano,
		List<JugadaCompleta>jugadasCompletas,int x, int y){
			generarArbolDeJugadas(jugadaDeLaMano.getValue().get(0), jugadaDeLaMano.getKey(), jugadasCompletas, new JugadaCompleta(), x, y, null);						
			if(!jugadaDeLaMano.getValue().get(1).isEmpty())
				generarArbolDeJugadas(jugadaDeLaMano.getValue().get(1), jugadaDeLaMano.getKey(), jugadasCompletas, new JugadaCompleta(), x, y, null);
	}
	private void generarArbolDeJugadas(List<Ficha>fichasQueFaltanPorColocar,
					Ficha fichaInicial,List<JugadaCompleta>jugadasCompletas, 
					JugadaCompleta jugada,int x,int y,Boolean esPorFila){
		//lo que ingresa es sí o sí una jugada válida
		fichasQueFaltanPorColocar.remove(fichaInicial);
		jugada.jugadas.add(new Jugada(x, y, fichaInicial));
		jugada.puntos+=getCantPuntos(x, y, fichaInicial);
		fichas[x][y]=fichaInicial;//hacer la jugada de forma hipotética (porque luego se deshace la jugada)
		if(fichasQueFaltanPorColocar.isEmpty()){ //Si no hacen falta fichas por colocar, este arbol de posible jugada estaría completo, por lo que termina la recursividad
			jugadasCompletas.add(jugada.copy());
		}
		else{
			boolean flag=false;
			for (int indiceFichasPorColocar=0;indiceFichasPorColocar<fichasQueFaltanPorColocar.size();indiceFichasPorColocar++){//Para cada ficha
				Ficha fichaPorColocar=fichasQueFaltanPorColocar.get(indiceFichasPorColocar);
				if(esPorFila==null||esPorFila){
					int nextY=y;
					while(fichas[x][nextY]!=null&&nextY<MATRIX_SIDE-1)nextY++;//Busca por fila a la derecha algún lugar nulo
					if(getCualesPuedoPoner(x,nextY).contains(fichaPorColocar)){
						generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugada, x,nextY,true);
						flag=true;
					}	
					nextY=y;
					while(fichas[x][nextY]!=null&&nextY<MATRIX_SIDE-1)nextY--;//
					if(getCualesPuedoPoner(x,nextY).contains(fichaPorColocar)){
						generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugada, x,nextY,true);
						flag=true;
					}	
				}if (esPorFila==null||!esPorFila){
					int nextX=x;
					while(fichas[nextX][y]!=null&&nextX<MATRIX_SIDE-1)nextX++;
					if(getCualesPuedoPoner(nextX, y).contains(fichaPorColocar)){
						generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugada, nextX, y,false);
						flag=true;
					}
					nextX=x;
					while(fichas[nextX][y]!=null&&nextX<MATRIX_SIDE-1)nextX--;
					if(getCualesPuedoPoner(nextX, y).contains(fichaPorColocar)){
						generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugada, nextX, y,false);
						flag=true;
					}
				}
			}//Si no encontró lugar para poner 
			if(!flag) jugadasCompletas.add(jugada.copy());
		}
		fichas[x][y]=null;
		jugada.jugadas.remove(jugada.jugadas.size()-1);
		jugada.isLine = esPorFila;
		fichasQueFaltanPorColocar.add(fichaInicial);
	}

	
	public List<Ficha>getCualesPuedoPoner(int x,int y){
		if(fichas[x][y]!=null)return new ArrayList<>();
		List<Ficha>todasLasFichas=new ArrayList<>();
		for (Figura figura:Qwirkle.FIGURAS)
			for(Color color:Qwirkle.COLORES)
				todasLasFichas.add(new Ficha(figura,color));
		int inicioHilera=x;
		int finHilera=x;
		while(inicioHilera>0){
			if(fichas[inicioHilera-1][y]==null)
				break;
			else inicioHilera--;
		}
		while(finHilera<MATRIX_SIDE-1){
			if(fichas[finHilera+1][y]==null)
				break;
			else finHilera++;
		}
		if(finHilera - inicioHilera>=6)return new ArrayList<>();
		for(int i=inicioHilera;i<=finHilera;i++){
			if(i!=x) {
				for(int j=0;j<todasLasFichas.size();){
					if(todasLasFichas.get(j).noCombina(fichas[i][y])){
						todasLasFichas.remove(j);
					} else j++;
				}
			}
		}
		if(todasLasFichas.isEmpty())return todasLasFichas;
		inicioHilera=finHilera=y;
		while(inicioHilera>0){
			if(fichas[x][inicioHilera-1]==null)
				break;
			else inicioHilera--;
		}
		while(finHilera<MATRIX_SIDE-1){
			if(fichas[x][finHilera+1]==null)
				break;
			else finHilera++;
		}
		if(finHilera - inicioHilera>=6)return new ArrayList<>();
		for(int i=inicioHilera;i<=finHilera;i++){
			if(i!=y){
				for(int j=0;j<todasLasFichas.size();){
					if(todasLasFichas.get(j).noCombina(fichas[x][i])){
						todasLasFichas.remove(j);
					}else j++;
				}
			}
		}
		return todasLasFichas;		
	}
	
	public int getCantPuntos(final int x,final int y,final Ficha ficha){
		if(fichas[x][y]!=null)return 0;
		//recorrer desde x,y para las cuatro direcciones contando la cantidad de fichas sin repetirse, si se repite es 0 tod
		int puntos=0;
		final ArrayList<Ficha>hileraHorizontal=new ArrayList<>(),hileraVertical=new ArrayList<>();
		int inicioHilera=x;
		int finHilera=x;
		while(inicioHilera>0){
			if(fichas[inicioHilera-1][y]==null)
				break;
			else inicioHilera--;
		}
		while(finHilera<MATRIX_SIDE-1){
			if(fichas[finHilera+1][y]==null)
				break;
			else finHilera++;
		}
		if(finHilera - inicioHilera>=6)return 0;
		for(int i=inicioHilera;i<=finHilera;i++){
			if(i==x) hileraVertical.add(ficha);
			else if(ficha.noCombina(fichas[i][y]))
				return 0;
			else
				hileraVertical.add(fichas[i][y]);
		}
		inicioHilera=finHilera=y;
		while(inicioHilera>0){
			if(fichas[x][inicioHilera-1]==null)
				break;
			else inicioHilera--;
		}
		while(finHilera<MATRIX_SIDE-1){
			if(fichas[x][finHilera+1]==null)
				break;
			else finHilera++;
		}
		if(finHilera - inicioHilera>=6)return 0;
		for(int i=inicioHilera;i<=finHilera;i++){
			if(i==y)hileraHorizontal.add(ficha);
			else if(ficha.noCombina(fichas[x][i]))return 0;
			else hileraHorizontal.add(fichas[x][i]);
		}
		//buscar repetidos
		Map<Figura,Map<Color,Boolean>>mapaParaEncontrarRepetidos=new HashMap<>();
		for(final Ficha f:hileraHorizontal){
			if(mapaParaEncontrarRepetidos.containsKey(f.figura)&&mapaParaEncontrarRepetidos.get(f.figura).containsKey(f.color))
				return 0;
			mapaParaEncontrarRepetidos.putIfAbsent(f.figura,new HashMap<>());
			mapaParaEncontrarRepetidos.get(f.figura).put(f.color, true);
		}
		mapaParaEncontrarRepetidos=new HashMap<>();
		for(final Ficha f:hileraVertical){
			if(mapaParaEncontrarRepetidos.containsKey(f.figura)&&mapaParaEncontrarRepetidos.get(f.figura).containsKey(f.color))
				return 0;
			mapaParaEncontrarRepetidos.putIfAbsent(f.figura,new HashMap<>());
			mapaParaEncontrarRepetidos.get(f.figura).put(f.color, true);
		}
		puntos+=hileraHorizontal.size()-1;
		puntos+=hileraVertical.size()-1;
		if(hileraVertical.size()==6)puntos+=6;
		if(hileraHorizontal.size()==6)puntos+=6;
		return puntos;
	}
	public Ficha[][]getFichas(){
		return fichas;
	}
	boolean meterFichaEnXY(final Ficha ficha,final int x,final int y){
		if(x<0||y<0||x>=MATRIX_SIDE||y>=MATRIX_SIDE)
			return false;
		placesToPlay.getOrDefault(x,new HashSet<>()).remove(y);
		if(x+1<MATRIX_SIDE&&fichas[x+1][y]==null)
			placesToPlay.computeIfAbsent(x+1, k->new HashSet<>()).add(y);
		if(x>0&&fichas[x-1][y]==null)
			placesToPlay.computeIfAbsent(x-1, k->new HashSet<>()).add(y);
		if(y+1<MATRIX_SIDE&&fichas[x][y+1]==null)
			placesToPlay.computeIfAbsent(x, k->new HashSet<>()).add(y+1);
		if(y>0&&fichas[x][y-1]==null)
			placesToPlay.computeIfAbsent(x, k->new HashSet<>()).add(y-1);
		fichas[x][y]=ficha;
		return true;
	}
	@Override
	public String toString(){
		String out="";
		for(int i=0;i<MATRIX_SIDE;i++){
			for(int j=0;j<MATRIX_SIDE;j++)
				out+="# "+(fichas[i][j]!=null?fichas[i][j].toString():"---")+" #";
			out+="\n";
		}
		return out;
	}
/*
	//Metodos de get y set
	public List<Fichas> getTablero()
	{
		return this.fichas;
	}
*/
}
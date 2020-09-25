import java.util.*;

import java.awt.Point;


class Tablero
{
	private class JugadaCompleta {
		public List<Jugada>jugadas=new ArrayList<>();
		public int puntos=0;
		public JugadaCompleta copy(){
			JugadaCompleta n=new JugadaCompleta();
			n.jugadas=new ArrayList<>(this.jugadas);
			n.puntos=this.puntos;
			return n;
		}

		public void deshacerUltimaJugada() {
			Jugada ultima=jugadas.remove(jugadas.size()-1);
			puntos-=ultima
		}
	}
	static final int MATRIX_SIDE=15;
	private final Ficha[][] fichas;
	private List<Point>lugaresDondeSePuedeJugar;

	//Constructor
	public Tablero() 
	{		
		fichas = new Ficha[MATRIX_SIDE][MATRIX_SIDE];	
		lugaresDondeSePuedeJugar= new ArrayList<>();

	}

	private void generarArbolDeJugadas(List<Ficha>fichasQueFaltanPorColocar,Ficha fichaInicial,List<JugadaCompleta>jugadasCompletas, JugadaCompleta jugadaActual,int x,int y,Boolean esPorFila){
		//lo que ingresa es sí o sí una jugada válida
		fichasQueFaltanPorColocar.remove(fichaInicial);
		fichas[x][y]=fichaInicial;//hacer la jugada de forma hipotética (porque luego se deshace la jugada)
		jugadaActual.jugadas.add(new Jugada(x, y, fichaInicial));
		jugadaActual.puntos+=getCantPuntos(x, y, fichaInicial);
		if(fichasQueFaltanPorColocar.isEmpty()){ //Si no hacen falta fichas por colocar, este arbol de posible jugada estaría completo, por lo que termina la recursividad
			jugadasCompletas.add(jugadaActual.copy());
		}
		else{
			boolean flag=false;
			for (int indiceFichasPorColocar=0;indiceFichasPorColocar<fichasQueFaltanPorColocar.size();indiceFichasPorColocar++){//Para cada ficha
				Ficha fichaPorColocar=fichasQueFaltanPorColocar.get(indiceFichasPorColocar);
				if(esPorFila==null||esPorFila){
					int nextY=y;
					while(fichas[x][nextY]!=null)nextY++;//Busca por fila a la derecha algún lugar nulo
					if(getCualesPuedoPoner(x,nextY).contains(fichaPorColocar)){
						generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugadaActual, x,nextY,true);
						fichasQueFaltanPorColocar.add(indiceFichasPorColocar,fichaPorColocar);
						flag=true;
					}	
					nextY=y;
					while(fichas[x][nextY]!=null)nextY--;//
					if(getCualesPuedoPoner(x,nextY).contains(fichaPorColocar)){
						generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugadaActual, x,nextY,true);
						fichasQueFaltanPorColocar.add(indiceFichasPorColocar,fichaPorColocar);
						flag=true;
					}	
				}if (esPorFila==null||!esPorFila){
					int nextX=x;
					while(fichas[nextX][y]!=null)nextX++;
					if(getCualesPuedoPoner(nextX, y).contains(fichaPorColocar)){
						generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugadaActual, nextX, y,false);
						fichasQueFaltanPorColocar.add(indiceFichasPorColocar,fichaPorColocar);
						flag=true;
					}
					nextX=x;
					while(fichas[nextX][y]!=null)nextX--;
					if(getCualesPuedoPoner(nextX, y).contains(fichaPorColocar)){
						generarArbolDeJugadas(fichasQueFaltanPorColocar, fichaPorColocar, jugadasCompletas, jugadaActual, nextX, y,false);
						fichasQueFaltanPorColocar.add(indiceFichasPorColocar,fichaPorColocar);
						flag=true;
					}
				}
			}//Si no encontró lugar para poner 
			if(!flag) jugadasCompletas.add(jugadaActual.copy());
		}
		fichas[x][y]=null;
		jugadaActual.jugadas.remove(jugadaActual.jugadas.size()-1);
		jugadaActual.puntos-=getCantPuntos(x, y, fichaInicial);
	}

	public List<List<Jugada>>getJugadas(Map<Ficha,List<Ficha>[]>grupitos){
		List<JugadaCompleta>todasLasPosiblesJugadasCompletas=new ArrayList<>();
		JugadaCompleta tmpJCompleta;
		Jugada jugadaActual;
		for(Point lugarQuePuedoJugar:lugaresDondeSePuedeJugar){
			for(Ficha fichaInicial:grupitos.keySet()){
				if(getCualesPuedoPoner(lugarQuePuedoJugar.x,lugarQuePuedoJugar.y).contains(fichaInicial)){
					tmpJCompleta=new JugadaCompleta();
					generarArbolDeJugadas(grupitos.get(fichaInicial)[0], fichaInicial, todasLasPosiblesJugadasCompletas, tmpJCompleta, lugarQuePuedoJugar.x, lugarQuePuedoJugar.y, null);
					generarArbolDeJugadas(grupitos.get(fichaInicial)[1], fichaInicial, todasLasPosiblesJugadasCompletas, tmpJCompleta, lugarQuePuedoJugar.x, lugarQuePuedoJugar.y, null);
				}
			}
		}
		return null;
	}
	public List<Point>getLugaresDondeSePuedeJugar(){
		return lugaresDondeSePuedeJugar;
	}
	public List<Ficha>getCualesPuedoPoner(int x,int y){
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
		//recorrer desde x,y para las cuatro direcciones contando la cantidad de fichas sin repetirse, si se repite es 0 todo
		int puntos=1;
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
		if(hileraHorizontal.size()>1)puntos+=hileraHorizontal.size()-1;
		if(hileraVertical.size()>1)puntos+=hileraVertical.size()-1;
		if(hileraVertical.size()==6)puntos+=6;
		if(hileraHorizontal.size()==6)puntos+=6;
		return puntos;
	}
	public Ficha[][]getFichas(){
		return fichas;
	}
	public void llenarTableroConEjemplo(){
		final int mitadDeLaMatriz=MATRIX_SIDE/2;
		meterFichaEnXY(new Ficha(Figura.ROMBO,Color.ROJO), mitadDeLaMatriz, mitadDeLaMatriz);
		meterFichaEnXY(new Ficha(Figura.CIRCULO,Color.ROJO), mitadDeLaMatriz+1, mitadDeLaMatriz);
		meterFichaEnXY(new Ficha(Figura.CUADRADO,Color.ROJO),mitadDeLaMatriz+2, mitadDeLaMatriz);
		meterFichaEnXY(new Ficha(Figura.SOL,Color.AZUL), mitadDeLaMatriz-1, mitadDeLaMatriz+1);
	}
	boolean meterFichaEnXY(final Ficha ficha,final int x,final int y){
		if(x<0||y<0||x>=MATRIX_SIDE||y>=MATRIX_SIDE)
			return false;
		for(int i=0;i<lugaresDondeSePuedeJugar.size();i++){
			Point point=lugaresDondeSePuedeJugar.get(i);
			if(point.x==x&&point.y==y){
				lugaresDondeSePuedeJugar.remove(i);
				break;
			}
		}
		if(x+1<MATRIX_SIDE&&fichas[x+1][y]==null)lugaresDondeSePuedeJugar.add(new Point(x+1,y));
		if(x>0&&fichas[x-1][y]==null)lugaresDondeSePuedeJugar.add(new Point(x-1,y));
		if(y+1<MATRIX_SIDE&&fichas[x][y+1]==null)lugaresDondeSePuedeJugar.add(new Point(x,y+1));
		if(y>0&&fichas[x][y-1]==null)lugaresDondeSePuedeJugar.add(new  Point(x,y-1));
		fichas[x][y]=ficha;
		return true;
	}
	@Override
	public String toString(){
		String out="";
		for(int i=0;i<MATRIX_SIDE;i++){
			for(int j=0;j<MATRIX_SIDE;j++)
				out+="# "+(fichas[i][j]!=null?fichas[i][j].toString():"\t\t\t")+" #";
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
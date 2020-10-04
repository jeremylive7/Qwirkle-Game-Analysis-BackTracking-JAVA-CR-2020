import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.awt.Point;

class Tablero
{
	static final int MATRIX_SIDE=21;
	private final Ficha[][] fichas;
	Map<Integer,Map<Integer,List<Ficha>>>placesToPlay;
	Set<Point>placesWithAnTokkenOnTheSide;
	protected static List<Ficha>todasLasFichas;

	//Constructor
	public Tablero() 
	{		
		fichas = new Ficha[MATRIX_SIDE][MATRIX_SIDE];	
		this.placesToPlay= new HashMap<>();
		placesWithAnTokkenOnTheSide=new HashSet<>();
		if(todasLasFichas==null){
			todasLasFichas=new ArrayList<>();
			for (Figura figura:Qwirkle.FIGURAS)
				for(Color color:Qwirkle.COLORES)
					todasLasFichas.add(new Ficha(figura,color));
		}

	}
	public void procesarJugada(Jugada jugada) {
		for(Jugadita par:jugada.jugaditas){
			meterFichaEnXY(par.ficha, par.x, par.y);
		}
	}

	public Map<Integer,Map<Integer,List<Ficha>>> setPlacesToPlay(Ficha ficha, int x, int y)
	{
		Map<Integer,List<Ficha> places = new HashMap<Integer,List<Ficha>();
		List<Ficha> lista_jugada = new ArrayList<Ficha>();
		lista_jugada.add(ficha);
		places.put(y, lista_jugada)
		this.placesToPlay.put(x, places);
	}

	public boolean meterFichaEnXY(final Ficha ficha,final int x,final int y){
		if(x<0||y<0||x>=MATRIX_SIDE||y>=MATRIX_SIDE)
			return false;
		this.setPlacesToPlay(ficha, x, y);
		this.updatePlacesToPlay(ficha, x, y);
		fichas[x][y]=ficha;
		return true;
	}
	

	public List<Ficha>getCualesSePuedePoner(int x,int y){
		return placesToPlay.computeIfAbsent(x, k->new HashMap<>()).computeIfAbsent(y,k->new ArrayList<>(todasLasFichas));
	}
	private void updatePlacesToPlay(Ficha f,int x,int y){
		int[]xs={x+1,x-1,x,x};
		int[]ys={y,y,y+1,y-1};
		for(int i=0;i<4;i++)
			if(xs[i]>0&&xs[i]<MATRIX_SIDE&&//"x" no se sale del borde
				ys[i]>0&&ys[i]<MATRIX_SIDE&&//"y" no se sale del borde
				fichas[xs[i]][ys[i]]==null)//y no hay una ficha ya ahí
				eliminarLasQueNoCoinciden(getCualesSePuedePoner(xs[i],ys[i]),f);
	}
	private void eliminarLasQueNoCoinciden(List<Ficha>fichas,Ficha f){
		for(int i=0;i<fichas.size();){
			if(f.noCombina(fichas.get(i)))
				fichas.remove(i);
			else i++;
		}
	}
	public int getPuntos(Jugada jugada){//Tengo dudas con esta función.
		jugada.puntos=0;
		for(Jugadita jugadita:jugada.jugaditas){
			if(jugada.isLine!=null&&jugada.isLine.booleanValue())
				jugada.puntos+=contarColumna(jugadita);			
			else 
				jugada.puntos+=contarFila(jugadita);			
		}
		if(jugada.isLine==null||jugada.isLine.booleanValue())
			jugada.puntos+=contarFila(jugada.jugaditas.get(0));
		else
			jugada.puntos+=contarColumna(jugada.jugaditas.get(0));
		return jugada.puntos;
	}
	private int contarColumna(Jugadita jugadita){
		int inicioHilera,finHilera;
		finHilera=inicioHilera=jugadita.x;
		while(inicioHilera>0){
			if(fichas[inicioHilera-1][jugadita.y]==null)
				break;
			else inicioHilera--;
		}
		while(finHilera<MATRIX_SIDE-1){
			if(fichas[finHilera+1][jugadita.y]==null)
				break;
			else finHilera++;
		}
		return finHilera-inicioHilera+1;
	}
	private int contarFila(Jugadita jugadita){
		int inicioHilera,finHilera;
		inicioHilera=finHilera=jugadita.y;
		while(inicioHilera>0){
			if(fichas[jugadita.x][inicioHilera-1]==null)
				break;
			else inicioHilera--;
		}
		while(finHilera<MATRIX_SIDE-1){
			if(fichas[jugadita.x][finHilera+1]==null)
				break;
			else finHilera++;
		}
		return finHilera-inicioHilera+1;
	}
	
	public Ficha[][]getFichas(){
		return fichas;
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
	public void llenarTableroConEjemplo(){
		final int mitadDeLaMatriz=MATRIX_SIDE/2;
		meterFichaEnXY(new Ficha(Figura.ROMBO,Color.AZUL), mitadDeLaMatriz-1, mitadDeLaMatriz);
		meterFichaEnXY(new Ficha(Figura.ROMBO,Color.ROJO), mitadDeLaMatriz, mitadDeLaMatriz);
		meterFichaEnXY(new Ficha(Figura.ROMBO,Color.VERDE), mitadDeLaMatriz, mitadDeLaMatriz-1);
	}
	public List<Point> demeLasPosicionesEnQuePueddoEmpezarJugada() {
		List<Point>out=new ArrayList<>();
		for(Integer i:placesToPlay.keySet()){
			for(Integer j:placesToPlay.get(i).keySet()){
				Point p=new Point(i,j);
				if(sePuedeJugarEsteLugar(p))
					out.add(p);
			}
		}
		return out;
	}

	private boolean sePuedeJugarEsteLugar(Point p) {
		return 
		fichas[p.x][p.y]==null&&
		(
			fichas[p.x-1][p.y]!=null||
			fichas[p.x+1][p.y]!=null||
			fichas[p.x][p.y-1]!=null||
			fichas[p.x][p.y+1]!=null
		);
	}
}
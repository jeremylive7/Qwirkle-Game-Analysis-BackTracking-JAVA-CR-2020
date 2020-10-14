import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.awt.Point;

class Tablero
{
	static final int MATRIX_SIDE=40;
	private final Ficha[][] fichas;
	Map<Integer,Map<Integer,List<Ficha>>>placesToPlay;
	Set<Point>placesWithAnTokkenOnTheSide;
	public static List<Ficha>todasLasFichas;

	//Constructor
	public Tablero() 
	{		
		fichas = new Ficha[MATRIX_SIDE][MATRIX_SIDE];	
		this.placesToPlay= new HashMap<>();
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
/*
	public Map<Integer,Map<Integer,List<Ficha>>> setPlacesToPlay(Ficha ficha, int x, int y)
	{
		Map<Integer,List<Ficha>> places = new HashMap<Integer,List<Ficha>>();
		List<Ficha> lista_jugada = new ArrayList<Ficha>();
		lista_jugada.add(ficha);
		places.put(y, lista_jugada);
		this.placesToPlay.put(x, places);
	}
*/
	public boolean meterFichaEnXY(final Ficha ficha,final int x,final int y){
		if(x<0||y<0||x>=MATRIX_SIDE||y>=MATRIX_SIDE)
			return false;
		fichas[x][y]=ficha;
		updatePlacesToPlay(x, y);
		getCualesSePuedePoner(x, y).clear();
		return true;
	}
	
	public List<Ficha>getCualesSePuedePoner(int x,int y,Jugada jugada){
		
		return null;
	}

	public List<Ficha>getCualesSePuedePoner(int x,int y){
		return placesToPlay.computeIfAbsent(x, k->new HashMap<>()).computeIfAbsent(y,k->new ArrayList<>(todasLasFichas));
	}
	private void updatePlacesToPlay(int x,int y){
		
		//para todos los de abajo, hacer intersección con arriba
		//para todos los de la izquierda, hacer intersección con derecha
		//para todos los de arriba, hacer intersección con abajo
		//para todos los de la derecha, hacer intersección con el de la izquierda
		//eliminarLosQueNoCoinciden(getCualesPuedoPoner(izquierda),todosLosDeDerecha);
		for(int a=x;a>=0&&a<Tablero.MATRIX_SIDE;a--)//mientras "x" no se sale del borde
			if(fichas[a-1][y]==null){
				for(int b=x;b>=0&&b<Tablero.MATRIX_SIDE;b++)
					if(fichas[b+1][y]==null){
						for(int g=a;g<=b;g++){
							for(int k=0;k<getCualesSePuedePoner(a-1,y).size();){
								if(fichas[g][y].noCombina(getCualesSePuedePoner(a-1,y).get(k)))
									getCualesSePuedePoner(a-1,y).remove(k);
								else k++;
							}
							for(int k=0;k<getCualesSePuedePoner(b+1,y).size();){
								if(fichas[g][y].noCombina(getCualesSePuedePoner(b+1,y).get(k)))
									getCualesSePuedePoner(b+1,y).remove(k);
								else k++;
							}
						}
						break;
					}
				break;
			}
		for(int a=y;a>=0&&a<Tablero.MATRIX_SIDE;a--)
			if(fichas[x][a-1]==null){
				for(int b=y;b>=0&&b<Tablero.MATRIX_SIDE;b++)
					if(fichas[x][b+1]==null){
						for(int w=a;w<=b;w++){
							for(int k=0;k<getCualesSePuedePoner(x,a-1).size();){
								if(fichas[x][w].noCombina(getCualesSePuedePoner(x,a-1).get(k)))
									getCualesSePuedePoner(x,a-1).remove(k);
								else k++;
							}
							for(int k=0;k<getCualesSePuedePoner(x,b+1).size();){
								if(fichas[x][w].noCombina(getCualesSePuedePoner(x,b+1).get(k)))
									getCualesSePuedePoner(x,b+1).remove(k);
								else k++;
							}
						}
						break;
					}
				break;
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
			jugada.puntos+=contarFila(jugada.jugaditas.iterator().next());
		else
			jugada.puntos+=contarColumna(jugada.jugaditas.iterator().next());
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
		if(finHilera-inicioHilera==5)return 12;
		return finHilera-inicioHilera+(finHilera-inicioHilera>0?1:0);
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
		if(finHilera-inicioHilera==5)return 12;
		return finHilera-inicioHilera+(finHilera-inicioHilera>0?1:0);
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
	public void ponerPrimeraFicha(){
		final int mitadDeLaMatriz=MATRIX_SIDE/2;
		meterFichaEnXY(new Ficha(Figura.ROMBO,Color.ROJO), mitadDeLaMatriz, mitadDeLaMatriz);
	}
	public void ejemploQwirkleDe84Puntos(){
		for(int i=10;i<16;i++){
			for (int j=11;j<16;j++){
				meterFichaEnXY(new Ficha(Qwirkle.FIGURAS[(j+i)%6],Color.ROJO), i, j);
			}
		}
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
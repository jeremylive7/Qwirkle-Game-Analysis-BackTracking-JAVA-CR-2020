import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
		return true;
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
		int[]dx={-1,1,0,0};
		int[]dy={0,0,-1,1};
		for(int i=0;i<4;i++){// para cada vector dirección (xs[i],ys[i])
			for(int a=x;a>=0&&a<Tablero.MATRIX_SIDE;a+=dx[i]){//mientras "x" no se sale del borde
				for(int b=y;b>=0&&b<Tablero.MATRIX_SIDE;b+=dy[i]){//mientras "y" no se sale del borde
					if(a+dx[i]>0&&a+dx[i]<MATRIX_SIDE&&//si el siguiente x no se sale del borde
					   b+dy[i]>0&&b+dy[i]<MATRIX_SIDE&&// y el siguiente y no se sale del borde
					   fichas[a+dx[i]][b+dy[i]]==null){//y no hay una ficha ya en el siguiente lugar de la matriz de fichas
						//aquí entonces ya está en un borde
						if(i%2==0){
							//Aquí puede estar en el borde inferior ya sea de x o de y
							//entonces busco el borde superior y lo guardo
							int tx=x;
							int ty=y;
							while(fichas[tx+dx[i+1]][ty+dy[i+1]]!=null){
								tx+=dx[i+1];
								ty+=dy[i+1];
							}
							//itero desde el borde inferior hasta el borde superior
							if(i==0){
								for(int g=a;g<=tx;g+=dx[i+1]){
									for(int k=0;k<getCualesSePuedePoner(a+dx[i],b).size();){
										if(fichas[g][b].noCombina(getCualesSePuedePoner(a+dx[i],b).get(k)))
											getCualesSePuedePoner(a+dx[i],b).remove(k);
										else k++;
									}
									for(int k=0;k<getCualesSePuedePoner(tx+dx[i+1], b).size();){
										if(fichas[g][b].noCombina(getCualesSePuedePoner(tx+dx[i+1],b).get(k)))
											getCualesSePuedePoner(tx+dx[i+1],b).remove(k);
										else k++;
									}
								}
							} else {
								for(int w=b;w<=ty;w+=dy[i+1]){
									for(int k=0;k<getCualesSePuedePoner(a,b+dy[i]).size();){
										if(fichas[a][w].noCombina(getCualesSePuedePoner(a,b+dy[i]).get(k)))
											getCualesSePuedePoner(a,b+dy[i]).remove(k);
										else k++;
									}
									for(int k=0;k<getCualesSePuedePoner(a, ty+dy[i+1]).size();){
										if(fichas[a][w].noCombina(getCualesSePuedePoner(tx+dy[i+1],ty+dy[i+1]).get(k)))
											getCualesSePuedePoner(a,ty+dy[i+1]).remove(k);
										else k++;
									}
								}
							}
							
						}
						
						a=-10;
						b=-10;
					}
				}
			}
		}
	}
	private void eliminarLasQueNoCoinciden(List<Ficha>fichas,Ficha f){
		
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
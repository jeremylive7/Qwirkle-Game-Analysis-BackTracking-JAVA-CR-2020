import java.util.*;
import java.util.Map.Entry;


class Tablero
{
	static final int MATRIX_SIDE=6;
	private final Ficha[][] fichas;
	Map<Integer,Map<Integer,ArrayList<Ficha>>>placesToPlay;
	protected static List<Ficha>todasLasFichas;

	//Constructor
	public Tablero() 
	{		
		fichas = new Ficha[MATRIX_SIDE][MATRIX_SIDE];	
		placesToPlay= new HashMap<>();
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
	boolean meterFichaEnXY(final Ficha ficha,final int x,final int y){
		if(x<0||y<0||x>=MATRIX_SIDE||y>=MATRIX_SIDE)
			return false;
		updatePlacesToPlay(ficha, x, y);
		this.fichas[x][y]=ficha;
		return true;
	}
	
	public ArrayList<Ficha>getCualesSePuedePoner(int x,int y){
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
	private void eliminarLasQueNoCoinciden(List<Ficha> pFichas,Ficha f){
		for(int i=0;i<pFichas.size();){
			if(f.noCombina(pFichas.get(i)))
				pFichas.remove(i);
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
		return inicioHilera-finHilera;
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
		return inicioHilera-finHilera;
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
	public void startGame()
	{
		final int mitadDeLaMatriz=MATRIX_SIDE/2;
		this.meterFichaEnXY(new Ficha(Figura.ROMBO,Color.ROJO), mitadDeLaMatriz, mitadDeLaMatriz);
	}

}
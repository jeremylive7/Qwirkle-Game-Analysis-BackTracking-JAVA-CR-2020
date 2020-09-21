import java.util.*;


class Tablero
{
	static final int MATRIX_SIDE=20;
	private final Ficha fichas[][] ;

	//Constructor
	public Tablero() 
	{		
		fichas = new Ficha[MATRIX_SIDE][MATRIX_SIDE];	
	}
	public List<Ficha>getCualesPuedoPoner(int x,int y){
		List<Ficha>todasLasFichas=new ArrayList<>();
		for (Figura figura:Qwirkle.FIGURAS)
			for(Color color:Qwirkle.COLORES)
				todasLasFichas.add(new Ficha(figura,color));
		int inicioHilera=x,finHilera=x;
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
		//recorrer desde x,y para las cuatro direcciones contando la cantidad de fichas sin repetirse, si se repite es 0 todo
		int puntos=1;
		final ArrayList<Ficha>hileraHorizontal=new ArrayList<>(),hileraVertical=new ArrayList<>();
		int inicioHilera=x,finHilera=x;
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
	public boolean meterFichaEnXY(final Ficha ficha,final int x,final int y){
		if(x<0||y<0||x>=MATRIX_SIDE||y>=MATRIX_SIDE)
			return false;
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
import java.util.*;


class Tablero
{
	static final int MATRIX_SIDE=20;
	private Ficha fichas[][] ;

	//Constructor
	public Tablero() 
	{		
		fichas = new Ficha[MATRIX_SIDE][MATRIX_SIDE];	
	}
	public int getCantPuntos(int x,int y,Ficha ficha){
		//recorrer desde x,y para las cuatro direcciones contando la cantidad de fichas sin repetirse, si se repite es 0 todo
		int puntos=0;
		ArrayList<Ficha>hileraHorizontal=new ArrayList<>(),hileraVertical=new ArrayList<>();
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
		for(int i=inicioHilera;i<=finHilera;i++){
			if(i==x) hileraVertical.add(ficha);
			else hileraVertical.add(fichas[i][y]);
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
		for(int i=inicioHilera;i<=finHilera;i++){
			if(i==y)hileraHorizontal.add(ficha);
			else hileraHorizontal.add(fichas[x][i]);
		}
		//buscar repetidos
		Map<Figura,Map<Color,Boolean>>mapaParaEncontrarRepetidos=new HashMap<>();
		for(Ficha f:hileraHorizontal){
			if(mapaParaEncontrarRepetidos.containsKey(f.figura)&&mapaParaEncontrarRepetidos.get(f.figura).containsKey(f.color))
				return 0;

			// ArrayList<Ficha> pFichas_disponibles = getFichasDisponiblesAJugar(hileraHorizontal);

 
			// //recorrer pFichas_disponibles para saber si puedo poner la ficha que estoy colocando.
			// Boolean canI = canIDoPutFicha(pFichas_disponibles);



			mapaParaEncontrarRepetidos.putIfAbsent(f.figura,new HashMap<>());
			mapaParaEncontrarRepetidos.get(f.figura).put(f.color, true);
		}
		mapaParaEncontrarRepetidos=new HashMap<>();
		for(Ficha f:hileraVertical){
			if(mapaParaEncontrarRepetidos.containsKey(f.figura)&&mapaParaEncontrarRepetidos.get(f.figura).containsKey(f.color))
				return 0;
			mapaParaEncontrarRepetidos.putIfAbsent(f.figura,new HashMap<>());
			mapaParaEncontrarRepetidos.get(f.figura).put(f.color, true);
		}
		System.out.println("pts horizonaral : "+hileraHorizontal.size() + "\npts vertical : "+hileraVertical.size());
		if(hileraHorizontal.size()>1)puntos+=hileraHorizontal.size();
		if(hileraVertical.size()>1)puntos+=hileraVertical.size();
		if(hileraVertical.size()==6)puntos+=6;
		if(hileraHorizontal.size()==6)puntos+=6;
		return puntos;
	}
/* 	public Boolean canIDoPutFicha(ArrayList<Ficha> pFichas_pDisponibles)
	{

	}
	public ArrayList<Ficha> getFichasDisponiblesAJugar(ArrayList<Ficha> pJugadas_tablero)
	{
		ArrayList<Ficha> pFichas_disponibles = new ArrayList<Ficha>();
		String[] pFiguras_disponibles = {"TREBOL","SOL","ROMBO","CUADRADO","CIRCULO","X"};
		String[] pColores_disponibles = {"ROJO","VERDE","AMARILLO","AZUL","MORADO","NARANJA"};

		for (pFicha : pJugadas_tablero) 
		{
			for (int pIndex=0; pIndex<pFichas_disponibles.length; pIndex++) 
			{
				if(pFicha.getFigura()==pFiguras_disponibles[pIndex])
				{
					if(pFicha.getColor()==pColores_disponibles[pIndex])
					{

					}
				}
				pFiguras_disponibles[pIndex];
			}

			pFichas_disponibles.add(pFicha);
		}

		return pFichas_disponibles;
	} */
	public Ficha[][]getFichas(){
		return fichas;
	}
	public void llenarTableroConEjemplo(){
		int mitadDeLaMatriz=MATRIX_SIDE/2;
		meterFichaEnXY(new Ficha(Figura.ROMBO,Color.ROJO), mitadDeLaMatriz, mitadDeLaMatriz);
		meterFichaEnXY(new Ficha(Figura.CIRCULO,Color.ROJO), mitadDeLaMatriz+1, mitadDeLaMatriz);
		meterFichaEnXY(new Ficha(Figura.CUADRADO,Color.ROJO),mitadDeLaMatriz+2, mitadDeLaMatriz);
		meterFichaEnXY(new Ficha(Figura.ROMBO,Color.AZUL), mitadDeLaMatriz, mitadDeLaMatriz+1);
	}
	public boolean meterFichaEnXY(Ficha ficha,int x,int y){
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
import java.util.*; 
import javax.swing.*;

class Qwirkle
{
	private static final Figura[] FIGURAS = {Figura.CIRCULO,
		Figura.CUADRADO, Figura.SOL, Figura.TREBOL, Figura.X, Figura.ROMBO};
	private static final Color[] COLORES = {Color.AMARILLO,
		Color.AZUL, Color.NARANJA, Color.MORADO, Color.ROJO, Color.VERDE};
	private static final int CANT_CARTAS_EN_LA_MANO=6;
	private Jugador jugador_humano_1, jugador_humano_2, jugadorActual;
	private Tablero tablero;
	private InterfazDeUsuario frame;
	private ArrayList<Ficha> bolsa_fichas;
	private int opcion;

	public Qwirkle() 
	{		
		this.bolsa_fichas = new ArrayList<Ficha>();
		this.tablero = new Tablero();
		this.frame = new InterfazDeUsuario(tablero);
		this.fullFichasToBolsa();
		this.jugador_humano_1 = new Jugador("Jeremy",getFichasDeLaBolsa(6));
		this.jugador_humano_2 = new Jugador("Edgerik",getFichasDeLaBolsa(6));
		jugadorActual=jugador_humano_1;
		this.showBolsaFichas();
		
	}

	public void fullFichasToBolsa()
	{	
		for (Figura figura:FIGURAS) 
		{
			System.out.println(figura);
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
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
			  frame.setVisible(true);
			}
		  });
	}

	public static void main(String[]args)
	{
		
		Qwirkle qwirkle = new Qwirkle();
		qwirkle.getTablero().llenarTableroConEjemplo();
		qwirkle.mostrarVentana();
		qwirkle.frame.mostrarTablero();
	}

	public Tablero getTablero() 
	{
		return tablero;
	}

	public void setTablero(Tablero tablero) 
	{
		this.tablero = tablero;
	}

	public void menu()
	{
		while(this.opcion < 4){

			do{
				JOptionPane.showMessageDialog(this.frame, "Es el turno del jugador " + jugadorActual.getNombre());
				//Muestro mano del jugador
				this.showMano(jugadorActual);
				
				//Obtengo el # de la opcion
				this.opcion = Integer.parseInt(JOptionPane.showInputDialog("1. Reseteo de fichas"
					+ "\n"
					+ "2. Seleccionar mi jugada"
					+ "\n"
					+ "3. No tengo fichas a jugar"
					+ "\n"
					
					+ "4. Salir del Juego"));

				if(opcion==1)
				{//Resetear fichas del jugadorActual
					System.out.println("Elegiste reseteo de fichas");
					//jugadorActual.resetearFichas(sacarFichasDeLaBolsa(interfaz.cualesFichas(jugadorActual.getMazo())))
				}	
				else if(opcion==2)
				{//Elige jugada a colocar
					System.out.println("Elegiste seleccionar mi jugada");
					seleccionoJugada(jugadorActual);//Empieza turno, selecciono mi jugada
					setJugadaTablero();//Coloco jugada en el tablero
					getPtsJugada(jugadorActual);//Obtencion de pts por las fichas seteadas
					showPtsJugador(jugadorActual);//Imprimo pts
				}
				else if(opcion==3)//No tiene fichas
					System.out.println("Elegiste no tengo fichas a jugara");
				else	//Salir del juego
					break;
						
				jugadorActual=(jugadorActual==jugador_humano_1?jugador_humano_2:jugador_humano_1);

			}while(true);
		}
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
		else return "";
		
	}

	private String getSimboloFigura(Figura f)
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

	private String fichaToSimbol(Ficha ficha)
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
		System.out.println(pJugador.getScore().getPtsTotales());
	}

	public void getPtsJugada(Jugador j){

	}

	public void setJugadaTablero()
	{

	}

	public List<Ficha>getFichasDeLaBolsa(int cantFichas){
		List<Ficha>out=new ArrayList<>();
		while(cantFichas-->0)
			out.add(popRandomFicha());
		return out;
	}

	public Ficha popRandomFicha(){
		return bolsa_fichas.remove((int)(Math.random()*(bolsa_fichas.size()-1)));
	}
	/*
			EJEMPLO #1
	*/
	public void printTablero(){
		System.out.println(getTablero().toString());
	}

	public int getCantPuntos(int x,int y,Ficha ficha)
	{
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

	public Ficha[][]getFichas()
	{
		return fichas;
	}

	public void llenarTableroConEjemplo()
	{
		int mitadDeLaMatriz=MATRIX_SIDE/2;
		meterFichaEnXY(new Ficha(Figura.ROMBO,Color.ROJO), mitadDeLaMatriz, mitadDeLaMatriz);
		meterFichaEnXY(new Ficha(Figura.CIRCULO,Color.ROJO), mitadDeLaMatriz+1, mitadDeLaMatriz);
		meterFichaEnXY(new Ficha(Figura.CUADRADO,Color.ROJO),mitadDeLaMatriz+2, mitadDeLaMatriz);
		meterFichaEnXY(new Ficha(Figura.ROMBO,Color.AZUL), mitadDeLaMatriz, mitadDeLaMatriz+1);
	}

	public boolean meterFichaEnXY(Ficha ficha,int x,int y)
	{
		if(x<0||y<0||x>=MATRIX_SIDE||y>=MATRIX_SIDE)
			return false;
		fichas[x][y]=ficha;
		return true;
	}

	public String toString()
	{
		String out="";
		for(int i=0;i<MATRIX_SIDE;i++){
			for(int j=0;j<MATRIX_SIDE;j++)
				out+="# "+(fichas[i][j]!=null?fichas[i][j].toString():"\t\t\t")+" #";
			out+="\n";
		}
		return out;
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
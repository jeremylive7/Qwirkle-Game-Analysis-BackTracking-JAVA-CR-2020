import java.util.*; 
import javax.swing.*;

class Qwirkle
{
	private Jugador jugador_humano_1;
	private Jugador jugador_humano_2;
	private Jugador jugadorActual;
	private Tablero tablero;
	private List<Ficha> bolsa_fichas;
	private int opcion;
	private InterfazDeUsuario frame;
	private static final Figura[]FIGURAS = {Figura.CIRCULO,Figura.CUADRADO,Figura.SOL, Figura.TREBOL,Figura.X, Figura.ROMBO};
	private static final Color[]COLORES={Color.AMARILLO,Color.AZUL,Color.NARANJA,Color.MORADO,Color.ROJO,Color.VERDE};
	private static final int CANT_CARTAS_EN_LA_MANO=6;

	public Qwirkle() 
	{		
		this.bolsa_fichas = new ArrayList<>();
		tablero=new Tablero();
		this.frame = new InterfazDeUsuario(tablero);
		this.fullFichasToBolsa();
		this.jugador_humano_1 = new Jugador("Jeremy",getFichasDeLaBolsa(6));
		this.jugador_humano_2 = new Jugador("Edgerik",getFichasDeLaBolsa(6));
		jugadorActual=jugador_humano_1;
		this.showBolsaFichas();
		
	}
	public void mostrarVentana(){
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
			  frame.setVisible(true);
			}
		  });
	}
	public static void main(String[]args){
		
		Qwirkle qwirkle = new Qwirkle();
		qwirkle.getTablero().llenarTableroConEjemplo();
		qwirkle.mostrarVentana();
		qwirkle.frame.mostrarTablero();
	}
	public Tablero getTablero() {
		return tablero;
	}

	public void setTablero(Tablero tablero) {
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
	public void imprimirTablero(){
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
	public void seleccionoJugada(Jugador j)
	{

	}

	/*
			EJEMPLO #1
	*/
	public void printTablero(){
		System.out.println(getTablero().toString());
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
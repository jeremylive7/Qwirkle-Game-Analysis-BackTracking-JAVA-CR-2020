import java.util.*;
import javax.swing.*;

class Main 
{
	public static void main(String[] args) 
	{		
		//Empieza el juego
		Qwirkle qwirkle;
		qwirkle = new Qwirkle();

		//qwirkle.mostrarVentana();
		//qwirkle.frame.mostrarTablero();

		//qwirkle.menu();

		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);

		qwirkle.showAllComb(qwirkle.getAllCombToEachComb(list));

		System.exit(0);
	}

}
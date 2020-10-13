import java.io.*;
 
public class FileOperations 
{
	public static void createdFileXRound(String pPlayer_name, String pPoints_round, String pTime_round) 
	{
		try {
			if(pPlayer_name == "Edgerik")
			{
				//PrintWriter basic_player = new PrintWriter(new FileWriter("C:\\Users\\Jerem\\Desktop\\Proyecto JAVA\\Qwirkle-Game-Analysis-BackTracking-JAVA-CR-2020\\basic-player.txt", true), true);
				PrintWriter basic_player = new PrintWriter(new FileWriter("D:\\xllEs\\Documents\\2020 - IISem\\Análisis de Algoritmos\\Qwirkle-Game-Analysis-BackTracking-JAVA-CR-2020\\Qwirkle-Game-Analysis-BackTracking-JAVA-CR-2020\\basic-player.txt", true), true);
				basic_player.println(pPlayer_name + "," + pPoints_round + "," + pTime_round);
			}else if(pPlayer_name == "Roberto")
			{
				//PrintWriter improved_player = new PrintWriter(new FileWriter("C:\\Users\\Jerem\\Desktop\\Proyecto JAVA\\Qwirkle-Game-Analysis-BackTracking-JAVA-CR-2020\\improved-player.txt", true), true);
				PrintWriter improved_player = new PrintWriter(new FileWriter("D:\\xllEs\\Documents\\2020 - IISem\\Análisis de Algoritmos\\Qwirkle-Game-Analysis-BackTracking-JAVA-CR-2020\\Qwirkle-Game-Analysis-BackTracking-JAVA-CR-2020\\improved-player.txt", true), true);
				improved_player.println(pPlayer_name + "," + pPoints_round + "," + pTime_round);
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void finDeTurno(String nombre,long tiempoTotal,int totalPuntos){
		try {
			//PrintWriter basic_player = new PrintWriter(new FileWriter("C:\\Users\\Jerem\\Desktop\\Proyecto JAVA\\Qwirkle-Game-Analysis-BackTracking-JAVA-CR-2020\\basic-player.txt", true), true);
			PrintWriter basic_player = new PrintWriter(new FileWriter("D:\\xllEs\\Documents\\2020 - IISem\\Análisis de Algoritmos\\Backtraking\\Qwirkle-Game-Analysis-BackTracking-JAVA-CR-2020\\resultadosFinales.txt", true), true);
			basic_player.println(nombre + "," + tiempoTotal + "," + totalPuntos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


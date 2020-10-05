
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
 
public class FileOperations 
{
	public static void createdFileXRound(String pName_player, String pPoints_round, String pTime_round) 
	{
		FileWriter flwriter = null;
		try {
			flwriter = new FileWriter("C:\\Users\\Jerem\\Desktop\\Proyecto JAVA\\Qwirkle-Game-Analysis-BackTracking-JAVA-CR-2020\\FilesTXT\\players-point-time-X-turno.txt");

			BufferedWriter bfwriter = new BufferedWriter(flwriter);

			bfwriter.write(pName_player + "," + pPoints_round + "," + pTime_round + "\n");

			bfwriter.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (flwriter != null) 
			{
				try {
					flwriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void createdFileFinishGame(String pName_player, String pPoints_total, String pTime_total) 
	{
		FileWriter flwriter = null;
		try {
			flwriter = new FileWriter("C:\\Users\\Jerem\\Desktop\\Proyecto JAVA\\Qwirkle-Game-Analysis-BackTracking-JAVA-CR-2020\\FilesTXT\\players-point-time-X-turno.txt");

			BufferedWriter bfwriter = new BufferedWriter(flwriter);

			bfwriter.write(pName_player + "," + pPoints_total + "," + pTime_total + "\n");

			bfwriter.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (flwriter != null) 
			{
				try {
					flwriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void addFileXTurno(String pName_player, String pPoints_round, String pTime_round) 
	{
		FileWriter flwriter = null;

		try {
			flwriter = new FileWriter("C:\\Users\\Jerem\\Desktop\\Proyecto JAVA\\Qwirkle-Game-Analysis-BackTracking-JAVA-CR-2020\\FilesTXT\\players-point.txt", true);
			
			BufferedWriter bfwriter = new BufferedWriter(flwriter);

			bfwriter.write(pName_player + "," + pPoints_round + "," + pTime_round + "\n");
			
			bfwriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (flwriter != null) 
			{
				try {
					flwriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}	
}
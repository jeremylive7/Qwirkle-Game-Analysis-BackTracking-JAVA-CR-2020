import java.util.ArrayList;
import java.util.List;

public class Jugada {
	public List<Jugadita> jugaditas=new ArrayList<>();
	public int puntos=0;
	public Boolean isLine;
	public Jugada copy(int puntos){
		Jugada n=new Jugada();
		n.jugaditas=new ArrayList<>(this.jugaditas);
		n.puntos=puntos;
		n.isLine=isLine;
		return n;
	}
}
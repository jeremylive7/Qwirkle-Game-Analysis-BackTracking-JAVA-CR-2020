import java.util.ArrayList;
import java.util.List;

public class Jugada {
	public List<ParFichaPosicion>pares=new ArrayList<>();
	public int puntos=0;
	public Boolean isLine;
	public Jugada copy(int puntos){
		Jugada n=new Jugada();
		n.pares=new ArrayList<>(this.pares);
		n.puntos=puntos;
		n.isLine=isLine;
		return n;
	}
}
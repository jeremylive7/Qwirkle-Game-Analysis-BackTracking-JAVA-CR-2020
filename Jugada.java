import java.util.ArrayList;
import java.util.List;

public class Jugada {
	public List<Jugadita>jugaditas=new ArrayList<>();
	public int puntos=0;
	public Boolean isLine;
	public Boolean complete=false;
	public Jugada copy(int puntos){
		Jugada n=new Jugada();
		n.jugaditas=new ArrayList<>(this.jugaditas);
		n.puntos=puntos;
		n.isLine=isLine;
		n.complete=complete;
		return n;
	}
	public String toString(){
		String out="[";
		for(Jugadita j:jugaditas){
			out+=j.ficha+",";
		}
		out=out.substring(0,out.length()-1)+"]";
		return out;
	}
}
import java.util.ArrayList;
import java.util.List;

public class Jugada {
	public List<Jugadita>jugaditas=new ArrayList<>();
	public int puntos=0;
	public Boolean isLine;
	public Jugada copy(int puntos){
		Jugada n=new Jugada();
		n.jugaditas=new ArrayList<>(this.jugaditas);
		n.puntos=puntos;
		n.isLine=isLine;
		return n;
	}
	public String toString(){
		String out="[";
		for(Jugadita j:jugaditas){
			out+=j.ficha+"(x: "+j.x+" y: "+j.y+"),";
		}
		out=out.substring(0,out.length()-1)+"]";
		return out;
	}
}
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
		String out="\n";
		int alpha=jugaditas.get(0).x;
		int betta=jugaditas.get(0).y-1;
		for(Jugadita j:jugaditas){
			if(j.x<alpha){
				out="## "+j.ficha+" ##\n"+out;
			}else if (j.x>alpha)
				out+="## "+j.ficha+" ##\n";
			else if(j.y<betta)
				out="## "+j.ficha+" ##"+out;
			else 
				out+="## "+j.ficha+" ##";
			alpha=j.x;
			betta=j.y;
		}
		return out;
	}
}
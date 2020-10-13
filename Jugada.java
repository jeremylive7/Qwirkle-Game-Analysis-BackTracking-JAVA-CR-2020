import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Jugada {
	public Set<Jugadita>jugaditas=new HashSet<>();
	public int puntos=0;
	public Boolean isLine;
	public Jugada copy(int puntos){
		Jugada n=new Jugada();
		n.jugaditas=new HashSet<>(this.jugaditas);
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
	public int hashCode(){
		return 0;
	}
	public boolean equals(Object o){
		Jugada otraJugada=(Jugada)o;
		return otraJugada!=null&&jugaditas.equals(otraJugada.jugaditas)&&puntos==otraJugada.puntos&&isLine==otraJugada.isLine;
	}
}
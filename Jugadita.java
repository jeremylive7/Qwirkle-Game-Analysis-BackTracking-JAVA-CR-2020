public class Jugadita {
    int x,y;
    Ficha ficha;

    public Jugadita(int x, int y, Ficha ficha) {
        this.x = x;
        this.y = y;
        this.ficha = ficha;
    }
    
	public int hashCode(){
		return 0;
	}
    public boolean equals(Object obj)
    {
    	Jugadita jug = (Jugadita)obj;
    	return jug!=null&&ficha.equals(jug.ficha)&&x==jug.x&&y==jug.y;
    }
    
}

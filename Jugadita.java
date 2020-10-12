public class Jugadita {
    int x,y;
    Ficha ficha;

    public Jugadita(int x, int y, Ficha ficha) {
        this.x = x;
        this.y = y;
        this.ficha = ficha;
    }

    public boolean equals(Object x)
    {
    	Jugadita pX = (Jugadita)x;
    	if(pX.ficha.getColor() == ficha.getColor() &&
    		pX.ficha.getFigura() == ficha.getFigura())
    	{
    		return true;
    	}
    	return false;
    }
    
}

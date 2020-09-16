import java.util.*;

public class Grafito 
{
	//Variables
    private List<Nodito> noditos;
    private int noditos_totales;
    
    //Constructor
    public Grafito(){
    	this.noditos = new ArrayList<Nodito>();
    	this.noditos_totales = 0;
    }

    //Metodos get y set
    public boolean seeIsItEnable() 
    { 
        return this.noditos_totales > 1;
    }
   
    public void creoNodito(Nodito pNoodito) 
    {
        this.noditos.add(pNoodito);
        this.noditos_totales++; 
    }
    
    public int getNoditosTotales() 
    {
        return this.noditos_totales;
    }
}
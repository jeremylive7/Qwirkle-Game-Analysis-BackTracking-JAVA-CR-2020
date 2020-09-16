import java.util.*;

public class Nodito 
{
	//Variables
    private int identificacion;
    private List<Conexion> conexiones;
    
    //Constructor
    public Nodito(int pIdentificacion)
    {
    	this.identificacion = pIdentificacion;
    	this.conexiones = new ArrayList<Conexion>();
    }

    //Metodos get y set
    public int getNoditoId()
    {
        return this.identificacion;
    }

     public void setConexion(Conexion pConexion) 
     {
        if(this.conexiones.contains(pConexion)) 
        {
            System.out.println("Ya existe esta conexion");

        } else 
        {
            System.out.println("Se agrego conexion" + pConexion);
            this.conexiones.add(pConexion);
        }
    }
    public void getConexion() 
    {
        System.out.println("Conexiones del " 
        	+ this.identificacion +" :\n");

        for (int index = 0; index < this.conexiones.size(); index++ )
        {
            System.out.println("Conexion: " 
            	+ conexiones.get(index).getId() 
            	+ "\nNodito en el que estoy: " 
            	+ neighbours.get(index).getIdNoditoPartida() 
            	+ "\nNodito al que estoy conectado: " 
            	+ conexiones.get(index).getIdNoditoLlegada())
            	+ "\n";

        }
    }
}
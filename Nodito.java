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
    	this.conexiones = new ArrayList<>();
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

        } else 
        {
        }
    }
    public void getConexion() 
    {

        for (int index = 0; index < this.conexiones.size(); index++ )
        {
        }
    }
}
public class Conexion 
{
    //Variables
    private Nodito partida;
    private Nodito llegada;
    private Ficha ficha;

    //Constructor
    public Conexion(Nodito pPartida, Nodito pLlegada, Ficha pFicha) {
        this.partida = pPartida;
        this.llegada = pLlegada;
        this.ficha = pFicha;
    }

    //Metododos de get y set
    public Nodito getPartida() 
    {
        return this.partida;
    }

    public int getIdNoditoPartida() 
    {
        return this.partida.getNodeId();
    }

    public Nodito getLlegada() 
    { 
        return this.llegada; 
    }

    public int getIdNoditoLlegada() 
    {
        return this.llegada.getNodeId();
    }
}
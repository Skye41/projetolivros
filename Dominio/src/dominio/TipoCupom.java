package dominio;

public enum TipoCupom
{
    PROMOCIONAL("Promocional"), TROCA("Troca");
    
    private final String valor;
    
    private TipoCupom(String valor)
    {
        this.valor = valor;
    }
    
    public String getValor()
    { return valor;}
}

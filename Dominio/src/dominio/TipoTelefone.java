package dominio;

public enum TipoTelefone
{
    RESIDENCIAL("Residencial"), COMERCIAL("Comercial"), CELULAR("Celular");
    
    private final String valor;
    
    private TipoTelefone(String valor)
    {
        this.valor = valor;
    }
    
    public String getValor()
    { return valor;}
}

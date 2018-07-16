package dominio;

public enum TipoUsuario
{
    ADMIN("Administrador"), CLIENTE("Cliente");
    
    private final String valor;
    
    private TipoUsuario(String valor)
    {
        this.valor = valor;
    }
    
    public String getValor()
    { return valor;}
}

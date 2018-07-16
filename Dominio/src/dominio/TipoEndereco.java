package dominio;

public enum TipoEndereco
{
    RESIDENCIAL("Residencial"), COBRANCA("Cobrança"), ENTREGA("Entrega"), CADASTRO("Cadastro");
    
    private final String valor;
    
    private TipoEndereco(String valor)
    {
        this.valor = valor;
    }
    
    public String getValor()
    { return valor;}
}

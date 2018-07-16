package dominio;

public enum Genero
{
    MASCULINO("Masculino"), FEMININO("Feminino"), OUTRO("Outro");
    
    private final String valor;
    
    private Genero(String valor)
    {
        this.valor = valor;
    }
    
    public String getValor()
    { return valor;}
}

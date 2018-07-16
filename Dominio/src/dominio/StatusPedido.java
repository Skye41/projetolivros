package dominio;

public enum StatusPedido
{
    APROVADO("Aprovado"), REPROVADO("Reprovado"), TRANSPORTE("Em Transporte"), 
    ENTREGUE("Entregue"), TROCA("Em Troca"), TROCADO("Trocado"), PROCESSAMENTO("Em Processamento");
    
    private final String valor;
    
    private StatusPedido(String valor)
    {
        this.valor = valor;
    }
    
    public String getValor()
    { return valor;}
}

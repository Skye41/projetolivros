package dominio;

public class ItemPedido extends EntidadeDominio
{
    private Livro livro;
    private int qtdeItem;
    private double valorItem;
    private Pedido pedido;
    
    public Livro getLivro()
    {
        return livro;
    }
    public void setLivro(Livro livro)
    {
        this.livro = livro;
    }
    public int getQtdeItem()
    {
        return qtdeItem;
    }
    public void setQtdeItem(int qtdeItem)
    {
        this.qtdeItem = qtdeItem;
    }
    public double getValorItem()
    {
        return valorItem;
    }
    public void setValorItem(double valorItem)
    {
        this.valorItem = valorItem;
    }
    public Pedido getPedido()
    {
        return pedido;
    }
    public void setPedido(Pedido pedido)
    {
        this.pedido = pedido;
    }
}

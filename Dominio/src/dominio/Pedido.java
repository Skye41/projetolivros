package dominio;

import java.util.Date;
import java.util.List;

public class Pedido extends EntidadeDominio
{
    private Date dtPedido;
    private StatusPedido status;
    private double valorFrete;
    private Endereco enderecoEntrega;
    private Cliente cliente;
    private List<CartaoPedido> cartoes;
    private Cupom promocional;
    private Cupom troca;
    private List<ItemPedido> itens;
    private boolean estoque;
    
    public Date getDtPedido()
    {
        return dtPedido;
    }
    public void setDtPedido(Date dtPedido)
    {
        this.dtPedido = dtPedido;
    }
    public StatusPedido getStatus()
    {
        return status;
    }
    public void setStatus(StatusPedido status)
    {
        this.status = status;
    }
    public double getValorFrete()
    {
        return valorFrete;
    }
    public void setValorFrete(double valorFrete)
    {
        this.valorFrete = valorFrete;
    }
    public Endereco getEnderecoEntrega()
    {
        return enderecoEntrega;
    }
    public void setEnderecoEntrega(Endereco enderecoEntrega)
    {
        this.enderecoEntrega = enderecoEntrega;
    }
    public Cliente getCliente()
    {
        return cliente;
    }
    public void setCliente(Cliente cliente)
    {
        this.cliente = cliente;
    }
    public List<CartaoPedido> getCartoes()
    {
        return cartoes;
    }
    public void setCartoes(List<CartaoPedido> cartoes)
    {
        this.cartoes = cartoes;
    }
    public Cupom getPromocional()
    {
        return promocional;
    }
    public void setPromocional(Cupom promocional)
    {
        this.promocional = promocional;
    }
    public Cupom getTroca()
    {
        return troca;
    }
    public void setTroca(Cupom troca)
    {
        this.troca = troca;
    }
    public List<ItemPedido> getItens()
    {
        return itens;
    }
    public void setItens(List<ItemPedido> itens)
    {
        this.itens = itens;
    }
    public boolean isEstoque()
    {
        return estoque;
    }
    public void setEstoque(boolean estoque)
    {
        this.estoque = estoque;
    }
    
    public double getTotalPedido()
    {
        double total = 0;
        for(ItemPedido ip : itens)
            total += ip.getValorItem() * ip.getQtdeItem();
        total += valorFrete;
        return total;
    }
}

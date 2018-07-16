package dominio;

import java.util.List;

public class Carrinho extends EntidadeDominio
{
    private List<ItemPedido> itens;

    public List<ItemPedido> getItens()
    {
        return itens;
    }

    public void setItens(List<ItemPedido> itens)
    {
        this.itens = itens;
    }
    public double getTotalPedido()
    {
        double total = 0;
        for(ItemPedido ip : itens)
            total += ip.getValorItem() * ip.getQtdeItem();
        return total;
    }
}

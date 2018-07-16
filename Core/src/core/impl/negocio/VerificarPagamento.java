package core.impl.negocio;

import core.IStrategy;
import dominio.CartaoPedido;
import dominio.EntidadeDominio;
import dominio.Pedido;
import dominio.StatusPedido;
import java.text.NumberFormat;

public class VerificarPagamento implements IStrategy
{
    @Override
    public String processar(EntidadeDominio entidade)
    {
        Pedido p = (Pedido)entidade;
        if(!p.getStatus().equals(StatusPedido.TROCA))
        {
            if((p.getCartoes() == null || p.getCartoes().isEmpty()) 
                && p.getPromocional() == null && p.getTroca() == null)
            return "O pedido deve ter uma forma de pagamento.";
            else
            {
                double total = 0;
                if(p.getCartoes() != null)
                {
                    for(CartaoPedido cp : p.getCartoes())
                        total += cp.getValorCartao();
                }
                if(p.getPromocional() != null)
                    total += p.getPromocional().getValorCupom();
                if(p.getTroca() != null)
                    total += p.getTroca().getValorCupom();
                if(total < p.getTotalPedido())
                {
                    String msg = "Faltam " + NumberFormat.getCurrencyInstance().format(p.getTotalPedido() - total) 
                            + " para completar o valor do pedido.";
                    p.setCartoes(null);
                    p.setTroca(null);
                    p.setPromocional(null);
                    return msg;
                }
                else
                    return null;
            }
        }
        else
            return null;
    }   
}

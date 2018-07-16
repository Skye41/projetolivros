/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.impl.negocio;

import core.IStrategy;
import dominio.CartaoPedido;
import dominio.EntidadeDominio;
import dominio.PagamentoTeste;
import dominio.StatusPedido;

public class ValidarPagamento implements IStrategy
{
    @Override
    public String processar(EntidadeDominio entidade)
    {
        try
        {
            PagamentoTeste p = (PagamentoTeste)entidade;
            String msg = "";
            long agora = System.currentTimeMillis();
            
            if(p.getPedido().getCartoes() != null)
            {
                for(CartaoPedido cp : p.getPedido().getCartoes())
                {
                    if(cp.getDtVencimento().getTime() < agora)
                        msg += "Cartão inválido. ";
                }
            }
            if(p.getPedido().getTroca() != null && p.getPedido().getTroca().getDtValidade().getTime() < agora) 
                msg += "Cupom de troca inválido. ";
            if(p.getPedido().getTroca() != null && p.getPedido().getTroca().getDtValidade().getTime() < agora) 
                msg += "Cupom promocional inválido. ";
            if(msg.trim().length() > 0)
		p.getPedido().setStatus(StatusPedido.REPROVADO);
	    else
		p.getPedido().setStatus(StatusPedido.APROVADO);
            return null;
        }
        catch(ClassCastException e)
        {
            return "Esta entidade não é um pagamento.";
        }
    }
}

package core.impl.negocio;

import core.IStrategy;
import core.impl.dao.CupomDAO;
import dominio.Cupom;
import dominio.EntidadeDominio;
import dominio.Pedido;
import dominio.StatusPedido;
import dominio.TipoCupom;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class GerarCupomTroca implements IStrategy
{
    @Override
    public String processar(EntidadeDominio entidade)
    {
        try
        {
            Pedido p = (Pedido)entidade;
            if(p.getStatus() != null && (p.getStatus().equals(StatusPedido.PROCESSAMENTO) || p.getStatus().equals(StatusPedido.TROCADO)))
            {
                Cupom c = new Cupom();
                c.setCliente(p.getCliente());
                c.setTipoCupom(TipoCupom.TROCA);
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.MONTH, 1);
                c.setDtValidade(cal.getTime());
                c.setStatus(true);

                if(p.getStatus().equals(StatusPedido.TROCADO))
                    c.setValorCupom(p.getTotalPedido());
                else
                {
                    double valor = 0;
                    if(p.getPromocional() != null)
                        valor += p.getPromocional().getValorCupom();
                    if(p.getTroca() != null)
                        valor += p.getTroca().getValorCupom();
                    if(valor > p.getTotalPedido())
                        c.setValorCupom(valor - p.getTotalPedido());
                    else
                        c.setValorCupom(0);
                }
                    
                try
                {
                    if(c.getValorCupom() > 0)
                        new CupomDAO().salvar(c);
                    return null;
                }
                catch(SQLException e)
                {
                    return "Não foi possível gerar cupom de troca!";
                }
            }
            return null;
        }
        catch(ClassCastException e)
        {
            e.printStackTrace();
            return "Esta entidade não é um pedido.";
        }
    }  
}

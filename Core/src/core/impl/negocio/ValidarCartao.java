package core.impl.negocio;

import core.IStrategy;
import dominio.Cartao;
import dominio.EntidadeDominio;

public class ValidarCartao implements IStrategy
{
    @Override
    public String processar(EntidadeDominio entidade)
    {
        if(entidade instanceof Cartao)
        {
            Cartao cartao = (Cartao)entidade;
            String msg = "";
            if(cartao.getNumero() == null || cartao.getNumero().trim().equals(""))
                msg += "O cartão deve conter um número válido. ";
            if(cartao.getNomeCartao() == null || cartao.getNomeCartao().trim().equals(""))
                msg += "O cartão deve conter um nome válido. ";
            if(cartao.getCodigoSeguranca() == null || cartao.getCodigoSeguranca().trim().equals(""))
                msg += "O cartão deve conter um código de segurança válido. ";
            if(cartao.getBandeira() == null)
                msg += "O cartão deve conter uma bandeira válida. ";
            if(msg.trim().length() > 0)
                return msg;
            else
                return null;
        }
        else
	    return "Esta entidade não é um livro.";
    }
}

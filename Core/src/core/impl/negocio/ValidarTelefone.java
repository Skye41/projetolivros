package core.impl.negocio;

import core.IStrategy;
import dominio.EntidadeDominio;
import dominio.Telefone;

public class ValidarTelefone implements IStrategy
{
    @Override
    public String processar(EntidadeDominio entidade)
    {
        if(entidade instanceof Telefone)
        {
            Telefone tel = (Telefone)entidade;
            String msg = "";
            
            //Telefone (deve ser composto pelo tipo, DDD e número)
            if(tel.getTipo() == null)
                msg += "O telefone deve ter um tipo. ";
            if(tel.getDdd() == null || tel.getDdd().trim().equals(""))
                msg += "O telefone deve ter um DDD válido. ";
            if(tel.getNumero() == null || tel.getNumero().trim().equals(""))
                msg += "O telefone deve ter um número válido. ";
            if(msg.trim().length() > 0)
                return msg;
            else
                return null;
        }
        else
            return "Esta entidade não é um telefone. ";
    }
}

package core.impl.negocio;

import core.IStrategy;
import dominio.EntidadeDominio;
import dominio.Usuario;

public class ValidarUsuario implements IStrategy
{
    @Override
    public String processar(EntidadeDominio entidade)
    {
        if(entidade instanceof Usuario)
	{
	    Usuario user = (Usuario)entidade;
	    String msg = "";
            if(user.getEmail() == null || user.getEmail().trim().equals(""))
                msg += "O email é inválido! ";
            if(msg.trim().length() > 0)
		return msg;
	    else
		return null;
	}
	else
	    return "Esta entidade não é um usuário.";
    }
}

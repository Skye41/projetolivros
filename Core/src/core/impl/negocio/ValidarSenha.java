package core.impl.negocio;

import core.IStrategy;
import dominio.EntidadeDominio;
import dominio.Usuario;

public class ValidarSenha implements IStrategy
{
    
    @Override
    public String processar(EntidadeDominio entidade)
    {
	if(entidade instanceof Usuario)
	{
	    Usuario user = (Usuario)entidade;
	    String msg = "";
            if(!user.getSenha().equals(user.getConfirmacaoSenha()))
		msg += "A senha não foi digitada corretamente. ";
	    if(user.getSenha().trim().length() < 8)
		msg += "A senha deve conter pelo menos 8 caracteres. ";
	    boolean maiuscula = false;
            boolean minuscula = false;
            boolean especial = false;
            boolean numero = false;
            for(char c : user.getSenha().toCharArray())
            {
                if(c >= '0' && c <= '9')
                {
                    numero = true;
                }
                else if(c >= 'A' && c <= 'Z')
                {
                    maiuscula = true;
                }
                else if(c >= 'a' && c <= 'z')
                {
                    minuscula = true;
                }
                else
                {
                    especial = true;
                }
            }
            if(!maiuscula)
                msg += "A senha deve conter pelo menos 1 caractere maiúsculo. \n";
            if(!minuscula)
                msg += "A senha deve conter pelo menos 1 caractere minúsculo. \n";
            if(!especial)
                msg += "A senha deve conter pelo menos 1 caractere especial. \n";
	    if(msg.trim().length() > 0)
		return msg;
	    else
		return null;
	}
	else
	    return "Esta entidade não é um usuário.";
    }
}

package core.impl.negocio;

import core.IStrategy;
import dominio.Cliente;
import dominio.Endereco;
import dominio.EntidadeDominio;
import dominio.Telefone;

public class ValidarDadosCliente implements IStrategy
{
    @Override
    public String processar(EntidadeDominio entidade)
    {
        if(entidade instanceof Cliente)
        {
            Cliente cliente = (Cliente)entidade;
            String msg = "";
            
            if(cliente.getNome() == null || cliente.getNome().trim().equals(""))
                msg += "O cliente deve ter um nome. ";
            if(cliente.getGenero() == null)
                msg += "O cliente deve ter um gênero. ";
            if(cliente.getDtNascimento() == null)
                msg += "O cliente deve ter uma data de nascimento. ";
            if(cliente.getCPF() == null || cliente.getCPF().trim().equals(""))
                msg += "O cliente deve ter um CPF. ";
            
            if(cliente.getTelefones() == null || cliente.getTelefones().isEmpty())
                msg += "O cliente deve ter no mínimo um telefone. ";
            else
            {
                for(Telefone t : cliente.getTelefones())
                    msg += new ValidarTelefone().processar(t);
            }
            
            if(cliente.getEnderecos() == null || cliente.getEnderecos().isEmpty())
                msg += "O cliente deve ter no mínimo um endereço. ";
            else
            {
                for(Endereco e : cliente.getEnderecos())
                    msg += new ValidarEndereco().processar(e);
            }
            
            if(cliente.getUsuario() == null)
                msg += "O cliente deve ter um e-mail e senha. ";
            else
            {
                msg += new ValidarUsuario().processar(cliente.getUsuario());
                msg += new ValidarSenha().processar(cliente.getUsuario());
            }
            
            if(msg.trim().length() > 0)
                return msg;
            else
                return null;
        }
        else
            return "Esta entidade não é um cliente. ";
    }
}

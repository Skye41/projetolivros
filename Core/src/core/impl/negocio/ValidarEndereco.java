package core.impl.negocio;

import core.IStrategy;
import dominio.Endereco;
import dominio.EntidadeDominio;

public class ValidarEndereco implements IStrategy
{
    
    @Override
    public String processar(EntidadeDominio entidade)
    {
	if(entidade instanceof Endereco)
	{
	    Endereco end = (Endereco)entidade;
	    String msg = "";
	    // Cidade, Estado e País. Todos campos anteriores são de preenchimento obrigatório. 
            // Opcionalmente pode ser preenchido um campo observações.
	    if(end.getTipoResidencia() == null || end.getTipoResidencia().trim().isEmpty())
	    {
		msg += "O endereço deve conter um Tipo de Residência válido. ";
	    }
	    if(end.getTipoLogradouro() == null || end.getTipoLogradouro().trim().isEmpty())
	    {
		msg += "O endereço deve conter um Tipo de Logradouro válido. ";
	    }
	    if(end.getLogradouro() == null || end.getLogradouro().trim().isEmpty())
	    {
		msg += "O endereço deve conter um Logradouro válido. ";
	    }
	    if(end.getNumero() == null || end.getNumero().trim().isEmpty())
	    {
		msg += "O endereço deve conter um Número válido. ";
	    }
	    if(end.getBairro() == null || end.getBairro().trim().isEmpty())
	    {
		msg += "O endereço deve conter um Bairro válido. ";
	    }
	    if(end.getCep() == null || end.getCep().trim().isEmpty())
	    {
		msg += "O endereço deve conter um CEP válido. ";
	    }
	    
	    if(end.getCidade() == null || end.getCidade().trim().isEmpty())
	    {
		msg += "O endereço deve conter uma Cidade válida. ";
	    }
	    if(end.getEstado() == null || end.getEstado().trim().isEmpty())
	    {
		msg += "O endereço deve conter um Estado válido. ";
	    }
	    if(end.getPais() == null || end.getPais().trim().isEmpty())
	    {
		msg += "O endereço deve conter um País válido. ";
	    }
	    if(msg.trim().length() > 0)
		return msg;
	    else
		return null;
	}
	else
	    return "Esta entidade não é um endereço.";
    }
    
}

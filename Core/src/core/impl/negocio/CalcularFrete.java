package core.impl.negocio;

import core.IStrategy;
import dominio.Endereco;
import dominio.EntidadeDominio;
import dominio.ItemPedido;
import dominio.Pedido;

public class CalcularFrete implements IStrategy
{
    
    @Override
    public String processar(EntidadeDominio entidade)
    {
	try
        {
            Pedido p = (Pedido)entidade; 
            if(p.getStatus() == null)
            {
                double frete = 0;
                for(ItemPedido ip : p.getItens())
                {
                    double medida = ip.getLivro().getDimensoes().getAltura() 
                            * ip.getLivro().getDimensoes().getLargura() 
                            * ip.getLivro().getDimensoes().getProfundidade();
                    medida = medida/1000000;
                    frete += getFreteMedida(medida);
                }
                frete += getFreteEndereco(p.getEnderecoEntrega());
                p.setValorFrete(frete);
                return "Frete calculado";
            }
            else
            {
                return null;
            }
        }
        catch(ClassCastException e)
        {
            e.printStackTrace();
            return "Esta entidade não é um pedido.";
        }
    }
    
    public double getFreteEndereco(Endereco end)
    {
        double teste = (end.getLogradouro().length() + end.getBairro().length() + end.getCidade().length()) * 0.5;
        return teste;
    }
    
    public double getFreteMedida(double medida)
    {
        if(medida < 1)
            return 3;
        else if(medida < 3)
            return 5;
        else if(medida < 5)
            return 7;
        else
            return 10;
    }
}

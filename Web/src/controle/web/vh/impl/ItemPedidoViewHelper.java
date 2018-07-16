package controle.web.vh.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.web.vh.IViewHelper;
import core.aplicacao.Resultado;
import dominio.EntidadeDominio;
import dominio.ItemPedido;
import dominio.Livro;

public class ItemPedidoViewHelper implements IViewHelper
{
    
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request)
    {
	ItemPedido item = null;
	
	// Recuperar os valores da request 
	String qtde = request.getParameter("txtQtde");
	String id = request.getParameter("txtIdLivro");
	Resultado livros = (Resultado)request.getSession().getAttribute("livros"); 
	    
	item = new ItemPedido();
	    
	if(id != null && !id.trim().equals(""))
	{
	    int idLivro = Integer.valueOf(id);
	    for(EntidadeDominio e : livros.getEntidades())
            {
		if(e.getId() == idLivro)
                {
		    item.setLivro((Livro)e);
                    item.setValorItem(((Livro)e).getPrecoVenda());
                    break;
                }
            }
	    if(qtde != null && !qtde.trim().equals(""))
		item.setQtdeItem(Integer.valueOf(qtde));
            
	    return item;
	}
	return null;
    }
    
    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException
    {
	// TODO Auto-generated method stub
	
    }
    
}

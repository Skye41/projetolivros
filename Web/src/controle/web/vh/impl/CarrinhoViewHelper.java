package controle.web.vh.impl;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.web.vh.IViewHelper;
import core.aplicacao.Resultado;
import dominio.Carrinho;
import dominio.Cliente;
import dominio.Endereco;
import dominio.EntidadeDominio;
import dominio.ItemPedido;
import dominio.Pedido;
import javax.servlet.http.HttpSession;

public class CarrinhoViewHelper implements IViewHelper
{
    
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request)
    {
	String operacao = request.getParameter("operacao");
	Carrinho carrinho = (Carrinho)request.getSession().getAttribute("carrinho");
	if(operacao.equals("SALVAR"))
	{
	    if(carrinho == null)
        	carrinho = new Carrinho();
	    if(carrinho.getItens() == null)
        	carrinho.setItens(new ArrayList<ItemPedido>());
	    ItemPedido item = (ItemPedido)new ItemPedidoViewHelper().getEntidade(request);
	    for(ItemPedido i : carrinho.getItens())
	    {
		if(i.getLivro().getId() == item.getLivro().getId())
		{
                    i.setQtdeItem(item.getQtdeItem());
		    item = null;
		    break;
		}
	    }
	    if(item != null)
		carrinho.getItens().add(item);
	}
	else if(operacao.equals("EXCLUIR"))
	{
	    ItemPedido item = (ItemPedido)new ItemPedidoViewHelper().getEntidade(request);
	    for(ItemPedido i : carrinho.getItens())
	    {
		if(i.getLivro().getId() == item.getLivro().getId())
		{
		    carrinho.getItens().remove(i);
		    break;
		}
	    }
	}
        else
        {
	    if(carrinho == null)
        	carrinho = new Carrinho();
        }
	
	request.getSession().setAttribute("carrinho", carrinho);
	return carrinho;
    }
    
    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException
    {
	// Objeto que encaminha para a pr�xima p�gina
	RequestDispatcher d = null;
	String operacao = request.getParameter("operacao");
	String uri = request.getRequestURI();
        HttpSession session = request.getSession();
	
	if(uri.equals("/Web/Carrinho"))
	{
	    if(operacao.equals("SALVAR"))
		resultado.setMsg("Livro adicionado ao carrinho com sucesso!");
	    if(operacao.equals("EXCLUIR"))
		resultado.setMsg("Livro removido do carrinho com sucesso!");
	    if(operacao.equals("CONSULTAR"))
	    {
		Carrinho carrinho = (Carrinho)session.getAttribute("carrinho");
		if(carrinho != null && 
			carrinho.getItens() != null && !carrinho.getItens().isEmpty())
		    resultado.setMsg(null);
		else
		    resultado.setMsg("Não há itens no carrinho!");
	    }
	    session.setAttribute("resultado", resultado);
	    d = request.getRequestDispatcher("CarrinhoCompras.jsp");
	}
	if(uri.equals("/Web/FinalizarCompra"))
	{
	    if(operacao.equals("CONSULTAR"))
	    {
		Carrinho c = (Carrinho)request.getSession().getAttribute("carrinho");
                Pedido p = new Pedido();
                p.setItens(c.getItens());
                session.setAttribute("pedido", p);
                resultado.setMsg(null);
		session.setAttribute("resultado", resultado);
		
		if(session.getAttribute("usuario") != null)
		    d = request.getRequestDispatcher("FinalizarCompra.jsp");
		else
		    d = request.getRequestDispatcher("Login.jsp");
	    }
	}
	
	d.forward(request, response);
    }
    
}

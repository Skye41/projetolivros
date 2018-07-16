package controle.web.vh.impl;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.web.vh.IViewHelper;
import core.aplicacao.Resultado;
import dominio.CartaoPedido;
import dominio.Cliente;
import dominio.Cupom;
import dominio.Endereco;
import dominio.EntidadeDominio;
import dominio.ItemPedido;
import dominio.Pedido;
import dominio.StatusPedido;

public class PedidoViewHelper implements IViewHelper
{
    
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request)
    {
	String operacao = request.getParameter("operacao");
	Pedido pedido = (Pedido)request.getSession().getAttribute("pedido");
        Cliente cliente = null;
	try
        {
            cliente = (Cliente)request.getSession().getAttribute("usuario");
        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }
	
        if(!operacao.equals("VISUALIZAR"))
	{
            // Recuperar os valores da request
	    String idEntrega = request.getParameter("cbEntrega");
	    String[] idCartao = request.getParameterValues("cbCartao");
	    String[] valorCartao = request.getParameterValues("txtValor");
	    String idPromocional = request.getParameter("txtCupomPromo");
	    String idTroca = request.getParameter("txtCupomTroca");
	    //String idPedido = request.getParameter("txtIdPedido");
            String status = request.getParameter("cbStatus");
            String troca = request.getParameter("chEstoque");
            
            // Se os valores não estiverem vazios, colocar no objeto pedido
            if(pedido == null)
            {
                pedido = getPedido(request);
                if(pedido == null)
                    pedido = new Pedido();
            }
	    if(idEntrega != null && !idEntrega.trim().equals(""))
            {
                if(cliente != null)
                {
                    for(Endereco e : cliente.getEnderecos())
                    {
                        if(e.getId() == Integer.valueOf(idEntrega))
                        {
                            pedido.setEnderecoEntrega(e);
                            break;
                        }
                    }
                }
            }
	    if(idCartao != null && valorCartao != null)
	    {
		pedido.setCartoes(new ArrayList<CartaoPedido>());
		for(int i = 0; i < idCartao.length; i++)
		{
		    CartaoPedido cp = new CartaoPedido();
		    if(idCartao[i] != null && !idCartao[i].trim().equals("")
			    && valorCartao[i] != null && !valorCartao[i].trim().equals(""))
		    {
			cp.setId(Integer.valueOf(idCartao[i]));
			cp.setValorCartao(Double.valueOf(valorCartao[i]));
			pedido.getCartoes().add(cp);
		    }
		}
	    }
	    if(idPromocional != null && !idPromocional.trim().equals(""))
            {
                if(cliente != null)
                {
                    for(Cupom c : cliente.getCupons())
                    {
                        if(c.getId() == Integer.valueOf(idPromocional))
                        {
                            pedido.setPromocional(c);
                            break;
                        }
                    }
                }   
            }
	    if(idTroca != null && !idTroca.trim().equals(""))
            {
                if(cliente != null)
                {
                    for(Cupom c : cliente.getCupons())
                    {
                        if(c.getId() == Integer.valueOf(idTroca))
                        {
                            pedido.setTroca(c);
                            break;
                        }
                    }
                }
            }
	    if(cliente != null)
		pedido.setCliente(cliente);
            else
                pedido.setCliente(new Cliente());
            if(status != null && !status.trim().equals(""))
                pedido.setStatus(StatusPedido.valueOf(status));
            if(troca != null && troca.equals("on"))
                pedido.setEstoque(true);
            
            if(operacao.equals("SALVAR"))
            {
                pedido.setDtPedido(new Date());
                pedido.setStatus(StatusPedido.PROCESSAMENTO);
            }       
	}
        else
        {
            pedido = getPedido(request);
        }
	return pedido;
    }
    
    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException
    {
	// Objeto que encaminha para a próxima página
	RequestDispatcher d = null;
	// Operação que foi realizada		
	String operacao = request.getParameter("operacao");
	
	if(resultado.getMsg() == null)
	{
            if(operacao.equals("SALVAR"))
            {
                resultado.setMsg("Pedido registrado com sucesso!");
                request.setAttribute("pedido", resultado.getEntidades().get(0));
                request.getSession().removeAttribute("carrinho");
                request.getSession().removeAttribute("pedido");
                d = request.getRequestDispatcher("VisualizarCompra.jsp");
            }
            if(operacao.equals("CONSULTAR"))
            {
                request.getSession().setAttribute("pedidos", resultado);
                if(request.getSession().getAttribute("usuario") instanceof Cliente)
                    d = request.getRequestDispatcher("MeusPedidos.jsp");
                else
                    d = request.getRequestDispatcher("ConsultarPedidos.jsp");    
            }
            if(operacao.equals("VISUALIZAR"))
            {
                request.setAttribute("pedido", resultado.getEntidades().get(0));
                if(request.getSession().getAttribute("usuario") instanceof Cliente)
                    d = request.getRequestDispatcher("VisualizarCompra.jsp");
                else
                    d = request.getRequestDispatcher("VisualizarPedido.jsp");
            }
            if(operacao.equals("ALTERAR"))
            {
                if(request.getSession().getAttribute("usuario") instanceof Cliente)
                {
                    request.setAttribute("pedido", resultado.getEntidades().get(0));
                    d = request.getRequestDispatcher("VisualizarCompra.jsp");
                }
                else
                    d = request.getRequestDispatcher("/ConsultarPedidos?operacao=CONSULTAR");
            }
	}
        else if(resultado.getMsg() != null)
        {
            if(operacao.equals("ALTERAR"))
            {
                resultado.setMsg(null);
                request.getSession().setAttribute("resultado", resultado);
                d = request.getRequestDispatcher("FinalizarCompra.jsp");
            }
            if(operacao.equals("SALVAR"))
            {
                request.getSession().setAttribute("resultado", resultado);
                d = request.getRequestDispatcher("FinalizarCompra.jsp");
            }
        }
	d.forward(request,response);
    }
    
    public Pedido getPedido(HttpServletRequest request)
    {
        Resultado resultado = (Resultado) request.getSession().getAttribute("pedidos");
        String txtCodigo = request.getParameter("txtIdPedido");
        int id = 0;

        if(txtCodigo != null && !txtCodigo.trim().equals(""))
        {
            id = Integer.valueOf(txtCodigo);
        }
        if(resultado != null && resultado.getEntidades() != null)
        {
            for(EntidadeDominio e : resultado.getEntidades())
            {
                if(e.getId() == id)
                {
                    return (Pedido)e;
                }
            }
        }
        return null;
    }
}

package controle.web.vh.impl;

import controle.web.vh.IViewHelper;
import core.aplicacao.Resultado;
import dominio.Cliente;
import dominio.EntidadeDominio;
import dominio.ItemPedido;
import dominio.Pedido;
import dominio.StatusPedido;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TrocaViewHelper implements IViewHelper
{

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request)
    {
        String operacao = request.getParameter("operacao");
	Pedido pedido = null;
        Cliente cliente = null;
	try
        {
            cliente = (Cliente)request.getSession().getAttribute("usuario");
        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }
        Resultado resultado = (Resultado) request.getSession().getAttribute("pedidos");
        String txtCodigo = request.getParameter("txtIdPedido");
        int id = 0;

        if(txtCodigo != null && !txtCodigo.trim().equals(""))
        {
            id = Integer.valueOf(txtCodigo);
        }
        for(EntidadeDominio e : resultado.getEntidades())
        {
            if(e.getId() == id)
            {
                pedido = (Pedido)e;
                break;
            }
        }
        pedido.setStatus(StatusPedido.TROCA);

        if(operacao.equals("SALVAR"))
        {
            Pedido p = new Pedido();
            p.setCliente(pedido.getCliente());
            p.setDtPedido(new Date());
            p.setEnderecoEntrega(pedido.getEnderecoEntrega());
            p.setStatus(StatusPedido.TROCA);
            p.setValorFrete(0);
            p.setItens(new ArrayList<ItemPedido>());
            String[] idItens = request.getParameterValues("chIdItem");
            if(idItens != null && idItens.length > 0)
            {
                for(String s : idItens)
                {
                    if(s != null && !s.trim().equals(""))
                    {
                        id = Integer.valueOf(s);
                        for(ItemPedido ip : pedido.getItens())
                        {
                            if(ip.getId() == id)
                            {
                                p.getItens().add(ip);
                                break;
                            }
                        }
                    }
                }
            }
            pedido = p;
        }
        return pedido;
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        // Objeto que encaminha para a próxima página
	RequestDispatcher d = null;
	// Operação que foi realizada		
	String operacao = request.getParameter("operacao");
        
        if(resultado.getMsg() == null)
        {
            resultado.setMsg("Troca registrada com sucesso!");
            request.setAttribute("pedido", resultado.getEntidades().get(0));
            d = request.getRequestDispatcher("VisualizarCompra.jsp");
        }
        else
        {
            request.setAttribute("resultado", resultado);
            d = request.getRequestDispatcher("index.jsp");
        }
        d.forward(request,response);
    }
    
}

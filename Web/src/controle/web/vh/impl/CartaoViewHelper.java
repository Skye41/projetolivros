package controle.web.vh.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controle.web.vh.IViewHelper;
import core.aplicacao.Resultado;
import dominio.BandeiraCartao;
import dominio.Cartao;
import dominio.Cliente;
import dominio.EntidadeDominio;
import java.util.List;

public class CartaoViewHelper implements IViewHelper
{
    
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request)
    {
	String operacao = request.getParameter("operacao");
	Cartao cartao = null;
	
	if(!operacao.equals("VISUALIZAR"))
	{
	    // Recuperar os valores da request
	    String idUsuario = request.getParameter("txtIdCliente");
	    String idCartao = request.getParameter("txtIdCartao");
	    String bandeira = request.getParameter("cbBandeiraCartao");
	    String numero = request.getParameter("txtNumeroCartao");
	    String cvv = request.getParameter("txtCodigoSeguranca");
	    String nome = request.getParameter("txtNomeCartao");
	    String dtVcto = request.getParameter("txtDtVenc");
            String preferencial = request.getParameter("chPreferencial");
	    
	    cartao = new Cartao();
	    
	    // Se os valores não estiverem vazios, colocar no objeto cliente
	    if(idCartao != null && !idCartao.trim().equals(""))
		cartao.setId(Integer.valueOf(idUsuario));
	    if(idUsuario != null && !idUsuario.trim().equals(""))
		cartao.setCliente(new Cliente(Integer.valueOf(idUsuario)));
	    if(bandeira != null && !bandeira.trim().equals(""))
            {
                Resultado bandeiras = (Resultado)request.getSession().getAttribute("bandeiras");
                for(EntidadeDominio b : bandeiras.getEntidades())
                {
                    if(b.getId() == Integer.valueOf(bandeira))
                    {
                        cartao.setBandeira((BandeiraCartao)b);
                        break;
                    }
                }
            }
	    if(numero != null && !numero.trim().equals(""))
		cartao.setNumero(numero);
	    if(cvv != null && !cvv.trim().equals(""))
		cartao.setCodigoSeguranca(cvv);
	    if(nome != null && !nome.trim().equals(""))
		cartao.setNomeCartao(nome);
	    if(dtVcto != null && !dtVcto.trim().equals(""))
	    {
		DateFormat f = DateFormat.getDateInstance();
		try
		{
		    cartao.setDtVencimento(f.parse(dtVcto));
		}
		catch(ParseException e)
		{
		    cartao.setDtVencimento(null);
		}
	    }
            if(preferencial != null && preferencial.equals("on"))
                cartao.setPreferencial(true);
	}
	else
	{
	    HttpSession session = request.getSession();
	    Resultado resultado = (Resultado) session.getAttribute("resultado");
	    String txtIdCartao = request.getParameter("txtIdCartao");
	    int idCartao = 0;
	    Cliente c = (Cliente)resultado.getEntidades().get(0);
	    
	    if(txtIdCartao != null && !txtIdCartao.trim().equals(""))
		idCartao = Integer.valueOf(txtIdCartao);
	    for (EntidadeDominio e : c.getCartoes())
	    {
		if(e.getId() == idCartao)
		    cartao = (Cartao)e;
	    }
	}
	return cartao;
    }
    
    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException
    {
	// Objeto que encaminha para a próxima página
	RequestDispatcher d = null;
	// Operação que foi realizada
	String operacao = request.getParameter("operacao");
        Cliente cliente = null;
	try
        {
            cliente = (Cliente)request.getSession().getAttribute("usuario");
        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        if(resultado.getMsg() == null)
	{
	    if(operacao.equals("SALVAR"))
            {
		resultado.setMsg("Cartão cadastrado com sucesso!");
                if(cliente != null)
                    cliente.getCartoes().add((Cartao)resultado.getEntidades().get(0));
            }
            request.getSession().setAttribute("resultado", resultado);
            d = request.getRequestDispatcher("index.jsp");            
	}
	if(resultado.getMsg() == null && operacao.equals("EXCLUIR"))
	{
	    request.getSession().setAttribute("resultado", null);
	    d = request.getRequestDispatcher("ConsultarClientes.jsp");  
	}
	if(resultado.getMsg() == null && operacao.equals("VISUALIZAR"))
	{
	    request.setAttribute("cartao", resultado.getEntidades().get(0));
	    d = request.getRequestDispatcher("SalvarCartao.jsp");  			
	}
	
	if(resultado.getMsg() == null && operacao.equals("CONSULTAR"))
	{
	    Resultado bandeiras = new Resultado();
	    bandeiras.setEntidades(new ArrayList<EntidadeDominio>());
	    for(EntidadeDominio e : resultado.getEntidades())
	    {
		if(e instanceof BandeiraCartao)
		    bandeiras.getEntidades().add(e);
	    }
	    request.getSession().setAttribute("bandeiras", bandeiras);
	    d = request.getRequestDispatcher("SalvarCartao.jsp");
	}
	d.forward(request, response);
    } //setView
}

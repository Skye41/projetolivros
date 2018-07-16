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
import dominio.Cliente;
import dominio.Endereco;
import dominio.EntidadeDominio;
import dominio.Genero;
import dominio.Telefone;
import dominio.TipoTelefone;
import dominio.Usuario;

public class ClienteViewHelper  implements IViewHelper
{

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request)
    {
	String operacao = request.getParameter("operacao");
	Cliente cliente = getUsuario(request);
	
	if(!operacao.equals("VISUALIZAR"))
	{
	    // Recuperar os valores da request 
	    String id = request.getParameter("txtCodigo");
	    String nomeCliente = request.getParameter("txtNomeCliente");
	    String dtNasc = request.getParameter("txtDtNasc");
	    String genero = request.getParameter("cbGenero");
	    String cpf = request.getParameter("txtCpf");
	    String ranking = request.getParameter("txtRanking");
	    String status = request.getParameter("cbStatus");
	    String idTelefone = request.getParameter("txtIdTelefone");
	    String tipoTelefone = request.getParameter("cbTipoTelefone");
	    String dddTelefone = request.getParameter("txtDDD");
	    String numTelefone = request.getParameter("txtNumeroTelefone");
	    
	    if(cliente == null)
                cliente = new Cliente();
	    
	    // Se os valores não estiverem vazios, colocar no objeto cliente
	    if(id != null && !id.trim().equals(""))
		cliente.setId(Integer.valueOf(id));
	    if(nomeCliente != null && !nomeCliente.trim().equals(""))
		cliente.setNome(nomeCliente);
	    if(dtNasc != null && !dtNasc.trim().equals(""))
	    {
		DateFormat f = DateFormat.getDateInstance();
		try
		{
		    cliente.setDtNascimento(f.parse(dtNasc));
		}
		catch(ParseException e)
		{
		    cliente.setDtNascimento(null);
		}
	    }
		
	    if(genero != null && !genero.trim().equals(""))
		cliente.setGenero(Genero.valueOf(genero));
	    if(cpf != null && !cpf.trim().equals(""))
		cliente.setCPF(cpf);
	    if(ranking != null && !ranking.trim().equals(""))
		cliente.setRanking(Integer.valueOf(ranking));
	    if(status != null && !status.trim().equals(""))
		cliente.setStatus(Boolean.valueOf(status));
	    
	    // TELEFONE
	    cliente.setTelefones(new ArrayList<Telefone>());
	    Telefone tel = new Telefone();
	    if(idTelefone != null && !idTelefone.trim().equals(""))
		tel.setId(Integer.valueOf(idTelefone));
	    if(tipoTelefone != null && !tipoTelefone.trim().equals(""))
		tel.setTipo(TipoTelefone.valueOf(tipoTelefone));
	    if(dddTelefone != null && !dddTelefone.trim().equals(""))
		tel.setDdd(dddTelefone);
	    if(numTelefone != null && !numTelefone.trim().equals(""))
		tel.setNumero(numTelefone);
	    cliente.addTelefone(tel);
	    
	    if(cliente.getEnderecos() == null)
            {
                EnderecoViewHelper end = new EnderecoViewHelper();
                Endereco cadastro = (Endereco)end.getEntidade(request);
                cliente.setEnderecos(new ArrayList<Endereco>());
                cliente.addEndereco(cadastro);
            }
            if(cliente.getUsuario() == null)
            {
                UsuarioViewHelper user = new UsuarioViewHelper();
                cliente.setUsuario((Usuario)user.getEntidade(request));
            }
	}
	else
	{
	    HttpSession session = request.getSession();
            if(cliente == null)
            {
                Resultado resultado = (Resultado) session.getAttribute("clientes");
                String txtCodigo = request.getParameter("txtCodigo");
                int id = 0;

                if(txtCodigo != null && !txtCodigo.trim().equals(""))
                        id = Integer.valueOf(txtCodigo);
                for (EntidadeDominio e : resultado.getEntidades())
                {
                    if(e.getId() == id)
                        cliente = (Cliente)e;
                }
            }
	}
	return cliente;
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
		resultado.setMsg("Cliente cadastrado com sucesso!");
	    request.getSession().setAttribute("clientes", resultado);
	    d = request.getRequestDispatcher("index.jsp");  			
	}
	
	if(resultado.getMsg() == null && operacao.equals("ALTERAR"))
	{
            Cliente c = getUsuario(request);
	    if(c != null)
            {
                request.getSession().setAttribute("usuario", resultado.getEntidades().get(0));
            }//request.setAttribute("cliente", resultado.getEntidades().get(0));
	    d = request.getRequestDispatcher("ConsultarClientes.jsp");
	}
	
	if(resultado.getMsg() == null && operacao.equals("VISUALIZAR"))
	{
	    request.setAttribute("cliente", resultado.getEntidades().get(0));
	    if(request.getParameter("alterar") != null)
		d = request.getRequestDispatcher("SalvarCliente.jsp");
	    else
		d = request.getRequestDispatcher("VisualizarCliente.jsp");  			
	}
	
	if(resultado.getMsg() == null && operacao.equals("EXCLUIR"))
	{
	    request.getSession().setAttribute("resultado", null);
	    d = request.getRequestDispatcher("ConsultarClientes.jsp");  
	}
	
	/*if(resultado.getMsg() != null){
	    if(operacao.equals("SALVAR") || operacao.equals("ALTERAR")){
		request.getSession().setAttribute("resultado", resultado);
		d= request.getRequestDispatcher("index.jsp");  	
	    }
	}*/
	d.forward(request,response); 
    }
    
    public Cliente getUsuario(HttpServletRequest request)
    {
        if(request.getSession().getAttribute("usuario") instanceof Cliente)
            return (Cliente)request.getSession().getAttribute("usuario");
        else
            return null;
    }
}

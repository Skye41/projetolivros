package controle.web.vh.impl;

import java.io.IOException;

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
import dominio.TipoEndereco;

public class EnderecoViewHelper implements IViewHelper
{
    
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request)
    {
	String operacao = request.getParameter("operacao");
	Endereco endereco = null;
	
	if(!operacao.equals("VISUALIZAR"))
	{
	    // Recuperar os valores da request 
	    String id = request.getParameter("txtIdEndereco");
	    String tipoEnd = request.getParameter("cbTipoEndereco");
	    String tipoRes = request.getParameter("txtTipoResidencia");
	    String tipoLog = request.getParameter("txtTipoLogradouro");
	    String logradouro = request.getParameter("txtLogradouro");
	    String numero = request.getParameter("txtNumeroCasa");
	    String bairro = request.getParameter("txtBairro");
	    String cep = request.getParameter("txtCep");
	    String estado = request.getParameter("txtEstado");
	    String cidade = request.getParameter("txtCidade");
	    String pais = request.getParameter("txtPais");
	    String obs = request.getParameter("txtOBS");
	    String idCliente = request.getParameter("txtIdCliente");
	    
	    endereco = new Endereco();
	    // Se os valores n�o estiverem vazios, colocar no objeto cliente
	    if(id != null && !id.trim().equals(""))
		endereco.setId(Integer.valueOf(id));
	    if(tipoEnd != null && !tipoEnd.trim().equals(""))
		endereco.setTipoEndereco(TipoEndereco.valueOf(tipoEnd));
	    if(tipoRes != null && !tipoRes.trim().equals(""))
		endereco.setTipoResidencia(tipoRes);
	    if(tipoLog != null && !tipoLog.trim().equals(""))
		endereco.setTipoLogradouro(tipoLog);
	    if(logradouro != null && !logradouro.trim().equals(""))
		endereco.setLogradouro(logradouro);
	    if(numero != null && !numero.trim().equals(""))
		endereco.setNumero(numero);
	    if(bairro != null && !bairro.trim().equals(""))
		endereco.setBairro(bairro);
	    if(cep != null && !cep.trim().equals(""))
		endereco.setCep(cep);
	    if(estado != null && !estado.trim().equals(""))
		endereco.setEstado(estado);
	    if(cidade != null && !cidade.trim().equals(""))
		endereco.setCidade(cidade);
	    if(pais != null && !pais.trim().equals(""))
		endereco.setPais(pais);
	    if(obs != null && !obs.trim().equals(""))
		endereco.setObservacoes(obs);
	    if(idCliente != null && ! idCliente.trim().equals(""))
	    {
		endereco.setCliente(new Cliente(Integer.valueOf(idCliente)));
	    }
	}
	else
	{
	    HttpSession session = request.getSession();
	    Resultado resultado = (Resultado) session.getAttribute("resultado");
	    String txtIdEndereco = request.getParameter("txtIdEndereco");
	    int idEndereco = 0;
	    Cliente c = (Cliente)resultado.getEntidades().get(0);
	    
	    if(txtIdEndereco != null && !txtIdEndereco.trim().equals(""))
		idEndereco = Integer.valueOf(txtIdEndereco);
	    for (EntidadeDominio e : c.getEnderecos())
	    {
		if(e.getId() == idEndereco)
		    endereco = (Endereco)e;
	    }
	}
	return endereco;
    }
    
    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException
    {
	// Objeto que encaminha para a pr�xima p�gina
	RequestDispatcher d = null;
	// Opera��o que foi realizada
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
        if(cliente != null)
        {
            cliente.getEnderecos().add((Endereco)resultado.getEntidades().get(0));
            request.getSession().setAttribute("usuario", cliente);
        }
	
	if(resultado.getMsg() == null)
	{
	    if(operacao.equals("SALVAR"))
		resultado.setMsg("Endere�o cadastrado com sucesso!");
	    d = request.getRequestDispatcher("VisualizarCliente.jsp");  			
	}
	
	if(resultado.getMsg() == null && operacao.equals("ALTERAR"))	    
	    d = request.getRequestDispatcher("index.jsp");  
	
	if(resultado.getMsg() == null && operacao.equals("VISUALIZAR"))
	{
	    request.setAttribute("endereco", resultado.getEntidades().get(0));
	    d = request.getRequestDispatcher("SalvarEndereco.jsp");  			
	}
	
	if(resultado.getMsg() == null && operacao.equals("EXCLUIR"))
	{
	    request.getSession().setAttribute("resultado", null);
	    d = request.getRequestDispatcher("index.jsp");  
	}
	
	/*if(resultado.getMsg() != null){
	    if(operacao.equals("SALVAR") || operacao.equals("ALTERAR")){
		request.getSession().setAttribute("resultado", resultado);
		d= request.getRequestDispatcher("index.jsp");  	
	    }
	}*/
	d.forward(request,response); 
    }
    
}

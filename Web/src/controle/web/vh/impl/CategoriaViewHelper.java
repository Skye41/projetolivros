package controle.web.vh.impl;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controle.web.vh.IViewHelper;
import core.aplicacao.Resultado;
import dominio.CategoriaLivro;
import dominio.EntidadeDominio;
import dominio.Livro;

public class CategoriaViewHelper implements IViewHelper
{
    
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request)
    {
	String operacao = request.getParameter("operacao");
	CategoriaLivro categoria = null;
	if(!operacao.equals("VISUALIZAR"))
	{
	    // Recuperar os valores da request 
	    String id = request.getParameter("txtIdCategoria");
	    
	    categoria = new CategoriaLivro();
	    
	    if(id != null && !id.trim().equals(""))
		categoria.setId(Integer.valueOf(id));
	}
	else
	{
	    HttpSession session = request.getSession();
	    Resultado resultado = (Resultado) session.getAttribute("resultado");
	    categoria = (CategoriaLivro)resultado.getEntidades().get(0);
	}
	return categoria;
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
	    d = request.getRequestDispatcher("ConsultarClientes.jsp");  			
	}
	
	if(resultado.getMsg() == null && operacao.equals("ALTERAR"))
	{
	    //request.setAttribute("cliente", resultado.getEntidades().get(0));
	    d = request.getRequestDispatcher("ConsultarClientes.jsp");
	}
	
	if(resultado.getMsg() == null && operacao.equals("VISUALIZAR"))
	{
	    request.setAttribute("usuario", resultado.getEntidades().get(0));
	    d = request.getRequestDispatcher("SalvarSenha.jsp");  			
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
	if(resultado.getMsg() == null && operacao.equals("CONSULTAR"))
	{
	    Resultado livros = new Resultado();
	    Resultado categorias = new Resultado();
	    livros.setEntidades(new ArrayList<EntidadeDominio>());
	    categorias.setEntidades(new ArrayList<EntidadeDominio>());
	    
	    for(EntidadeDominio e : resultado.getEntidades())
	    {
		if(e instanceof CategoriaLivro)
		    categorias.getEntidades().add(e);
		if(e instanceof Livro)
		    livros.getEntidades().add(e);
	    }
	    request.setAttribute("categorias", categorias);
	    request.setAttribute("livros", livros);
	    d = request.getRequestDispatcher("SalvarLivro.jsp");
	}
	d.forward(request,response);
    }    
}

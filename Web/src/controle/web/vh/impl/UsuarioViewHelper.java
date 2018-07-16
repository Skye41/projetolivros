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
import dominio.EntidadeDominio;
import dominio.TipoUsuario;
import dominio.Usuario;

public class UsuarioViewHelper implements IViewHelper
{
    
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request)
    {
	String operacao = request.getParameter("operacao");
	Usuario usuario = null;
	
	if(!operacao.equals("VISUALIZAR"))
	{
	 // Recuperar os valores da request 
	    String id = request.getParameter("txtIdUsuario");
	    String email = request.getParameter("txtEmail");
	    String senha = request.getParameter("txtSenha");
	    String confirma = request.getParameter("txtConfirma");
	    String tipo = request.getParameter("txtTipoUsuario");
	    
	    usuario = new Usuario();
	    
	 // Se os valores n�o estiverem vazios, colocar no objeto cliente
	    if(id != null && !id.trim().equals(""))
		usuario.setId(Integer.valueOf(id));
	    if(email != null && !email.trim().equals(""))
		usuario.setEmail(email);
	    if(senha != null && !senha.trim().equals(""))
		usuario.setSenha(senha);
	    if(confirma != null && !confirma.trim().equals(""))
		usuario.setConfirmacaoSenha(confirma);
	    if(tipo != null && !confirma.trim().equals(""))
		usuario.setTipo(TipoUsuario.valueOf(tipo));
	    else
		usuario.setTipo(TipoUsuario.CLIENTE);
	}
	else
	{
	    HttpSession session = request.getSession();
	    Resultado resultado = (Resultado) session.getAttribute("resultado");
	    Cliente c = (Cliente)resultado.getEntidades().get(0);
	    usuario = c.getUsuario();
	}
	if(operacao.equals("EXCLUIR") && request.getRequestURI().equals("/Web/Login"))
	{
	    request.getSession().removeAttribute("usuario");
            request.getSession().removeAttribute("carrinho");
	}
	
	return usuario;
    }
    
    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException
    {
	// Objeto que encaminha para a pr�xima p�gina
	RequestDispatcher d = null;
	// Opera��o que foi realizada
	String operacao = request.getParameter("operacao");
		
	if(resultado.getMsg() == null)
	{
	    if(resultado.getEntidades() != null && !resultado.getEntidades().isEmpty())
	    {
		EntidadeDominio usuario = resultado.getEntidades().get(0);
		request.getSession().setAttribute("usuario", usuario);
	    }
	    else
	    {
		resultado.setMsg("Usuário/Senha inválidos");
		request.setAttribute("resultado", resultado);
	    }
	    d = request.getRequestDispatcher("index.jsp");
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
	    request.getSession().invalidate();
	    d = request.getRequestDispatcher("index.jsp");  
	}
	if(resultado.getMsg() != null && operacao.equals("CONSULTAR"))
	{
	    d = request.getRequestDispatcher("Login.jsp");  	
	}
	d.forward(request,response); 
    }
    
}

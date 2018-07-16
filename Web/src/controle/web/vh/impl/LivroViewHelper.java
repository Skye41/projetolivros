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
import dominio.Autor;
import dominio.CategoriaLivro;
import dominio.Dimensoes;
import dominio.Editora;
import dominio.EntidadeDominio;
import dominio.GrupoPrecificacao;
import dominio.Livro;
import dominio.StatusLivro;

public class LivroViewHelper implements IViewHelper
{   
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request)
    {
	String operacao = request.getParameter("operacao");
	Livro livro = null;
	
	if(!operacao.equals("VISUALIZAR"))
	{
	    // Recuperar os valores da request 
	    String id = request.getParameter("txtCodigo"); 
	    String titulo = request.getParameter("txtTitulo");
	    String editora = request.getParameter("cbEditora");
	    String[] autores = request.getParameterValues("cbAutor");
	    String[] categorias = request.getParameterValues("cbCategoria");
	    String sinopse = request.getParameter("txtSinopse");
	    String paginas = request.getParameter("txtPaginas");
	    String edicao = request.getParameter("txtEdicao");
	    String ano = request.getParameter("txtAno");
	    String isbn = request.getParameter("txtISBN");
	    String codigoBarras = request.getParameter("txtBarras");
	    String altura = request.getParameter("txtAltura");
	    String largura = request.getParameter("txtLargura");
	    String peso = request.getParameter("txtPeso");
	    String profundidade = request.getParameter("txtProfundidade");
	    String precoCusto = request.getParameter("txtCusto");
	    String grupo = request.getParameter("cbGrupo");
	    String precoVenda = request.getParameter("txtVenda");
	    String qtdeEstoque = request.getParameter("txtQtdeEstoque");
	    String qtdeMinimaVenda = request.getParameter("txtQtdeVenda");
	    String statusLivro = request.getParameter("cbStatus");
	    String justificativa = request.getParameter("cbJustificativa");
	    
	    livro = new Livro();
	    
	    // Se os valores não estiverem vazios, colocar no objeto livro
	    if(id != null && !id.trim().equals(""))
		livro.setId(Integer.valueOf(id));
	    
	    if(titulo != null && !titulo.trim().equals(""))
		livro.setTitulo(titulo);
	    
	    if(editora != null && !editora.trim().equals(""))
	    {
		livro.setEditora(new Editora());
		livro.getEditora().setId(Integer.valueOf(editora));
	    }
	    
	    livro.setAutores(new ArrayList<Autor>());
	    if(autores != null && autores.length > 0)
	    {
		for (String autor : autores)
		{
		    if(autor != null && !autor.trim().equals(""));
			livro.getAutores().add(new Autor(Integer.valueOf(autor)));
		}
	    }
	    
	    livro.setCategorias(new ArrayList<CategoriaLivro>());
	    if(categorias != null && categorias.length > 0)
	    {
		CategoriaLivro c = null;
		for(int i = 0; i < categorias.length; i++)
		{
		    String cat = categorias[i];
		    if(cat != null && !cat.trim().equals(""))
		    {
			c = new CategoriaLivro(Integer.valueOf(cat));
			livro.getCategorias().add(c);
		    }
		}
	    }
	    
	    if(sinopse != null && !sinopse.trim().equals(""))
		livro.setSinopse(sinopse);
	    
	    if(paginas != null && !paginas.trim().equals(""))
		livro.setPaginas(Integer.valueOf(paginas));
	    
	    if(edicao != null && !edicao.trim().equals(""))
		livro.setEdicao(Integer.valueOf(edicao));
	    
	    if(ano != null && !ano.trim().equals(""))
		livro.setAno(Integer.valueOf(ano));
	    
	    if(isbn != null && !isbn.trim().equals(""))
		livro.setIsbn(isbn);
	    
	    if(codigoBarras != null && !codigoBarras.trim().equals(""))
		livro.setCodigoBarras(codigoBarras);
	    
	    livro.setDimensoes(new Dimensoes());
	    if(altura != null && !altura.trim().equals(""))
		livro.getDimensoes().setAltura(Double.valueOf(altura));
	    if(largura != null && !largura.trim().equals(""))
		livro.getDimensoes().setLargura(Double.valueOf(largura));
	    if(peso != null && !peso.trim().equals(""))
		livro.getDimensoes().setPeso(Double.valueOf(peso));
	    if(profundidade != null && !profundidade.trim().equals(""))
		livro.getDimensoes().setProfundidade(Double.valueOf(profundidade));
	    
	    if(precoCusto != null && !precoCusto.trim().equals(""))
		livro.setPrecoCusto(Double.valueOf(precoCusto));
	    
	    if(grupo != null && !grupo.trim().equals(""))
	    {
		livro.setGrupo(new GrupoPrecificacao());
		livro.getGrupo().setId(Integer.valueOf(grupo));
	    }
	    
	    if(precoVenda != null && !precoVenda.trim().equals(""))
		livro.setPrecoVenda(Double.valueOf(precoVenda));
	    
	    if(qtdeEstoque != null && !qtdeEstoque.trim().equals(""))
		livro.setQtdeEstoque(Integer.valueOf(qtdeEstoque));
	    
	    if(qtdeMinimaVenda != null && !qtdeMinimaVenda.trim().equals(""))
		livro.setQtdeMinimaVenda(Integer.valueOf(qtdeMinimaVenda));
	    
	    if(statusLivro != null && !statusLivro.trim().equals(""))
	    {
		livro.setStatusLivro(new StatusLivro());
		livro.getStatusLivro().setStatusLivro(Boolean.valueOf(statusLivro));
		
		if(justificativa != null && !justificativa.trim().equals(""))
			livro.getStatusLivro().setId(Integer.valueOf(justificativa));
	    }
	}
	else
	{
	    HttpSession session = request.getSession();
	    Resultado resultado = (Resultado) session.getAttribute("livros");
	    String txtCodigo = request.getParameter("txtCodigo");
	    int id = 0;
	    
	    if(txtCodigo != null && !txtCodigo.trim().equals(""))
		    id = Integer.valueOf(txtCodigo);
	    for (EntidadeDominio e : resultado.getEntidades())
	    {
		if(e.getId() == id)
		    livro = (Livro)e;
	    }
	}
	return livro;
    }
    
    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException
    {
	// Objeto que encaminha para a próxima página
	RequestDispatcher d = null;
	// Operação que foi realizada
	String operacao = request.getParameter("operacao");
	// URI que chamou a servlet
	String uri = request.getRequestURI();
	
	if(resultado.getMsg() == null)
	{
	    if(operacao.equals("SALVAR"))
		resultado.setMsg("Livro cadastrado com sucesso!");
	    request.getSession().setAttribute("resultado", resultado);
	    d = request.getRequestDispatcher("ConsultarLivros.jsp");  			
	}
	if(resultado.getMsg() == null && operacao.equals("EXCLUIR"))
	{
	    request.getSession().setAttribute("resultado", null);
	    d = request.getRequestDispatcher("ConsultarLivros.jsp");  
	}
	if(resultado.getMsg() == null && operacao.equals("VISUALIZAR"))
	{
	    request.setAttribute("livro", resultado.getEntidades().get(0));
	    if(uri.equals("/Web/VisualizarLivro"))
		d = request.getRequestDispatcher("SalvarLivro.jsp");
	    if(uri.equals("/Web/DetalhesLivro"))
		d = request.getRequestDispatcher("DetalhesLivro.jsp");
	}
	
	if(resultado.getMsg() == null && operacao.equals("CONSULTAR"))
	{
	    prepararSelect(resultado, request);
	    if(uri.equals("/Web/SalvarLivro"))
		d = request.getRequestDispatcher("SalvarLivro.jsp");
	    if(uri.equals("/Web/ConsultarLivros"))
		d = request.getRequestDispatcher("ConsultarLivros.jsp");
	    if(uri.equals("/Web/ComprarLivros"))
		d = request.getRequestDispatcher("ComprarLivros.jsp");
	}
	d.forward(request, response);
    } //setView
    
    public void prepararSelect(Resultado resultado, HttpServletRequest request)
    {
	Resultado livros = new Resultado();
	Resultado editoras = new Resultado();
	Resultado autores = new Resultado();
	Resultado categorias = new Resultado();
	Resultado grupos = new Resultado();
	Resultado status = new Resultado();
	    
	livros.setEntidades(new ArrayList<EntidadeDominio>());
	editoras.setEntidades(new ArrayList<EntidadeDominio>());
	autores.setEntidades(new ArrayList<EntidadeDominio>());
	categorias.setEntidades(new ArrayList<EntidadeDominio>());
	grupos.setEntidades(new ArrayList<EntidadeDominio>());
	status.setEntidades(new ArrayList<EntidadeDominio>());
	    
	for(EntidadeDominio e : resultado.getEntidades())
	{
       	    if(e instanceof Livro)
       		livros.getEntidades().add(e);
       	    if(e instanceof Editora)
       		editoras.getEntidades().add(e);
       	    if(e instanceof Autor)
       		autores.getEntidades().add(e);
       	    if(e instanceof CategoriaLivro)
       		categorias.getEntidades().add(e);
       	    if(e instanceof GrupoPrecificacao)
       		grupos.getEntidades().add(e);
       	    if(e instanceof StatusLivro)
       		status.getEntidades().add(e);
        }
	request.getSession().setAttribute("editoras", editoras);
	request.getSession().setAttribute("autores", autores);
	request.getSession().setAttribute("categorias", categorias);
	request.getSession().setAttribute("grupos", grupos);
	request.getSession().setAttribute("status", status);
	request.getSession().setAttribute("livros", livros);
    }
}

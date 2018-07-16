package controle.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.web.command.ICommand;
import controle.web.command.impl.AlterarCommand;
import controle.web.command.impl.ConsultarCommand;
import controle.web.command.impl.ExcluirCommand;
import controle.web.command.impl.SalvarCommand;
import controle.web.command.impl.VisualizarCommand;
import controle.web.vh.IViewHelper;
import controle.web.vh.impl.AnaliseViewHelper;
import controle.web.vh.impl.CarrinhoViewHelper;
import controle.web.vh.impl.CartaoViewHelper;
import controle.web.vh.impl.ClienteViewHelper;
import controle.web.vh.impl.EnderecoViewHelper;
import controle.web.vh.impl.LivroViewHelper;
import controle.web.vh.impl.PagamentoTesteViewHelper;
import controle.web.vh.impl.PedidoViewHelper;
import controle.web.vh.impl.TrocaViewHelper;
import controle.web.vh.impl.UsuarioViewHelper;
import core.aplicacao.Resultado;
import dominio.EntidadeDominio;

public class Servlet extends HttpServlet 
{
    private static final long serialVersionUID = 1L;
    private static Map<String, ICommand> commands;
    private static Map<String, IViewHelper> vhs; 
       
    public Servlet() 
    {
	// Mapa de Commands
        commands = new HashMap<>();
        commands.put("SALVAR", new SalvarCommand());
        commands.put("EXCLUIR", new ExcluirCommand());
        commands.put("CONSULTAR", new ConsultarCommand());
        commands.put("VISUALIZAR", new VisualizarCommand());
        commands.put("ALTERAR", new AlterarCommand());
        
        // Mapa de ViewHelpers
        vhs = new HashMap<>();
        vhs.put("/Web/SalvarLivro", new LivroViewHelper());
        vhs.put("/Web/SalvarCliente", new ClienteViewHelper());
        vhs.put("/Web/VisualizarCliente", new ClienteViewHelper());
        vhs.put("/Web/ConsultarClientes", new ClienteViewHelper());
        vhs.put("/Web/SalvarEndereco", new EnderecoViewHelper());
        vhs.put("/Web/SalvarSenha", new UsuarioViewHelper());
        vhs.put("/Web/CadastrarLivro", new LivroViewHelper());
        vhs.put("/Web/ConsultarLivros", new LivroViewHelper());
        vhs.put("/Web/VisualizarLivro", new LivroViewHelper());
        vhs.put("/Web/ComprarLivros", new LivroViewHelper());
        vhs.put("/Web/DetalhesLivro", new LivroViewHelper());
        vhs.put("/Web/SalvarCartao", new CartaoViewHelper());
        vhs.put("/Web/Carrinho", new CarrinhoViewHelper());
        vhs.put("/Web/FinalizarCompra", new CarrinhoViewHelper());
        vhs.put("/Web/SalvarPedido", new PedidoViewHelper());
        vhs.put("/Web/Login", new UsuarioViewHelper());
        vhs.put("/Web/ConsultarPedidos", new PedidoViewHelper());
        vhs.put("/Web/Troca", new TrocaViewHelper());
        vhs.put("/Web/ValidarPagamento", new PagamentoTesteViewHelper());
        vhs.put("/Web/Dashboard", new AnaliseViewHelper());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException 
    {
	doProcessRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException 
    {
	doProcessRequest(request, response);
    }
    
    protected void doProcessRequest(HttpServletRequest request,	HttpServletResponse response) 
	    throws ServletException, IOException
    {
	// URI que chamou a servlet
	String uri = request.getRequestURI();
	// Operação a ser executada
	String operacao = request.getParameter("operacao");
	// Instancia a ViewHelper
	IViewHelper vh = vhs.get(uri);
	// Envia a request e recebe a entidade
	EntidadeDominio entidade = vh.getEntidade(request);
	// Instancia o command
	ICommand command = commands.get(operacao);
	// Executa a operação
	Resultado resultado = command.execute(entidade);
	// Coloca os dados na request/sessão e encaminha para a próxima página
	vh.setView(resultado, request, response);
    }   
}
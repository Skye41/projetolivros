package core.impl.controle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.IDAO;
import core.IFachada;
import core.IStrategy;
import core.aplicacao.Resultado;
import core.impl.dao.AnaliseDAO;
import core.impl.dao.CarrinhoDAO;
import core.impl.dao.CartaoDAO;
import core.impl.dao.ClienteDAO;
import core.impl.dao.EnderecoDAO;
import core.impl.dao.LivroDAO;
import core.impl.dao.LoginDAO;
import core.impl.dao.PagamentoTesteDAO;
import core.impl.dao.PedidoDAO;
import core.impl.dao.TelefoneDAO;
import core.impl.negocio.CalcularFrete;
import core.impl.negocio.GerarCupomTroca;
import core.impl.negocio.ValidarPagamento;
import core.impl.negocio.ValidarSenha;
import core.impl.negocio.VerificarPagamento;
import dominio.Analise;
import dominio.Carrinho;
import dominio.Cartao;
import dominio.Cliente;
import dominio.Endereco;
import dominio.EntidadeDominio;
import dominio.Livro;
import dominio.PagamentoTeste;
import dominio.Pedido;
import dominio.Telefone;
import dominio.Usuario;

public class Fachada implements IFachada
{
    public Map<String, IDAO> daos;
    public Map<String, Map<String, List<IStrategy>>> rns;
    public Resultado resultado;
    
    public Fachada()
    {
	// Mapa de DAOs
	daos = new HashMap<String, IDAO>();
	daos.put(Livro.class.getName(), new LivroDAO());
	daos.put(Cliente.class.getName(), new ClienteDAO());
	daos.put(Telefone.class.getName(), new TelefoneDAO());
	daos.put(Endereco.class.getName(), new EnderecoDAO());
	daos.put(Cartao.class.getName(), new CartaoDAO());
	daos.put(Pedido.class.getName(), new PedidoDAO());
	daos.put(Usuario.class.getName(), new LoginDAO());
        daos.put(PagamentoTeste.class.getName(), new PagamentoTesteDAO());
        daos.put(Analise.class.getName(), new AnaliseDAO());
        daos.put(Carrinho.class.getName(), new CarrinhoDAO());
	
	// Mapa de Strategies
	rns = new HashMap<String, Map<String, List<IStrategy>>>();

        // Lista de strategies pra salvar Cliente
	List<IStrategy> rnsSalvarCliente = new ArrayList<IStrategy>();
	rnsSalvarCliente.add(new ValidarSenha());
        
	// Lista de strategies pra salvar Pedido
	List<IStrategy> rnsSalvarPedido = new ArrayList<IStrategy>();
        rnsSalvarPedido.add(new VerificarPagamento());
        rnsSalvarPedido.add(new GerarCupomTroca());
	// Lista de strategies pra alterar Pedido
	List<IStrategy> rnsAlterarPedido = new ArrayList<IStrategy>();
	rnsAlterarPedido.add(new CalcularFrete());
        rnsAlterarPedido.add(new GerarCupomTroca());
	// Mapa com todas as strategies de Pedido
	Map<String, List<IStrategy>> rnsPedido = new HashMap<String, List<IStrategy>>();
	rnsPedido.put("SALVAR", rnsSalvarPedido);
	rnsPedido.put("ALTERAR", rnsAlterarPedido);
        
        // Lista de strategies pra validar Pagamento
        List<IStrategy> rnsValidarPagamento = new ArrayList<IStrategy>();
	rnsValidarPagamento.add(new ValidarPagamento());
	// Mapa com todas as strategies de Pagamento
        Map<String, List<IStrategy>> rnsPagamento = new HashMap<String, List<IStrategy>>();
	rnsPagamento.put("CONSULTAR", rnsValidarPagamento);
	
        // Adicionar todas as strategies à lista
	rns.put(Pedido.class.getName(), rnsPedido);
        rns.put(PagamentoTeste.class.getName(), rnsPagamento);
    }
    
    @Override
    public Resultado salvar(EntidadeDominio entidade)
    {
	resultado = new Resultado();
	// Recuperar o nome da classe
	String nomeClasse = entidade.getClass().getName();
	// Executar regras de negócio
	String msg = executarRegras(entidade, "SALVAR");
	
	// Se não houver msg de erro
	if(msg == null)
	{
	    IDAO dao = daos.get(nomeClasse);
	    try
	    {
		// Salvar a entidade
		dao.salvar(entidade);
		List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
		// Adicionar entidade à lista
		entidades.add(entidade);
		// Adicionar lista ao resultado
		resultado.setEntidades(entidades);
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
		resultado.setMsg("Não foi possível completar o registro");
	    }
	}
	// Se houver msg de erro
	else
	{
	    // Adicionar a msg ao resultado
	    resultado.setMsg(msg);
	}
	// Retornar o resultado
	return resultado;
    }
    
    @Override
    public Resultado alterar(EntidadeDominio entidade)
    {
	resultado = new Resultado();
	// Recuperar o nome da classe
	String nomeClasse = entidade.getClass().getName();
	// Executar regras de negócio
	String msg = executarRegras(entidade, "ALTERAR");
	
	// Se não houver msg de erro
	if(msg == null)
	{
	    IDAO dao = daos.get(nomeClasse);
	    try
	    {
		// Alterar a entidade
		dao.alterar(entidade);
		List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
		// Adicionar entidade à lista
		entidades.add(entidade);
		// Adicionar lista ao resultado
		resultado.setEntidades(entidades);
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
		resultado.setMsg("Não foi possível alterar o registro");
	    }
	}
	// Se houver msg de erro
	else
	{
	    // Adicionar a msg ao resultado
	    resultado.setMsg(msg);
	}
	// Retornar o resultado
	return resultado;
    }
    
    @Override
    public Resultado excluir(EntidadeDominio entidade)
    {
	resultado = new Resultado();
	// Recuperar o nome da classe
	String nomeClasse = entidade.getClass().getName();
	// Executar regras de negócio
	String msg = executarRegras(entidade, "EXCLUIR");
	
	// Se não houver msg de erro
	if(msg == null)
	{
	    IDAO dao = daos.get(nomeClasse);
	    try
	    {
		// Excluir a entidade
		dao.excluir(entidade);
		List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
		// Adicionar entidade à lista
		entidades.add(entidade);
		// Adicionar lista ao resultado
		resultado.setEntidades(entidades);
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
		resultado.setMsg("Não foi possível excluir o registro");
	    }
	}
	// Se houver msg de erro
	else
	{
	    // Adicionar a msg ao resultado
	    resultado.setMsg(msg);
	}
	// Retornar o resultado
	return resultado;
    }
    
    @Override
    public Resultado consultar(EntidadeDominio entidade)
    {
	resultado = new Resultado();
	// Recuperar o nome da classe
	String nomeClasse = entidade.getClass().getName();
	// Executar regras de negócio
	String msg = executarRegras(entidade, "CONSULTAR");
	
	// Se não houver msg de erro
	if(msg == null)
	{
	    IDAO dao = daos.get(nomeClasse);
	    try
	    {
		// Consultar entidades
		resultado.setEntidades(dao.consultar(entidade));
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
		resultado.setMsg("Não foi possível realizar consulta");
	    }
	}
	// Se houver msg de erro
	else
	{
	    // Adicionar a msg ao resultado
	    resultado.setMsg(msg);
	}
	// Retornar o resultado
	return resultado;
    }
    
    @Override
    public Resultado visualizar(EntidadeDominio entidade)
    {
	resultado = new Resultado();
	resultado.setEntidades(new ArrayList<EntidadeDominio>(1));
	resultado.getEntidades().add(entidade);
	return resultado;
    }
    
    private String executarRegras(EntidadeDominio entidade, String operacao)
    {
	// Recupera o nome da classe
	String nomeClasse = entidade.getClass().getName();
	StringBuilder msg = new StringBuilder();
	// Recupera as regras para a entidade 
	Map<String, List<IStrategy>> regras = rns.get(nomeClasse);
	// Se houver regras para a entidade
	if(regras != null)
	{
	    // Recuperar as regras da entidade para a operação
	    List<IStrategy> regrasOperacao = regras.get(operacao);
	    // Se houver regras para a operação
	    if(regrasOperacao != null)
	    {
		for (IStrategy s : regrasOperacao)
		{
		    // Executar as regras		   
		    String m = s.processar(entidade);
		    // Se houver msg de erro
		    if(m != null)
		    {
			// Salvar a msg
			msg.append(m);
			msg.append("\n");
		    }
		}
	    }
	}
	// Se houver alguma msg de erro, retornar a msg
	if(msg.length() > 0)
	    return msg.toString();
	else
	    return null;
    }
}

package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dominio.Cartao;
import dominio.Cliente;
import dominio.Cupom;
import dominio.Endereco;
import dominio.EntidadeDominio;
import dominio.Genero;
import dominio.Telefone;
import dominio.Usuario;

public class ClienteDAO extends AbstractJdbcDAO
{
    
    public ClienteDAO()
    {
	super("Cliente", "id_cliente");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException
    {
	openConnection();
	PreparedStatement pst = null;
	Cliente cliente = (Cliente)entidade; 
	UsuarioDAO dao = new UsuarioDAO();
	dao.salvar(cliente.getUsuario());
	try
	{	    
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
	    sql.append("INSERT INTO Cliente (nome_cliente, dt_nascimento, genero, cpf, status_cliente, ranking, id_usuario) VALUES(?,?,?,?,?,?,?);");
	    pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
	    pst.setString(1, cliente.getNome());
	    pst.setTimestamp(2, new Timestamp(cliente.getDtNascimento().getTime()));
	    pst.setString(3, cliente.getGenero().name());
	    pst.setString(4, cliente.getCPF());
	    pst.setBoolean(5, cliente.isStatus());
	    pst.setInt(6, cliente.getRanking());
	    pst.setInt(7, cliente.getUsuario().getId());
	    pst.executeUpdate();
	    ResultSet rs = pst.getGeneratedKeys();
	    int id = 0;
	    while(rs.next())
	    {
		id = rs.getInt(1);
	    }
	    cliente.setId(id);
	    conn.commit();
	    cliente.getEnderecos().get(0).setCliente(cliente);
	    EnderecoDAO end = new EnderecoDAO();
	    end.salvar(cliente.getEnderecos().get(0));
	    for(Telefone tel : cliente.getTelefones())
	    {
		tel.setCliente(cliente);
		TelefoneDAO teldao = new TelefoneDAO();
		teldao.salvar(tel);
	    }
	    conn.commit();
	}
	catch(SQLException e)
	{
	    try
	    {
		conn.rollback();
	    }
	    catch(SQLException e1)
	    {
		e1.printStackTrace();
	    }
	    e.printStackTrace();
	}
	finally
	{
	    try
	    {
		pst.close();
		conn.close();
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	}
    } // salvar
    
    @Override
    public void alterar(EntidadeDominio entidade) throws SQLException
    {
	openConnection();
	PreparedStatement pst = null;
	Cliente cliente = (Cliente)entidade; 
	try
	{	    
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
	    sql.append("UPDATE Cliente SET nome_cliente=?, dt_nascimento=?, genero=?, cpf=?, status_cliente=?, ranking=? WHERE id_cliente=?");
	    pst = conn.prepareStatement(sql.toString());
	    pst.setString(1, cliente.getNome());
	    pst.setTimestamp(2, new Timestamp(cliente.getDtNascimento().getTime()));
	    pst.setString(3, cliente.getGenero().name());
	    pst.setString(4, cliente.getCPF());
	    pst.setBoolean(5, cliente.isStatus());
	    pst.setInt(6, cliente.getRanking());
	    pst.setInt(7, cliente.getId());
	    pst.executeUpdate();
	    conn.commit();
	    TelefoneDAO tel = new TelefoneDAO();
	    cliente.getTelefones().get(0).setCliente(cliente);
	    tel.alterar(cliente.getTelefones().get(0));
	}
	catch(SQLException e)
	{
	    try
	    {
		conn.rollback();
	    }
	    catch(SQLException e1)
	    {
		e1.printStackTrace();
	    }
	    e.printStackTrace();
	}
	finally
	{
	    try
	    {
		pst.close();
		conn.close();
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	}
    } // alterar
   
    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException
    {
	openConnection();
	PreparedStatement pst = null;
	Cliente cliente = (Cliente)entidade; 
	String sql = "SELECT * FROM Cliente WHERE";
	
	if(cliente.getId() != 0)
	    sql += " id_cliente = ? AND";
	if(cliente.getNome() != null && !cliente.getNome().trim().equals(""))
	    sql += " nome_cliente LIKE ? AND";
	if(cliente.getCPF() != null && !cliente.getCPF().trim().equals(""))
	    sql += " cpf = ? AND";
	if(cliente.getDtNascimento() != null)
	    sql += " dt_nascimento = ? AND";
	if(cliente.getGenero() != null)
	    sql += " genero = ? AND";
	if(cliente.getRanking() != 0)
	    sql += " ranking = ? AND";
	if(cliente.getUsuario() != null && cliente.getUsuario().getId() != 0)
	    sql += " id_usuario = ? AND";
	if(sql.endsWith(" AND"))
	    sql = sql.substring(0, sql.length() - 4) + ";";
	else
	    sql = "SELECT * FROM Cliente;";

	try
	{
	    pst = conn.prepareStatement(sql);
	    int i = 1;
	    
	    if(cliente.getId() != 0)
	    {
		pst.setInt(i, cliente.getId());
		i++;
	    }
	    if(cliente.getNome() != null && !cliente.getNome().trim().equals(""))
	    {
		pst.setString(i, "%" + cliente.getNome() + "%");
		i++;
	    }
	    if(cliente.getCPF() != null && !cliente.getCPF().trim().equals(""))
	    {
		pst.setString(i, cliente.getCPF());
		i++;
	    }
	    if(cliente.getDtNascimento() != null)
	    {
		pst.setTimestamp(i, new Timestamp(cliente.getDtNascimento().getTime()));
		i++;
	    }
	    if(cliente.getGenero() != null)
	    {
		pst.setString(i, cliente.getGenero().name());
		i++;
	    }
	    if(cliente.getRanking() != 0)
	    {
		pst.setInt(i, cliente.getRanking());
		i++;
	    }
	    if(cliente.getUsuario() != null && cliente.getUsuario().getId() != 0)
	    {
		pst.setInt(i, cliente.getUsuario().getId());
		i++;
	    }
	    ResultSet rs = pst.executeQuery();
	    List<EntidadeDominio> clientes = new ArrayList<EntidadeDominio>();
	    while (rs.next()) 
	    {
		Cliente c = new Cliente();
		c.setId(rs.getInt("id_cliente"));
		c.setNome(rs.getString("nome_cliente"));
		c.setCPF(rs.getString("cpf"));
		c.setDtNascimento(new Date(rs.getTimestamp("dt_nascimento").getTime()));
		c.setGenero(Genero.valueOf(rs.getString("genero")));
		c.setRanking(rs.getInt("ranking"));
		c.setStatus(rs.getBoolean("status_cliente"));
		
		c.setUsuario(new Usuario());
		c.getUsuario().setId(rs.getInt("id_usuario"));
		UsuarioDAO user = new UsuarioDAO();
		c.setUsuario((Usuario)user.consultar(c.getUsuario()).get(0));
		
		TelefoneDAO tel = new TelefoneDAO();
		List<EntidadeDominio> tels = tel.consultar(new Telefone(c));
		c.setTelefones(new ArrayList<Telefone>());
		for(EntidadeDominio e : tels)
		    c.addTelefone((Telefone)e);
		
		EnderecoDAO end = new EnderecoDAO();
		List<EntidadeDominio> ends = end.consultar(new Endereco(c));
		c.setEnderecos(new ArrayList<Endereco>());
		for(EntidadeDominio ed : ends)
		{
		    Endereco e = (Endereco)ed;
		    c.addEndereco(e);
		}
		CartaoDAO ct = new CartaoDAO();
		List<EntidadeDominio> cartoes = ct.consultar(new Cartao(c));
		c.setCartoes(new ArrayList<Cartao>());
		for(EntidadeDominio e : cartoes)
		{
		    if(e instanceof Cartao)
		    {
			Cartao c1 = (Cartao)e;
			c.getCartoes().add(c1);
		    }
		}
                CupomDAO cp = new CupomDAO();
		List<EntidadeDominio> cupons = cp.consultar(new Cupom(c));
		c.setCupons(new ArrayList<Cupom>());
		for(EntidadeDominio e : cupons)
		{
		    Cupom c1 = (Cupom)e;
                    c.getCupons().add(c1);
		}
		clientes.add(c);
	    }
            try
	    {
		pst.close();
		conn.close();
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	    return clientes;
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
	return null;
    } // consultar
    
    @Override
    public void excluir(EntidadeDominio entidade)
    {
	try
	{
	    Cliente cliente = (Cliente)consultar(entidade).get(0);
	    for(Endereco e : cliente.getEnderecos())
	    {
		EnderecoDAO end = new EnderecoDAO();
		end.excluir(e);
	    }
	    for(Telefone t : cliente.getTelefones())
	    {
		TelefoneDAO tel = new TelefoneDAO();
		tel.excluir(t);
	    }
	    for(Cartao c : cliente.getCartoes())
	    {
		CartaoDAO car = new CartaoDAO();
		car.excluir(c);
	    }
	    UsuarioDAO user = new UsuarioDAO();
	    user.excluir(cliente.getUsuario());
	    super.excluir(cliente);
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
    }
}

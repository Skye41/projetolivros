package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dominio.BandeiraCartao;
import dominio.Cartao;
import dominio.Cliente;
import dominio.EntidadeDominio;

public class CartaoDAO extends AbstractJdbcDAO
{
    public CartaoDAO()
    {
	super("Cartao", "id_cartao");
    }
    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException
    {
	openConnection();
	PreparedStatement pst = null;
	Cartao cartao = (Cartao)entidade;
	try
	{
            if(cartao.isPreferencial())
                atualizarPreferencial(entidade);
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
	    sql.append("INSERT INTO Cartao (numero_cartao, nome_cartao, cvv, dt_vencimento, id_bandeira, preferencial, id_cliente) VALUES (?,?,?,?,?,?,?);");
	    pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
	    pst.setString(1, cartao.getNumero());
	    pst.setString(2, cartao.getNomeCartao());
	    pst.setString(3, cartao.getCodigoSeguranca());
	    pst.setTimestamp(4, new Timestamp(cartao.getDtVencimento().getTime()));
	    pst.setInt(5, cartao.getBandeira().getId());
            pst.setBoolean(6, cartao.isPreferencial());
	    pst.setInt(7, cartao.getCliente().getId());
	    pst.executeUpdate();
	    ResultSet rs = pst.getGeneratedKeys();
	    int id = 0;
	    while(rs.next())
	    {
		id = rs.getInt(1);
	    }
	    cartao.setId(id);
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
	Cartao cartao = (Cartao)entidade;
	try
	{	    
	    if(cartao.isPreferencial())
                atualizarPreferencial(entidade);
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
	    sql.append("UPDATE Cartao SET numero_cartao=?, nome_cartao=?, cvv=?, dt_vencimento=?, id_bandeira=?, preferencial=? WHERE id_cartao=?;");
	    pst = conn.prepareStatement(sql.toString());
	    pst.setString(1, cartao.getNumero());
	    pst.setString(2, cartao.getNomeCartao());
	    pst.setString(3, cartao.getCodigoSeguranca());
	    pst.setTimestamp(4, new Timestamp(cartao.getDtVencimento().getTime()));
	    pst.setInt(5, cartao.getBandeira().getId());
            pst.setBoolean(6, cartao.isPreferencial());
	    pst.setInt(7, cartao.getId());
	    pst.executeUpdate();
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
    } // alterar
    
    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException
    {
	openConnection();
	PreparedStatement pst = null;
	Cartao cartao = (Cartao)entidade;
	
	String sql = "SELECT * FROM Cartao WHERE";
	if(cartao.getId() != 0)
	    sql += " id_cartao = ? AND";
	if(cartao.getNumero() != null && !cartao.getNumero().trim().equals(""))
	    sql += " numero_cartao = ? AND";
	if(cartao.getNomeCartao() != null && !cartao.getNomeCartao().trim().equals(""))
	    sql += " nome_cartao = ? AND";
	if(cartao.getCodigoSeguranca() != null && !cartao.getCodigoSeguranca().trim().equals(""))
	    sql += " cvv = ? AND";
	if(cartao.getBandeira() != null && cartao.getBandeira().getId() != 0)
	    sql += " id_bandeira = ? AND";
	if(cartao.getCliente() != null && cartao.getCliente().getId() != 0)
	    sql += " id_cliente = ? AND";
	if(sql.endsWith(" AND"))
	    sql = sql.substring(0, sql.length() - 4) + ";";
	else
	    sql = "SELECT * FROM Cartao;";
	
	try
	{
	    pst = conn.prepareStatement(sql);
	    int i = 1;
	    
	    if(cartao.getId() != 0)
	    {
		pst.setInt(i, cartao.getId());
		i++;
	    }
	    if(cartao.getNumero() != null && !cartao.getNumero().trim().equals(""))
	    {
		pst.setString(i, cartao.getNumero());
		i++;
	    }
	    if(cartao.getNomeCartao() != null && !cartao.getNomeCartao().trim().equals(""))
	    {
		pst.setString(i, cartao.getNomeCartao());
		i++;
	    }
	    if(cartao.getCodigoSeguranca() != null && !cartao.getCodigoSeguranca().trim().equals(""))
	    {
		pst.setString(i, cartao.getCodigoSeguranca());
		i++;
	    }
	    if(cartao.getBandeira() != null && cartao.getBandeira().getId() != 0)
	    {
		pst.setInt(i, cartao.getBandeira().getId());
		i++;
	    }
	    if(cartao.getCliente() != null && cartao.getCliente().getId() != 0)
	    {
		pst.setInt(i, cartao.getCliente().getId());
		i++;
	    }
	    ResultSet rs = pst.executeQuery();
	    List<EntidadeDominio> cartoes = new ArrayList<EntidadeDominio>();
	    while (rs.next()) 
	    {
		Cartao c = new Cartao();
		c.setId(rs.getInt("id_cartao"));
		c.setNumero(rs.getString("numero_cartao"));
		c.setNomeCartao(rs.getString("nome_cartao"));
		c.setCodigoSeguranca(rs.getString("cvv"));
		c.setDtVencimento(new Date(rs.getTimestamp("dt_vencimento").getTime()));
		BandeiraCartao b = new BandeiraCartao(rs.getInt("id_bandeira"));
		c.setBandeira((BandeiraCartao)new BandeiraCartaoDAO().consultar(b).get(0));
		c.setCliente(new Cliente(rs.getInt("id_cliente")));
                c.setPreferencial(rs.getBoolean("preferencial"));
		cartoes.add(c);
	    }
	    cartoes.addAll(new BandeiraCartaoDAO().consultar(new BandeiraCartao()));
            try
	    {
		pst.close();
		conn.close();
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	    return cartoes;
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
	return null;
    } // consultar  
    
    public void atualizarPreferencial(EntidadeDominio entidade) throws SQLException
    {
	PreparedStatement pst = null;
	Cartao cartao = (Cartao)entidade;
	try
	{	    
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
	    sql.append("UPDATE Cartao SET preferencial=false WHERE id_cliente=?;");
	    pst = conn.prepareStatement(sql.toString());
	    pst.setInt(1, cartao.getCliente().getId());
	    pst.executeUpdate();
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
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	}
    } // alterar
}

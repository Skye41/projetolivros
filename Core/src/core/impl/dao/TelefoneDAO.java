package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dominio.Cliente;
import dominio.EntidadeDominio;
import dominio.Telefone;
import dominio.TipoTelefone;

public class TelefoneDAO extends AbstractJdbcDAO
{
    
    public TelefoneDAO()
    {
	super("Telefone", "id_telefone");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException
    {
	openConnection();
	PreparedStatement pst = null;
	Telefone tel = (Telefone)entidade;
	try
	{	    
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
	    sql.append("INSERT INTO Telefone (tipo_telefone, ddd, numero_telefone, id_cliente) VALUES (?,?,?,?);");
	    pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
	    pst.setString(1, tel.getTipo().name());
	    pst.setString(2, tel.getDdd());
	    pst.setString(3, tel.getNumero());
	    pst.setInt(4, tel.getCliente().getId());
	    pst.executeUpdate();
	    ResultSet rs = pst.getGeneratedKeys();
	    int id = 0;
	    while(rs.next())
	    {
		id = rs.getInt(1);
	    }
	    tel.setId(id);
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
	Telefone tel = (Telefone)entidade;
	try
	{	    
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
	    sql.append("UPDATE Telefone SET tipo_telefone=?, ddd=?, numero_telefone=?, id_cliente=? WHERE id_telefone=?");
	    pst = conn.prepareStatement(sql.toString());
	    pst.setString(1, tel.getTipo().name());
	    pst.setString(2, tel.getDdd());
	    pst.setString(3, tel.getNumero());
	    pst.setInt(4, tel.getCliente().getId());
	    pst.setInt(5, tel.getId());
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
	Telefone tel = (Telefone)entidade;
	String sql = "SELECT * FROM Telefone WHERE";
	
	if(tel.getId() != 0)
	    sql += " id_telefone = ? AND";
	if(tel.getTipo() != null)
	    sql += " tipo_telefone = ? AND";
	if(tel.getDdd() != null && !tel.getDdd().trim().equals(""))
	    sql += " ddd = ? AND";
	if(tel.getNumero() != null && !tel.getNumero().trim().equals(""))
	    sql += " numero_telefone = ? AND";
	if(tel.getCliente() != null && tel.getCliente().getId() != 0)
	    sql += " id_cliente = ? AND";
	if(sql.endsWith(" AND"))
	    sql = sql.substring(0, sql.length() - 4) + ";";
	else
	    sql = "SELECT * FROM Telefone;";
	
	try
	{
	    pst = conn.prepareStatement(sql);
	    int i = 1;
	    
	    if(tel.getId() != 0)
	    {
		pst.setInt(i, tel.getId());
		i++;
	    }
	    if(tel.getTipo() != null)
	    {
		pst.setString(i, tel.getTipo().name());
		i++;
	    }
	    if(tel.getDdd() != null && !tel.getDdd().trim().equals(""))
	    {
		pst.setString(i, tel.getDdd());
		i++;
	    }
	    if(tel.getNumero() != null && !tel.getNumero().trim().equals(""))
	    {
		pst.setString(i, tel.getNumero());
		i++;
	    }
	    if(tel.getCliente() != null && tel.getCliente().getId() != 0)
	    {
		pst.setInt(i, tel.getCliente().getId());
		i++;
	    }
	    ResultSet rs = pst.executeQuery();
	    List<EntidadeDominio> tels = new ArrayList<EntidadeDominio>();
	    while (rs.next()) 
	    {
		Telefone t = new Telefone();
		t.setId(rs.getInt("id_telefone"));
		t.setTipo(TipoTelefone.valueOf(rs.getString("tipo_telefone")));
		t.setDdd(rs.getString("ddd"));
		t.setNumero(rs.getString("numero_telefone"));
		t.setCliente(new Cliente(rs.getInt("id_cliente")));
		tels.add(t);
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
	    return tels;
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
	return null;
    }   
}

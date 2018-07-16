package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dominio.Cliente;
import dominio.Endereco;
import dominio.EntidadeDominio;
import dominio.TipoEndereco;

public class EnderecoDAO extends AbstractJdbcDAO
{
    
    public EnderecoDAO()
    {
	super("Endereco", "id_endereco");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException
    {
	openConnection();
	PreparedStatement pst = null;
	Endereco end = (Endereco)entidade;
	try
	{	    
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
	    sql.append("INSERT INTO Endereco (tipo_endereco, tipo_residencia, tipo_logradouro, logradouro, numero, ");
	    sql.append("bairro, cep, cidade, estado, pais, obs, id_cliente) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");
	    pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
	    pst.setString(1, end.getTipoEndereco().name());
	    pst.setString(2, end.getTipoResidencia());
	    pst.setString(3, end.getTipoLogradouro());
	    pst.setString(4, end.getLogradouro());
	    pst.setString(5, end.getNumero());
	    pst.setString(6, end.getBairro());
	    pst.setString(7, end.getCep());
	    pst.setString(8, end.getCidade());
	    pst.setString(9, end.getEstado());
	    pst.setString(10, end.getPais());
	    pst.setString(11, end.getObservacoes());
	    pst.setInt(12, end.getCliente().getId());
	    pst.executeUpdate();
	    ResultSet rs = pst.getGeneratedKeys();
	    int id = 0;
	    while(rs.next())
	    {
		id = rs.getInt(1);
	    }
	    end.setId(id);
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
	Endereco end = (Endereco)entidade;
	try
	{	    
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
	    sql.append("UPDATE Endereco SET tipo_endereco=?, tipo_residencia=?, tipo_logradouro=?, logradouro=?, numero=?, ");
	    sql.append("bairro=?, cep=?, cidade=?, estado=?, pais=?, obs=?, id_cliente=? WHERE id_endereco=?;");
	    pst = conn.prepareStatement(sql.toString());
	    pst.setString(1, end.getTipoEndereco().name());
	    pst.setString(2, end.getTipoResidencia());
	    pst.setString(3, end.getTipoLogradouro());
	    pst.setString(4, end.getLogradouro());
	    pst.setString(5, end.getNumero());
	    pst.setString(6, end.getBairro());
	    pst.setString(7, end.getCep());
	    pst.setString(8, end.getCidade());
	    pst.setString(9, end.getEstado());
	    pst.setString(10, end.getPais());
	    pst.setString(11, end.getObservacoes());
	    pst.setInt(12, end.getCliente().getId());
	    pst.setInt(13, end.getId());
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
    }
    
    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException
    {
	openConnection();
	PreparedStatement pst = null;
	Endereco end = (Endereco)entidade;
	String sql = "SELECT * FROM Endereco WHERE";
	
	if(end.getId() != 0)
	    sql += " id_endereco = ? AND";
	if(end.getTipoEndereco() != null)
	    sql += " tipo_endereco = ? AND";
	if(end.getTipoResidencia() != null && !end.getTipoResidencia().trim().equals(""))
	    sql += " tipo_residencia = ? AND";
	if(end.getTipoLogradouro() != null &&!end.getTipoLogradouro().trim().equals(""))
	    sql += " tipo_logradouro = ? AND";
	if(end.getLogradouro() != null && !end.getLogradouro().trim().equals(""))
	    sql += " logradouro = ? AND";
	if(end.getNumero() != null && !end.getNumero().trim().equals(""))
	    sql += " numero = ? AND";
	if(end.getBairro() != null && !end.getBairro().trim().equals(""))
	    sql += " bairro = ? AND";
	if(end.getCep() != null && !end.getCep().trim().equals(""))
	    sql += " cep = ? AND";
	if(end.getCidade() != null && !end.getCidade().trim().equals(""))
	    sql += " cidade = ? AND";
	if(end.getEstado() != null && !end.getEstado().trim().equals(""))
	    sql += " estado = ? AND";
	if(end.getPais() != null && !end.getPais().trim().equals(""))
	    sql += " pais = ? AND";
	if(end.getObservacoes() != null && !end.getObservacoes().trim().equals(""))
	    sql += " obs = ? AND";
	if(end.getCliente() != null && end.getCliente().getId() != 0)
	    sql += " id_cliente = ? AND";
	if(sql.endsWith(" AND"))
	    sql = sql.substring(0, sql.length() - 4) + ";";
	else
	    sql = "SELECT * FROM Endereco;";
	try
	{
	    pst = conn.prepareStatement(sql);
	    int i = 1;
	    if(end.getId() != 0)
	    {
		pst.setInt(i, end.getId());
		i++;
	    }
	    if(end.getTipoEndereco() != null)
	    {
		pst.setString(i, end.getTipoEndereco().name());
		i++;
	    }
	    if(end.getTipoResidencia() != null && !end.getTipoResidencia().trim().equals(""))
	    {
		pst.setString(i, end.getTipoResidencia());
		i++;
	    }
	    if(end.getTipoLogradouro() != null &&!end.getTipoLogradouro().trim().equals(""))
            {
                pst.setString(i, end.getTipoLogradouro());
                i++;
            }
            if(end.getLogradouro() != null && !end.getLogradouro().trim().equals(""))
            {
                pst.setString(i, end.getLogradouro());
                i++;
            }
            if(end.getNumero() != null && !end.getNumero().trim().equals(""))
            {
                pst.setString(i, end.getNumero());
                i++;
            }
            if(end.getBairro() != null && !end.getBairro().trim().equals(""))
            {
                pst.setString(i, end.getBairro());
                i++;
            }
            if(end.getCep() != null && !end.getCep().trim().equals(""))
            {
                pst.setString(i, end.getCep());
                i++;
            }
            if(end.getCidade() != null && !end.getCidade().trim().equals(""))
            {
                pst.setString(i, end.getCidade());
                i++;
            }
            if(end.getEstado() != null && !end.getEstado().trim().equals(""))
            {
                pst.setString(i, end.getEstado());
                i++;
            }
            if(end.getPais() != null && !end.getPais().trim().equals(""))
            {
                pst.setString(i, end.getPais());
                i++;
            }
            if(end.getObservacoes() != null && !end.getObservacoes().trim().equals(""))
            {
                pst.setString(i, end.getObservacoes());
                i++;
            }
            if(end.getCliente() != null && end.getCliente().getId() != 0)
            {
		pst.setInt(i, end.getCliente().getId());
		i++;
	    }
            ResultSet rs = pst.executeQuery();
	    List<EntidadeDominio> ends = new ArrayList<EntidadeDominio>();
	    while (rs.next()) 
	    {
		Endereco e = new Endereco();
		e.setId(rs.getInt("id_endereco"));
		e.setTipoEndereco(TipoEndereco.valueOf(rs.getString("tipo_endereco")));
		e.setTipoResidencia(rs.getString("tipo_residencia"));
		e.setTipoLogradouro(rs.getString("tipo_logradouro"));
		e.setLogradouro(rs.getString("logradouro"));
		e.setNumero(rs.getString("numero"));
		e.setBairro(rs.getString("bairro"));
		e.setCep(rs.getString("cep"));
		e.setCidade(rs.getString("cidade"));
		e.setEstado(rs.getString("estado"));
		e.setPais(rs.getString("pais"));
		e.setObservacoes(rs.getString("obs"));
		e.setCliente(new Cliente(rs.getInt("id_cliente")));
		ends.add(e);
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
	    return ends;
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
	return null;
    } // consultar   
}

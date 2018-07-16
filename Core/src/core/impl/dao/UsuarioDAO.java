package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dominio.EntidadeDominio;
import dominio.TipoUsuario;
import dominio.Usuario;

public class UsuarioDAO extends AbstractJdbcDAO
{
    
    public UsuarioDAO()
    {
	super("Usuario", "id_usuario");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException
    {
	openConnection();
	PreparedStatement pst = null;
	Usuario user = (Usuario)entidade;
	try
	{	    
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
	    sql.append("INSERT INTO Usuario (email, senha, tipo_usuario) VALUES (?,?,?);");
	    pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
	    pst.setString(1, user.getEmail());
	    pst.setString(2, user.getSenha());
	    pst.setString(3, user.getTipo().name());
	    pst.executeUpdate();
	    ResultSet rs = pst.getGeneratedKeys();
	    int id = 0;
	    while(rs.next())
	    {
		id = rs.getInt(1);
	    }
	    user.setId(id);
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
	Usuario user = (Usuario)entidade;
	try
	{	    
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
	    sql.append("UPDATE Usuario SET senha=? WHERE id_usuario=?;");
	    pst = conn.prepareStatement(sql.toString());
	    pst.setString(1, user.getSenha());
	    pst.setInt(2, user.getId());
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
	Usuario user = (Usuario)entidade;
	String sql = "SELECT * FROM Usuario WHERE";
	
	if(user.getId() != 0)
	    sql += " id_usuario = ? AND";
	if(user.getEmail() != null && !user.getEmail().trim().equals(""))
	    sql += " email = ? AND";
	if(user.getSenha() != null && !user.getSenha().trim().equals(""))
	    sql += " senha = ? AND";
	if(sql.endsWith(" AND"))
	    sql = sql.substring(0, sql.length() - 4) + ";";
	else
	    sql = "SELECT * FROM Usuario;";
	
	try
	{
	    pst = conn.prepareStatement(sql);
	    int i = 1;
	    
	    if(user.getId() != 0)
	    {
		pst.setInt(i, user.getId());
		i++;
	    }
	    if(user.getEmail() != null && !user.getEmail().trim().equals(""))
	    {
		pst.setString(i, user.getEmail());
		i++;
	    }
	    if(user.getSenha() != null && !user.getSenha().trim().equals(""))
	    {
		pst.setString(i, user.getSenha());
		i++;
	    }
	    ResultSet rs = pst.executeQuery();
	    List<EntidadeDominio> users = new ArrayList<EntidadeDominio>();
	    while (rs.next()) 
	    {
		Usuario u = new Usuario();
		u.setId(rs.getInt("id_usuario"));
		u.setEmail(rs.getString("email"));
		u.setSenha(rs.getString("senha"));
		u.setTipo(TipoUsuario.valueOf(rs.getString("tipo_usuario")));
		users.add(u);
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
	    return users;
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
	return null;
    } // consultar  
}

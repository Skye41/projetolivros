package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dominio.Autor;
import dominio.EntidadeDominio;

public class AutorDAO extends AbstractJdbcDAO
{
    public AutorDAO()
    {
	super("Autor", "id_autor");
    }
    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException
    {
	// TODO Auto-generated method stub
	
    }
    
    @Override
    public void alterar(EntidadeDominio entidade) throws SQLException
    {
	// TODO Auto-generated method stub
	
    }
    
    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException
    {
	openConnection();
	PreparedStatement pst = null;
	Autor autor = (Autor)entidade;
	
	String sql = "SELECT * FROM Autor WHERE";
	
	if(autor.getId() != 0)
	    sql += " id_autor = ? AND";
	if(autor.getNomeAutor() != null && autor.getNomeAutor().trim().equals(""))
	    sql += " nome_autor = ? AND";
	if(sql.endsWith(" AND"))
	    sql = sql.substring(0, sql.length() - 4) + ";";
	else
	    sql = "SELECT * FROM Autor;";
	try
	{
	    pst = conn.prepareStatement(sql);
	    int i = 1;
	    
	    if(autor.getId() != 0)
	    {
		pst.setInt(i, autor.getId());
		i++;
	    }
	    if(autor.getNomeAutor() != null && autor.getNomeAutor().trim().equals(""))
	    {
		pst.setString(i, autor.getNomeAutor());
		i++;
	    }
	    ResultSet rs = pst.executeQuery();
	    List<EntidadeDominio> autores = new ArrayList<EntidadeDominio>();
	    while (rs.next()) 
	    {
		Autor a = new Autor();
		a.setId(rs.getInt("id_autor"));
		a.setNomeAutor(rs.getString("nome_autor"));
		autores.add(a);
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
	    return autores;
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
	return null;
    } // consultar 
}

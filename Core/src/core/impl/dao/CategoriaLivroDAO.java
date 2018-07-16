package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dominio.CategoriaLivro;
import dominio.EntidadeDominio;

public class CategoriaLivroDAO extends AbstractJdbcDAO
{
    public CategoriaLivroDAO()
    {
	super("CategoriaLivro", "id_categoria");
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
	CategoriaLivro categoria = (CategoriaLivro)entidade;
	
	String sql = "SELECT * FROM CategoriaLivro WHERE";
	
	if(categoria.getId() != 0)
	    sql += " id_categoria = ? AND";
	if(categoria.getNomeCategoria() != null && categoria.getNomeCategoria().trim().equals(""))
	    sql += " nome_categoria = ? AND";
	if(sql.endsWith(" AND"))
	    sql = sql.substring(0, sql.length() - 4) + ";";
	else
	    sql = "SELECT * FROM CategoriaLivro;";
	
	try
	{
	    pst = conn.prepareStatement(sql);
	    int i = 1;
	    
	    if(categoria.getId() != 0)
	    {
		pst.setInt(i, categoria.getId());
		i++;
	    }
	    if(categoria.getNomeCategoria() != null && categoria.getNomeCategoria().trim().equals(""))
	    {
		pst.setString(i, categoria.getNomeCategoria());
		i++;
	    }
	    ResultSet rs = pst.executeQuery();
	    List<EntidadeDominio> categorias = new ArrayList<EntidadeDominio>();
	    while (rs.next()) 
	    {
		CategoriaLivro c = new CategoriaLivro();
		c.setId(rs.getInt("id_categoria"));
		c.setNomeCategoria(rs.getString("nome_categoria"));
		categorias.add(c);
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
	    return categorias;
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
	return null;
    } // consultar 
}

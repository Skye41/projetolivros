package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dominio.Editora;
import dominio.EntidadeDominio;

public class EditoraDAO extends AbstractJdbcDAO
{
    public EditoraDAO()
    {
	super("Editora", "id_editora");
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
	Editora editora = (Editora)entidade;
	
	String sql = "SELECT * FROM Editora WHERE";
	
	if(editora.getId() != 0)
	    sql += " id_editora = ? AND";
	if(editora.getNomeEditora() != null && editora.getNomeEditora().trim().equals(""))
	    sql += " nome_editora = ? AND";
	if(sql.endsWith(" AND"))
	    sql = sql.substring(0, sql.length() - 4) + ";";
	else
	    sql = "SELECT * FROM Editora;";
	try
	{
	    pst = conn.prepareStatement(sql);
	    int i = 1;
	    
	    if(editora.getId() != 0)
	    {
		pst.setInt(i, editora.getId());
		i++;
	    }
	    if(editora.getNomeEditora() != null && editora.getNomeEditora().trim().equals(""))
	    {
		pst.setString(i, editora.getNomeEditora());
		i++;
	    }
	    ResultSet rs = pst.executeQuery();
	    List<EntidadeDominio> editoras = new ArrayList<EntidadeDominio>();
	    while (rs.next()) 
	    {
		Editora e = new Editora();
		e.setId(rs.getInt("id_editora"));
		e.setNomeEditora(rs.getString("nome_editora"));
		editoras.add(e);
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
	    return editoras;
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
	return null;
    } // consultar 
}

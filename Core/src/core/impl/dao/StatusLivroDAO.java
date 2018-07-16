package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dominio.StatusLivro;
import dominio.EntidadeDominio;

public class StatusLivroDAO extends AbstractJdbcDAO
{
    public StatusLivroDAO()
    {
	super("StatusLivro", "id_status");
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
	StatusLivro status = (StatusLivro)entidade;
	
	String sql = "SELECT * FROM StatusLivro WHERE";
	
	if(status.getId() != 0)
	    sql += " id_status = ? AND";
	if(status.getJustificativa() != null && status.getJustificativa().trim().equals(""))
	    sql += " justificativa = ? AND";
	if(sql.endsWith(" AND"))
	    sql = sql.substring(0, sql.length() - 4) + ";";
	else
	    sql = "SELECT * FROM StatusLivro;";
	try
	{
	    pst = conn.prepareStatement(sql);
	    int i = 1;
	    
	    if(status.getId() != 0)
	    {
		pst.setInt(i, status.getId());
		i++;
	    }
	    if(status.getJustificativa() != null && status.getJustificativa().trim().equals(""))
	    {
		pst.setString(i, status.getJustificativa());
		i++;
	    }
	    ResultSet rs = pst.executeQuery();
	    List<EntidadeDominio> listaStatus = new ArrayList<EntidadeDominio>();
	    while (rs.next()) 
	    {
		StatusLivro j = new StatusLivro();
		j.setId(rs.getInt("id_status"));
		j.setJustificativa(rs.getString("justificativa"));
		j.setStatusLivro(rs.getBoolean("status"));
		listaStatus.add(j);
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
	    return listaStatus;
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
	return null;
    } // consultar 
}

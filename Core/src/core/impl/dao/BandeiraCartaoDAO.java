package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dominio.BandeiraCartao;
import dominio.EntidadeDominio;

public class BandeiraCartaoDAO extends AbstractJdbcDAO
{
    public BandeiraCartaoDAO()
    {
	super("BandeiraCartao", "id_bandeira");
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
	BandeiraCartao bandeira = (BandeiraCartao)entidade;
	String sql = "SELECT * FROM BandeiraCartao WHERE";
	
	if(bandeira.getId() != 0)
	    sql += " id_bandeira = ? AND";
	if(bandeira.getNomeBandeira() != null && bandeira.getNomeBandeira().trim().equals(""))
	    sql += " nome_bandeira = ? AND";
	if(sql.endsWith(" AND"))
	    sql = sql.substring(0, sql.length() - 4) + ";";
	else
	    sql = "SELECT * FROM BandeiraCartao;";
	
	try
	{
	    pst = conn.prepareStatement(sql);
	    int i = 1;
	    
	    if(bandeira.getId() != 0)
	    {
		pst.setInt(i, bandeira.getId());
		i++;
	    }
	    if(bandeira.getNomeBandeira() != null && !bandeira.getNomeBandeira().trim().equals(""))
	    {
		pst.setString(i, bandeira.getNomeBandeira());
		i++;
	    }
	    ResultSet rs = pst.executeQuery();
	    List<EntidadeDominio> bandeiras = new ArrayList<EntidadeDominio>();
	    while (rs.next()) 
	    {
		BandeiraCartao b = new BandeiraCartao();
		b.setId(rs.getInt("id_bandeira"));
		b.setNomeBandeira(rs.getString("nome_bandeira"));
		bandeiras.add(b);
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
	    return bandeiras;
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
	return null;
    } // consultar
}

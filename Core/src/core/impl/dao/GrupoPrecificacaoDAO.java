package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dominio.GrupoPrecificacao;
import dominio.EntidadeDominio;

public class GrupoPrecificacaoDAO extends AbstractJdbcDAO
{
    public GrupoPrecificacaoDAO()
    {
	super("GrupoPrecificacao", "id_grupo");
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
	GrupoPrecificacao grupo = (GrupoPrecificacao)entidade;
	
	String sql = "SELECT * FROM GrupoPrecificacao WHERE";
	
	if(grupo.getId() != 0)
	    sql += " id_grupo = ? AND";
	if(grupo.getNomeGrupo() != null && grupo.getNomeGrupo().trim().equals(""))
	    sql += " nome_grupo = ? AND";
	if(grupo.getMargemLucro() != 0)
	    sql += " porcentagem_lucro = ? AND";
	if(sql.endsWith(" AND"))
	    sql = sql.substring(0, sql.length() - 4) + ";";
	else
	    sql = "SELECT * FROM GrupoPrecificacao;";
	try
	{
	    pst = conn.prepareStatement(sql);
	    int i = 1;
	    
	    if(grupo.getId() != 0)
	    {
		pst.setInt(i, grupo.getId());
		i++;
	    }
	    if(grupo.getNomeGrupo() != null && grupo.getNomeGrupo().trim().equals(""))
	    {
		pst.setString(i, grupo.getNomeGrupo());
		i++;
	    }
	    if(grupo.getMargemLucro() != 0)
	    {
		pst.setDouble(i, grupo.getMargemLucro());
		i++;
	    }
	    ResultSet rs = pst.executeQuery();
	    List<EntidadeDominio> grupos = new ArrayList<EntidadeDominio>();
	    while (rs.next()) 
	    {
		GrupoPrecificacao g = new GrupoPrecificacao();
		g.setId(rs.getInt("id_grupo"));
		g.setNomeGrupo(rs.getString("nome_grupo"));
		g.setMargemLucro(rs.getDouble("porcentagem_lucro"));
		grupos.add(g);
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
	    return grupos;
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
	return null;
    } // consultar 
}

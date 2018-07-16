package core.impl.dao;

import dominio.Analise;
import dominio.EntidadeDominio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnaliseDAO extends AbstractJdbcDAO
{

    public AnaliseDAO()
    {
        super(null, null);
    }
    
    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alterar(EntidadeDominio entidade) throws SQLException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException
    {
        openConnection();
	PreparedStatement pst = null;
	Analise a = (Analise)entidade;
	String sql = "SELECT c.nome_categoria, SUM(i.qtde_livros) as q, SUM(i.preco_venda) as v " 
                + "FROM Pedido JOIN ItemPedido i USING(id_pedido) JOIN Livro USING(id_livro) " 
                + "JOIN LivroCat USING(id_livro) JOIN CategoriaLivro c USING(id_categoria) WHERE"; 
	
	if(a.getDtInicio() != null)
	    sql += " dt_pedido > ? AND";
	if(a.getDtFinal() != null)
	    sql += " dt_pedido < ? AND";
	if(sql.endsWith(" AND"))
	    sql = sql.substring(0, sql.length() - 4);
	else
	    sql = sql.substring(0, sql.length() - 6);
	sql += " GROUP BY c.nome_categoria;";
	try
	{
	    pst = conn.prepareStatement(sql);
	    int i = 1;
	    
	    if(a.getDtInicio() != null)
	    {
		pst.setTimestamp(i, new Timestamp(a.getDtInicio().getTime()));
		i++;
	    }
	    if(a.getDtFinal() != null)
	    {
		pst.setTimestamp(i, new Timestamp(a.getDtFinal().getTime()));
		i++;
	    }
	    ResultSet rs = pst.executeQuery();
	    List<EntidadeDominio> analise = new ArrayList<EntidadeDominio>();
            String resultado = "";
	    while (rs.next()) 
	    {
		resultado += "['" + rs.getString("nome_categoria") + "', "
                        + rs.getInt("q") + ", "
                        + rs.getDouble("v") + "],";
	    }
            a.setTabela(resultado);
            analise.add(a);
            try
	    {
		pst.close();
		conn.close();
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	    return analise;
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
	return null;
    } // consultar  
}

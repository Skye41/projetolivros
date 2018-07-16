package core.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import core.IDAO;
import core.util.Conexao;
import dominio.EntidadeDominio;

public abstract class AbstractJdbcDAO implements IDAO
{
    protected Connection conn;
    protected String table;
    protected String idTable;
    protected boolean ctrlTransaction = true;
    
    public AbstractJdbcDAO( Connection connection, String table, String idTable)
    {
	this.table = table;
	this.idTable = idTable;
	this.conn = connection;
    }
    
    protected AbstractJdbcDAO(String table, String idTable)
    {
	this.table = table;
	this.idTable = idTable;
    }
    
    @Override
    public void excluir(EntidadeDominio entidade)
    {
	openConnection();
	PreparedStatement pst = null;
	StringBuilder sb = new StringBuilder();
	sb.append("DELETE FROM ");
	sb.append(table);
	sb.append(" WHERE ");
	sb.append(idTable);
	sb.append(" = ?");
	try
	{
	    conn.setAutoCommit(false);
	    pst = conn.prepareStatement(sb.toString());
	    pst.setInt(1, entidade.getId());
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
		if(ctrlTransaction)
		    conn.close();
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	}
    }
    
    protected void openConnection()
    {
	try
	{
	    if(conn == null || conn.isClosed())
		conn = Conexao.getConnection();
	}
	catch(ClassNotFoundException e)
	{
	    e.printStackTrace();
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
    }
}

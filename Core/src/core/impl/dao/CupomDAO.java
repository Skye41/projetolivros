/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.impl.dao;

import dominio.Cliente;
import dominio.Cupom;
import dominio.EntidadeDominio;
import dominio.TipoCupom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CupomDAO extends AbstractJdbcDAO
{
    public CupomDAO()
    {
        super("Cupom", "id_cupom");
    }
    
    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException
    {
        openConnection();
	PreparedStatement pst = null;
	Cupom cupom = (Cupom)entidade;
	try
	{	    
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
	    sql.append("INSERT INTO Cupom (tipo_cupom, valor_cupom, dt_validade, id_cliente, status) VALUES (?,?,?,?,?);");
	    pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
	    pst.setString(1, cupom.getTipoCupom().name());
	    pst.setDouble(2, cupom.getValorCupom());
	    pst.setTimestamp(3, new Timestamp(cupom.getDtValidade().getTime()));
            pst.setInt(4, cupom.getCliente().getId());
            pst.setBoolean(5, cupom.isStatus());
	    pst.executeUpdate();
	    ResultSet rs = pst.getGeneratedKeys();
	    int id = 0;
	    while(rs.next())
	    {
		id = rs.getInt(1);
	    }
	    cupom.setId(id);
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
	Cupom cupom = (Cupom)entidade;
	try
	{	    
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
	    sql.append("UPDATE Cupom SET status=? WHERE id_cupom=?;");
	    pst = conn.prepareStatement(sql.toString());
	    pst.setBoolean(1, cupom.isStatus());
	    pst.setInt(2, cupom.getId());
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
	Cupom cupom = (Cupom)entidade;
	String sql = "SELECT * FROM Cupom WHERE";
	
	if(cupom.getId() != 0)
	    sql += " id_cupom = ? AND";
	if(cupom.getTipoCupom() != null)
	    sql += " tipo_cupom = ? AND";
	if(cupom.getValorCupom() != 0)
	    sql += " valor_cupom = ? AND";
	if(cupom.getDtValidade() != null)
	    sql += " tipo_cupom = ? AND";
	if(cupom.getCliente() != null && cupom.getCliente().getId() != 0)
	    sql += " id_cliente = ? AND status=true AND";
	if(sql.endsWith(" AND"))
	    sql = sql.substring(0, sql.length() - 4) + ";";
	else
	    sql = "SELECT * FROM Cupom;";
	
	try
	{
	    pst = conn.prepareStatement(sql);
	    int i = 1;
	    
	    if(cupom.getId() != 0)
            {
		pst.setInt(i, cupom.getId());
		i++;
	    }
            if(cupom.getTipoCupom() != null)
            {
		pst.setString(i, cupom.getTipoCupom().name());
		i++;
	    }
            if(cupom.getValorCupom() != 0)
            {
		pst.setDouble(i, cupom.getValorCupom());
		i++;
	    }
            if(cupom.getDtValidade() != null)
            {
		pst.setTimestamp(i, new Timestamp(cupom.getDtValidade().getTime()));
		i++;
	    }
            if(cupom.getCliente() != null && cupom.getCliente().getId() != 0)
            {
		pst.setInt(i, cupom.getCliente().getId());
		i++;
	    }
        
	    ResultSet rs = pst.executeQuery();
	    List<EntidadeDominio> cupons = new ArrayList<EntidadeDominio>();
	    while (rs.next()) 
	    {
		Cupom c = new Cupom();
		c.setId(rs.getInt("id_cupom"));
		c.setTipoCupom(TipoCupom.valueOf(rs.getString("tipo_cupom")));
		c.setValorCupom(rs.getDouble("valor_cupom"));
		c.setDtValidade(new Date(rs.getTimestamp("dt_validade").getTime()));
                c.setCliente(new Cliente(rs.getInt("id_cliente")));
		cupons.add(c);
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
	    return cupons;
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
	return null;
    } // consultar  
    
}

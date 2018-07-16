/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.impl.dao;

import dominio.EntidadeDominio;
import dominio.PagamentoTeste;
import dominio.StatusPedido;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Ana Miani
 */
public class PagamentoTesteDAO extends AbstractJdbcDAO
{

    public PagamentoTesteDAO()
    {
        super("", "");
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
        PagamentoTeste pt = (PagamentoTeste)entidade;
        new PedidoDAO().alterar(pt.getPedido());
        return null;
    }
    
}

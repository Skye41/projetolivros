package core.impl.dao;

import dominio.EntidadeDominio;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoDAO extends AbstractJdbcDAO
{

    public CarrinhoDAO()
    {
        super(null, null);
    }
    
    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException{}

    @Override
    public void alterar(EntidadeDominio entidade) throws SQLException{}

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException
    {
        return new ArrayList<EntidadeDominio>();
    }
    
    @Override
    public void excluir(EntidadeDominio entidade){}
    
}

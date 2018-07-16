package core.impl.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dominio.Cliente;
import dominio.EntidadeDominio;
import dominio.TipoUsuario;
import dominio.Usuario;

public class LoginDAO extends AbstractJdbcDAO
{
    public LoginDAO()
    {
	super("","");
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
	List<EntidadeDominio> usuario = null;
	try
	{
	    UsuarioDAO udao = new UsuarioDAO();
	    Usuario u = (Usuario)udao.consultar(entidade).get(0);
	    if(u != null)
	    {
		if(u.getTipo().equals(TipoUsuario.CLIENTE))
		{
		    Cliente c = new Cliente();
		    c.setUsuario(u);
		    usuario = new ClienteDAO().consultar(c);
		}
		else
		{
		    usuario = new ArrayList<EntidadeDominio>();
		    usuario.add(u);
		}
	    }
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
	return usuario;
    }
}

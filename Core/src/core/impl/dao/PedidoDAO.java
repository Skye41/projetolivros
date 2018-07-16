package core.impl.dao;

import dominio.Cartao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import dominio.CartaoPedido;
import dominio.Cliente;
import dominio.Cupom;
import dominio.Endereco;
import dominio.EntidadeDominio;
import dominio.ItemPedido;
import dominio.Livro;
import dominio.Pedido;
import dominio.StatusPedido;
import dominio.TipoCupom;
import java.util.ArrayList;
import java.util.Date;

public class PedidoDAO extends AbstractJdbcDAO
{
    public PedidoDAO()
    {
	super("Pedido", "id_pedido");
    }
    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException
    {
	openConnection();
	PreparedStatement pst = null;
	Pedido pedido = (Pedido)entidade;
	try
	{	    
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
	    sql.append("INSERT INTO Pedido (dt_pedido, status_pedido, valor_frete, id_entrega, id_cliente) VALUES (?,?,?,?,?);");
	    pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
	    pst.setTimestamp(1, new Timestamp(pedido.getDtPedido().getTime()));
	    pst.setString(2, pedido.getStatus().name());
	    pst.setDouble(3, pedido.getValorFrete());
	    pst.setInt(4, pedido.getEnderecoEntrega().getId());
	    pst.setInt(5, pedido.getCliente().getId());
	    pst.executeUpdate();
	    ResultSet rs = pst.getGeneratedKeys();
	    int id = 0;
	    while(rs.next())
	    {
		id = rs.getInt(1);
	    }
	    pedido.setId(id);
	    conn.commit();
	    pst.close();
	    
	    for(ItemPedido ip : pedido.getItens())
	    {
		sql = new StringBuilder();
		sql.append("INSERT INTO ItemPedido (id_livro, qtde_livros, preco_venda, id_pedido) VALUES (?,?,?,?);");
		pst = conn.prepareStatement(sql.toString());
		pst.setInt(1, ip.getLivro().getId());
		pst.setInt(2, ip.getQtdeItem());
		pst.setDouble(3, ip.getValorItem());
		pst.setInt(4, pedido.getId());
		pst.executeUpdate();
		conn.commit();
		pst.close();
	    }
	    
	    if(pedido.getPromocional() != null)
	    {
		sql = new StringBuilder();
		sql.append("INSERT INTO CuponsPedido (id_pedido, id_cupom) VALUES (?,?);");
		pst = conn.prepareStatement(sql.toString());
		pst.setInt(1, pedido.getId());
		pst.setInt(2, pedido.getPromocional().getId());
		pst.executeUpdate();
		conn.commit();
		pst.close();
                pedido.getPromocional().setStatus(false);
                new CupomDAO().alterar(pedido.getPromocional());
	    }
	    
	    if(pedido.getTroca() != null)
	    {
		sql = new StringBuilder();
		sql.append("INSERT INTO CuponsPedido (id_pedido, id_cupom) VALUES (?,?);");
		pst = conn.prepareStatement(sql.toString());
		pst.setInt(1, pedido.getId());
		pst.setInt(2, pedido.getTroca().getId());
		pst.executeUpdate();
		conn.commit();
		pst.close();
                pedido.getTroca().setStatus(false);
                new CupomDAO().alterar(pedido.getTroca());
	    }
	    
	    if(pedido.getCartoes() != null && !pedido.getCartoes().isEmpty())
	    {
		for(CartaoPedido c : pedido.getCartoes())
		{
		    sql = new StringBuilder();
		    sql.append("INSERT INTO CartoesPedido (id_pedido, id_cartao, valor_cartao) VALUES (?,?,?);");
		    pst = conn.prepareStatement(sql.toString());
		    pst.setInt(1, pedido.getId());
		    pst.setInt(2, c.getId());
		    pst.setDouble(3, c.getValorCartao());
		    pst.executeUpdate();
		    conn.commit();
		    pst.close();
		}
	    }
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
	Pedido pedido = (Pedido)entidade;
	try
	{	    
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
	    sql.append("UPDATE Pedido SET status_pedido=? WHERE id_pedido=?");
            pst = conn.prepareStatement(sql.toString());
            pst.setString(1, pedido.getStatus().name());
            pst.setInt(2, pedido.getId());
            pst.executeUpdate();
	    conn.commit();
            if(pedido.getStatus().equals(StatusPedido.APROVADO))
            {
                for(ItemPedido ip : pedido.getItens())
                {
                    sql = new StringBuilder();
                    sql.append("UPDATE Livro SET qtde_estoque=? WHERE id_livro=?;");
                    pst = conn.prepareStatement(sql.toString());
                    pst.setInt(1, (ip.getLivro().getQtdeEstoque() - ip.getQtdeItem()));
                    pst.setInt(2, ip.getLivro().getId());
                    pst.executeUpdate();
                    conn.commit();
                    pst.close();
                }
            }
            if(pedido.getStatus().equals(StatusPedido.TROCADO) && pedido.isEstoque())
            {
                for(ItemPedido ip : pedido.getItens())
                {
                    sql = new StringBuilder();
                    sql.append("UPDATE Livro SET qtde_estoque=? WHERE id_livro=?;");
                    pst = conn.prepareStatement(sql.toString());
                    pst.setInt(1, (ip.getLivro().getQtdeEstoque() + ip.getQtdeItem()));
                    pst.setInt(2, ip.getLivro().getId());
                    pst.executeUpdate();
                    conn.commit();
                }
            }
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
    }
    
    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException
    {
	openConnection();
	PreparedStatement pst = null;
	Pedido pedido = (Pedido)entidade;
	String sql = "SELECT * FROM Pedido WHERE";
	
	if(pedido.getId() != 0)
	    sql += " id_pedido = ? AND";
	if(pedido.getDtPedido() != null)
	    sql += " dt_pedido = ? AND";
	if(pedido.getCliente() != null && pedido.getCliente().getId() != 0)
	    sql += " id_cliente = ? AND";
	if(sql.endsWith(" AND"))
	    sql = sql.substring(0, sql.length() - 4) + "ORDER BY dt_pedido DESC;";
	else
	    sql = "SELECT * FROM Pedido ORDER BY dt_pedido DESC;";
	
	try
	{
	    pst = conn.prepareStatement(sql);
	    int i = 1;
	    
	    if(pedido.getId() != 0)
	    {
		pst.setInt(i, pedido.getId());
		i++;
	    }
	    if(pedido.getDtPedido() != null)
	    {
		pst.setTimestamp(i, new Timestamp(pedido.getDtPedido().getTime()));
		i++;
	    }
	    if(pedido.getCliente() != null && pedido.getCliente().getId() != 0)
	    {
		pst.setInt(i, pedido.getCliente().getId());
		i++;
	    }
	    ResultSet rs = pst.executeQuery();
	    List<EntidadeDominio> pedidos = new ArrayList<EntidadeDominio>();
	    while (rs.next()) 
	    {
		Pedido p = new Pedido();
		p.setId(rs.getInt("id_pedido"));
                p.setDtPedido(new Date(rs.getTimestamp("dt_pedido").getTime()));
                p.setStatus(StatusPedido.valueOf(rs.getString("status_pedido")));
                p.setValorFrete(rs.getDouble("valor_frete"));
                
                p.setEnderecoEntrega((Endereco)new EnderecoDAO().consultar(new Endereco(rs.getInt("id_entrega"))).get(0));
                p.setCliente((Cliente)new ClienteDAO().consultar(new Cliente(rs.getInt("id_cliente"))).get(0));
                
                sql = "SELECT * FROM CartoesPedido WHERE id_pedido = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, p.getId());
                ResultSet rs2 = pst.executeQuery();
                List<CartaoPedido> cartoes = new ArrayList<CartaoPedido>();
                while(rs2.next())
                {
                    Cartao ct = (Cartao)new CartaoDAO().consultar(new Cartao(rs2.getInt("id_cartao"))).get(0);
                    CartaoPedido ctp = new CartaoPedido();
                    ctp.setId(ct.getId());
                    ctp.setBandeira(ct.getBandeira());
                    ctp.setCliente(ct.getCliente());
                    ctp.setCodigoSeguranca(ct.getCodigoSeguranca());
                    ctp.setDtVencimento(ct.getDtVencimento());
                    ctp.setNomeCartao(ct.getNomeCartao());
                    ctp.setNumero(ct.getNumero());
                    ctp.setValorCartao(rs2.getDouble("valor_cartao"));
                    cartoes.add(ctp);
                }
                p.setCartoes(cartoes);
                
                sql = "SELECT * FROM CuponsPedido WHERE id_pedido = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, p.getId());
                rs2 = pst.executeQuery();
                while(rs2.next())
                {
                    Cupom cp = (Cupom)new CupomDAO().consultar(new Cupom(rs2.getInt("id_cupom"))).get(0);
                    if(cp.getTipoCupom().equals(TipoCupom.PROMOCIONAL))
                        p.setPromocional(cp);
                    else
                        p.setTroca(cp);
                }
                
                sql = "SELECT * FROM ItemPedido WHERE id_pedido = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, p.getId());
                rs2 = pst.executeQuery();
                List<ItemPedido> itens = new ArrayList<ItemPedido>();
                while(rs2.next())
                {
                    ItemPedido ip = new ItemPedido();
                    ip.setId(rs2.getInt("id_item"));
                    ip.setLivro((Livro)new LivroDAO().consultar(new Livro(rs2.getInt("id_livro"))).get(0));
                    ip.setPedido(p);
                    ip.setQtdeItem(rs2.getInt("qtde_livros"));
                    ip.setValorItem(rs2.getDouble("preco_venda"));
                    itens.add(ip);
                }
                p.setItens(itens);
                
                pedidos.add(p);
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
	    return pedidos;
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
	return null;
    } // consultar  
}

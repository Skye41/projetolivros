package core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dominio.Autor;
import dominio.CategoriaLivro;
import dominio.Dimensoes;
import dominio.Editora;
import dominio.EntidadeDominio;
import dominio.GrupoPrecificacao;
import dominio.Livro;
import dominio.StatusLivro;

public class LivroDAO extends AbstractJdbcDAO
{
    public LivroDAO()
    {
	super("Livro", "id_livro");
    }
    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException
    {
	openConnection();
	PreparedStatement pst = null;
	Livro livro = (Livro)entidade;
	
	try
	{	    
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
	    sql.append("INSERT INTO Livro (titulo, id_editora, sinopse, paginas, edicao, ano, isbn, ");
	    sql.append("codigo_barras, preco_custo, id_grupo, preco_venda, qtde_estoque, qtde_venda, id_status, altura, ");
	    sql.append("largura, peso, profundidade) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
	    pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
	    pst.setString(1, livro.getTitulo());
	    pst.setInt(2, livro.getEditora().getId());
	    pst.setString(3, livro.getSinopse());
	    pst.setInt(4, livro.getPaginas());
	    pst.setInt(5, livro.getEdicao());
	    pst.setInt(6, livro.getAno());
	    pst.setString(7, livro.getIsbn());
	    pst.setString(8, livro.getCodigoBarras());
	    pst.setDouble(9, livro.getPrecoCusto());
	    pst.setInt(10, livro.getGrupo().getId());
	    pst.setDouble(11, livro.getPrecoVenda());
	    pst.setDouble(12, livro.getQtdeEstoque());
	    pst.setDouble(13, livro.getQtdeMinimaVenda());
	    pst.setInt(14, livro.getStatusLivro().getId());
	    pst.setDouble(15, livro.getDimensoes().getAltura());
	    pst.setDouble(16, livro.getDimensoes().getLargura());
	    pst.setDouble(17, livro.getDimensoes().getPeso());
	    pst.setDouble(18, livro.getDimensoes().getProfundidade());
	    pst.executeUpdate();
	    ResultSet rs = pst.getGeneratedKeys();
	    int id = 0;
	    while(rs.next())
	    {
		id = rs.getInt(1);
	    }
	    livro.setId(id);
	    conn.commit();
	    for (Autor autor : livro.getAutores())
	    {
		pst = conn.prepareStatement("INSERT INTO LivroAutor (id_autor, id_livro) VALUES (?,?);");
		pst.setInt(1, autor.getId());
		pst.setInt(2, livro.getId());
		pst.executeUpdate();
	    }
	    conn.commit();
	    for(CategoriaLivro cat : livro.getCategorias())
	    {
		pst = conn.prepareStatement("INSERT INTO LivroCat (id_livro, id_categoria) VALUES (?, ?);");
		pst.setInt(1, livro.getId());
		pst.setInt(2, cat.getId());
		pst.executeUpdate();
	    }
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
	Livro livro = (Livro)entidade;
	try
	{	    
	    conn.setAutoCommit(false);
	    StringBuilder sql = new StringBuilder();
            sql.append("UPDATE Livro SET titulo=?, id_editora=?, sinopse=?, paginas=?, ");
            sql.append("edicao=?, ano=?, isbn=?, codigo_barras=?, preco_custo=?, id_grupo=?, ");
            sql.append("preco_venda=?, qtde_estoque=?, qtde_venda=?, id_status=?, altura=?, ");
            sql.append("largura=?, peso=?, profundidade=? WHERE id_livro=?");
	    pst = conn.prepareStatement(sql.toString());
	    pst.setString(1, livro.getTitulo());
	    pst.setInt(2, livro.getEditora().getId());
	    pst.setString(3, livro.getSinopse());
	    pst.setInt(4, livro.getPaginas());
	    pst.setInt(5, livro.getEdicao());
	    pst.setInt(6, livro.getAno());
	    pst.setString(7, livro.getIsbn());
	    pst.setString(8, livro.getCodigoBarras());
	    pst.setDouble(9, livro.getPrecoCusto());
	    pst.setInt(10, livro.getGrupo().getId());
	    pst.setDouble(11, livro.getPrecoVenda());
	    pst.setDouble(12, livro.getQtdeEstoque());
	    pst.setDouble(13, livro.getQtdeMinimaVenda());
	    pst.setInt(14, livro.getStatusLivro().getId());
	    pst.setDouble(15, livro.getDimensoes().getAltura());
	    pst.setDouble(16, livro.getDimensoes().getLargura());
	    pst.setDouble(17, livro.getDimensoes().getPeso());
	    pst.setDouble(18, livro.getDimensoes().getProfundidade());
            pst.setInt(19, livro.getId());
	    pst.executeUpdate();
	    conn.commit();
            excluirAutor(entidade);
            for (Autor autor : livro.getAutores())
	    {
		pst = conn.prepareStatement("INSERT INTO LivroAutor (id_autor, id_livro) VALUES (?,?);");
		pst.setInt(1, autor.getId());
		pst.setInt(2, livro.getId());
		pst.executeUpdate();
	    }
	    conn.commit();
            excluirCategoria(entidade);
	    for(CategoriaLivro cat : livro.getCategorias())
	    {
		pst = conn.prepareStatement("INSERT INTO LivroCat (id_livro, id_categoria) VALUES (?, ?);");
		pst.setInt(1, livro.getId());
		pst.setInt(2, cat.getId());
		pst.executeUpdate();
	    }
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
	Livro livro = (Livro)entidade;
	
	String sql = "SELECT * FROM Livro WHERE";
	
	if(livro.getId() != 0)
	    sql += " id_livro = ? AND";
	
	if(sql.endsWith(" AND"))
	    sql = sql.substring(0, sql.length() - 4) + ";";
	else
	    sql = "SELECT * FROM Livro;";
	try
	{
	    pst = conn.prepareStatement(sql);
	    int i = 1;
	    
	    if(livro.getId() != 0)
	    {
		pst.setInt(i, livro.getId());
		i++;
	    }
	    
	    ResultSet rs = pst.executeQuery();
	    List<EntidadeDominio> livros = new ArrayList<EntidadeDominio>();
	    while (rs.next()) 
	    {
		Livro l = new Livro();
		l.setId(rs.getInt("id_livro"));
		l.setTitulo(rs.getString("titulo"));
		l.setEditora((Editora)new EditoraDAO().consultar(new Editora(rs.getInt("id_editora"))).get(0));
		l.setSinopse(rs.getString("sinopse"));
		l.setPaginas(rs.getInt("paginas"));
		l.setEdicao(rs.getInt("edicao"));
		l.setAno(rs.getInt("ano"));
		l.setIsbn(rs.getString("isbn"));
		l.setCodigoBarras(rs.getString("codigo_barras"));
		l.setPrecoCusto(rs.getDouble("preco_custo"));
		l.setGrupo(new GrupoPrecificacao(rs.getInt("id_grupo")));
		l.setPrecoVenda(rs.getDouble("preco_venda"));
		l.setQtdeEstoque(rs.getInt("qtde_estoque"));
		l.setQtdeMinimaVenda(rs.getInt("qtde_venda"));
		l.setStatusLivro((StatusLivro)new StatusLivroDAO().consultar(new StatusLivro(rs.getInt("id_status"))).get(0));
		l.setDimensoes(new Dimensoes());
		l.getDimensoes().setAltura(rs.getDouble("altura"));
		l.getDimensoes().setLargura(rs.getDouble("largura"));
		l.getDimensoes().setPeso(rs.getDouble("peso"));
		l.getDimensoes().setProfundidade(rs.getDouble("profundidade"));
		
		// Consultar autores do livro
		l.setAutores(new ArrayList<Autor>());
		pst = conn.prepareStatement("SELECT * FROM LivroAutor WHERE id_livro=?;");
		pst.setInt(1, l.getId());
		ResultSet rs2 = pst.executeQuery();
		while (rs2.next())
		{
		    Autor a = new Autor(rs2.getInt("id_autor"));
		    l.getAutores().add((Autor)new AutorDAO().consultar(a).get(0));
		}
		
		// Consultar categorias do livro
		l.setCategorias(new ArrayList<CategoriaLivro>());
		pst = conn.prepareStatement("SELECT * FROM LivroCat WHERE id_livro=?;");
		pst.setInt(1, l.getId());
		rs2 = pst.executeQuery();
		while (rs2.next())
		{
		    CategoriaLivro c = new CategoriaLivro(rs2.getInt("id_categoria"));
		    l.getCategorias().add((CategoriaLivro)new CategoriaLivroDAO().consultar(c).get(0));
		}
		
		livros.add(l);
	    }
	    livros.addAll(new EditoraDAO().consultar(new Editora()));
	    livros.addAll(new AutorDAO().consultar(new Autor()));
	    livros.addAll(new CategoriaLivroDAO().consultar(new CategoriaLivro()));
	    livros.addAll(new GrupoPrecificacaoDAO().consultar(new GrupoPrecificacao()));
	    livros.addAll(new StatusLivroDAO().consultar(new StatusLivro()));
	    
            try
	    {
		pst.close();
		conn.close();
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	    return livros;
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
	return null;
    } // consultar 
    
    @Override
    public void excluir(EntidadeDominio entidade)
    {
        excluirAutor(entidade);
        excluirCategoria(entidade);
        super.excluir(entidade);
    } // excluir
    
    public void excluirAutor(EntidadeDominio entidade)
    {
        openConnection();
	PreparedStatement pst = null;
        
        try
	{
            conn.setAutoCommit(false);
            pst = conn.prepareStatement("DELETE FROM LivroAutor WHERE id_livro=?;");
            pst.setInt(1, entidade.getId());
            pst.executeUpdate();
            conn.commit();
            pst.close();
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
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	}
    } // excluirAutor
    
    public void excluirCategoria(EntidadeDominio entidade)
    {
        openConnection();
	PreparedStatement pst = null;
        
        try
	{
            conn.setAutoCommit(false);
            pst = conn.prepareStatement("DELETE FROM LivroCat WHERE id_livro=?;");
            pst.setInt(1, entidade.getId());
            pst.executeUpdate();
            conn.commit();
            pst.close();
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
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	}
    } // excluirCategoria
}

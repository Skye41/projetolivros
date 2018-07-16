package dominio;

import java.util.List;

public class Livro extends EntidadeDominio
{
    private String titulo;
    private Editora editora;
    private List<CategoriaLivro> categorias;
    private List<Autor> autores;
    private String sinopse;
    private int paginas;
    private int edicao;
    private int ano;
    private String isbn;
    private String codigoBarras;
    private Dimensoes dimensoes;
    private double precoCusto;
    private double precoVenda;
    private GrupoPrecificacao grupo;
    private StatusLivro statusLivro;
    private int qtdeEstoque;
    private int qtdeMinimaVenda;

    public Livro()
    {
    }

    public Livro(int id)
    {
        setId(id);
    }
    
    public String getTitulo()
    {
        return titulo;
    }
    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }
    public Editora getEditora()
    {
        return editora;
    }
    public void setEditora(Editora editora)
    {
        this.editora = editora;
    }
    public List<CategoriaLivro> getCategorias()
    {
        return categorias;
    }
    public void setCategorias(List<CategoriaLivro> categoria)
    {
        this.categorias = categoria;
    }
    public List<Autor> getAutores()
    {
        return autores;
    }
    public void setAutores(List<Autor> autores)
    {
        this.autores = autores;
    }
    public String getSinopse()
    {
        return sinopse;
    }
    public void setSinopse(String sinopse)
    {
        this.sinopse = sinopse;
    }
    public int getPaginas()
    {
        return paginas;
    }
    public void setPaginas(int paginas)
    {
        this.paginas = paginas;
    }
    public int getEdicao()
    {
        return edicao;
    }
    public void setEdicao(int edicao)
    {
        this.edicao = edicao;
    }
    public int getAno()
    {
        return ano;
    }
    public void setAno(int ano)
    {
        this.ano = ano;
    }
    public String getIsbn()
    {
        return isbn;
    }
    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }
    public String getCodigoBarras()
    {
        return codigoBarras;
    }
    public void setCodigoBarras(String codigoBarras)
    {
        this.codigoBarras = codigoBarras;
    }
    public Dimensoes getDimensoes()
    {
        return dimensoes;
    }
    public void setDimensoes(Dimensoes dimensoes)
    {
        this.dimensoes = dimensoes;
    }
    public double getPrecoCusto()
    {
        return precoCusto;
    }
    public void setPrecoCusto(double precoCusto)
    {
        this.precoCusto = precoCusto;
    }
    public double getPrecoVenda()
    {
        return precoVenda;
    }
    public void setPrecoVenda(double precoVenda)
    {
        this.precoVenda = precoVenda;
    }
    public GrupoPrecificacao getGrupo()
    {
        return grupo;
    }
    public void setGrupo(GrupoPrecificacao grupo)
    {
        this.grupo = grupo;
    }
    public StatusLivro getStatusLivro()
    {
        return statusLivro;
    }
    public void setStatusLivro(StatusLivro statusLivro)
    {
        this.statusLivro = statusLivro;
    }
    public int getQtdeEstoque()
    {
        return qtdeEstoque;
    }
    public void setQtdeEstoque(int qtdeEstoque)
    {
        this.qtdeEstoque = qtdeEstoque;
    }
    public int getQtdeMinimaVenda()
    {
        return qtdeMinimaVenda;
    }
    public void setQtdeMinimaVenda(int qtdeMinimaVenda)
    {
        this.qtdeMinimaVenda = qtdeMinimaVenda;
    }
}

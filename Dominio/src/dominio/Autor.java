package dominio;

import java.util.ArrayList;
import java.util.List;

public class Autor extends EntidadeDominio
{
    private String nomeAutor;
    private List<Livro> livros = new ArrayList<Livro>();
    
    public Autor(){}
    public Autor(int id)
    {
	setId(id);
    }
    
    public String getNomeAutor()
    {
        return nomeAutor;
    }
    public void setNomeAutor(String nomeAutor)
    {
        this.nomeAutor = nomeAutor;
    }
    public List<Livro> getLivros()
    {
        return livros;
    }
    public void setLivros(List<Livro> livros)
    {
        this.livros = livros;
    }
    public void addLivro(Livro livro)
    {
	livros.add(livro);
    }
}

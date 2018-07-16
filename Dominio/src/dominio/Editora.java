package dominio;

import java.util.ArrayList;
import java.util.List;

public class Editora extends EntidadeDominio
{
    private String nomeEditora;
    private List<Livro> livros = new ArrayList<Livro>();
    
    public Editora(){}
    
    public Editora(int id)
    {
	setId(id);
    }
    
    public String getNomeEditora()
    {
        return nomeEditora;
    }
    public void setNomeEditora(String nomeEditora)
    {
        this.nomeEditora = nomeEditora;
    }
    public List<Livro> getLivros()
    {
        return livros;
    }
    public void setLivros(List<Livro> livros)
    {
        this.livros = livros;
    }
}

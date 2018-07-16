package dominio;

public class CategoriaLivro extends EntidadeDominio
{
    private String nomeCategoria;
    
    public CategoriaLivro(){}
    
    public CategoriaLivro(int id)
    {
	setId(id);
    }
    public String getNomeCategoria()
    {
        return nomeCategoria;
    }
    public void setNomeCategoria(String nomeCategoria)
    {
        this.nomeCategoria = nomeCategoria;
    }
}

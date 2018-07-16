package dominio;

public class SubCategoria extends EntidadeDominio
{
    private String nomeSubCategoria;
    private CategoriaLivro categoria;
    
    public SubCategoria(){}
    
    public SubCategoria(int id)
    {
	setId(id);
    }
    
    public String getNomeSubCategoria()
    {
        return nomeSubCategoria;
    }
    public void setNomeSubCategoria(String nomeSubCategoria)
    {
        this.nomeSubCategoria = nomeSubCategoria;
    }
    public CategoriaLivro getCategoria()
    {
        return categoria;
    }
    public void setCategoria(CategoriaLivro categoria)
    {
        this.categoria = categoria;
    }
}

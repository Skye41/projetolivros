package dominio;

public class StatusLivro extends EntidadeDominio
{
    private boolean statusLivro;
    private String justificativa;
    
    public StatusLivro(){}
    
    public StatusLivro(int id)
    {
	this.setId(id);
    }
    
    public boolean isStatusLivro()
    {
        return statusLivro;
    }
    public void setStatusLivro(boolean statusLivro)
    {
        this.statusLivro = statusLivro;
    }
    public String getJustificativa()
    {
        return justificativa;
    }
    public void setJustificativa(String justificativa)
    {
        this.justificativa = justificativa;
    }
}

package dominio;

public class BandeiraCartao extends EntidadeDominio
{
    private String nomeBandeira;
    
    public BandeiraCartao(){}
    public BandeiraCartao(int id)
    {
	setId(id);
    }
    
    public String getNomeBandeira()
    {
        return nomeBandeira;
    }

    public void setNomeBandeira(String nomeBandeira)
    {
        this.nomeBandeira = nomeBandeira;
    }
}

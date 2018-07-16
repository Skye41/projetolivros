package dominio;

public class GrupoPrecificacao extends EntidadeDominio
{
    private String nomeGrupo;
    private double margemLucro;
    
    public GrupoPrecificacao(){}
    
    public GrupoPrecificacao(int id)
    {
	this.setId(id);
    }
    public String getNomeGrupo()
    {
        return nomeGrupo;
    }
    public void setNomeGrupo(String nomeGrupo)
    {
        this.nomeGrupo = nomeGrupo;
    }
    public double getMargemLucro()
    {
        return margemLucro;
    }
    public void setMargemLucro(double margemLucro)
    {
        this.margemLucro = margemLucro;
    }
}

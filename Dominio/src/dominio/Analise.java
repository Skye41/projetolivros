package dominio;

import java.util.Date;

public class Analise extends EntidadeDominio
{
    private Date dtInicio;
    private Date dtFinal;
    private String tabela;

    public Date getDtInicio()
    {
        return dtInicio;
    }

    public void setDtInicio(Date dtInicio)
    {
        this.dtInicio = dtInicio;
    }

    public Date getDtFinal()
    {
        return dtFinal;
    }

    public void setDtFinal(Date dtFinal)
    {
        this.dtFinal = dtFinal;
    }

    public String getTabela()
    {
        return tabela;
    }

    public void setTabela(String tabela)
    {
        this.tabela = tabela;
    }
}

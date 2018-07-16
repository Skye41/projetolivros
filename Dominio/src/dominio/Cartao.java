package dominio;

import java.util.Date;

public class Cartao extends EntidadeDominio
{
    private String numero;
    private String nomeCartao;
    private Date dtVencimento;
    private String codigoSeguranca;
    private BandeiraCartao bandeira;
    private Cliente cliente;
    private boolean preferencial;
    
    public Cartao()
    {
	// TODO Auto-generated constructor stub
    }

    public Cartao(int id)
    {
        setId(id);
    }
    
    public Cartao(Cliente cliente)
    {
	this.cliente = cliente;
    }
    
    public String getNumero()
    {
        return numero;
    }
    public void setNumero(String numero)
    {
        this.numero = numero;
    }
    public String getNomeCartao()
    {
        return nomeCartao;
    }
    public void setNomeCartao(String nomeCartao)
    {
        this.nomeCartao = nomeCartao;
    }
    public Date getDtVencimento()
    {
        return dtVencimento;
    }
    public void setDtVencimento(Date dtVencimento)
    {
        this.dtVencimento = dtVencimento;
    }
    public String getCodigoSeguranca()
    {
        return codigoSeguranca;
    }
    public void setCodigoSeguranca(String codigoSeguranca)
    {
        this.codigoSeguranca = codigoSeguranca;
    }
    public BandeiraCartao getBandeira()
    {
        return bandeira;
    }
    public void setBandeira(BandeiraCartao bandeira)
    {
        this.bandeira = bandeira;
    }
    public Cliente getCliente()
    {
        return cliente;
    }
    public void setCliente(Cliente cliente)
    {
        this.cliente = cliente;
    }

    public boolean isPreferencial()
    {
        return preferencial;
    }

    public void setPreferencial(boolean preferencial)
    {
        this.preferencial = preferencial;
    }
    
}

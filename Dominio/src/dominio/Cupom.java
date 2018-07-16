package dominio;

import java.util.Date;

public class Cupom extends EntidadeDominio
{
    private TipoCupom tipoCupom;
    private double valorCupom;
    private Date dtValidade;
    private Cliente cliente;
    private boolean status;

    public Cupom()
    {
    }
    public Cupom(Cliente cliente)
    {
	this.cliente = cliente;
    }
    public Cupom(Integer id)
    {
	setId(id);
    }
    public TipoCupom getTipoCupom()
    {
        return tipoCupom;
    }
    public void setTipoCupom(TipoCupom tipoCupom)
    {
        this.tipoCupom = tipoCupom;
    }
    public double getValorCupom()
    {
        return valorCupom;
    }
    public void setValorCupom(double valorCupom)
    {
        this.valorCupom = valorCupom;
    }
    public Date getDtValidade()
    {
        return dtValidade;
    }
    public void setDtValidade(Date dtValidade)
    {
        this.dtValidade = dtValidade;
    }
    public Cliente getCliente()
    {
        return cliente;
    }
    public void setCliente(Cliente cliente)
    {
        this.cliente = cliente;
    }

    public boolean isStatus()
    {
        return status;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
    }
    
}

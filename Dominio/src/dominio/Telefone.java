package dominio;

public class Telefone extends EntidadeDominio
{
    private TipoTelefone tipo;
    private String ddd;
    private String numero;
    private Cliente cliente;
    
    public Telefone(){}
    
    public Telefone(Cliente cliente)
    {
	this.cliente = cliente;
    }
    
    public TipoTelefone getTipo()
    {
        return tipo;
    }
    public void setTipo(TipoTelefone tipo)
    {
        this.tipo = tipo;
    }
    public String getDdd()
    {
        return ddd;
    }
    public void setDdd(String ddd)
    {
        this.ddd = ddd;
    }
    public String getNumero()
    {
        return numero;
    }
    public void setNumero(String numero)
    {
        this.numero = numero;
    }
    public Cliente getCliente()
    {
        return cliente;
    }
    public void setCliente(Cliente cliente)
    {
        this.cliente = cliente;
    }
}

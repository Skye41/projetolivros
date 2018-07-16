package dominio;

public class Endereco extends EntidadeDominio
{ 
    private TipoEndereco tipoEndereco;
    private String tipoResidencia;
    private String tipoLogradouro;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;
    private String pais;
    private String observacoes;
    private Cliente cliente;
    private boolean preferencial;
    
    public Endereco(){}
    
    public Endereco(Cliente cliente)
    {
	this.cliente = cliente;
    }
    
    public Endereco(int id)
    {
	setId(id);
    }

    public TipoEndereco getTipoEndereco()
    {
        return tipoEndereco;
    }
    public void setTipoEndereco(TipoEndereco tipoEndereco)
    {
        this.tipoEndereco = tipoEndereco;
    }
    public String getTipoResidencia()
    {
        return tipoResidencia;
    }
    public void setTipoResidencia(String tipoResidencia)
    {
        this.tipoResidencia = tipoResidencia;
    }
    public String getTipoLogradouro()
    {
        return tipoLogradouro;
    }
    public void setTipoLogradouro(String tipoLogradouro)
    {
        this.tipoLogradouro = tipoLogradouro;
    }
    public String getLogradouro()
    {
        return logradouro;
    }
    public void setLogradouro(String logradouro)
    {
        this.logradouro = logradouro;
    }
    public String getNumero()
    {
        return numero;
    }
    public void setNumero(String numero)
    {
        this.numero = numero;
    }
    public String getBairro()
    {
        return bairro;
    }
    public void setBairro(String bairro)
    {
        this.bairro = bairro;
    }
    public String getCep()
    {
        return cep;
    }
    public void setCep(String cep)
    {
        this.cep = cep;
    }
    public String getCidade()
    {
        return cidade;
    }
    public void setCidade(String cidade)
    {
        this.cidade = cidade;
    }
    public String getEstado()
    {
        return estado;
    }
    public void setEstado(String estado)
    {
        this.estado = estado;
    }
    public String getPais()
    {
        return pais;
    }
    public void setPais(String pais)
    {
        this.pais = pais;
    }
    public String getObservacoes()
    {
        return observacoes;
    }
    public void setObservacoes(String observacoes)
    {
        this.observacoes = observacoes;
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

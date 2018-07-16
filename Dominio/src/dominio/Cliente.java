package dominio;

import java.util.Date;
import java.util.List;

public class Cliente extends EntidadeDominio
{
    private boolean status;
    private List<Endereco> enderecos;
    private List<Cartao> cartoes;
    private Usuario usuario;
    private Genero genero;
    private String nome;
    private Date dtNascimento;
    private String CPF;
    private List<Telefone> telefones;
    private int ranking;
    private List<Cupom> cupons;
    
    public Cliente(){}
    
    public Cliente(int id)
    {
	this.setId(id);
    }
    
    public boolean isStatus()
    {
        return status;
    }
    public void setStatus(boolean status)
    {
        this.status = status;
    }
    public List<Endereco> getEnderecos()
    {
        return enderecos;
    }
    public void setEnderecos(List<Endereco> enderecos)
    {
        this.enderecos = enderecos;
    }
    public List<Cartao> getCartoes()
    {
        return cartoes;
    }
    public void setCartoes(List<Cartao> cartoes)
    {
        this.cartoes = cartoes;
    }
    public Usuario getUsuario()
    {
        return usuario;
    }
    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }
    public Genero getGenero()
    {
        return genero;
    }
    public void setGenero(Genero genero)
    {
        this.genero = genero;
    }
    public String getNome()
    {
        return nome;
    }
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    public Date getDtNascimento()
    {
        return dtNascimento;
    }
    public void setDtNascimento(Date dtNascimento)
    {
        this.dtNascimento = dtNascimento;
    }
    public String getCPF()
    {
        return CPF;
    }
    public void setCPF(String cPF)
    {
        CPF = cPF;
    }
    public List<Telefone> getTelefones()
    {
        return telefones;
    }
    public void setTelefones(List<Telefone> telefone)
    {
        this.telefones = telefone;
    }
    public int getRanking()
    {
        return ranking;
    }
    public void setRanking(int ranking)
    {
        this.ranking = ranking;
    }
    
    public void addTelefone(Telefone telefone)
    {
	this.telefones.add(telefone);
    }
    public void addCartao(Cartao cartao)
    {
	this.cartoes.add(cartao);
    }
    public void addEndereco(Endereco endereco)
    {
	this.enderecos.add(endereco);
    }
    public List<Cupom> getCupons()
    {
        return cupons;
    }
    public void setCupons(List<Cupom> cupons)
    {
        this.cupons = cupons;
    }
}

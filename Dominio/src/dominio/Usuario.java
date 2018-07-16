package dominio;

public class Usuario extends EntidadeDominio
{
    private String email;
    private String senha;
    private String confirmacaoSenha;
    private TipoUsuario tipo;
    
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getSenha()
    {
        return senha;
    }
    public void setSenha(String senha)
    {
        this.senha = senha;
    }
    public String getConfirmacaoSenha()
    {
        return confirmacaoSenha;
    }
    public void setConfirmacaoSenha(String confirmacaoSenha)
    {
        this.confirmacaoSenha = confirmacaoSenha;
    }
    public TipoUsuario getTipo()
    {
        return tipo;
    }
    public void setTipo(TipoUsuario tipo)
    {
        this.tipo = tipo;
    }  
}

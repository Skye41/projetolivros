package dominio;

public class Dimensoes extends EntidadeDominio
{
    private double altura; 
    private double largura; 
    private double peso; 
    private double profundidade;
    
    public double getAltura()
    {
        return altura;
    }
    public void setAltura(double altura)
    {
        this.altura = altura;
    }
    public double getLargura()
    {
        return largura;
    }
    public void setLargura(double largura)
    {
        this.largura = largura;
    }
    public double getPeso()
    {
        return peso;
    }
    public void setPeso(double peso)
    {
        this.peso = peso;
    }
    public double getProfundidade()
    {
        return profundidade;
    }
    public void setProfundidade(double profundidade)
    {
        this.profundidade = profundidade;
    }
}

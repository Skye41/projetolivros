package core.impl.negocio;

import core.IStrategy;
import dominio.EntidadeDominio;
import dominio.Livro;

public class ValidarDadosLivro implements IStrategy
{
    @Override
    public String processar(EntidadeDominio entidade)
    {
        if(entidade instanceof Livro)
        {
            Livro livro = (Livro)entidade;
            String msg = "";
            if(livro.getAutores() == null || livro.getAutores().isEmpty())
                msg += "O livro deve conter no mínimo um autor. ";
            if(livro.getCategorias() == null || livro.getCategorias().isEmpty())
                msg += "O livro deve conter no mínimo uma categoria. ";
            if(livro.getAno() == 0)
                msg += "O livro deve conter um ano de lançamento válido. ";
            if(livro.getTitulo() == null || livro.getTitulo().trim().equals(""))
                msg += "O livro deve conter um titulo válido. ";
            if(livro.getEditora() == null)
                msg += "O livro deve conter uma editora. ";
            if(livro.getEdicao() == 0)
                msg += "O livro deve conter uma edição válida. ";
            if(livro.getIsbn() == null || livro.getIsbn().trim().equals(""))
                msg += "O livro deve conter um ISBN válido. ";
            if(livro.getCodigoBarras() == null || livro.getCodigoBarras().trim().equals(""))
                msg += "O livro deve conter um código de barras válido. ";
            if(livro.getPaginas() == 0)
                msg += "O livro deve conter uma quantidade de páginas válida. ";
            if(livro.getSinopse() == null || livro.getSinopse().trim().equals(""))
                msg += "O livro deve conter uma sinopse válida. ";
            if(livro.getSinopse().length() > 500)
                msg += "A sinopse do livro não pode conter mais de 500 caracteres. ";
            if(livro.getGrupo() == null)
                msg += "O livro deve conter um grupo de precificação válido. ";
            if(livro.getDimensoes() == null || livro.getDimensoes().getAltura() == 0)
                msg += "O livro deve conter uma altura válida. ";
            if(livro.getDimensoes() == null || livro.getDimensoes().getLargura()== 0)
                msg += "O livro deve conter uma largura válida. ";
            if(livro.getDimensoes() == null || livro.getDimensoes().getPeso()== 0)
                msg += "O livro deve conter um peso válido. ";
            if(livro.getDimensoes() == null || livro.getDimensoes().getProfundidade()== 0)
                msg += "O livro deve conter uma profundidade válida. ";
            if(msg.trim().length() > 0)
                return msg;
            else
                return null;
        }
        else
	    return "Esta entidade não é um livro.";
    }
}

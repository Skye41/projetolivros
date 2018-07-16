package controle.web.command.impl;

import core.aplicacao.Resultado;
import dominio.EntidadeDominio;

public class VisualizarCommand extends AbstractCommand
{  
    @Override
    public Resultado execute(EntidadeDominio entidade)
    {
	return fachada.visualizar(entidade);
    }   
}

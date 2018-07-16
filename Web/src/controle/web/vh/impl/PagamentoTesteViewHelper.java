package controle.web.vh.impl;

import controle.web.vh.IViewHelper;
import core.aplicacao.Resultado;
import dominio.EntidadeDominio;
import dominio.PagamentoTeste;
import dominio.Pedido;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PagamentoTesteViewHelper implements IViewHelper
{

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request)
    {
        PagamentoTeste pt = new PagamentoTeste();
        Resultado resultado = (Resultado) request.getSession().getAttribute("pedidos");
        String txtCodigo = request.getParameter("txtIdPedido");
        int id = 0;

        if(txtCodigo != null && !txtCodigo.trim().equals(""))
        {
            id = Integer.valueOf(txtCodigo);
        }
        for(EntidadeDominio e : resultado.getEntidades())
        {
            if(e.getId() == id)
            {
                pt.setPedido((Pedido)e);
            }
        }
        return pt;
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        if(resultado.getMsg() != null)
            request.setAttribute("resultado", resultado);
        RequestDispatcher d = request.getRequestDispatcher("/ConsultarPedidos?operacao=CONSULTAR");
        d.forward(request,response);
    }
    
}

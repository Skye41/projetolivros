package controle.web.vh.impl;

import controle.web.vh.IViewHelper;
import core.aplicacao.Resultado;
import dominio.Analise;
import dominio.EntidadeDominio;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnaliseViewHelper implements IViewHelper
{

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request)
    {
        String dtInicio = request.getParameter("txtDataInicio");
        String dtFinal = request.getParameter("txtDataFinal");
        Analise a = new Analise();
        if(dtInicio != null && !dtInicio.trim().equals(""))
        {
            DateFormat f = DateFormat.getDateInstance();
            try
            {
                a.setDtInicio(f.parse(dtInicio));
            }
            catch(ParseException e)
            {
                a.setDtInicio(null);
            }
        }
        if(dtFinal != null && !dtFinal.trim().equals(""))
        {
            DateFormat f = DateFormat.getDateInstance();
            try
            {
                a.setDtFinal(f.parse(dtFinal));
            }
            catch(ParseException e)
            {
                a.setDtFinal(null);
            }
        }
        return a;
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        if(resultado.getMsg() != null)
            request.getSession().setAttribute("resultado", resultado);
        else
        {
            Analise a = (Analise)resultado.getEntidades().get(0);
            request.setAttribute("analise", a);
        }
        RequestDispatcher d = request.getRequestDispatcher("AdminDashboard.jsp");
        d.forward(request,response);
    }
    
}

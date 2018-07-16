<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="dominio.Pedido"%>
<%@page import="dominio.CartaoPedido"%>
<%@page import="dominio.Cartao"%>
<%@page import="dominio.Usuario"%>
<%@page import="dominio.Livro"%>
<%@page import="dominio.CategoriaLivro"%>
<%@page import="dominio.EntidadeDominio"%>
<%@page import="core.aplicacao.Resultado"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Insert title here</title>
    </head>
    <body>
        <%
            DateFormat df = DateFormat.getDateInstance();
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            out.print(df.format(cal.getTime()));
            out.print("<br>");
            cal.add(Calendar.MONTH, 1);
            out.print(df.format(cal.getTime()));
        %>
        <br>
        <input type="text" id="yourText" disabled value="<%out.print(request.getParameter("id"));%>"/>
        <input type="checkbox" id="yourBox" />
    </body>
</html>
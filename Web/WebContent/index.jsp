<%@page import="core.aplicacao.Resultado"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <!-- Bootstrap -->
        <link href="lib/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/extra.css" rel="stylesheet">

        <title>Cadastro de Livros</title>
    </head>
    <body>
        <div>
            <%@include file = "header.jsp"%>
        </div>

        <!-- container-externo -->
        <div class="container-fluid">
            <div class="row">
                <ol class="breadcrumb">
                    <li class="active">Inicio</li>
                </ol>
            </div>

            <div class="row">
                <div class="alert alert-danger" role="alert">
                    <%Resultado resultado = (Resultado) session.getAttribute("resultado");
                                    if(resultado != null)
                                    {
                                        out.print(resultado.getMsg());
                                    }%></div>
            </div>
        </div>

        <!-- jQuery (obrigatório para plugins JavaScript do Bootstrap) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <!-- Inclui todos os plugins compilados (abaixo), ou inclua arquivos separadados se necessário -->
        <script src="lib/Bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
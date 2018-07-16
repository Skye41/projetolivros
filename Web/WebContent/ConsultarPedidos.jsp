<%@page import="dominio.StatusPedido"%>
<%@page import="dominio.Pedido"%>
<%@page import="java.text.DateFormat"%>
<%@page import="dominio.EntidadeDominio"%>
<%@page import="core.aplicacao.Resultado"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <!-- Bootstrap -->
        <link href="lib/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/extra.css" rel="stylesheet">

        <title>Cadastro de Clientes</title>
    </head>
    <body>
        <div>
            <%@include file = "header.jsp"%>
        </div>

        <!-- container-externo -->
        <div class="container-fluid">
            <div class="row">
                <ol class="breadcrumb">
                    <li><a href="index.jsp">Início</a></li>
                    <li><a href="#">Vendas</a></li>
                    <li class="active">Consultar Pedidos</li>
                </ol>
            </div>

            <%Resultado resultado = (Resultado) session.getAttribute("pedidos");
            if (resultado != null && resultado.getMsg() != null)
            {%>

            <div class="row">
                <div class="col-sm-12">
                    <div class="alert alert-<%
                    if (resultado.getMsg().contains("sucesso!"))
                    {
                        out.print("success");
                    }
                    else
                    {
                        out.print("danger");
                    }%> alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <%out.print(resultado.getMsg()); %>
                    </div>
                </div>
            </div>
            <%} %>

            <div class="row">
                <div class="col-sm-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Consulta de Pedidos</h3>
                        </div> <!--panel-heading-->

                        <div class="panel-body">
                            <form action="${pageContext.request.contextPath}/ConsultarPedidos" method="post" class="form">
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <input type="text" class="form-control" placeholder="Pesquisar...">
                                        <span class="input-group-btn">
                                            <button type="submit" class="form-control btn btn-default" name="operacao" value="CONSULTAR">
                                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                                            </button>
                                        </span>
                                    </div>
                                </div>
                            </form>
                        </div> <!-- panel-body -->
                        <%if (resultado != null && resultado.getEntidades() != null)
                        {%>
                        <table class="table table-striped table-hover">
                            <thead>
                            <th>Codigo do Pedido</th>
                            <th>Nome do Cliente</th>
                            <th>Data do Pedido</th>
                            <th>Status do Pedido</th>
                            <th></th>
                            <th></th>
                            </thead>
                            
                            <%
                            for (EntidadeDominio e : resultado.getEntidades())
                            {
                                Pedido p = (Pedido) e; %>
                            <tr>
                                <td>
                                    <% out.print(p.getId()); %> 
                                </td>
                                <td>
                                    <% out.print(p.getCliente().getNome()); %>
                                </td>
                                <td>
                                    <% out.print(DateFormat.getDateInstance().format(p.getDtPedido())); %>
                                </td>
                                <td>
                                    <% out.print(p.getStatus().getValor()); %>
                                </td>
                                <td>
                                    <a class="btn btn-primary btn-xs" 
                                       href="<% out.print("ConsultarPedidos?txtIdPedido=" + p.getId() + "&operacao=VISUALIZAR");%>">
                                        <span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>
                                        Visualizar
                                    </a>
                                </td>
                                <td>
                                    <%if(p.getStatus().equals(StatusPedido.PROCESSAMENTO))
                                    {%>
                                    <a class="btn btn-warning btn-xs"
                                       href="<% out.print("ValidarPagamento?txtIdPedido=" + p.getId() + "&operacao=CONSULTAR"); %>">
                                        <span class="glyphicon glyphicon-credit-card" aria-hidden="true"></span>
                                        Confirmar pagamento
                                    </a>
                                    <%}%>
                                </td>
                            </tr>
                            <%}%>
                        </table>
                        <%}%>
                    </div> <!-- panel -->
                </div>
            </div>
        </div> <!-- container externo -->

        <!-- jQuery (obrigatório para plugins JavaScript do Bootstrap) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <!-- Inclui todos os plugins compilados (abaixo), ou inclua arquivos separadados se necessário -->
        <script src="lib/Bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
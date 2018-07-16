<%@page import="dominio.StatusPedido"%>
<%@page import="dominio.Livro"%>
<%@page import="dominio.ItemPedido"%>
<%@page import="java.text.NumberFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="dominio.Pedido"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<html ng-app="pedido">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <!-- Bootstrap -->
        <link href="lib/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/extra.css" rel="stylesheet">
        
        <!-- Angular-->
        <script src="lib/Angular/angular.js"></script>
        <script src="lib/Angular/angular-locale_pt-br.js"></script>
        <script src="lib/Angular/angular-messages.js"></script>
        <script src="js/pedidoCtrl.js"></script>

        <title>Histórico de Compras</title>
    </head>

    <body ng-controller="pedidoCtrl">
        <%@include file="header.jsp"%>

        <!-- container-externo -->
        <div class="container-fluid">
            <div class="row">
                <ol class="breadcrumb">
                    <li><a href="index.jsp">Início</a></li>
                    <li><a href="#">Vendas</a></li>
                    <li><a href="#">Consultar Pedidos</a></li>
                    <li class="active">Detalhes do Pedido</li>
                </ol>
            </div>

            <div class="row">
                <div class="col-sm-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Detalhes do Pedido</h3>
                        </div> <!--panel-heading-->
                        <%Pedido p = (Pedido) request.getAttribute("pedido");
                        NumberFormat nf = NumberFormat.getCurrencyInstance();%>
                        <form action="${pageContext.request.contextPath}/SalvarPedido?txtIdPedido=<%out.print(p.getId());%>" method="post" class="form">
                        <div class="panel-body">
                            <div class="container-fluid">
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label for="txtIdPedido" class="control-label col-sm-4">Código:</label>
                                        <div class="col-sm-3">
                                            <p class="form-control-static" name="txtIdPedido" id="txtIdPedido">
                                                <% out.print(p.getId()); %>
                                            </p>
                                        </div>
                                    </div>
                                        
                                    <div class="form-group">
                                        <label for="txtDtCompra" class="control-label col-sm-4">Data do Pedido:</label>
                                        <div class="col-sm-3">
                                            <p class="form-control-static" name="txtDtCompra" id="txtDtCompra">
                                                <% out.print(DateFormat.getDateInstance().format(p.getDtPedido())); %>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="cbStatus" class="control-label col-sm-4">Status do Pedido:</label>
                                        <div class="col-sm-3">
                                            <select id="cbStatus" name="cbStatus" class="form-control col-sm-2"
                                                ng-model="cbStatus" ng-init="cbStatus='<%out.print(p.getStatus().name());%>'">
                                                <%
                                                for(StatusPedido st : StatusPedido.values())
                                                {
                                                %>
                                                <option value="<%out.print(st.name());
                                                    if(st.equals(p.getStatus()))
                                                        out.print("\" selected=\"selected");%>">
                                                    <%out.print(st.getValor());%>
                                                </option>
                                                <%}%>
                                            </select>
                                        </div>
                                        
                                        <%if(p.getStatus().equals(StatusPedido.PROCESSAMENTO))
                                        { %>
                                        <a class="btn btn-warning"
                                           href="<% out.print("ValidarPagamento?txtCodigo=" + p.getId() + "&operacao=CONSULTAR"); %>">
                                            <span class="glyphicon glyphicon-credit-card" aria-hidden="true"></span>
                                            CONSULTAR PAGAMENTO
                                        </a>
                                        <%}%>
                                    </div>
                                    <div class="form-group" ng-show="cbStatus=='TROCADO'">
                                        <div class="col-sm-4"></div>
                                        <div class="col-sm-3">
                                            <label for="chEstoque" class="control-label">
                                                <input type="checkbox" id="chEstoque" name="chEstoque">
                                                Devolver itens ao estoque
                                            </label>
                                        </div>
                                    </div>
                                </div> <!--/form-horizontal-->
                            </div> <!--/container-->
                        </div> <!--/panel-body-->                                        

                        <table class="table table-striped table-hover">
                            <thead>
                            <th>Codigo</th>
                            <th>Titulo</th>
                            <th>Valor Unit.</th>
                            <th>Qtde</th>
                            <th>Valor Total</th>
                            <th></th>
                            </thead>
                            <%
                                for(EntidadeDominio e : p.getItens())
                                {
                                    ItemPedido i = (ItemPedido) e;
                                    Livro l = i.getLivro();
                            %>
                            <tr>
                                <td class="col-sm-1">
                                    <%out.print(l.getId());%>
                                </td>
                                <td class="col-sm-6">
                                    <%out.print(l.getTitulo());%>
                                </td>
                                <td class="col-sm-1">
                                    <%out.print(nf.format(i.getValorItem())); %>
                                </td>
                                <td class="col-sm-1">
                                    <%out.print(i.getQtdeItem());%>
                                </td>
                                <td class="col-sm-2">
                                    <%out.print(nf.format(i.getValorItem() * i.getQtdeItem())); %>
                                </td>
                                <td class="col-sm-1">
                                </td>
                            </tr>
                            <%
                                }
                            %>
                            <tr>
                                <td></td>
                                <td colspan="3">Frete</td>
                                <td>
                                    <%out.print(nf.format(p.getValorFrete())); %>
                                </td>
                                <td>
                                </td>
                            </tr>
                            <tr class="success">
                                <td></td>
                                <td colspan="3">TOTAL</td>
                                <td><%out.print(nf.format(p.getTotalPedido())); %></td>
                                <td></td>
                            </tr>
                        </table>
                        <div class="panel-body">
                            <div class="form-inline">
                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary" id="operacao" name="operacao" value="ALTERAR">
                                        <span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
                                        ALTERAR STATUS
                                    </button>
                                </div>
                                <div class="form-group">
                                    <button type="reset" class="btn btn-default">
                                        <span class="glyphicon glyphicon-erase" aria-hidden="true"></span>
                                        REINICIAR
                                    </button>
                                </div>
                            </div>
                        </div>
                        </form>
                    </div> <!--/.panel-->
                </div>
            </div>
        </div> <!-- /.container-externo -->				

        <!-- jQuery (obrigatório para plugins JavaScript do Bootstrap) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <!-- Inclui todos os plugins compilados (abaixo), ou inclua arquivos separadados se necessário -->
        <script src="lib/Bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
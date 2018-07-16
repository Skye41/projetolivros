<%@page import="dominio.StatusPedido"%>
<%@page import="dominio.Livro"%>
<%@page import="dominio.ItemPedido"%>
<%@page import="java.text.NumberFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="dominio.Pedido"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <!-- Bootstrap -->
        <link href="lib/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/extra.css" rel="stylesheet">

        <title>Histórico de Compras</title>
    </head>

    <body>
        <%@include file="header.jsp"%>

        <!-- container-externo -->
        <div class="container-fluid">
            <div class="row">
                <ol class="breadcrumb">
                    <li><a href="index.jsp">Início</a></li>
                    <li><a href="ConsultarPedidos?operacao=CONSULTAR">Meus Pedidos</a></li>
                    <li class="active">Detalhes da Compra</li>
                </ol>
            </div>

            <div class="row">
                <div class="col-sm-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Detalhes da Compra</h3>
                        </div> <!--panel-heading-->
                        <%Pedido p = (Pedido) request.getAttribute("pedido");
                        NumberFormat nf = NumberFormat.getCurrencyInstance();
                        double total = 0;%>
                        <form action="${pageContext.request.contextPath}/Troca" method="post" class="form">
                        <div class="panel-body">
                            <div class="container-fluid">
                                <div class="form-horizontal">
                                    <input type="hidden" id="txtIdPedido" name="txtIdPedido" value="<%out.print(p.getId());%>">
                                    <div class="form-group">
                                        <label for="txtIdPedido" class="control-label col-sm-4">Código:</label>
                                        <p class="form-control-static" name="txtIdPedido" id="txtIdPedido">
                                            <% out.print(p.getId()); %>
                                        </p>
                                    </div>
                                        
                                    <div class="form-group">
                                        <label for="txtDtCompra" class="control-label col-sm-4">Data da Compra:</label>
                                        <p class="form-control-static" name="txtDtCompra" id="txtDtCompra">
                                            <% out.print(DateFormat.getDateInstance().format(p.getDtPedido())); %>
                                        </p>
                                    </div>

                                    <div class="form-group">
                                        <label for="txtStatusCompra" class="control-label col-sm-4">Status da Compra:</label>
                                        <p class="form-control-static" name="txtStatusCompra" id="txtStatusCompra">
                                            <% out.print(p.getStatus().getValor()); %>
                                        </p>
                                    </div>
                                </div> <!--/form-horizontal-->
                            </div> <!--/container-->
                        </div> <!--/panel-body-->                                        

                        <table class="table table-striped table-hover">
                            <thead>
                                <th>
                                <%if(p.getStatus().equals(StatusPedido.ENTREGUE))
                                {%>    
                                    <input type="checkbox" id="chTodos" name="chTodos" class="checkbox" onclick="selecionarTudo(this.checked)">
                                <%}%>
                                </th>
                                <th>Codigo</th>
                                <th>Titulo</th>
                                <th>Valor Unit.</th>
                                <th>Qtde</th>
                                <th>Valor Total</th>
                            </thead>
                            <%
                                for(EntidadeDominio e : p.getItens())
                                {
                                    ItemPedido i = (ItemPedido) e;
                                    Livro l = i.getLivro();
                            %>
                            <tr>
                                <td class="col-sm-1">
                                    <%if(p.getStatus().equals(StatusPedido.ENTREGUE))
                                    {%>
                                    <input type="checkbox" id="chIdItem" name="chIdItem" class="checkbox" 
                                           value="<%out.print(i.getId());%>" onclick="verificarItens()">
                                    <%}%>
                                </td>
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
                            </tr>
                            <%
                                    total += i.getValorItem() * i.getQtdeItem();
                                }
                            %>
                            <tr>
                                <td></td>
                                <td></td>
                                <td colspan="3">Frete</td>
                                <td>
                                    <%out.print(nf.format(p.getValorFrete())); %>
                                </td>
                            </tr>
                            <tr class="success">
                                <td></td>
                                <td></td>
                                <td colspan="3">TOTAL</td>
                                <td><%out.print(nf.format(total + p.getValorFrete())); %></td>
                            </tr>
                        </table>
                        <div class="panel-body">
                            <%if(p.getStatus().equals(StatusPedido.ENTREGUE))
                            { %>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary" id="operacao" name="operacao" value="SALVAR">
                                    <span class="glyphicon glyphicon-transfer" aria-hidden="true"></span>
                                    SOLICITAR TROCA
                                </button>
                            </div>
                            <%}%>
                        </div>
                        </form>
                    </div> <!--/panel-->
                </div>
            </div>
        </div> <!-- container-externo -->				

        <script type="text/javascript">
            function testebotao()
            {
                alert(document.getElementById("operacao").value);
            }
            function verificarItens()
            {
                var check = document.getElementById("chTodos");
                var boxes = document.getElementsByName("chIdItem");
                var full = true;
                for (i = 0; i < boxes.length; i++) 
                {
                    if(boxes[i].checked == false)
                    {
                        full = false;
                        break;
                    }
                }
                if(full == true)
                {
                    check.checked = true;
                    document.getElementById("operacao").value = "ALTERAR"
                }
                else
                {
                    check.checked = false;
                    document.getElementById("operacao").value = "SALVAR"
                }
            }
    
            function selecionarTudo(retorno) 
            {
                var boxes = document.getElementsByName("chIdItem");
                for (i = 0; i < boxes.length; i++) 
                {
                    if (retorno == true)
                    {
                        boxes[i].checked = true;
                        document.getElementById("operacao").value = "ALTERAR"
                    }
                    else
                    {
                        boxes[i].checked = false;
                        document.getElementById("operacao").value = "SALVAR"
                    }
                }
            }
        </script>
        
        <!-- jQuery (obrigatório para plugins JavaScript do Bootstrap) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <!-- Inclui todos os plugins compilados (abaixo), ou inclua arquivos separadados se necessário -->
        <script src="lib/Bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
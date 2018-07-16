<%@page import="dominio.TipoCupom"%>
<%@page import="dominio.Cupom"%>
<%@page import="dominio.Livro"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="dominio.ItemPedido"%>
<%@page import="dominio.Pedido"%>
<%@page import="dominio.Cartao"%>
<%@page import="dominio.Endereco"%>
<%@page import="dominio.Cliente"%>
<%@page import="dominio.EntidadeDominio"%>
<%@page import="core.aplicacao.Resultado"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

        <title>Cadastro de Cliente</title>
    </head>
    <body ng-controller="pedidoCtrl">
        <%@include file="header.jsp"%>

        <!-- container-externo -->
        <div class="container-fluid">
            <div class="row">
                <ol class="breadcrumb">
                    <li><a href="index.jsp">Início</a></li>
                    <li><a href="#">Vendas</a></li>
                    <li><a href="#">Carrinho de Compras</a></li>
                    <li class="active">Finalizar Compra</li>
                </ol>
            </div>

            <%Resultado resultado = (Resultado) session.getAttribute("resultado");
            Pedido pedido = (Pedido) session.getAttribute("pedido");
            Cliente c = (Cliente) session.getAttribute("usuario");
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            
            if(pedido.getValorFrete() == 0)
            {
                resultado.setMsg("É necessário calcular o frete antes de finalizar a compra!");
            }

            if(resultado != null && resultado.getMsg() != null)
            {%>
            <div class="row">
                <div class="col-sm-12">
                    <div class="alert alert-<%
                    if(resultado.getMsg().endsWith("sucesso!"))
                        out.print("success");
                    else
                        out.print("danger");%> alert-dismissible" role="alert">
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
                            <h3 class="panel-title">Finalizar Compra</h3>
                        </div> <!--panel-heading-->

                        <form action="${pageContext.request.contextPath}/SalvarPedido" method="post" class="form">
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                        <label for="cbEntrega" class="control-label">
                                            Endereço de Entrega:
                                            <a class="btn btn btn-success btn-xs" 
                                               href="<%out.print("SalvarEndereco.jsp?txtIdCliente=" + c.getId());%>">
                                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                                CADASTRAR NOVO ENDEREÇO
                                            </a>
                                        </label>
                                        
                                        <div class="input-group">
                                            
                                            <select id="cbEntrega" name="cbEntrega" class="form-control">
                                            <%
                                            if(c.getEnderecos() != null)
                                            {
                                                for(Endereco e : c.getEnderecos())
                                                {
                                            %>
                                                <option value="<%out.print(e.getId());%>"<%
                                                if(pedido.getEnderecoEntrega() != null && pedido.getEnderecoEntrega().getId() == e.getId()) 
                                                    out.print(" selected=\"selected\"");
                                                else if(pedido.getEnderecoEntrega() == null && e.isPreferencial())
                                                    out.print(" selected=\"selected\"");%>>
                                                    <%out.print(e.getTipoLogradouro() + " " + e.getLogradouro() + ", " + e.getNumero());%>
                                                </option>
                                            <%  }
                                            }  %>
                                            </select>
                                            <span class="input-group-btn">
                                                <button type="submit" name="operacao" value="ALTERAR" class="btn btn-success">
                                                    <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
                                                    Calcular Frete
                                                </button>
                                            </span>
                                            
                                        </div><!-- /input-group -->
                                    </div>
                                </div> <!-- /.row -->

                                <div ng-repeat="ct in cartao">
                                    <div class="row">
                                        <div class="form-group col-sm-4">
                                            <label for="cbCartao" class="control-label">
                                                Cartão:
                                                <a class="btn btn btn-success btn-xs" ng-show="$first"
                                                   href="<%out.print("SalvarCartao?txtIdCliente=" + c.getId() + "&operacao=CONSULTAR");%>">
                                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                                    CADASTRAR NOVO CARTÃO
                                                </a>
                                            </label>

                                            <div class="input-group">
                                                <select id="cbCartao" name="cbCartao" class="form-control">
                                                <%
                                                if(c.getCartoes() != null)
                                                {
                                                    for(Cartao ct : c.getCartoes())
                                                    {
                                                %>
                                                    <option value="<%out.print(ct.getId());%>"<%
                                                    if(ct.isPreferencial()) 
                                                        out.print(" selected=\"selected\"");%>>
                                                        <%out.print(ct.getBandeira().getNomeBandeira() + " *****" + ct.getNumero().substring(ct.getNumero().length() - 4));%>
                                                    </option>
                                                <%  }
                                                }  %>
                                                </select>
                                                <span class="input-group-btn">
                                                    <button type="button" class="form-control btn btn-danger" 
                                                            ng-click="deleteCartao()">
                                                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                                    </button>
                                                </span>
                                            </div><!-- /input-group -->
                                        </div>

                                        <div class="form-group col-sm-2">
                                            <label for="txtValor" class="control-label">
                                                Valor:
                                            </label>

                                            <div class="input-group">
                                                <span class="input-group-addon" id="reais">R$</span>
                                                <input type="number" id="txtValor" name="txtValor" class="form-control" 
                                                       placeholder="00,00" step="0.01" min=10 aria-describedby="reais" required>
                                                <span class="input-group-btn">
                                                    <button type="button" class="form-control btn btn-danger" 
                                                            ng-click="deleteCartao()">
                                                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                                    </button>
                                                </span>
                                            </div><!-- /input-group -->
                                        </div>
                                    </div> <!-- /row -->
                                </div> <!-- /ng-repeat -->

                                <div class="row">
                                    <div class="form-group col-sm-2">
                                        <button type="button" class="btn btn-success form-control" 
                                                ng-click="addCartao()">
                                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                            Adicionar Cartão
                                        </button>
                                    </div>
                                </div> <!-- /row -->

                                <div class="row">
                                    <div class="form-group col-sm-3">
                                        <label for="txtCupomPromo" class="control-label">Cupom Promocional:</label>
                                        <div class="input-group">
                                            <span class="input-group-addon">
                                                <input type="checkbox" id="chPromo" <%
                                                    if(c.getCupons() == null || c.getCupons().isEmpty()) 
                                                        out.print("disabled"); %>>
                                            </span>
                                            <select id="txtCupomPromo" name="txtCupomPromo" class="form-control" disabled>
                                                <%
                                                if(c.getCupons() != null)
                                                {
                                                    for(Cupom cp : c.getCupons())
                                                    {
                                                        if(cp.getTipoCupom().equals(TipoCupom.PROMOCIONAL))
                                                        {
                                                %>
                                                    <option value="<%out.print(cp.getId());%>"<%
                                                    //if(livro != null && livro.getId() == ed.getId()) 
                                                        out.print(" selected=\"selected\"");%>>
                                                        <%out.print(cp.getTipoCupom().getValor() + " - " + nf.format(cp.getValorCupom()));%>
                                                    </option>
                                                <%      }
                                                    }
                                                }  %>
                                            </select>
                                        </div><!-- /input-group -->
                                    </div>

                                    <div class="form-group col-sm-3">
                                        <label for="txtCupomTroca" class="control-label">Cupom Troca:</label>
                                        <div class="input-group">
                                            <span class="input-group-addon">
                                                <input type="checkbox" id="chTroca" <%
                                                    if(c.getCupons() == null || c.getCupons().isEmpty()) 
                                                        out.print("disabled"); %>>
                                            </span>
                                            <select id="txtCupomTroca" name="txtCupomTroca" class="form-control" disabled>
                                                <%
                                                if(c.getCupons() != null)
                                                {
                                                    for(Cupom cp : c.getCupons())
                                                    {
                                                        if(cp.getTipoCupom().equals(TipoCupom.TROCA))
                                                        {
                                                %>
                                                    <option value="<%out.print(cp.getId());%>"<%
                                                    //if(livro != null && livro.getId() == ed.getId()) 
                                                        out.print(" selected=\"selected\"");%>>
                                                        <%out.print(cp.getTipoCupom().getValor() + " - " + nf.format(cp.getValorCupom()));%>
                                                    </option>
                                                <%      }
                                                    }
                                                }  %>
                                            </select>
                                        </div><!-- /input-group -->
                                    </div>
                                </div> <!-- /row -->
                            </div> <!-- /.panel-body -->

                            <%
                                if(pedido != null && pedido.getItens() != null)
                                {
                            %>
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
                                    for(EntidadeDominio e : pedido.getItens())
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
                                        <%out.print(nf.format(l.getPrecoVenda())); %>
                                    </td>
                                    <td class="col-sm-1">
                                        <%out.print(i.getQtdeItem());%>
                                    </td>
                                    <td class="col-sm-2">
                                        <%out.print(nf.format(l.getPrecoVenda() * i.getQtdeItem())); %>
                                    </td>
                                    <td class="col-sm-1">
                                    </td>
                                </tr>
                                <%}%>
                                <tr>
                                    <td></td>
                                    <td colspan="3">Frete</td>
                                    <td>
                                        <%out.print(nf.format(pedido.getValorFrete())); %>
                                    </td>
                                    <td>
                                    </td>
                                </tr>
                                <tr class="success">
                                    <td></td>
                                    <td colspan="3">TOTAL</td>
                                    <td><%out.print(nf.format(pedido.getTotalPedido())); %></td>
                                    <td></td>
                                </tr>
                            </table>

                            <div class="panel-body">
                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary" id="operacao" 
                                        name="operacao" value="SALVAR"<%
                                        if(pedido.getValorFrete() == 0)
                                            out.print(" disabled");%>>
                                        <span class="glyphicon glyphicon-credit-card" aria-hidden="true"></span>
                                        CONFIRMAR PEDIDO
                                    </button>
                                </div>
                                
                            </div> <!--/panel-body-->
                            <%}
                            %>
                        </form>
                    </div> <!--/panel-->
                </div>
            </div>
        </div> <!-- container-externo -->

        <script type="text/javascript">
            document.getElementById('chPromo').onchange = function ()
            {
                document.getElementById('txtCupomPromo').disabled = !this.checked;
                if(this.checked)
                {
                    document.getElementById('txtValor').min = 1;
                }
                else
                {
                    document.getElementById('txtValor').min = 10;
                }
            };
            document.getElementById('chTroca').onchange = function ()
            {
                document.getElementById('txtCupomTroca').disabled = !this.checked;
                if(this.checked)
                {
                    document.getElementById('txtValor').min = 1;
                }
                else
                {
                    document.getElementById('txtValor').min = 10;
                }
            };
        </script>

        <!-- jQuery (obrigatório para plugins JavaScript do Bootstrap) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <!-- Inclui todos os plugins compilados (abaixo), ou inclua arquivos separadados se necessÃ¡rio -->
        <script src="lib/Bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
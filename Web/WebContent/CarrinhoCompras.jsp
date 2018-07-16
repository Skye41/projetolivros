<%@page import="dominio.Carrinho"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="dominio.Livro"%>
<%@page import="dominio.ItemPedido"%>
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

	<title>Cadastro de Cliente</title>
    </head>
    
    <body>
	<%@include file="header.jsp"%>
		
	<!-- container-externo -->
	<div class="container-fluid">
            <div class="row">
		<ol class="breadcrumb">
                    <li><a href="index.jsp">Início</a></li>
                    <li><a href="#">Vendas</a></li>
                    <li class="active">Carrinho de Compras</li>
		</ol>
            </div>
			
            <%Resultado resultado = (Resultado) session.getAttribute("resultado");
            Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
            if(resultado != null && resultado.getMsg() != null)
            {
            %>
            <div class="row">
                <div class="col-sm-12">
                    <div class="alert alert-<%
                    if(resultado.getMsg().contains("sucesso")) out.print("success");
                    else out.print("danger");%> alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">						 	<span aria-hidden="true">&times;</span>
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
                            <h3 class="panel-title">Carrinho de Compras</h3>
			</div> <!--panel-heading-->
						
			<div class="panel-body">
                            
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
			<%
			if(carrinho != null && carrinho.getItens() != null)
			{
			    NumberFormat nf = NumberFormat.getCurrencyInstance();
			%>
			
                            <table class="table table-striped table-hover">
                                <thead>
                                    <th>Codigo</th>
                                    <th>Titulo</th>
                                    <th>Valor Unit.</th>
                                    <th>Qtde</th>
                                    <th>Valor Total</th>
                                    <th></th>
                                    <th></th>
				</thead>
				<%
				for(EntidadeDominio e : carrinho.getItens())
				{
                                    ItemPedido i = (ItemPedido)e;
                                    Livro l = i.getLivro(); 
				%>
				<tr>
                                    <form action="${pageContext.request.contextPath}/Carrinho" method="post" class="form">
                                    <td class="col-sm-1">
                                        <input type="hidden" id="txtIdLivro" name="txtIdLivro" value="<%out.print(l.getId());%>">
                                        <%out.print(l.getId());%>
                                    </td>
                                    <td class="col-sm-5">
					<%out.print(l.getTitulo());%>
                                    </td>
                                    <td class="col-sm-1">
					<label class="form-control-static" id="<%out.print("txtValorUnit" + l.getId());%>">
                                            <%out.print(nf.format(l.getPrecoVenda())); %>
					</label>
                                    </td>
                                    <td class="col-sm-1">
					<input type="number" class="form-control input-sm" min="1" max="<%out.print(l.getQtdeEstoque());%>"
                                        id="<%out.print("txtQtde" + l.getId());%>" name="txtQtde" value="<%out.print(i.getQtdeItem());%>">
                                    </td>
                                    <td class="col-sm-1">
                                        <label id="<%out.print("txtValorTotal" + l.getId());%>">
                                            <%out.print(nf.format(l.getPrecoVenda() * i.getQtdeItem())); %>
					</label>
                                    </td>
                                    <td class="col-sm-1">
                                        <button type="submit" class="btn btn-success btn-xs" name="operacao" value="SALVAR">
                                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                            Alterar Quantidade
                                        </button>
                                    </td>
                                    <td class="col-sm-1">
                                        <a class="btn btn-danger btn-xs"
                                        href="<% out.print("Carrinho?txtIdLivro=" + l.getId() + "&operacao=EXCLUIR"); %>">
                                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                            Excluir
					</a>
                                    </td>
                                    </form>
				</tr>
				<%}%>
							
				<tr class="success">
                                    <td colspan="4">TOTAL</td>
                                    <td><%out.print(nf.format(carrinho.getTotalPedido())); %></td>
                                    <td></td>
                                    <td></td>
				</tr>
                            </table>
						
                            <div class="panel-body">
                                <a class="btn btn-default" href="FinalizarCompra?operacao=CONSULTAR">
                                    <span class="glyphicon glyphicon-credit-card" aria-hidden="true"></span>
                                    Finalizar Compra
                                </a>
                            </div> <!--panel-body-->
			
			<%}
			%>
			<div class="panel-body">
                            <a href="ComprarLivros?operacao=CONSULTAR" class="btn btn-primary">
                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                                Continuar Comprando
                            </a>
			</div> <!--panel-body-->
                    </div> <!--panel-->
		</div>
            </div>
	</div> <!-- container-externo -->		
	
        <!-- jQuery (obrigatório para plugins JavaScript do Bootstrap) -->
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    	<!-- Inclui todos os plugins compilados (abaixo), ou inclua arquivos separadados se necessário -->
    	<script src="lib/Bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
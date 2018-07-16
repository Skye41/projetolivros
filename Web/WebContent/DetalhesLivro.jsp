<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="dominio.CategoriaLivro"%>
<%@page import="dominio.Autor"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="dominio.Livro"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<!-- Bootstrap -->
    	<link href="lib/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
    	<link href="css/extra.css" rel="stylesheet">

    	<title>Livro</title>
    </head>

	<body>
		<%@include file="header.jsp"%>
		
		<!-- container-externo -->
		<div class="container-fluid">
			<div class="row">
				<ol class="breadcrumb">
		  			<li><a href="index.jsp">Início</a></li>
		  			<li><a href="#">Vendas</a></li>
		  			<li class="active">Comprar livros</li>
				</ol>
			</div> 
			
			<%Livro l = (Livro)request.getAttribute("livro"); %>
			<div class="row">
				<div class="col-sm-12">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">Detalhes do Livro</h3>
						</div> <!--panel-heading-->
						
						<div class="panel-body">
							<div class="container-fluid">
								<form action="${pageContext.request.contextPath}/Carrinho" method="post">
									<input type="hidden" id="txtIdLivro" name="txtIdLivro" 
									value="<%out.print(l.getId());%>">
									
									<div class="form-horizontal">
									<%
									if(l != null)
									{%>
										<div class="form-group">
											<div class="page-header col-sm-12">
												<h4><%out.print(l.getTitulo()); %></h4>
											</div>
										</div>
										<div class="form-group">
											<label for="txtPreco" class="control-label col-sm-4">Preço:</label>
											<p class="form-control-static" name="txtPreco" id="txtPreco">
												<%out.print(NumberFormat.getCurrencyInstance().format(l.getPrecoVenda())); %>
											</p>
										</div>
										<div class="form-group">
											<label for="txtEstoque" class="control-label col-sm-4">Disponível:</label>
											<p class="form-control-static" name="txtEstoque" id="txtEstoque">
												<%out.print(l.getQtdeEstoque()); %>
											</p>
										</div>
										<div class="form-group col-sm-3">
											<div class="input-group">
												<input type="number" class="form-control" name="txtQtde" id="txtQtde"
												min="1" max="<%out.print(l.getQtdeEstoque()); %>" value="1">
												<span class="input-group-btn">
													<button type="submit" class="btn btn-warning" id="operacao" name="operacao" value="SALVAR">
														<span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
														Adicionar ao carrinho
													</button>
												</span>
												
											</div>
										</div>
										
										<div class="form-group">
											<div class="col-sm-12"><hr></div>	
										</div>
										
										<div class="form-group">
											<label for="txtSinopse" class="control-label col-sm-4">Sinopse:</label>
											<p class="form-control-static" name="txtSinopse" id="txtSinopse">
												<%out.print(l.getSinopse()); %>
											</p>
										</div>
										<div class="form-group">
											<label for="txtAutor" class="control-label col-sm-4">Autor:</label>
											<p class="form-control-static" name="txtAutor" id="txtAutor">
												<%
												if(l.getAutores() != null)
												{
												    String autores = "";
													for(Autor a : l.getAutores())
														autores += a.getNomeAutor() + " - ";
													out.print(autores.substring(0, autores.length() - 3));
												}
												%>
											</p>
										</div>
										<div class="form-group">
											<label for="txtEditora" class="control-label col-sm-4">Editora:</label>
											<p class="form-control-static" name="txtEditora" id="txtEditora">
												<%out.print(l.getEditora().getNomeEditora()); %>
											</p>
										</div>
										<div class="form-group">
											<label for="txtCategoria" class="control-label col-sm-4">Categoria:</label>
											<p class="form-control-static" name="txtCategoria" id="txtCategoria">
												<%
												if(l.getCategorias() != null)
												{
												    String categorias = "";
													for(CategoriaLivro c : l.getCategorias())
													    categorias += c.getNomeCategoria() + " - ";
													out.print(categorias.substring(0, categorias.length() - 3));
												}
												%>
											</p>
										</div>
										<div class="form-group">
											<label for="txtPaginas" class="control-label col-sm-4">Páginas:</label>
											<p class="form-control-static" name="txtPaginas" id="txtPaginas">
												<%out.print(l.getPaginas()); %>
											</p>
										</div>
										<div class="form-group">
											<label for="txtEdicao" class="control-label col-sm-4">Edição:</label>
											<p class="form-control-static" name="txtEdicao" id="txtEdicao">
												<%out.print(l.getEdicao()); %>ª
											</p>
										</div>
										<div class="form-group">
											<label for="txtAno" class="control-label col-sm-4">Ano:</label>
											<p class="form-control-static" name="txtAno" id="txtAno">
												<%out.print(l.getAno()); %>
											</p>
										</div>
										<div class="form-group">
											<label for="txtISBN" class="control-label col-sm-4">ISBN/Código de Barras:</label>
											<p class="form-control-static" name="txtISBN" id="txtISBN">
												<%out.print(l.getIsbn()); %>
											</p>
										</div>
										
										<div class="form-group">
											<label for="txtDimensoes" class="control-label col-sm-4">Dimensões (L x A x P):</label>
											<p class="form-control-static" name="txtDimensoes" id="txtDimensoes">
												<%
												if(l.getDimensoes() != null)
													out.print(l.getDimensoes().getLargura() + " x " + l.getDimensoes().getAltura() + " x "
													+ l.getDimensoes().getProfundidade()); 
												%>
											</p>
										</div>
										<div class="form-group">
											<label for="txtPeso" class="control-label col-sm-4">Peso:</label>
											<p class="form-control-static" name="txtPeso" id="txtPeso">
												<%
												if(l.getDimensoes() != null)
													out.print(l.getDimensoes().getPeso()); 
												%>
											</p>
										</div>
										
										<div class="form-group col-sm-3">
											<div class="input-group">
												<input type="number" class="form-control" name="txtQtde" id="txtQtde"
												min="1" max="<%out.print(l.getQtdeEstoque()); %>" value="1">
												<span class="input-group-btn">
													<button type="submit" class="btn btn-warning">
														<span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
														Adicionar ao carrinho
													</button>
												</span>
												
											</div>
										</div>
										<% }%>
									</div> <!--form-horizontal-->
								</form>
							</div> <!--container-->
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
<%@page import="dominio.CategoriaLivro"%>
<%@page import="dominio.Autor"%>
<%@page import="dominio.Livro"%>
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
		  			<li><a href="index.jsp">Início</a></li>
		  			<li><a href="#">Livros</a></li>
		  			<li class="active">Consultar Livros</li>
				</ol>
			</div>
			<%Resultado resultado = (Resultado) session.getAttribute("livros");
			if(resultado != null && resultado.getMsg() != null)
			{%>
			<div class="row">
				<div class="col-sm-12">
					<div class="alert alert-<%
					if(resultado.getMsg().endsWith("sucesso!")) out.print("success");
					else out.print("danger");%> alert-dismissible" role="alert">
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
							<h3 class="panel-title">Consulta de Livros</h3>
						</div> <!--panel-heading-->
						
						<div class="panel-body">
							<form action="${pageContext.request.contextPath}/ConsultarLivros" method="post" class="form">
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
						<table class="table table-striped table-hover">
							<thead>
								<th>Codigo</th>
								<th>Titulo</th>
								<th>Autor</th>
                                                                <th>Categoria</th>
								<th>Editora</th>
								<th></th>
								<th></th>
							</thead>
							<%if(resultado != null)
							{
							    for(EntidadeDominio e : resultado.getEntidades())
							    {
									Livro l = (Livro)e; 
							%>
							<tr>
								<td>
									<%out.print(l.getId());%>
								</td>
								<td>
									<%out.print(l.getTitulo());%>
								</td>
								<td>
									<%String autor = "";
									for(Autor a : l.getAutores())
									{
									    autor += a.getNomeAutor() + " - ";
									}
									if(autor.endsWith(" - "))
									    autor = autor.substring(0, autor.length() - 3);
									out.print(autor);%>
								</td>
                                                                <td>
                                                                    <%String cat = "";
                                                                    for(CategoriaLivro ct : l.getCategorias())
                                                                    {
                                                                        cat += ct.getNomeCategoria() + " - ";
                                                                    }
                                                                    if(cat.endsWith(" - "))
                                                                        cat = cat.substring(0, cat.length() - 3);
                                                                    out.print(cat);%>
                                                                </td>
								<td>
									<%out.print(l.getEditora().getNomeEditora());%>
								</td>
								<td>
									<a class="btn btn-primary btn-xs" 
									href="<% out.print("VisualizarLivro?txtCodigo=" + l.getId() + "&operacao=VISUALIZAR");%>">
										<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>
										Visualizar
									</a>
								</td>
								<td>
									<a class="btn btn-danger btn-xs"
									href="<% out.print("SalvarLivro?txtCodigo=" + l.getId() + "&operacao=EXCLUIR"); %>">
										<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
										Excluir
									</a>
								</td>
							</tr>
							<%	}
							} %>
						</table>
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
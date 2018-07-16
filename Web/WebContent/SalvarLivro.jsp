<%@page import="dominio.StatusLivro"%>
<%@page import="dominio.GrupoPrecificacao"%>
<%@page import="dominio.Editora"%>
<%@page import="dominio.Autor"%>
<%@page import="dominio.EntidadeDominio"%>
<%@page import="dominio.Livro"%>
<%@page import="dominio.CategoriaLivro"%>
<%@page import="core.aplicacao.Resultado"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="cadastroLivros">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<!-- Bootstrap -->
    	<link href="lib/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
    	<link href="css/extra.css" rel="stylesheet">

    	<!-- Angular-->
    	<script src="lib/Angular/angular.js"></script>
		<script src="lib/Angular/angular-locale_pt-br.js"></script>
		<script src="lib/Angular/angular-messages.js"></script>
		<script src="js/cadastroLivroCtrl.js"></script>
		
		<title>Cadastro de Livros</title>
	</head>
	<body ng-controller="cadastroLivrosCtrl">
		<%@include file="header.jsp"%>
		
		<!-- container-externo -->
		<div class="container-fluid">
			<div class="row">
				<ol class="breadcrumb">
		  			<li><a href="index.jsp">Início</a></li>
		  			<li><a href="#">Livros</a></li>
		  			<li class="active">Cadastrar Novo Livro</li>
				</ol>
			</div>

			<div class="row">
				<div class="col-sm-12">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">Cadastro de Livros</h3>
						</div> <!--panel-heading-->
						
						<div class="panel-body">
							<div class="container-fluid">
								<form action="${pageContext.request.contextPath}/SalvarLivro" method="post" class="form">
								
								<%Livro livro = (Livro)request.getAttribute("livro");
								Resultado editoras = (Resultado)request.getSession().getAttribute("editoras");
								Resultado autores = (Resultado)request.getSession().getAttribute("autores");
								Resultado categorias = (Resultado)request.getSession().getAttribute("categorias");
								Resultado grupos = (Resultado)request.getSession().getAttribute("grupos");
								Resultado status = (Resultado)request.getSession().getAttribute("status");
								
								%>
									<div class="row">
										<div class="form-group col-sm-2">
											<label for="txtCodigo" class="control-label">Código:</label>
											<input type="text" id="txtCodigo" name="txtCodigo" class="form-control" 
											readonly="true" value="<%if(livro != null) out.print(livro.getId());%>">
										</div>

										<div class="form-group col-sm-4">
											<label for="txtTitulo" class="control-label">Titulo:</label>
											<input type="text" id="txtTitulo" name="txtTitulo" class="form-control"
											value="<%if(livro != null) out.print(livro.getTitulo());%>">
										</div>

										<div class="form-group col-sm-3">
											<label for="cbEditora" class="control-label">Editora:</label>
											<select id="cbEditora" name="cbEditora" class="form-control">
											<%
											if(editoras != null)
											{
												for(EntidadeDominio e : editoras.getEntidades())
												{
													Editora ed = (Editora)e;
											%>
												<option value="<%out.print(ed.getId());%>"<%
												if(livro != null && livro.getId() == ed.getId()) 
												    out.print(" selected=\"selected\"");%>>
													<%out.print(ed.getNomeEditora());%>
												</option>
											<%	}
											} %>
											</select>
										</div>
										
										<div ng-repeat="autor in autores">
											<div class="form-group col-sm-3" >
												<label for="cbAutor" class="control-label">Autor:</label>
												<div class="input-group">
													<select id="cbAutor" name="cbAutor" class="form-control">
													<%
													if(autores != null)
													{
														for(EntidadeDominio e : autores.getEntidades())
														{
														    Autor a = (Autor)e;
													%>
														<option value="<%out.print(a.getId());%>">
															<%out.print(a.getNomeAutor());%>
														</option>
													<%	}
													} %>
													</select>
													<span class="input-group-btn">
														<button type="button" class="form-control btn btn-default" 
														ng-show="$first">
															<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
														</button>
														<button type="button" class="form-control btn btn-danger" 
														ng-click="deleteAutor()" ng-show="!$first">
															<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
														</button>
													</span>
	    										</div><!-- /input-group -->
											</div>
										</div>
									</div> <!--row-->

									<div class="row">
										<div ng-repeat="cat in categorias">
											<div class="form-group col-sm-3" >
												<label for="cbCategoria" class="control-label">Categoria:</label>
												<div class="input-group">
													<select id="cbCategoria" name="cbCategoria" class="form-control">
													<%
													if(categorias != null)
													{
														for(EntidadeDominio e : categorias.getEntidades())
														{
														 	CategoriaLivro c = (CategoriaLivro)e;
													%>
														<option value="<%out.print(c.getId());%>"<%
														if(livro != null && (livro.getCategorias().get(0).getId() == c.getId()) )
															out.print(" selected=\"selected\"");%>>
															<%out.print(c.getNomeCategoria());%>
														</option>
													<%	}
													}%>
													</select>
													<span class="input-group-btn">
														<button type="button" class="form-control btn btn-default" 
														ng-show="$first">
															<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
														</button>
														<button type="button" class="form-control btn btn-danger" 
														ng-click="deleteCategoria()" ng-show="!$first">
															<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
														</button>
													</span>
	    										</div><!-- /input-group -->
											</div>
										</div>
									</div> <!--row-->

									<div class="row">
										<div class="form-group col-sm-3">
											<button type="button" class="btn btn-success form-control" 
											ng-click="addAutor()">
												<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
												Adicionar autor
											</button>
										</div>
										
										<div class="form-group col-sm-3">
											<button type="button" class="btn btn-success form-control" 
											ng-click="addCategoria()">
												<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
												Adicionar categoria
											</button>
										</div>
									</div> <!--row--> 

									<div class="row">
										<div class="form-group col-sm-12">
											<label for="txtSinopse" class="control-label">Sinopse:</label>
											<textarea id="txtSinopse" name="txtSinopse" class="form-control"
											style="resize: none;"><%
												if(livro != null && livro.getSinopse() != null)
													out.print(livro.getSinopse());
											%></textarea>
										</div>
									</div> <!--row-->

									<div class="row">
										<div class="form-group col-sm-2">
											<label for="txtPaginas" class="control-label">Nº de páginas:</label>
											<input type="number" id="txtPaginas" name="txtPaginas" class="form-control"
											min="1" step="1" value="<%if(livro != null) out.print(livro.getPaginas());%>">
										</div>

										<div class="form-group col-sm-2">
											<label for="txtEdicao" class="control-label">Edição:</label>
											<input type="number" id="txtEdicao" name="txtEdicao" class="form-control"
											min="1" step="1" value="<%if(livro != null) out.print(livro.getEdicao());%>">
										</div>

										<div class="form-group col-sm-2">
											<label for="txtAno" class="control-label">Ano:</label>
											<input type="number" id="txtAno" name="txtAno" class="form-control" 
											min="1900" max="2099" step="1" value="<%if(livro != null) out.print(livro.getAno());%>">
										</div>

										<div class="form-group col-sm-3">
											<label for="txtISBN" class="control-label">ISBN:</label>
											<input type="text" id="txtISBN" name="txtISBN" class="form-control" onkeyup="copiaCodigo(this.id)"
											value="<%if(livro != null) out.print(livro.getIsbn());%>">
										</div>

										<div class="form-group col-sm-3">
											<label for="txtBarras" class="control-label">Código de Barras:</label>
											<input type="text" id="txtBarras" name="txtBarras" class="form-control" onkeyup="copiaCodigo(this.id)"
											value="<%if(livro != null) out.print(livro.getCodigoBarras());%>">
										</div>
									</div> <!--row--> 

									<div class="row">
										<div class="form-group col-sm-2">
											<label for="txtAltura" class="control-label">Altura:</label>
											<div class="input-group">
												<input type="number" id="txtAltura" name="txtAltura" class="form-control"
												min="0" step="0.1" aria-describedby="altura" 
												value="<%if(livro != null) out.print(livro.getDimensoes().getAltura());%>">
												<span class="input-group-addon" id="altura">cm</span>
											</div><!-- /input-group -->
										</div>

										<div class="form-group col-sm-2">
											<label for="txtLargura" class="control-label">Largura:</label>
											<div class="input-group">
												<input type="number" id="txtLargura" name="txtLargura" class="form-control"
												min="0" step="0.1" aria-describedby="largura" 
												value="<%if(livro != null) out.print(livro.getDimensoes().getLargura());%>">
												<span class="input-group-addon" id="largura">cm</span>
											</div><!-- /input-group -->
										</div>

										<div class="form-group col-sm-2">
											<label for="txtPeso" class="control-label">Peso:</label>
											<div class="input-group">
												<input type="number" id="txtPeso" name="txtPeso" class="form-control"
												min="0" step="0.1" aria-describedby="peso"
												value="<%if(livro != null) out.print(livro.getDimensoes().getPeso());%>">
												<span class="input-group-addon" id="peso">g</span>
											</div><!-- /input-group -->
										</div>

										<div class="form-group col-sm-2">
											<label for="txtProfundidade" class="control-label">Profundidade:</label>
											<div class="input-group">
												<input type="number" id="txtProfundidade" name="txtProfundidade" class="form-control"
												min="0" step="0.1" aria-describedby="prof"
												value="<%if(livro != null) out.print(livro.getDimensoes().getProfundidade());%>">
												<span class="input-group-addon" id="prof">cm</span>
											</div><!-- /input-group -->
										</div>
									</div> <!--row-->

									<div class="row">
										<div class="form-group col-sm-2">
											<label for="txtCusto" class="control-label">Preço de Custo:</label>
											<div class="input-group">
							  					<span class="input-group-addon" id="reais">R$</span>
												<input type="number" id="txtCusto" name="txtCusto" class="form-control" 
												placeholder="00,00" step="0.01" min=0 aria-describedby="reais"
												value="<%if(livro != null) out.print(livro.getPrecoCusto());%>">
											</div>
										</div>	

										<div class="form-group col-sm-3">
											<label for="cbGrupo" class="control-label">Classe de Precificação:</label>
											<select id="cbGrupo" name="cbGrupo"  class="form-control">
											<%
											if(grupos != null)
											{
											    for(EntidadeDominio e : grupos.getEntidades())
											    {
											    	GrupoPrecificacao g = (GrupoPrecificacao)e;
											%>
												<option value="<%out.print(g.getId());%>"<%
												if(livro != null && livro.getGrupo().getId() == g.getId()) 
												    out.print(" selected=\"selected\"");%>>
													<%out.print(g.getNomeGrupo() + " - " + g.getMargemLucro() + "%");%>
												</option>
											<%	} 
											}%>
											</select>
										</div>

										<div class="form-group col-sm-2">
											<label for="txtVenda" class="control-label">Preço de Venda:</label>
											<div class="input-group">
							  					<span class="input-group-addon" id="reais">R$</span>
												<input type="number" id="txtVenda" name="txtVenda" class="form-control" 
												placeholder="00,00" step="0.01" min=0 aria-describedby="reais"
												value="<%if(livro != null) out.print(livro.getPrecoVenda());%>">
											</div>
										</div>

										<div class="col-sm-1"></div>

										<div class="form-group col-sm-2">
											<label for="txtQtdeEstoque" class="control-label">Qtde em estoque:</label>
											<input type="number" id="txtQtdeEstoque" name="txtQtdeEstoque" class="form-control"
											min="0" step="1" value="<%if(livro != null) out.print(livro.getQtdeEstoque());%>">
										</div>

										<div class="form-group col-sm-2">
											<label for="txtQtdeVenda" class="control-label">Qtde mínima de vendas:</label>
											<input type="number" id="txtQtdeVenda" name="txtQtdeVenda" class="form-control"
											min="0" step="1" value="<%if(livro != null) out.print(livro.getQtdeMinimaVenda());%>">
										</div>
									</div> <!--row-->

									<div class="row">
										<div class="form-group col-sm-3">
											<label for="cbStatus" class="control-label">Status:</label>
											<select id="cbStatus" name="cbStatus" class="form-control">
												<option value="true"<%
												if(livro != null && livro.getStatusLivro().isStatusLivro())
													out.print(" selected=\"selected\"");%>>
													Ativo
												</option>
												<option value="false"<%
												if(livro != null && !livro.getStatusLivro().isStatusLivro())
													out.print(" selected=\"selected\"");%>>
													Inativo
												</option>
											</select>
										</div>

										<div class="form-group col-sm-3">
											<label for="cbJustificativa" class="control-label">Justificativa:</label>
											<select id="cbJustificativa" name="cbJustificativa" class="form-control">
											<%
											if(status != null)
											{
											    for(EntidadeDominio e : status.getEntidades())
												{
													StatusLivro s = (StatusLivro)e;
											%>
												<option value="<%out.print(s.getId());%>"<%
												if(livro != null && livro.getStatusLivro().getId() == s.getId()) 
												    out.print(" selected=\"selected\"");%>>
													<%out.print(s.getJustificativa());%>
												</option>
											<%	}
											}%>
											</select>
										</div>
									</div> <!--row-->

									<div class="row form-inline col-sm-12">
						  				<div class="form-group">
						  					<button type="submit" class="btn btn-primary" id="operacao" 
						  					name="operacao" value="<%
						  					if(livro != null)
						  					{
						  						out.print("ALTERAR");
						  					}
						  					else
						  					{
						  						out.print("SALVAR");
						  					}%>">
						  						<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
						  						SALVAR
						  					</button>
						  				</div>
						  				<div class="form-group">
						  					<button type="submit" class="btn btn-danger" id="operacao" 
						  					name="operacao" value="EXCLUIR">
						  						<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
						  						EXCLUIR
						  					</button>
						  				</div>
						  				<div class="form-group">
						  					<button type="reset" class="btn btn-default" ng-click="zerarAutor(); zerarCategoria();">
						  						<span class="glyphicon glyphicon-erase" aria-hidden="true"></span>
						  						REINICIAR
						  					</button>
						  				</div>
						  			</div> <!--row-->
								</form>
							</div> <!--container-->
						</div> <!--panel-body-->
					</div> <!--panel-->
				</div>
			</div>
		</div> <!-- container-externo -->
		
		<script type="text/javascript">
			function copiaCodigo(id)
			{
				var barras = document.getElementById("txtBarras");
				var isbn = document.getElementById("txtISBN");
				
				if(id == "txtBarras")
				{
					isbn.value = barras.value;					
				}
				if(id == "txtISBN")
				{
					barras.value = isbn.value;					
				}
				
			}
		</script>
						
		<!-- jQuery (obrigatório para plugins JavaScript do Bootstrap) -->
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    	<!-- Inclui todos os plugins compilados (abaixo), ou inclua arquivos separadados se necessário -->
    	<script src="lib/Bootstrap/js/bootstrap.min.js"></script>
	</body>
</html>
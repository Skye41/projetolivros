<%@page import="dominio.Usuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="cadastroCliente">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<!-- Bootstrap -->
    	<link href="lib/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
    	<link href="css/extra.css" rel="stylesheet">

    	<!-- Angular-->
    	<script src="lib/Angular/angular.js"></script>
		<script src="lib/Angular/angular-locale_pt-br.js"></script>
		<script src="lib/Angular/angular-messages.js"></script>
		
		<title>Cadastro de Cliente</title>
	</head>
	<body ng-controller="cadastroClienteCtrl">
		<%@include file="header.jsp"%>
		
		<!-- container-externo -->
		<div class="container-fluid">
			<div class="row">
				<ol class="breadcrumb">
		  			<li><a href="index.jsp">Início</a></li>
		  			<li><a href="#">Clientes</a></li>
		  			<li class="active">Cadastrar Novo Cliente</li>
				</ol>
			</div>

			<div class="row">
				<div class="col-sm-12">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">Cadastro de Clientes</h3>
						</div> <!--panel-heading-->
						
						<div class="panel-body">
							<div class="container-fluid">
								<form action="${pageContext.request.contextPath}/<%
								if(request.getParameter("txtIdUsuario") != null)
								    out.print("SalvarSenha");
								else
								    out.print("SalvarCliente.jsp");%>" method="post" class="form">
									<div class="row">
										<div class="page-header col-sm-12">
											<label class="control-label">Login de Usuário:</label>
										</div>	
									</div> <!--row-->
									
									<%Usuario u = (Usuario)request.getAttribute("usuario"); %>
									<div class="row">
										<input type="hidden" name="txtIdUsuario" id="txtIdUsuario" 
										value="<%if(u != null) out.print(u.getId());%>">
									</div>
									
									<div class="row">
										<div class="form-group col-sm-3">
											<label for="txtEmail" class="control-label">E-mail:</label>
											<input type="text" id="txtEmail" name="txtEmail" class="form-control"
											value="<%if(u != null) out.print(u.getEmail());%>"<%
											if(u != null) out.print(" readonly=\"true\"");%> required="true">
										</div>
									</div> <!-- row -->
									
									<div class="row">
										<div class="form-group col-sm-3">
											<label for="txtSenha" class="control-label">Senha:</label>
											<input type="password" id="txtSenha" name="txtSenha" class="form-control" required="true">
										</div>
									</div> <!-- row -->
									
									<div class="row">
										<div class="form-group col-sm-3">
											<label for="txtConfirma" class="control-label">Confirme a Senha:</label>
											<input type="password" id="txtSenha" name="txtConfirma" class="form-control" required="true">
										</div>
									</div> <!-- row -->

									<div class="row form-inline col-sm-12">
						  				<div class="form-group">
						  					<button type="submit" class="btn btn-primary" id="operacao" 
						  					name="operacao" value="<%
						  					if(u != null)
						  						out.print("ALTERAR");
						  					else
						  					  out.print("SALVAR");
						  					%>">
						  						<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
						  						SALVAR
						  					</button>
						  				</div>
						  				<div class="form-group">
						  					<button type="reset" class="btn btn-default" ng-click="zerarTelefone(); zerarCartoes();">
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
		<!-- jQuery (obrigatório para plugins JavaScript do Bootstrap) -->
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    	<!-- Inclui todos os plugins compilados (abaixo), ou inclua arquivos separadados se necessÃ¡rio -->
    	<script src="lib/Bootstrap/js/bootstrap.min.js"></script>
	</body>
</html>